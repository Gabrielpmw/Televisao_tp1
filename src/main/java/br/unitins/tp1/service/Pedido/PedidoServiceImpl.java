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
import java.util.UUID;

@ApplicationScoped
public class PedidoServiceImpl implements PedidoService{

    @Inject
    UsuarioRepository usuarioRepository;

    @Inject
    PedidoRepository pedidoRepository;

    @Inject
    TelevisaoRepository televisaoRepository;

    @Inject
    EnderecoRepository enderecoRepository;

    @Inject
    EnderecoEntregaRepository enderecoEntregaRepository;

    @Inject
    ItemPedidoRepository itemPedidoRepository;

    @Inject
    PagamentoRepository pagamentoRepository;

    @Inject
    JsonWebToken jwt;

    @Override
    @Transactional
    public PedidoResponseDTO create(PedidoRequestDTO dto) {
        String username = jwt.getSubject();
        Pedido pedido = new Pedido();

        Usuario usuario = usuarioRepository.findByUsername(username);
        Endereco endereco = enderecoRepository.findById(dto.idEndereco());
        EnderecoEntrega enderecoEntrega = new EnderecoEntrega();
        List<ItemPedido> itemPedidos = new ArrayList<>();
        LocalDateTime dataPedido = LocalDateTime.now();

        enderecoEntrega.setEndereco(endereco);
        enderecoEntrega.setPedido(pedido);

        pedido.setDataPedido(dataPedido);
        pedido.setStatusPedido(StatusPedido.PEDIDO_EM_PROCESSO);
        pedido.setUsuario(usuario);
        pedido.setEnderecoEntrega(enderecoEntrega);

        double valorTotal = 0.0;



        for (ItemPedidoRequestDTO itemPedidoRequestDTO : dto.itens()){
            ItemPedido item = new ItemPedido();
            Televisao televisao = televisaoRepository.findById((long) itemPedidoRequestDTO.idTelevisao());

            if (televisao.getEstoque() < itemPedidoRequestDTO.quatidade()){
                throw new ValidationException("televisao", "Quantidade ultrapassou o estoque disponível");
            }

            televisao.setEstoque((televisao.getEstoque() - itemPedidoRequestDTO.quatidade()));

            item.setTelevisao(televisao);
            item.setQuantidade(itemPedidoRequestDTO.quatidade());
            item.setPreco(televisao.getValor());

            item.setPedido(pedido);

            itemPedidos.add(item);

            valorTotal += item.getPreco() * item.getQuantidade();
        }

        pedido.setItensPedidos(itemPedidos);
        pedido.setValorTotal(valorTotal);

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

        if (!usuario.getUsername().equalsIgnoreCase(pedido.getUsuario().getUsername())){
            throw new ValidationException("pedido" ,"Este pedido não é seu");
        }
        if (!pedido.getStatusPedido().equals(StatusPedido.PEDIDO_EM_PROCESSO)){
            throw new ValidationException("pedido", "Este pedido não é mais possível atualizar");
        }

        if (id <= 0){
            throw new ValidationException("Id pedido", "Id do pedido está inválido");
        }

        if (pedido == null){
            throw new ValidationException("Pedido", "Pedido não encontrado");
        }

        Endereco endereco = enderecoRepository.findById(dto.idEndereco());
        EnderecoEntrega enderecoEntrega = pedidoRepository.findById(id).getEnderecoEntrega();

        enderecoEntrega.setEndereco(endereco);
        enderecoEntrega.setPedido(pedido);
        pedido.setEnderecoEntrega(enderecoEntrega);

        if (dto.status().equalsIgnoreCase("sim")){
            pedido.setStatusPedido(StatusPedido.CANCELADO);
        }
    }

