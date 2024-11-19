package school.sptech;

public class Leitura {

    private String data;
    private Double consumo;
    private Double potenciaReativaAtrasada;
    private Double PotenciaReativaAdiantada;
    private Double emissao;
    private Double fatorPotenciaAtrasado;
    private Double fatorPotenciaAdiantado;
    private String statusSemana;
    private String diaSemana;


    public Leitura() {

    }

    public Leitura(String data, Double consumo, Double potenciaReativaAtrasada,
                   Double potenciaReativaAdiantada, Double emissao, Double fatorPotenciaAtrasado,
                   Double fatorPotenciaAdiantado, String statusSemana, String diaSemana) {
        this.data = data;
        this.consumo = consumo;
        this.potenciaReativaAtrasada = potenciaReativaAtrasada;
        this.PotenciaReativaAdiantada = potenciaReativaAdiantada;
        this.emissao = emissao;
        this.fatorPotenciaAtrasado = fatorPotenciaAtrasado;
        this.fatorPotenciaAdiantado = fatorPotenciaAdiantado;
        this.statusSemana = statusSemana;
        this.diaSemana = diaSemana;
    }

    public String getData() {
        return data;
    }


    public Double getConsumo() {
        return consumo;
    }


    public Double getPotenciaReativaAtrasada() {
        return potenciaReativaAtrasada;
    }


    public Double getPotenciaReativaAdiantada() {
        return PotenciaReativaAdiantada;
    }

    public Double getEmissao() {
        return emissao;
    }

    public Double getFatorPotenciaAtrasado() {
        return fatorPotenciaAtrasado;
    }

    public Double getFatorPotenciaAdiantado() {
        return fatorPotenciaAdiantado;
    }

    public String getDiaSemana() {
        return diaSemana;
    }

    public String getStatusSeamana() {
        return statusSemana;
    }

    @Override
    public String toString() {
        return "Leitura{" +
                "data='" + data + '\'' +
                ", consumo=" + consumo +
                ", potenciaReativaAtrasada=" + potenciaReativaAtrasada +
                ", PotenciaReativaAdiantada=" + PotenciaReativaAdiantada +
                ", emissao=" + emissao +
                ", fatorPotenciaAtrasado=" + fatorPotenciaAtrasado +
                ", fatorPotenciaAdiantado=" + fatorPotenciaAdiantado +
                ", diaSemana='" + diaSemana + '\'' +
                ", statusSeamana='" + statusSemana + '\'' +
                '}';
    }
}



