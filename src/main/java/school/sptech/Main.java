package school.sptech;

public class Main {

    public static void main(String[] args) {

        //Instancia uma nova S3.
        BaseDeDados baseDeDados = new BaseDeDados();

        // Busca a base de dados dentro do bucket.
        baseDeDados.getBaseDeDados();

        // Faz a instalação da base de dados na máquina.

        try {
            baseDeDados.instalar();
            System.out.println("Arquivo instalado no caminho " + baseDeDados.getCaminhoParaInstalacao());
        }
        catch (RuntimeException err) {
            System.err.println("Arquivo já instalado no diretório!");
        }



        Arquivo arquivo = new Arquivo();







    }
}