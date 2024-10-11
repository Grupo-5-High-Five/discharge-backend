package school.sptech;

import org.springframework.jdbc.core.JdbcTemplate;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

        //Instancia uma nova base de dados armazenada em um bucket S3
        BaseDeDados baseDeDados = new BaseDeDados();

        // Busca a base de dados dentro do bucket.
        baseDeDados.getBaseDeDados();

        //Instância um novo arquivo (Base de dados em formato de tabela, tratada, pronta para inserção no banco)
        Arquivo arquivo = new Arquivo();

        //Inserindo o total de 96 leituras, um dia fiscal
        try {
            arquivo.inserirLeiturasNoBanco();
            System.out.println("\nDia de leituras inserido no banco de dados com sucesso!");
        } catch (RuntimeException err) {
            System.err.println(err.getMessage());
        }

    }
}