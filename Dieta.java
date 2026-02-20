package br.com.consultoria.model;

import java.io.Serializable;
import java.time.LocalDate;

public class Dieta implements Plano, Serializable {

    private static final long serialVersionUID = 1L;

    private String detalhesAlimentares;
    private double caloriasDiarias;
    private LocalDate dataPrescricao;

    public Dieta(String detalhes, double calorias) {
        this.detalhesAlimentares = detalhes;
        this.caloriasDiarias = calorias;
        this.dataPrescricao = LocalDate.now();
    }

    @Override
    public String gerarResumo() {
        return "DIETA [" + dataPrescricao + "]: " + detalhesAlimentares +
                " | Meta: " + caloriasDiarias + " kcal/dia.";
    }

    public String getDetalhesAlimentares() { return detalhesAlimentares; }
    public double getCaloriasDiarias() { return caloriasDiarias; }
    public LocalDate getDataPrescricao() { return dataPrescricao; }

    protected void setDetalhesAlimentares(String detalhes) { this.detalhesAlimentares = detalhes; }
    protected void setCaloriasDiarias(double calorias) { this.caloriasDiarias = calorias; }
}
