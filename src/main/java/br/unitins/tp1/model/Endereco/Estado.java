    package br.unitins.tp1.model.Endereco;

    import br.unitins.tp1.model.DefaultEntity;
    import jakarta.persistence.*;

    import java.util.ArrayList;
    import java.util.List;

    @Entity
    public class Estado extends DefaultEntity {

        @Column(length = 60)
        private String nome;

        @Column(length = 60)
        private String sigla;

        @Column
        @Enumerated(EnumType.STRING)
        private Regiao regiao;

        @OneToMany(mappedBy = "estado")
        private List<Municipio> municipios = new ArrayList<>();


        public String getNome() {
            return nome;
        }

        public void setNome(String nome) {
            this.nome = nome;
        }

        public List<Municipio> getMunicipios() {
            return municipios;
        }

        public void setMunicipios(List<Municipio> municipios) {
            this.municipios = municipios;
        }

        public String getSigla() {
            return sigla;
        }

        public void setSigla(String sigla) {
            this.sigla = sigla;
        }

        public Regiao getRegiao() {
            return regiao;
        }

        public void setRegiao(Regiao regiao) {
            this.regiao = regiao;
        }
    }
