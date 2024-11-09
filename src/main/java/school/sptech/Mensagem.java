package school.sptech;

import com.slack.api.Slack;
import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.request.chat.ChatPostMessageRequest;
import com.slack.api.methods.response.chat.ChatPostMessageResponse;
import java.io.IOException;

public class Mensagem {

    public void enviarMensagem() throws SlackApiException, IOException {

        Slack slack = Slack.getInstance();

        String token = System.getenv("TOKEN");

        MethodsClient methods = slack.methods(token);

        ChatPostMessageRequest request = ChatPostMessageRequest.builder()
                .channel("C080F659GG0")
                .text("👋 Olá, Equipe Discharge! :)")
                .build();


        ChatPostMessageResponse response = methods.chatPostMessage(request);

        if (response.isOk()){
            System.out.println("Mensagem enviada com sucesso!");
        }else {
            System.err.println("Erro ao enviar a mensagem: " + response.getError());
        }
    }

}
