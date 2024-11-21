package school.sptech;

import com.slack.api.Slack;
import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.request.chat.ChatPostMessageRequest;
import com.slack.api.methods.response.chat.ChatPostMessageResponse;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Mensagem {

    private static final Slack slack = Slack.getInstance();
    private static final String token = System.getenv("TOKEN");

    public static void enviarMensagem(String mensagem) throws SlackApiException, IOException {

        MethodsClient methods = slack.methods(token);

        ChatPostMessageRequest request = ChatPostMessageRequest.builder()
                .channel("C080F659GG0")
                .text(mensagem)
                .build();


        ChatPostMessageResponse response = methods.chatPostMessage(request);

        if (response.isOk()){
            System.out.println("Mensagem enviada com sucesso!");
        } else {
            System.err.println("Erro ao enviar a mensagem: " + response.getError());
        }
    }

    public static void salvarMensagemNoBanco(String mensagem){

        DBConnectionProvider dbConnectionProvider = new DBConnectionProvider();
        JdbcTemplate con = dbConnectionProvider.getConnection();

        String insert = """
                INSERT INTO historico_mensagens(mensagem, data_hora, fkEmpresa)
                VALUES(?, NOW(), 1)
                """;

        try {
            con.execute("SELECT * FROM leitura;");
            con.update(insert, mensagem);// Insere a mensagem no banco de dados
            System.out.println("Mensagem inserida com sucesso!");
        } catch (Exception e) {
            System.err.println("Erro ao salvar a mensagem no banco de dados: " + e.getMessage());
        }
    }
}
