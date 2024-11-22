package school.sptech;

public class LeituraExcedente extends Leitura {


    static final Double LIMITE_CONSUMO = 100.00;
    static final Double LIMITE_EMISSAO = 0.04;


    public LeituraExcedente(
            String data, Double consumo, Double potenciaReativaAtrasada,
            Double potenciaReativaAdiantada, Double emissao,
            Double fatorPotenciaAtrasado, Double fatorPotenciaAdiantado, String statusSemana, String diaSemana
    ) {
        super(
                data, consumo, potenciaReativaAtrasada,
                potenciaReativaAdiantada, emissao, fatorPotenciaAtrasado,
                fatorPotenciaAdiantado, statusSemana, diaSemana
        );
    }


    public LeituraExcedente() {

    }


    public static Double getLimiteConsumo() {
        return LIMITE_CONSUMO;
    }

    public static Double getLimiteEmissao() {
        return LIMITE_EMISSAO;
    }


    public Double consumoExcedente() {
        return LIMITE_CONSUMO - getConsumo();
    }

    public Double emissaoExcedente() {
        return LIMITE_EMISSAO - getEmissao();
    }
}
