package school.sptech;



import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

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

        Sheet sheet = workbook.getSheetAt(0);


        for (Row linha : sheet) {


            for (Cell celula : linha) {
                switch (celula.getCellType()) {
                    case STRING:
                        System.out.print(celula.getStringCellValue() + "\t");
                        break;
                    case NUMERIC:
                        System.out.print(celula.getNumericCellValue() + "\t");
                        break;
                    case BOOLEAN:
                        System.out.print(celula.getBooleanCellValue() + "\t");
                        break;
                    default:
                        System.out.print("Tipo desconhecido\t");
                }
            }
            System.out.println(); // Nova linha após cada linha da planilha
        }
    }

}



