    package br.unitins.tp1.model;

    import br.unitins.tp1.model.Endereco.Endereco;
    import br.unitins.tp1.model.Pedido.Pedido;
    import jakarta.persistence.*;

    import java.time.LocalDate;
    import java.util.ArrayList;
    import java.util.List;

    @Entity
    public class Usuario extends DefaultEntity {

        @Column(unique = true)
        private String username;

        @Column
        @Enumerated(EnumType.STRING)
        private Perfil perfil;

        @Column
        private String senha;

        @Column(unique = true)
        private String cpf;

        @Column
        private String nome;

        @Column
        private String sobrenome;

        @Column
        private String email; // Pode colocar @Email se quiser validar o formato, mas sem @NotNull

        @Column
        private LocalDate dataNascimento;

        @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
        private List<Telefone> telefones = new ArrayList<>();

        @OneToMany(mappedBy = "usuario")
        private List<Pedido> pedidos;

        @OneToOne(cascade = CascadeType.ALL)
        @JoinColumn(name = "id_funcionario")
        private Funcionario funcionario;

        @OneToMany(mappedBy = "usuario")
        private List<Endereco> enderecos;

        public List<Endereco> getEnderecos() {
            return enderecos;
        }

        public void setEnderecos(List<Endereco> enderecos) {
            this.enderecos = enderecos;
        }

        public Funcionario getFuncionario() {
            return funcionario;
        }

        public void setFuncionario(Funcionario funcionario) {
            this.funcionario = funcionario;
        }

        public List<Pedido> getPedidos() {
            return pedidos;
        }

        public void setPedidos(List<Pedido> pedidos) {
            this.pedidos = pedidos;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public Perfil getPerfil() {
            return perfil;
        }

        public void setPerfil(Perfil perfil) {
            this.perfil = perfil;
        }

        public String getSenha() {
            return senha;
        }

        public void setSenha(String senha) {
            this.senha = senha;
        }

        public String getCpf() {
            return cpf;
        }

        public void setCpf(String cpf) {
            this.cpf = cpf;
        }

        public String getNome() {
            return nome;
        }

        public void setNome(String nome) {
            this.nome = nome;
        }

        public String getSobrenome() {
            return sobrenome;
        }

        public void setSobrenome(String sobrenome) {
            this.sobrenome = sobrenome;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public LocalDate getDataNascimento() {
            return dataNascimento;
        }

        public void setDataNascimento(LocalDate dataNascimento) {
            this.dataNascimento = dataNascimento;
        }

        public List<Telefone> getTelefones() {
            return telefones;
        }

        public void setTelefones(List<Telefone> telefones) {
            this.telefones = telefones;
        }
    }