    @Override
    @Transactional
    public void update_admin(long idPedido, PedidoRequestDTO dto) {
        Pedido pedido = pedidoRepository.findById(idPedido);

        if (idPedido <= 0){
            throw new ValidationException("Id pedido", "Id do pedido está inválido");
        }

        if (pedido == null){
            throw new ValidationException("Pedido", "Pedido não encontrado");
        }

        Endereco endereco = enderecoRepository.findById(dto.idEndereco());
        EnderecoEntrega enderecoEntrega = pedido.getEnderecoEntrega();
        List<ItemPedido> itemPedidos = new ArrayList<>();

        enderecoEntrega.setEndereco(endereco);
        enderecoEntrega.setPedido(pedido);
        pedido.setEnderecoEntrega(enderecoEntrega);

        double valorTotal = 0.0;

        pedido.getItensPedidos().clear();
        for (ItemPedidoRequestDTO itemPedidoRequestDTO : dto.itens()){
            ItemPedido item = new ItemPedido();
            Televisao televisao = televisaoRepository.findById((long) itemPedidoRequestDTO.idTelevisao());

            if (televisao.getEstoque() < itemPedidoRequestDTO.quatidade()){
                throw new ValidationException("televisao", "Quantidade ultrapassou o estoque disponível");
            }

            televisao.setEstoque((televisao.getEstoque() - itemPedidoRequestDTO.quatidade()));

            item.setTelevisao(televisao);
            item.setQuantidade(itemPedidoRequestDTO.quatidade());
            item.setPreco(televisao.getValor());

            item.setPedido(pedido);

            itemPedidos.add(item);

            valorTotal += item.getPreco() * item.getQuantidade();
        }

        pedido.setItensPedidos(itemPedidos);
        pedido.setValorTotal(valorTotal);
    }

    @Override
    @Transactional
    public void updateStatusPedido(long id, int num) {
        Pedido pedido = pedidoRepository.findById(id);

        if (id <= 0){
            throw new ValidationException("Id pedido", "Id do pedido está inválido");
        }

        if (pedido == null){
            throw new ValidationException("Pedido", "Pedido não encontrado");
        }

        pedido.setStatusPedido(StatusPedido.valueOf(num));
    }

    @Override
    @Transactional
    public void delete(long id) {
        Pedido pedido = pedidoRepository.findById(id);

        if (id <= 0){
            throw new ValidationException("Id pedido", "Id do pedido está inválido");
        }

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

        double total = pedido.getValorTotal();

        pix.setChave("Xicão@gmail.com");
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

        if (!usuario.getUsername().equalsIgnoreCase(pedido.getUsuario().getUsername())){
            throw new ValidationException("pedido" ,"Este pedido não é seu");
        }

        if (pedido.getFormaPagamento() != null){
            throw new ValidationException("Pedido", "Este pedido está pendente em outra forma de pagamento");
        }

        double total = pedidoRepository.findById(idPedido).getValorTotal();

        boleto.setCodigoBarras(UUID.randomUUID().toString());
        boleto.setValor(total);
        boleto.setStatusPagamento(StatusPagamento.PAGAMENTO_PENDENTE);
        boleto.setPedido(pedido);

        pedido.setFormaPagamento(boleto);

        pagamentoRepository.persist(boleto);

        return BoletoResponseDTO.valueOf(boleto);
    }

    @Override
    @Transactional
    public PixResponseDTO registrarPagamentoPix(Long idPix) {
        String username = jwt.getSubject();
        Usuario usuario = usuarioRepository.findByUsername(username);
        Pix pix = (Pix) pagamentoRepository.findById(idPix);
        LocalDateTime dataAtual = LocalDateTime.now();
        Pedido pedido = pedidoRepository.findById(pix.getPedido().getId());
        if (usuario == null){
            throw new IllegalArgumentException("Usuario não encontrado");
        }

        if (!usuario.getUsername().equalsIgnoreCase(pedido.getUsuario().getUsername())){
            throw new ValidationException("pedido" ,"Este pedido não é seu");
        }

        if (!(pedido.getFormaPagamento() instanceof Pix)){
            throw new ValidationException("Pedido", "Este pedido está pendente em outra forma de pagamento");
        }

        pix.setStatusPagamento(StatusPagamento.PAGAMENTO_EFETUADO);
        pix.setDataPagamento(dataAtual);
        pix.setPedido(pedido);
        pix.setValor(pedido.getValorTotal());
        updateStatusPedido(pix.getPedido().getId(), 2);

        pagamentoRepository.persist(pix);

        return PixResponseDTO.valueOf(pix);
    }

