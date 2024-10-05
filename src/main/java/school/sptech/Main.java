package school.sptech;

public class Main {

    public static void main(String[] args) {

        //Instancia uma nova S3.
        S3Provider s3 = new S3Provider();

        // Busca a base de dados dentro do bucket.
        s3.buscarbaseDeDados();

        // Faz a instalação da base de dados na máquina.
        s3.instalarBaseDeDados();

        System.out.println("Arquivo instalado no caminho " + s3.getCaminhoParaInstalacao());




    }
}