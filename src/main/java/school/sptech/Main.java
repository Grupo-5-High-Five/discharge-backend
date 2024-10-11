package school.sptech;

import org.springframework.jdbc.core.JdbcTemplate;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

        //Instancia uma nova base de dados armazenada em um bucket S3
        BaseDeDados baseDeDados = new BaseDeDados();

        // Busca a base de dados dentro do bucket.
        baseDeDados.getBaseDeDados();


        // Tenta instalar a base de dados
        System.out.println("Instalando base de dados...");
        try {
            baseDeDados.instalar();
            System.out.println("Arquivo instalado no caminho " + baseDeDados.getCaminhoParaInstalacao());
        }
        // caso não consiga, a base já está instalada na máquina
        catch (RuntimeException err) {
            System.err.println("Base de dados já instalada no diretório: " + baseDeDados.getCaminhoParaInstalacao() + "\n");
        }

        //Instância um novo arquivo (Base de dados em formato de tabela, tratada, pronta para inserção no banco)
        Arquivo arquivo = new Arquivo();

        //Inserindo o total de 96 leituras, um
        try {
            arquivo.inserirLeiturasNoBanco();
            System.out.println("\nDia de leituras inserido no banco de dados com sucesso!");
        } catch (RuntimeException err) {
            System.err.println(err.getMessage());
        }

    }
}