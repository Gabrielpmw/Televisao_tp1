package br.unitins.tp1.service.Pedido;

import br.unitins.tp1.model.DTO.Pagamento.BoletoResponseDTO;
import br.unitins.tp1.model.DTO.Pagamento.CartaoRequestDTO;
import br.unitins.tp1.model.DTO.Pagamento.CartaoResponseDTO;
import br.unitins.tp1.model.DTO.Pagamento.PixResponseDTO;
import br.unitins.tp1.model.DTO.Pedido.ItemPedido.ItemPedidoRequestDTO;
import br.unitins.tp1.model.DTO.Pedido.PedidoRequestDTO;
import br.unitins.tp1.model.DTO.Pedido.PedidoResponseDTO;
import br.unitins.tp1.model.DTO.Pedido.PedidoupdateRequestDTO;
import br.unitins.tp1.model.Endereco.Endereco;
import br.unitins.tp1.model.Endereco.EnderecoEntrega;
import br.unitins.tp1.model.Pagamento.*;
import br.unitins.tp1.model.Pedido.ItemPedido;
import br.unitins.tp1.model.Pedido.Pedido;
import br.unitins.tp1.model.Pedido.StatusPedido;
import br.unitins.tp1.model.Televisao.Televisao;
import br.unitins.tp1.model.Usuario;
import br.unitins.tp1.repository.*;
import br.unitins.tp1.validation.ValidationException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@ApplicationScoped
public class PedidoServiceImpl implements PedidoService {

    @Inject CartaoRepository cartaoRepository;
    @Inject UsuarioRepository usuarioRepository;
    @Inject PedidoRepository pedidoRepository;
    @Inject TelevisaoRepository televisaoRepository;
    @Inject EnderecoRepository enderecoRepository;
    @Inject EnderecoEntregaRepository enderecoEntregaRepository;
    @Inject ItemPedidoRepository itemPedidoRepository;
    @Inject PagamentoRepository pagamentoRepository;
    @Inject BoletoRepository boletoRepository;
    @Inject JsonWebToken jwt;

    @Override
    @Transactional
    public PedidoResponseDTO create(PedidoRequestDTO dto) {
        String username = jwt.getSubject();
        Set<String> grupos = jwt.getGroups();

        // 1. Validações de Permissão e Usuário
        if (grupos.contains("ADM")) {
            throw new ValidationException("Permissão", "Administradores não podem criar pedidos.");
        }

        Usuario usuario = usuarioRepository.findByUsername(username);
        Endereco endereco = enderecoRepository.findById(dto.idEndereco());

        if (endereco == null) {
            throw new ValidationException("Endereço", "Endereço não encontrado.");
        }

        if (!endereco.getUsuario().getUsername().equalsIgnoreCase(username)) {
            throw new ValidationException("Endereço", "O endereço informado não pertence ao usuário autenticado.");
        }

        Pedido pedido = new Pedido();
        LocalDateTime dataPedido = LocalDateTime.now();

        // 2. Criação do Snapshot de Endereço (Lógica Importante)
        EnderecoEntrega enderecoEntrega = new EnderecoEntrega();

        // Copia dados estáticos para garantir histórico imutável
        enderecoEntrega.setCep(endereco.getCep());
        enderecoEntrega.setBairro(endereco.getBairro());
        enderecoEntrega.setNumero(endereco.getNumero());
        enderecoEntrega.setComplemento(endereco.getComplemento());
        enderecoEntrega.setMunicipio(endereco.getMunicipio()); // Relacionamento seguro com Município

        // Mantém referência histórica (opcional, mas útil)
        enderecoEntrega.setEndereco(endereco);

        // Vincula ao pedido
        enderecoEntrega.setPedido(pedido);

        pedido.setDataPedido(dataPedido);
        pedido.setStatusPedido(StatusPedido.PEDIDO_EM_PROCESSO);
        pedido.setUsuario(usuario);
        pedido.setEnderecoEntrega(enderecoEntrega);

        // ----------------------------------------------------
        // CORREÇÃO CRÍTICA AQUI: USANDO VALORES DO DTO DO FRONT
        // ----------------------------------------------------
        // Agora, o Backend salva o valor total e o frete enviados pelo Front-end
        pedido.setValorTotal(dto.valorTotal());
        pedido.setValorFrete(dto.valorFrete());
        // ----------------------------------------------------

        // 3. Processamento dos Itens
        // REMOVIDO: double valorTotal = 0.0;
        List<ItemPedido> itemPedidos = new ArrayList<>();

        for (PedidoRequestDTO.ItemPedidoRequestDTO itemPedidoRequestDTO : dto.itens()){
            ItemPedido item = new ItemPedido();
            Televisao televisao = televisaoRepository.findById((long) itemPedidoRequestDTO.idTelevisao());

            if (televisao == null) {
                throw new ValidationException("Televisao", "Televisão não encontrada.");
            }

            if (televisao.getEstoque() < itemPedidoRequestDTO.quatidade()){
                throw new ValidationException("televisao", "Quantidade ultrapassou o estoque disponível");
            }

            // Baixa no estoque
            televisao.setEstoque((televisao.getEstoque() - itemPedidoRequestDTO.quatidade()));

            item.setTelevisao(televisao);
            item.setQuantidade(itemPedidoRequestDTO.quatidade());
            item.setPreco(televisao.getValor());
            item.setPedido(pedido);

            itemPedidos.add(item);

            // REMOVIDO: valorTotal += item.getPreco() * item.getQuantidade();
        }

        pedido.setItensPedidos(itemPedidos);

        // REMOVIDO: pedido.setValorTotal(valorTotal);

        pedidoRepository.persist(pedido);

        return PedidoResponseDTO.valueOf(pedido);
    }

