package br.com.consultoria.model;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);

        try {

            PlataformaSaaS plataforma = GerenciadorDados.carregarPlataforma();


            if (plataforma.getListaPersonais().isEmpty()) {
                PersonalTrainer admin = new PersonalTrainer("Glauco Personal", "glauco@consultoria.com", "000000-G/SP");
                admin.setSenha("123456");
                plataforma.getListaPersonais().add(admin);
            }

            int escolha = 0;
            while (escolha != 4) {
                exibirBannerIncial();
                System.out.println("  1:  ACESSAR SISTEMA (Login)");
                System.out.println("  2 :  SOU ALUNO (Novo Cadastro)");
                System.out.println("  3 :  SOU PERSONAL (Novo Cadastro)");
                System.out.println("  4:   DESLIGAR SISTEMA");
                System.out.print("\nSelecione: ");

                if (!teclado.hasNextInt()) {
                    System.out.println("️ Digite um numero de 1 a 4.");
                    teclado.next();
                    continue;
                }

                escolha = teclado.nextInt();
                teclado.nextLine();

                switch (escolha) {
                    case 1 -> realizarLogin(plataforma, teclado);
                    case 2 -> cadastrarNovoAluno(plataforma, teclado);
                    case 3 -> cadastrarNovoPersonal(plataforma, teclado);
                    case 4 -> {
                        GerenciadorDados.salvarPlataforma(plataforma);
                        System.out.println("\n Base sincronizada. Ate logo!");
                    }
                    default -> System.out.println("Opção invalida.");
                }
            }
        } catch (Exception e) {
            System.err.println("️ Erro critico: " + e.getMessage());
        } finally {
            teclado.close();
        }
    }






    private static void exibirBannerIncial() {
        System.out.println("\n" + "=".repeat(45));
        System.out.println("  HOSCHETT  CONSULTORIA ");
        System.out.println("=".repeat(45));
    }

    private static void realizarLogin(PlataformaSaaS plataforma, Scanner teclado) {
        System.out.println("\n--- LOGIN UNIFICADO ---");
        System.out.print(" E-mail: ");
        String email = teclado.nextLine();
        System.out.print(" Senha: ");
        String senha = teclado.nextLine();

        for (PersonalTrainer p : plataforma.getListaPersonais()) {
            if (p.autenticar(email, senha)) {
                exibirMenuPersonal(p, teclado, plataforma);
                return;
            }
            for (Aluno a : p.getMeusAlunos()) {
                if (a.autenticar(email, senha)) {
                    exibirMenuAluno(a, p, teclado, plataforma);
                    return;
                }
            }
        }
        System.out.println(" Usuário ou senha incorretos.");
    }

    private static void exibirMenuPersonal(PersonalTrainer personal, Scanner teclado, PlataformaSaaS saas) {
        int op = 0;
        do {
            System.out.println("\n" + "-".repeat(45));
            System.out.println("  TREINADOR: " + personal.getNome().toUpperCase());
            System.out.println("  CREF: " + personal.getCref());
            System.out.println("-".repeat(45));
            System.out.println("1.  Listar Alunos (Detalhado)");
            System.out.println("2.  Prescrever Treino");
            System.out.println("3.  Dashboard Geral");
            System.out.println("4.  Verificar Lesões");
            System.out.println("5.  Logout e Salvar");
            System.out.print("Escolha: ");

            if (!teclado.hasNextInt()) { teclado.next(); continue; }
            op = teclado.nextInt();
            teclado.nextLine();

            switch (op) {
                case 1 -> personal.listarAlunosComDetalhes();
                case 2 -> {
                    System.out.print("E-mail do Aluno: ");
                    String mail = teclado.nextLine();
                    personal.prescreverTreino(mail, "Treino Base", "Manutenção", 30);
                }
                case 3 -> personal.gerarDashboardConsultoria();
                case 4 -> personal.listarAlunosComLesao();
                case 5 -> GerenciadorDados.salvarPlataforma(saas);
            }
        } while (op != 5);
    }







    private static void exibirMenuAluno(Aluno aluno, PersonalTrainer meuPersonal, Scanner teclado, PlataformaSaaS saas) {
        if (aluno.getStatus().equalsIgnoreCase("Inadimplente")) {
            System.out.println("\n ACESSO BLOQUEADO. Fale com o Prof. " + meuPersonal.getNome());
            return;
        }

        if (aluno.getAltura() == 0) {
            System.out.println("\n---  CONFIGURAÇÃO DE PERFIL ---");
            System.out.print("Sua Altura (ex 1,75): ");
            aluno.setAltura(teclado.nextDouble());
            System.out.print("Seu Peso Atual (kg): ");
            aluno.registrarPeso(teclado.nextDouble());
            teclado.nextLine();
            System.out.print("Seu Objetivo Principal: ");
            aluno.setObjetivo(teclado.nextLine());
            System.out.print("Possui alguma lesão ou restrição ? ");
            aluno.setLesoes(teclado.nextLine());
            System.out.println(" Dados salvos com sucesso!");
        }

        int op = 0;
        do {
            System.out.println("\n" + "-".repeat(45));
            System.out.println("  ALUNO: " + aluno.getNome().toUpperCase());
            System.out.println("  TREINADOR: " + meuPersonal.getNome() + " | CREF: " + meuPersonal.getCref());
            System.out.println("-".repeat(45));
            System.out.println("1.  Ver Meu Treino");
            System.out.println("2.  Ver Minha Dieta");
            System.out.println("3.  Atualizar Peso");
            System.out.println("4.  Logout");
            System.out.print("Escolha: ");

            if (!teclado.hasNextInt()) { teclado.next(); continue; }
            op = teclado.nextInt();
            teclado.nextLine();

            switch (op) {
                case 1 -> System.out.println(aluno.getTreinoAtual() != null ? aluno.getTreinoAtual().gerarResumo() : " Aguardando treino...");
                case 2 -> System.out.println(aluno.getDietaAtual() != null ? aluno.getDietaAtual().gerarResumo() : " Aguardando dieta...");
                case 3 -> {
                    System.out.print("Novo peso atual: ");
                    aluno.registrarPeso(teclado.nextDouble());
                    teclado.nextLine();
                    System.out.println(" Peso atualizado!");
                }
                case 4 -> GerenciadorDados.salvarPlataforma(saas);
            }
        } while (op != 4);
    }






    private static void cadastrarNovoPersonal(PlataformaSaaS saas, Scanner teclado) {
        System.out.println("\n  NOVO TREINADOR ");
        System.out.print("Nome Completo: "); String n = teclado.nextLine();
        System.out.print(" Registro CREF: "); String cref = teclado.nextLine();
        System.out.print(" E-mail: "); String e = teclado.nextLine();
        System.out.print(" Senha: "); String s = teclado.nextLine();

        PersonalTrainer p = new PersonalTrainer(n, e, cref);
        p.setSenha(s);
        saas.getListaPersonais().add(p);
        System.out.println(" Personal cadastrado com sucesso!");
    }

    private static void cadastrarNovoAluno(PlataformaSaaS saas, Scanner teclado) {
        if (saas.getListaPersonais().isEmpty()) {
            System.out.println(" Nenhum personal disponível .");
            return;
        }
        System.out.println("\n  ESCOLHA SEU PROFESSOR ");
        for (int i = 0; i < saas.getListaPersonais().size(); i++) {
            PersonalTrainer pt = saas.getListaPersonais().get(i);
            // CORREÇÃO AQUI: Linha fechada corretamente
            System.out.println(i + " - " + pt.getNome() + " (CREF: " + pt.getCref() + ")");
        }
        System.out.print("Digite o numero correspondente: ");
        int pIdx = teclado.nextInt(); teclado.nextLine();

        System.out.print("Seu Nome: "); String nome = teclado.nextLine();
        System.out.print("Sua Idade: "); int idade = teclado.nextInt(); teclado.nextLine();
        System.out.print("Seu E-mail: "); String email = teclado.nextLine();
        System.out.print("Crie uma Senha: "); String senha = teclado.nextLine();

        try {
            saas.getListaPersonais().get(pIdx).autoCadastrarAluno(nome, email, senha, idade);
            System.out.println(" Cadastro realizado!");
        } catch (Exception ex) {
            System.out.println(" Erro: " + ex.getMessage());
        }
    }
}
