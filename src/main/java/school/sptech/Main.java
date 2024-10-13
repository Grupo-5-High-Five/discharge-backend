package school.sptech;

import org.apache.log4j.PropertyConfigurator;

public class Main {

    public static void main(String[] args){

        String log4jConfPath = "target/log4j.properties";
        PropertyConfigurator.configure(log4jConfPath);

        //Instancia uma nova base de dados armazenada em um bucket S3
        System.out.println("Iniciando conexão com a AWS...\n" + "");
        BaseDeDados baseDeDados = new BaseDeDados();

        // Busca a base de dados dentro do bucket.
        System.out.println("Buscando a base de dados dentro do bucket selecionado...");
        baseDeDados.getBaseDeDados();

        //Instância um novo arquivo (Base de dados em formato de tabela, tratada, pronta para inserção no banco)
        Arquivo arquivo = new Arquivo();

        //Inserindo o total de 96 leituras, um dia fiscal
        arquivo.inserirLeiturasNoBanco();
        System.out.println("Dia de leituras inserido no banco de dados com sucesso!");
    }
}