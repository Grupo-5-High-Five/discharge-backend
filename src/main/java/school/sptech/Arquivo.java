package school.sptech;


import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Arquivo {

    private final BaseDeDados baseDeDados = new BaseDeDados();

    private final Path caminho = Path.of("C:\\Users\\Gusta\\Downloads\\base-de-dados.xlsx");

    private List<Leitura> leituras = new ArrayList<>();

    public Arquivo() {
        try {
            carregarArquivo();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void carregarArquivo() throws IOException {

        InputStream arquivo = Files.newInputStream(caminho);
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
            Leitura leitura = new Leitura(data, consumo, potenciaReativaAtrasada,
                    potenciaReativaAdiantada, emissao,
                    fatorPotenciaReativaAtrasada, fatorPotenciaReativaAdiantada,
                    statusSemana, diaDaSemana);

            //E a cada leitura criada, é adicionado a lista de leituras
            leituras.add(leitura);
        }

    }



    public void inserirLeiturasNoBanco(){

        DBConnectionProvider dbConnectionProvider = new DBConnectionProvider();

        JdbcTemplate con = dbConnectionProvider.getConnection();

        String insert = """
                     
                INSERT INTO Leitura (
                     data, consumo, potenciaReativaAtrasada, potenciaReativaAdiantada,
                     emissao, fatorPotenciaAtrasado, fatorPotenciaAdiantado,statusSemana, diaSemana)
                                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);
                     """;

        String checarExistencia = "SELECT COUNT(*) FROM Leitura WHERE data = ?;";

        int inseridos = 0;

        for (Leitura leitura : leituras) {

            if(inseridos == 96){
                break;
            }

             String data = leitura.getData();
             Double consumo = leitura.getConsumo();
             Double potenciaReativaAtrasada = leitura.getPotenciaReativaAtrasada();
             Double potenciaReativaAdiantada = leitura.getPotenciaReativaAdiantada();
             Double emissao = leitura.getEmissao();
             Double fatorPotenciaAtrasado = leitura.getFatorPotenciaAtrasado();
             Double fatorPotenciaAdiantado = leitura.getFatorPotenciaAdiantado();
             String statusSeamana = leitura.getStatusSeamana();
             String diaSemana = leitura.getDiaSemana();

             Integer existe = con.queryForObject(checarExistencia, Integer.class, data);

             if(existe != null || existe != 0){

                 try {
                     con.update(
                             insert, data, consumo, potenciaReativaAtrasada,
                             potenciaReativaAdiantada, emissao, fatorPotenciaAtrasado,
                             fatorPotenciaAdiantado, statusSeamana, diaSemana
                     );
                     inseridos++;
                 }
                 catch (RuntimeException e) {
                     throw new RuntimeException(e);
                 }
             }
             


        }
    }

}



