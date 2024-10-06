package school.sptech;

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
            System.err.println("Base de dados já instalada no diretório!");
        }

        //Instância um novo arquivo (Base de dados em formato de tabela, pronta para tratamento e inserção)
        Arquivo arquivo = new Arquivo();


        //Tenta carregar o arquivo, o que o carrega e trata o arquivo
        try {
            arquivo.carregarArquivo();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }



    }
}