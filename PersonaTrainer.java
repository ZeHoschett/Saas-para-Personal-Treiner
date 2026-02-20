package br.com.consultoria.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PersonalTrainer extends Usuario implements Serializable {

    private static final long serialVersionUID = 1L;
    private List<Aluno> meusAlunos;
    private String cref;
    private static final int MAX_ALUNOS = 150;

    public PersonalTrainer(String nome, String email, String cref) {
        super(nome, email);
        this.cref = cref;
        this.meusAlunos = new ArrayList<>();
        this.setEhAdmin(true);
    }

    // --- GETTERS E SETTERS ---
    public String getCref() { return cref; }
    public void setCref(String cref) { this.cref = cref; }
    public List<Aluno> getMeusAlunos() { return meusAlunos; }

    // --- CADASTRO E GESTÃO ---

    public void autoCadastrarAluno(String nome, String email, String senha, int idade) throws Exception {
        if (meusAlunos.size() >= MAX_ALUNOS) {
            throw new Exception("Minha consultoria atingiu o limite de 150 alunos.");
        }

        boolean jaExiste = meusAlunos.stream().anyMatch(a -> a.getEmail().equalsIgnoreCase(email));
        if (jaExiste) {
            throw new Exception("Este e-mail ja esta cadastrado com este professor.");
        }

        Aluno novoAluno = new Aluno(nome, email);
        novoAluno.setSenha(senha);
        novoAluno.setIdade(idade);
        novoAluno.setStatus("Ativo");

        meusAlunos.add(novoAluno);
        System.out.println(" " + nome + " cadastrado com sucesso!");
    }

    // --- PRESCRIÇÕES ---

    public void prescreverTreino(String email, String descricaoTreino, String objetivoTreino, int diasValidade) {
        encontrarAluno(email).ifPresentOrElse(aluno -> {
            aluno.setTreinoAtual(new Treino(descricaoTreino, objetivoTreino, diasValidade));
            System.out.println(" Treino enviado para: " + aluno.getNome());
        }, () -> System.out.println(" Aluno nao encontrado."));
    }

