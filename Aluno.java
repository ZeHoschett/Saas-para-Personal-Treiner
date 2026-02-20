package br.com.consultoria.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

public class Aluno extends Usuario implements Serializable {

    private static final long serialVersionUID = 1L;

    // --- Campos de Identificação e Plano ---
    private Treino treinoAtual;
    private Dieta dietaAtual;
    private String status;
    private int idade; // NOVO: Demandado para cálculos de metabolismo e perfil

    // --- Campos de Biometria e Saúde ---
    private double altura;
    private TreeMap<LocalDate, Double> historicoPeso;
    private String objetivo;
    private String restricoesAlimentares;
    private String lesoes;
    private String historicoFisico;

    public Aluno(String nome, String email) {
        super(nome, email);
        this.historicoPeso = new TreeMap<>();
        this.status = "Ativo";
        this.restricoesAlimentares = "Nenhuma";
        this.lesoes = "Nenhuma";
        this.historicoFisico = "Iniciante";
        this.objetivo = "Não definido";
    }

    // --- Métodos de Evolução e Lógica ---

    public void registrarPeso(double novoPeso) {
        if (novoPeso > 0) {
            this.historicoPeso.put(LocalDate.now(), novoPeso);
        }
    }

    public Double getPesoAtual() {
        if (historicoPeso == null || historicoPeso.isEmpty()) return 0.0;
        return historicoPeso.lastEntry().getValue();
    }

    /**
     * Calcula o Índice de Massa Corporal.
     * $IMC = \frac{peso}{altura^2}$
     */
    public double calcularIMC() {
        if (altura > 0 && getPesoAtual() > 0) {
            return getPesoAtual() / (altura * altura);
        }
        return 0;
    }






    public int getIdade() { return idade; }
    public void setIdade(int idade) { this.idade = idade; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public double getAltura() { return altura; }
    public void setAltura(double altura) { this.altura = altura; }

    public String getObjetivo() { return objetivo; }
    public void setObjetivo(String objetivo) { this.objetivo = objetivo; }

    public String getRestricoesAlimentares() { return restricoesAlimentares; }
    public void setRestricoesAlimentares(String restricoesAlimentares) { this.restricoesAlimentares = restricoesAlimentares; }

    public String getLesoes() { return lesoes; }
    public void setLesoes(String lesoes) { this.lesoes = lesoes; }

    public String getHistoricoFisico() { return historicoFisico; }
    public void setHistoricoFisico(String historicoFisico) { this.historicoFisico = historicoFisico; }

    public Treino getTreinoAtual() { return treinoAtual; }
    protected void setTreinoAtual(Treino treino) { this.treinoAtual = treino; }

    public Dieta getDietaAtual() { return dietaAtual; }
    protected void setDietaAtual(Dieta dieta) { this.dietaAtual = dieta; }

    public Map<LocalDate, Double> getHistoricoPeso() {
        return Collections.unmodifiableMap(historicoPeso);
    }

    @Override
    public String toString() {
        return String.format("Aluno: %-15s | Idade: %-3d | Status: %-10s | IMC: %.2f",
                getNome(), idade, status, calcularIMC());
    }
}
