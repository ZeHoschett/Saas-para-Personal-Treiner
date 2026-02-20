package br.com.consultoria.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Treino implements Plano, Serializable {

    // Versão da classe para o processo de salvamento
    private static final long serialVersionUID = 1L;

    private String descricaoExercicios;
    private String objetivo;
    private LocalDate dataCriacao;
    private LocalDate dataVencimento;

    // Construtor principal
    public Treino(String descricao, String objetivo, int diasValidade) {
        this.descricaoExercicios = descricao;
        this.objetivo = objetivo;
        this.dataCriacao = LocalDate.now();
        // Lógica de Data: calcula automaticamente o vencimento
        this.dataVencimento = dataCriacao.plusDays(diasValidade);
    }

    // --- Métodos de Regra de Negócio ---

    public boolean estaVencido() {
        if (dataVencimento == null) return false;
        return LocalDate.now().isAfter(dataVencimento);
    }

    public long diasParaVencer() {
        if (dataVencimento == null) return 0;
        return ChronoUnit.DAYS.between(LocalDate.now(), dataVencimento);
    }

    @Override
    public String gerarResumo() {
        String status = estaVencido() ? " [VENCIDO]" : " [Ativo - Faltam " + diasParaVencer() + " dias]";

        return "TREINO (" + objetivo + ")" +
                "\nCriado em: " + dataCriacao +
                "\nVence em: " + dataVencimento + status +
                "\nExercícios: " + descricaoExercicios;
    }

    // --- Getters e Setters ---

    public String getDescricaoExercicios() { return descricaoExercicios; }
    public String getObjetivo() { return objetivo; }
    public LocalDate getDataCriacao() { return dataCriacao; }
    public LocalDate getDataVencimento() { return dataVencimento; }

    public void setDescricaoExercicios(String desc) { this.descricaoExercicios = desc; }
    public void setDataVencimento(LocalDate novaData) { this.dataVencimento = novaData; }
}
