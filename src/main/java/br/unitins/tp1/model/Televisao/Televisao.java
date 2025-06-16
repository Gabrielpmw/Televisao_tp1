        package br.unitins.tp1.model.Televisao;

        import br.unitins.tp1.model.DefaultEntity;
        import br.unitins.tp1.model.Fabricante;
        import br.unitins.tp1.model.Fornecedor;
        import br.unitins.tp1.model.Pedido.ItemPedido;
        import com.fasterxml.jackson.annotation.JsonIgnore;
        import jakarta.persistence.*;

        import java.util.ArrayList;
        import java.util.List;

        @Entity
        public class Televisao extends DefaultEntity {

            @Column(length = 60, nullable = false)
            private String marca;

            @Column(length = 60, nullable = false)
            private String modelo;

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

            @ManyToMany(mappedBy = "televisaos")
            private List<Fornecedor> fornecedores = new ArrayList<>();

            @ManyToOne
            @JoinColumn(name = "id_fabricante")
            private Fabricante fabricante;

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

            public Fabricante getFabricante() {
                return fabricante;
            }

            public void setFabricante(Fabricante fabricante) {
                this.fabricante = fabricante;
            }

            public List<Fornecedor> getFornecedores() {
                return fornecedores;
            }

            public void setFornecedores(List<Fornecedor> fornecedores) {
                this.fornecedores = fornecedores;
            }

            public String getMarca() {
                return marca;
            }

            public void setMarca(String marca) {
                this.marca = marca;
            }

            public String getModelo() {
                return modelo;
            }

            public void setModelo(String modelo) {
                this.modelo = modelo;
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
        }
