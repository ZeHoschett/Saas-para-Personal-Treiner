package br.com.consultoria.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public abstract class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;

    private String nome;
    private String email;
    private String senha; // Novo campo para autenticação
    private final LocalDateTime dataCadastro;
    private boolean ehAdmin; // Define se tem acesso total ou restrito

    public Usuario(String nome, String email) {
        this.nome = nome;
        this.email = email;
        this.dataCadastro = LocalDateTime.now();
        this.senha = "123456"; // Senha padrão inicial para todos os novos usuários
        this.ehAdmin = false;  // Por padrão, ninguém nasce administrador
    }

    // --- Métodos de Segurança ---

    public boolean autenticar(String emailTentativa, String senhaTentativa) {
        return this.email.equalsIgnoreCase(emailTentativa) && this.senha.equals(senhaTentativa);
    }

    // --- Getters e Setters ---

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }

    public LocalDateTime getDataCadastro() { return dataCadastro; }

    public boolean isEhAdmin() { return ehAdmin; }
    protected void setEhAdmin(boolean ehAdmin) { this.ehAdmin = ehAdmin; }

    @Override
    public String toString() {
        return String.format("Nome: %-15s | Email: %-25s", nome, email);
    }
}