    @Override
    @Transactional
    public void update_user(long id, PedidoupdateRequestDTO dto) {
        String username = jwt.getSubject();
        Usuario usuario = usuarioRepository.findByUsername(username);

        if (usuario == null){
            throw new ValidationException("Usuario", "Usuário inválido");
        }

        Pedido pedido = pedidoRepository.findById(id);

        if (pedido == null){
            throw new ValidationException("Pedido", "Pedido não encontrado");
        }

        if (!usuario.getUsername().equalsIgnoreCase(pedido.getUsuario().getUsername())){
            throw new ValidationException("pedido" ,"Este pedido não é seu");
        }

        // --- REGRA DE NEGÓCIO: PRAZO DE 1 DIA ---
        LocalDateTime dataLimite = pedido.getDataPedido().plusDays(1);
        if (LocalDateTime.now().isAfter(dataLimite)) {
            throw new ValidationException("Prazo", "O prazo de 24 horas para alterações expirou. Entre em contato com o suporte.");
        }

        // --- REGRA DE NEGÓCIO: STATUS ---
        // Se o pagamento for confirmado, o status continua EM_PROCESSO, permitindo edição.
        // Se o Admin mudar para SAIU_PARA_ENTREGA, aí sim bloqueia, mesmo que seja antes de 24h (questão física).
        if (!pedido.getStatusPedido().equals(StatusPedido.PEDIDO_EM_PROCESSO)){
            throw new ValidationException("pedido", "Este pedido já está em transporte e não pode ser alterado.");
        }

        // --- ATUALIZAÇÃO DE ENDEREÇO (SNAPSHOT) ---
        if (dto.idEndereco() > 0) { // Verifica se veio um ID válido para trocar
            Endereco endereco = enderecoRepository.findById(dto.idEndereco());
            if (endereco == null) {
                throw new ValidationException("Endereço", "Endereço não encontrado");
            }

            EnderecoEntrega enderecoEntrega = pedido.getEnderecoEntrega();

            // Refazendo o Snapshot com o novo endereço
            enderecoEntrega.setEndereco(endereco);
            enderecoEntrega.setCep(endereco.getCep());
            enderecoEntrega.setBairro(endereco.getBairro());
            enderecoEntrega.setNumero(endereco.getNumero());
            enderecoEntrega.setComplemento(endereco.getComplemento());
            enderecoEntrega.setMunicipio(endereco.getMunicipio());

            enderecoEntrega.setPedido(pedido);
            pedido.setEnderecoEntrega(enderecoEntrega);
        }

        // --- CANCELAMENTO ---
        if (dto.status() != null && dto.status().equalsIgnoreCase("sim")){
            pedido.setStatusPedido(StatusPedido.CANCELADO);
        }
    }

