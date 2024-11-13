package school.sptech;

import com.slack.api.methods.SlackApiException;
import org.apache.log4j.PropertyConfigurator;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws SlackApiException, IOException {

        String log4jConfPath = "target/log4j.properties";
        PropertyConfigurator.configure(log4jConfPath);

        //Instancia uma nova base de dados armazenada em um bucket S3
        System.out.println("Iniciando conexão com a AWS e com o bucket S3...\n");
        BaseDeDados baseDeDados = new BaseDeDados();

        // Busca a base de dados dentro do bucket.
        System.out.println("Buscando a base de dados dentro do bucket selecionado...");
        baseDeDados.getBaseDeDados();

        //Instância um novo arquivo (Base de dados em formato de tabela, tratada, pronta para inserção no banco)
        Arquivo arquivo = new Arquivo();

        //Inserindo o total de 480 leituras, 5 dias fiscais
        arquivo.inserirLeiturasNoBanco();
        System.out.println("Dia de leituras inserido no banco de dados com sucesso!");

        Mensagem mensagem = new Mensagem();

        try {
            // Chama o método para enviar a mensagem e salvar no banco
            mensagem.enviarMensagem();
            mensagem.salvarMensagemNoBanco("");
        } catch (SlackApiException | IOException e) {
            System.err.println("Erro ao enviar a mensagem para o Slack: " + e.getMessage());
        }
    }
}