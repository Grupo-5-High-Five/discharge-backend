package school.sptech;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.jdbc.core.JdbcTemplate;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Arquivo {

    private List<Leitura> leituras = new ArrayList<>();
    private final BaseDeDados baseDeDados = new BaseDeDados();
    private final DBConnectionProvider dbConnectionProvider = new DBConnectionProvider();

    public Arquivo() {
        try {
            carregarArquivo();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void carregarArquivo() throws IOException {
        InputStream arquivo = baseDeDados.getInputStream();
        Workbook workbook = new XSSFWorkbook(arquivo);

        Sheet tabela = workbook.getSheetAt(0);

        formataArquivo(tabela);
    }

    public void formataArquivo(Sheet tabela) {

        // Criação dos formatadores de data, tanto pra excel, quanto para o banco de dados

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"); // esse formatador pega nossa string da primeira coluna que é a data e transforma em um objeto da classe LocalDateTime

        DateTimeFormatter jFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        for (int i = 1; i <= tabela.getLastRowNum(); i++) {
            //Formatando a coluna de data
            Row linha = tabela.getRow(i);

            Cell colunaData = linha.getCell(0);

            LocalDateTime dataJ = LocalDateTime.parse(colunaData.toString(), formatter);
            String data = dataJ.format(jFormatter);

            //Formatando a coluna de consumo

            Cell colunaConsmo = linha.getCell(1);
            Double consumo = colunaConsmo.getNumericCellValue();

            //Formatando a coluna de potencia reativa atrasada

            Cell colunaPotenciaReativaAtrasada = linha.getCell(2);
            Double potenciaReativaAtrasada = colunaPotenciaReativaAtrasada.getNumericCellValue();

            //Formatando a coluna de potencia reativa adiantada
            Cell colunaPoteniaReativaAdiantada = linha.getCell(3);
            Double potenciaReativaAdiantada = colunaPoteniaReativaAdiantada.getNumericCellValue();

            // Formatando a coluna de co2

            Cell colunaCo2 = linha.getCell(4);
            Double emissao = colunaCo2.getNumericCellValue();

            // Formatando a coluna fatorPotenciaAtrasado
            Cell colunaFatorPotenciaReativaAtrasada = linha.getCell(5);
            Double fatorPotenciaReativaAtrasada = colunaFatorPotenciaReativaAtrasada.getNumericCellValue();

            //Formatando a coluna fatorPotenciaReativaAdiantado
            Cell colunaFatorPotenciaReativaAdiantada = linha.getCell(6);
            Double fatorPotenciaReativaAdiantada = colunaFatorPotenciaReativaAdiantada.getNumericCellValue();

            //Formatando a coluna diaSemana
            Cell colunaStatusSemana = linha.getCell(8);
            String statusSemana = colunaStatusSemana.getStringCellValue();

            //Formatando a coluna
            Cell colunaDiaDaSemana = linha.getCell(9);
            String diaDaSemana = colunaDiaDaSemana.getStringCellValue();

            //A cada vez que os dados de uma linha é formatado, é instanciada uma nova leitura com todos atributos necessários

            if(consumo > LeituraExcedente.LIMITE_CONSUMO || emissao > LeituraExcedente.LIMITE_EMISSAO) {
                leituras.add(new LeituraExcedente(data, consumo, potenciaReativaAtrasada,
                        potenciaReativaAdiantada, emissao,
                        fatorPotenciaReativaAtrasada, fatorPotenciaReativaAdiantada,
                        statusSemana, diaDaSemana));
            } else {
                leituras.add(new Leitura(data, consumo, potenciaReativaAtrasada,
                        potenciaReativaAdiantada, emissao,
                        fatorPotenciaReativaAtrasada, fatorPotenciaReativaAdiantada,
                        statusSemana, diaDaSemana));
            }

        }


        System.out.println("Foi encontrado um arquivo com: " + leituras.size()   + " leituras.\n");
    }




    public void inserirLeiturasNoBanco() {

        System.out.println("Iniciando conexão com o banco de dados...");
        JdbcTemplate con = dbConnectionProvider.getConnection();

        String insert = """
        
               INSERT INTO leitura (
                   dt, consumo, potencia_reativa_atrasada, potencia_reativa_adiantada,
                   emissao, fator_potencia_atrasado, fator_potencia_adiantado, status_semana, dia_semana, fkempresa)
               VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);
               
        """;

        try {
            String buscarDatasExistentes = "SELECT dt FROM leitura";
            List<String> datasExistentes = con.queryForList(buscarDatasExistentes, String.class);
            Set<String> datasExistentesSet = new HashSet<>(datasExistentes);

            int qtdExcedentes = 0;
            int inseridos = 0;
            String mensagem = "";

            for (Leitura leitura : leituras) {

                if (inseridos == 96) {
                    break;
                }

                if(leitura instanceof LeituraExcedente) {
                    qtdExcedentes ++;
                }


                if (!datasExistentesSet.contains(leitura.getData())) {
                    con.update(
                            insert, leitura.getData(), leitura.getConsumo(), leitura.getPotenciaReativaAtrasada(),
                            leitura.getPotenciaReativaAdiantada(), leitura.getEmissao(), leitura.getFatorPotenciaAtrasado(),
                            leitura.getFatorPotenciaAdiantado(), leitura.getStatusSeamana(), leitura.getDiaSemana(), 1
                    );

                    inseridos++;

                    if (inseridos == 1) {
                        System.out.println("Conexão realizada com sucesso!\n");
                    }

                    System.out.println("Leitura de: " + leitura.getData() + " inserida no banco de dados com sucesso\n");
                }
            }

            mensagem = "Foram identificadas " + qtdExcedentes + " leituras nesse dia.\n";

            try{
                Mensagem.enviarMensagem(mensagem);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage() + " Não foi possível realizar a conexão com o banco de dados!");
        }
    }

}
