package school.sptech;



import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class Arquivo {

    private final BaseDeDados baseDeDados = new BaseDeDados();

    private Path caminho = Path.of("C:/Users/Gusta/Downloads/base-de-dados.xlsx");

    public Arquivo() {
        try {
            carregarArquivo();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void carregarArquivo() throws IOException {

        try (InputStream arquivo = Files.newInputStream(caminho);
             Workbook workbook = new XSSFWorkbook(arquivo)) {

        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

}