    @Override
    @Transactional
    public void update_admin(long idPedido, PedidoRequestDTO dto) {
        Pedido pedido = pedidoRepository.findById(idPedido);

        if (pedido == null){
            throw new ValidationException("Pedido", "Pedido não encontrado");
        }

        // Atualiza Endereço e refaz o Snapshot
        Endereco endereco = enderecoRepository.findById(dto.idEndereco());
        if (endereco == null) {
            throw new ValidationException("Endereço", "Endereço não encontrado");
        }

        EnderecoEntrega enderecoEntrega = pedido.getEnderecoEntrega();

        // Atualiza os dados do snapshot
        enderecoEntrega.setEndereco(endereco);
        enderecoEntrega.setCep(endereco.getCep());
        enderecoEntrega.setBairro(endereco.getBairro());
        enderecoEntrega.setNumero(endereco.getNumero());
        enderecoEntrega.setComplemento(endereco.getComplemento());
        enderecoEntrega.setMunicipio(endereco.getMunicipio());

        enderecoEntrega.setPedido(pedido);
        pedido.setEnderecoEntrega(enderecoEntrega);

        // Recalcula itens
        double valorTotalRecalculado = 0.0;
        List<ItemPedido> itemPedidos = new ArrayList<>(); // Nova lista ou limpar a antiga

        // Nota: Ao limpar a lista antiga, cuidado com orphanRemoval se não for gerido automaticamente pelo JPA aqui.
        // O ideal seria limpar a lista existente: pedido.getItensPedidos().clear();
        pedido.getItensPedidos().clear();

        for (PedidoRequestDTO.ItemPedidoRequestDTO itemPedidoRequestDTO : dto.itens()){
            ItemPedido item = new ItemPedido();
            Televisao televisao = televisaoRepository.findById((long) itemPedidoRequestDTO.idTelevisao());

            if (televisao == null) {
                throw new ValidationException("Televisao", "TV não encontrada ID: " + itemPedidoRequestDTO.idTelevisao());
            }

            // Nota: Em update de admin, talvez precise devolver o estoque dos itens antigos antes de tirar?
            // Simplificado aqui conforme sua lógica original.

            if (televisao.getEstoque() < itemPedidoRequestDTO.quatidade()){
                throw new ValidationException("televisao", "Quantidade ultrapassou o estoque disponível");
            }

            televisao.setEstoque((televisao.getEstoque() - itemPedidoRequestDTO.quatidade()));

            item.setTelevisao(televisao);
            item.setQuantidade(itemPedidoRequestDTO.quatidade());
            item.setPreco(televisao.getValor());
            item.setPedido(pedido);

            itemPedidos.add(item);

            valorTotalRecalculado += item.getPreco() * item.getQuantidade();
        }

        // Adiciona os novos itens à lista do pedido
        pedido.getItensPedidos().addAll(itemPedidos);

        // 💡 ATUALIZAÇÃO: Usa o valor total do DTO se for enviado, senão usa o recalculado
        if (dto.valorTotal() != null) {
            pedido.setValorTotal(dto.valorTotal());
        } else {
            // Se o front admin não mandar o total, usamos o subtotal recalculado
            pedido.setValorTotal(valorTotalRecalculado);
        }

        // 💡 NOVO CAMPO: Seta o frete
        if (dto.valorFrete() != null) {
            pedido.setValorFrete(dto.valorFrete());
        } else {
            // Opcional: Se não enviou o frete no update, ele deve ser recalculado ou mantido.
            // Para simplicidade, assumimos que ele deve ser enviado se houver alteração.
        }
    }

    @Override
    @Transactional
    public void updateStatusPedido(long id, int num) {
        Pedido pedido = pedidoRepository.findById(id);

        if (pedido == null){
            throw new ValidationException("Pedido", "Pedido não encontrado");
        }

        pedido.setStatusPedido(StatusPedido.valueOf(num));
    }

    @Override
    @Transactional
    public void delete(long id) {
        Pedido pedido = pedidoRepository.findById(id);

        if (pedido == null){
            throw new ValidationException("Pedido", "Pedido não encontrado");
        }
        pedidoRepository.deleteById(id);
    }