    @Override
    @Transactional
    public BoletoResponseDTO registrarPagamentoBoleto(Long idBoleto) {
        String username = jwt.getSubject();

        Usuario usuario = usuarioRepository.findByUsername(username);
        Boleto boleto = (Boleto) pagamentoRepository.findById(idBoleto);
        LocalDateTime dataAtual = LocalDateTime.now();
        Pedido pedido = pedidoRepository.findById(boleto.getPedido().getId());
        if (usuario == null){
            throw new IllegalArgumentException("Usuario não encontrado");
        }

        if (!usuario.getUsername().equalsIgnoreCase(pedido.getUsuario().getUsername())){
            throw new ValidationException("pedido" ,"Este pedido não é seu");
        }

        Pagamento pagamentoExistente = pedido.getFormaPagamento();

        if (!(pagamentoExistente instanceof Boleto)) {
            throw new ValidationException("Pedido", "Este pedido já está vinculado a outro pagamento.");
        }

        Pagamento pagamento = pagamentoRepository.findById(idBoleto);


        boleto.setStatusPagamento(StatusPagamento.PAGAMENTO_EFETUADO);
        boleto.setDataPagamento(dataAtual);
        boleto.setPedido(pedido);
        boleto.setValor(pedido.getValorTotal());
        updateStatusPedido(boleto.getPedido().getId(), 2);

        pagamentoRepository.persist(boleto);

        return BoletoResponseDTO.valueOf(boleto);
    }


    @Override
    @Transactional
    public CartaoResponseDTO registrarPagamentoCartao(long idPedido, CartaoRequestDTO dto) {
        String username = jwt.getSubject();

        Pedido pedido = pedidoRepository.findById(idPedido);
        Usuario usuario = usuarioRepository.findByUsername(username);
        LocalDateTime dataAtual = LocalDateTime.now();
        Cartao cartao = CartaoRequestDTO.convertToCartao(dto);

        if (!(pedido.getFormaPagamento() instanceof Cartao) ){
            throw new ValidationException("Pedido", "Este pedido está pendente em outra forma de pagamento");
        }

        if (usuario == null){
            throw new ValidationException("Usuário", "Usuário não encontrado");
        }

        if (!usuario.getUsername().equalsIgnoreCase(pedido.getUsuario().getUsername())){
            throw new ValidationException("pedido" ,"Este pedido não é seu");
        }

        cartao.setValor(pedido.getValorTotal());
        cartao.setDataPagamento(dataAtual);
        cartao.setPedido(pedido);
        cartao.setStatusPagamento(StatusPagamento.PAGAMENTO_EFETUADO);
        updateStatusPedido(idPedido, 2);

        pagamentoRepository.persist(cartao);

        return CartaoResponseDTO.valueOf(cartao);
    }

    @Override
    public PedidoResponseDTO findById(Long id) {
        Pedido pedido = pedidoRepository.findById(id);
        if (pedido == null){
            throw new ValidationException("Pedido", "Pedido não encontrado");
        }

        if (id <= 0){
            throw new ValidationException("Id", "Id inválido");
        }

        return PedidoResponseDTO.valueOf(pedidoRepository.findById(id));
    }

    @Override
    public List<PedidoResponseDTO> findAll() {
        return pedidoRepository.listAll().stream().map(PedidoResponseDTO::valueOf).toList();
    }

    @Override
    public List<PedidoResponseDTO> findMyPedidos() {
        String username = jwt.getSubject();
        return  pedidoRepository.findPedidoByUsername(username).stream().map(PedidoResponseDTO::valueOf).toList();
    }

    @Override
    public List<PedidoResponseDTO> findPedidosByUsername(String username) {

        Usuario usuario = usuarioRepository.findByUsername(username);

        if (username == null || usuario == null){
            throw new ValidationException("Usuario", "Usuário inválido");
        }

        return  pedidoRepository.findPedidoByUsername(username).stream().map(PedidoResponseDTO::valueOf).toList();
    }

}
