package br.com.consultoria.model;

import java.io.*;

public class GerenciadorDados {
    // Nome do novo arquivo para o modelo Multi-Personal
    private static final String NOME_ARQUIVO = "plataforma_consultoria.dat";

    // Método para salvar o objeto Plataforma completo
    public static void salvarPlataforma(PlataformaSaaS plataforma) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(NOME_ARQUIVO))) {
            oos.writeObject(plataforma);
            System.out.println("  Plataforma salva com sucesso!");
        } catch (IOException e) {
            System.err.println(" Erro ao salvar plataforma: " + e.getMessage());
        }
    }

    // Método para carregar o objeto Plataforma completo
    public static PlataformaSaaS carregarPlataforma() {
        File arquivo = new File(NOME_ARQUIVO);

        // Se o arquivo não existir, retorna uma nova plataforma vazia
        if (!arquivo.exists()) {
            return new PlataformaSaaS();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(NOME_ARQUIVO))) {
            return (PlataformaSaaS) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println(" Erro ao carregar plataforma: " + e.getMessage());
            return new PlataformaSaaS();
        }
    }
}