    @Override
    @Transactional
    public PixResponseDTO gerarPix(Long idPedido) {
        Pix pix = new Pix();
        Pedido pedido = pedidoRepository.findById(idPedido);

        if (pedido == null) {
            throw new ValidationException("Pedido", "Pedido não encontrado");
        }

        String username = jwt.getSubject();
        Usuario usuario = usuarioRepository.findByUsername(username);

        if (usuario == null){
            throw new ValidationException("usuario", "Usuário inválido");
        }
        if (!usuario.getUsername().equalsIgnoreCase(pedido.getUsuario().getUsername())){
            throw new ValidationException("pedido" ,"Este pedido não é seu");
        }

        if (pedido.getFormaPagamento() != null){
            throw new ValidationException("Pedido", "Este pedido está pendente em outra forma de pagamento");
        }

        // O valor total que o Pix vai usar já inclui o frete (foi salvo corretamente na criação)
        double total = pedido.getValorTotal();

        pix.setChave("email_loja@unitins.br"); // Exemplo
        pix.setValor(total);
        pix.setStatusPagamento(StatusPagamento.PAGAMENTO_PENDENTE);
        pix.setPedido(pedido);

        pedido.setFormaPagamento(pix);

        pagamentoRepository.persist(pix);

        return PixResponseDTO.valueOf(pix);
    }

    @Override
    @Transactional
    public BoletoResponseDTO gerarBoleto(Long idPedido) {
        String username = jwt.getSubject();

        Usuario usuario = usuarioRepository.findByUsername(username);
        Boleto boleto = new Boleto();
        Pedido pedido = pedidoRepository.findById(idPedido);

        if (usuario == null){
            throw new ValidationException("Usuario", "Usuário não encontrado");
        }

        if (pedido == null){
            throw new ValidationException("Pedido", "Pedido não encontrado");
        }

        if (!usuario.getUsername().equalsIgnoreCase(pedido.getUsuario().getUsername())){
            throw new ValidationException("pedido" ,"Este pedido não é seu");
        }

        if (pedido.getFormaPagamento() != null){
            throw new ValidationException("Pedido", "Este pedido está pendente em outra forma de pagamento");
        }

        // O valor total que o Boleto vai usar já inclui o frete (foi salvo corretamente na criação)
        double total = pedido.getValorTotal();

        boleto.setCodigoBarras(UUID.randomUUID().toString());
        boleto.setValor(total);
        boleto.setStatusPagamento(StatusPagamento.PAGAMENTO_PENDENTE);
        boleto.setPedido(pedido);

        pedido.setFormaPagamento(boleto);

        boletoRepository.persist(boleto);

        return BoletoResponseDTO.valueOf(boleto);
    }

    @Override
    @Transactional
    public PixResponseDTO registrarPagamentoPix(Long idPix) {
        String username = jwt.getSubject();
        Usuario usuario = usuarioRepository.findByUsername(username);

        // Busca o Pix usando o repositório genérico (com cast) ou específico se tiver
        Pix pix = (Pix) pagamentoRepository.findById(idPix);

        if (pix == null) {
            throw new ValidationException("Pagamento", "Pagamento Pix não encontrado");
        }

        LocalDateTime dataAtual = LocalDateTime.now();
        Pedido pedido = pix.getPedido();

        if (usuario == null){
            throw new IllegalArgumentException("Usuario não encontrado");
        }

        // Verifica se o usuário logado é o dono do pedido
        if (!usuario.getUsername().equalsIgnoreCase(pedido.getUsuario().getUsername())){
            throw new ValidationException("pedido" ,"Este pedido não é seu");
        }

        // Verifica consistência (se o pedido realmente é via Pix)
        if (!(pedido.getFormaPagamento() instanceof Pix)){
            throw new ValidationException("Pedido", "Este pedido está vinculado a outra forma de pagamento");
        }

        // Atualiza os dados do PAGAMENTO
        pix.setStatusPagamento(StatusPagamento.PAGAMENTO_EFETUADO);
        pix.setDataPagamento(dataAtual);
        // Garante que o pagamento registre o valor total do pedido (que agora inclui o frete)
        pix.setValor(pedido.getValorTotal());

        // updateStatusPedido(pedido.getId(), 2); // <--- REMOVIDO/COMENTADO

        // Persiste a atualização do pagamento
        pagamentoRepository.persist(pix);

        return PixResponseDTO.valueOf(pix);
    }

