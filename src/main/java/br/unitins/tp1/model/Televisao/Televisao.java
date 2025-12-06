package br.unitins.tp1.model.Televisao;

import br.unitins.tp1.model.DefaultEntity;
        import br.unitins.tp1.model.Modelo;
        import br.unitins.tp1.model.PessoaJuridica.Fabricante;
        import br.unitins.tp1.model.PessoaJuridica.Fornecedor;
        import br.unitins.tp1.model.Pedido.ItemPedido;
        import com.fasterxml.jackson.annotation.JsonIgnore;
        import jakarta.persistence.*;

        import java.util.ArrayList;
        import java.util.List;
        @Entity
        public class Televisao extends DefaultEntity {

            @Column
            private Double valor;

            @Enumerated(EnumType.STRING)
            @Column(length = 60, nullable = false)
            private TipoResolucao resolucao;

            @Enumerated(EnumType.STRING)
            @Column(length = 60, nullable = false)
            private TipoTela tipoTela;

            @Column
            private Integer estoque;

            @Column
            private String descricao;

            @Column()
            private Boolean ativo = true;

            @Column(nullable = true, name = "nome_imagem")
            private String nomeImagem;

            @ManyToOne
            @JoinColumn(name = "id_modelo")
            private Modelo modelo;

            @ManyToOne
            @JoinColumn(name = "id_dimensao")
            private Dimensao dimensao;

            @OneToMany(mappedBy = "televisao")
            @JsonIgnore
            private List<ItemPedido> itensPedidos = new ArrayList<>();


            public Integer getEstoque() {
                return estoque;
            }

            public void setEstoque(Integer estoque) {
                this.estoque = estoque;
            }

            public TipoResolucao getResolucao() {
                return resolucao;
            }

            public void setResolucao(TipoResolucao resolucao) {
                this.resolucao = resolucao;
            }

            public TipoTela getTipoTela() {
                return tipoTela;
            }

            public void setTipoTela(TipoTela tipoTela) {
                this.tipoTela = tipoTela;
            }

            public Dimensao getDimensao() {
                return dimensao;
            }

            public void setDimensao(Dimensao dimensao) {
                this.dimensao = dimensao;
            }

            public Double getValor() {
                return valor;
            }

            public void setValor(Double valor) {
                this.valor = valor;
            }

            public List<ItemPedido> getItensPedidos() {
                return itensPedidos;
            }

            public void setItensPedidos(List<ItemPedido> itensPedidos) {
                this.itensPedidos = itensPedidos;
            }

            public Modelo getModelo() {
                return modelo;
            }

            public void setModelo(Modelo modelo) {
                this.modelo = modelo;
            }

            public String getDescricao() {
                return descricao;
            }

            public void setDescricao(String descricao) {
                this.descricao = descricao;
            }

            public String getNomeImagem() {
                return nomeImagem;
            }

            public void setNomeImagem(String nomeImagem) {
                this.nomeImagem = nomeImagem;
            }

            public Boolean getAtivo() {
                return ativo;
            }

            public void setAtivo(Boolean ativo) {
                this.ativo = ativo;
            }
        }

