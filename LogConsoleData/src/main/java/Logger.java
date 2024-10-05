import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {

    public enum LogLevel {
        INFO, WARNING, ERROR, DEBUG
    }

    public static void log(LogLevel level, String mensagem) {

        LocalDateTime agora = LocalDateTime.now();

        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        StackTraceElement caller = stackTrace[2];

        String className = caller.getClassName();
        String methodName = caller.getMethodName();
        int lineNumber = caller.getLineNumber();

        System.out.printf("[%s] [%s] [Thread: %s] [%s.%s:%d] %s%n",
                agora.format(formatador), level, Thread.currentThread().getName(),
                className, methodName, lineNumber, mensagem);
    }

    public static void main(String[] args) {
        log(LogLevel.INFO, "Iniciando o programa");
        realizarTarefa();
        log(LogLevel.ERROR, "Erro ao processar tarefa");
        log(LogLevel.DEBUG, "Tarefa finalizada com sucesso");
    }

    public static void realizarTarefa() {
        log(LogLevel.WARNING, "Atenção, a tarefa pode demorar");
    }
}