    @Override
    @Transactional
    public BoletoResponseDTO registrarPagamentoBoleto(Long idBoleto) {
        String username = jwt.getSubject();

        Usuario usuario = usuarioRepository.findByUsername(username);
        // Cast para Boleto, pois o findById retorna Pagamento genérico
        Boleto boleto = (Boleto) pagamentoRepository.findById(idBoleto);

        if (boleto == null) {
            throw new ValidationException("Pagamento", "Boleto não encontrado");
        }

        LocalDateTime dataAtual = LocalDateTime.now();
        Pedido pedido = boleto.getPedido();

        if (usuario == null){
            throw new IllegalArgumentException("Usuario não encontrado");
        }

        if (!usuario.getUsername().equalsIgnoreCase(pedido.getUsuario().getUsername())){
            throw new ValidationException("pedido" ,"Este pedido não é seu");
        }

        if (!(pedido.getFormaPagamento() instanceof Boleto)) {
            throw new ValidationException("Pedido", "Este pedido está vinculado a outra forma de pagamento.");
        }

        // Atualiza apenas os dados do PAGAMENTO
        boleto.setStatusPagamento(StatusPagamento.PAGAMENTO_EFETUADO);
        boleto.setDataPagamento(dataAtual);
        // Garante que o pagamento registre o valor total do pedido (que agora inclui o frete)
        boleto.setValor(pedido.getValorTotal());

        // updateStatusPedido(pedido.getId(), 2); // <--- REMOVIDO/COMENTADO

        pagamentoRepository.persist(boleto);

        return BoletoResponseDTO.valueOf(boleto);
    }


    @Override
    @Transactional
    public CartaoResponseDTO registrarPagamentoCartao(long idPedido, CartaoRequestDTO dto) {
        String username = jwt.getSubject();
        Pedido pedido = pedidoRepository.findById(idPedido);
        Usuario usuario = usuarioRepository.findByUsername(username);

        if (pedido == null || usuario == null) {
            throw new ValidationException("Dados", "Pedido ou Usuário não encontrados");
        }
        if (!usuario.getUsername().equalsIgnoreCase(pedido.getUsuario().getUsername())){
            throw new ValidationException("Pedido", "Este pedido não pertence ao usuário logado");
        }

        if (pedido.getFormaPagamento() != null) {
            throw new ValidationException("Pedido", "Este pedido já possui um pagamento vinculado/pendente.");
        }

        Cartao cartao = CartaoRequestDTO.convertToCartao(dto);

        // Garante que o cartão registre o valor total do pedido (que agora inclui o frete)
        cartao.setValor(pedido.getValorTotal());
        cartao.setDataPagamento(LocalDateTime.now());
        cartao.setPedido(pedido);
        cartao.setStatusPagamento(StatusPagamento.PAGAMENTO_EFETUADO);

        pedido.setFormaPagamento(cartao);

        cartaoRepository.persist(cartao);

        // updateStatusPedido(idPedido, 2); // <--- REMOVIDO/COMENTADO

        return CartaoResponseDTO.valueOf(cartao);
    }

    @Override
    public PedidoResponseDTO findById(Long id) {
        Pedido pedido = pedidoRepository.findById(id);
        if (pedido == null){
            throw new ValidationException("Pedido", "Pedido não encontrado");
        }

        return PedidoResponseDTO.valueOf(pedido);
    }

    @Override
    public List<PedidoResponseDTO> findAll() {
        return pedidoRepository.listAll().stream().map(PedidoResponseDTO::valueOf).toList();
    }

    @Override
    public List<PedidoResponseDTO> findMyPedidos() {
        String username = jwt.getSubject();
        return pedidoRepository.findPedidoByUsername(username).stream().map(PedidoResponseDTO::valueOf).toList();
    }

    @Override
    public List<PedidoResponseDTO> findPedidosByUsername(String username) {
        Usuario usuario = usuarioRepository.findByUsername(username);

        if (username == null || usuario == null){
            throw new ValidationException("Usuario", "Usuário inválido");
        }

        return pedidoRepository.findPedidoByUsername(username).stream().map(PedidoResponseDTO::valueOf).toList();
    }
}