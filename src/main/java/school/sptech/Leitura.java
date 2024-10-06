package school.sptech;

import java.time.LocalDateTime;

public class Leitura {

    private LocalDateTime data;
    private Double consumo;
    private Double potenciaReativaAtrasada;
    private Double PotenciaReativaAdiantada;
    private Double emissao;
    private Double fatorPotenciaAtrasado;
    private Double fatorPotenciaAdiantado;
    private String diaSemana;
    private String statusSeamana;

    public Leitura() {
    }

    public Leitura(Integer id, LocalDateTime data, Double consumo, Double potenciaReativaAtrasada,
                   Double potenciaReativaAdiantada, Double emissao, Double fatorPotenciaAtrasado,
                   Double fatorPotenciaAdiantado, String diaSemana
    ) {
        this.data = data;
        this.consumo = consumo;
        this.potenciaReativaAtrasada = potenciaReativaAtrasada;
        this.PotenciaReativaAdiantada = potenciaReativaAdiantada;
        this.emissao = emissao;
        this.fatorPotenciaAtrasado = fatorPotenciaAtrasado;
        this.fatorPotenciaAdiantado = fatorPotenciaAdiantado;
        this.diaSemana = diaSemana;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public Double getConsumo() {
        return consumo;
    }

    public void setConsumo(Double consumo) {
        this.consumo = consumo;
    }

    public Double getPotenciaReativaAtrasada() {
        return potenciaReativaAtrasada;
    }

    public void setPotenciaReativaAtrasada(Double potenciaReativaAtrasada) {
        this.potenciaReativaAtrasada = potenciaReativaAtrasada;
    }

    public Double getPotenciaReativaAdiantada() {
        return PotenciaReativaAdiantada;
    }

    public void setPotenciaReativaAdiantada(Double potenciaReativaAdiantada) {
        PotenciaReativaAdiantada = potenciaReativaAdiantada;
    }

    public Double getEmissao() {
        return emissao;
    }

    public void setEmissao(Double emissao) {
        this.emissao = emissao;
    }

    public Double getFatorPotenciaAtrasado() {
        return fatorPotenciaAtrasado;
    }

    public void setFatorPotenciaAtrasado(Double fatorPotenciaAtrasado) {
        this.fatorPotenciaAtrasado = fatorPotenciaAtrasado;
    }

    public Double getFatorPotenciaAdiantado() {
        return fatorPotenciaAdiantado;
    }

    public void setFatorPotenciaAdiantado(Double fatorPotenciaAdiantado) {
        this.fatorPotenciaAdiantado = fatorPotenciaAdiantado;
    }

    public String getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(String diaSemana) {
        this.diaSemana = diaSemana;
    }

    public String getStatusSeamana() {
        return statusSeamana;
    }

    public void setStatusSeamana(String statusSeamana) {
        this.statusSeamana = statusSeamana;
    }
}



