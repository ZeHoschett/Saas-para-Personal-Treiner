package br.com.consultoria.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PlataformaSaaS implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<PersonalTrainer> listaPersonais = new ArrayList<>();

    public void cadastrarPersonal(PersonalTrainer p) {
        listaPersonais.add(p);
    }

    public List<PersonalTrainer> getListaPersonais() {
        return listaPersonais;
    }
}
