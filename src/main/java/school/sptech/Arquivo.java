package school.sptech;


import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Arquivo {

    private final BaseDeDados baseDeDados = new BaseDeDados();

    private final Path caminho = Path.of("C:\\Users\\Gusta\\Downloads\\base-de-dados.xlsx");

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

        //Instânciando uma conexão com o banco de dados
        DBConnectionProvider database = new DBConnectionProvider();

        // Criação dos formatadores de data, tanto pra excel, quanto para o banco de dados
        DataFormatter dataFormatter = new DataFormatter();
        DateTimeFormatter excelFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"); // Formato da data no Excel
        DateTimeFormatter dbFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"); // Formato de data para o banco de dados




        for (Row linha : tabela) { // pegando linha da tabela

            Cell primeiraColuna = linha.getCell(0); // aqui pegamos a primeira coluna (data), de todas as linhas da tabela

            if (primeiraColuna != null) {
                String valorColuna = dataFormatter.formatCellValue(primeiraColuna); // Formatar o valor da coluna como string

                //esse Bloco tenta tratar a data e inserí-la no banco
                try {
                    // Tenta converter o valor da coluna para LocalDateTime, assumindo que seja uma data no formato dd/MM/yyyy HH:mm
                    LocalDateTime data = LocalDateTime.parse(valorColuna, excelFormatter);

                    // Convertendo a data para o formato do banco de dados
                    String dataFormatada = data.format(dbFormatter);

                    // Inserir no banco de dados
//                    String sql = "INSERT INTO leituras (data_coluna) VALUES (?)"; A conexão com o banco não está completa
//                    database.getConnection().update(sql, dataFormatada);

                    //Caso não consiga, aqui a exeção é tratada e o erro é exibido
                } catch (Exception e) {
                    System.out.println("Erro ao converter a célula em LocalDateTime: " + e.getMessage());
                }
            }
        }
    }

}



