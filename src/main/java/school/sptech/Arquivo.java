package school.sptech;


import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

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

        List<Leitura> leituras = new ArrayList<>();

        //Instânciando uma conexão com o banco de dados
        DBConnectionProvider database = new DBConnectionProvider();

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

            System.out.println(diaDaSemana);

            Leitura leitura = new Leitura(data, consumo, potenciaReativaAtrasada,
                    potenciaReativaAdiantada, emissao,
                    fatorPotenciaReativaAtrasada, fatorPotenciaReativaAdiantada,
                    statusSemana, diaDaSemana);

            leituras.add(leitura);
            System.out.println(leitura);
        }

    }

}



