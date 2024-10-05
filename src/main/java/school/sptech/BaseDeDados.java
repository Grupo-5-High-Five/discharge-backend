package school.sptech;

import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.ListObjectsRequest;
import software.amazon.awssdk.services.s3.model.S3Object;

import java.nio.file.Paths;
import java.util.List;

public class BaseDeDados {

    private final S3Provider session = new S3Provider();

    private String caminhoParaInstalacao = "C:/Users/Gusta/Downloads/base-de-dados.xlsx";

    public String getCaminhoParaInstalacao() {
        return caminhoParaInstalacao;
    }

    public S3Object getBaseDeDados() {

        ListObjectsRequest listObjects = ListObjectsRequest.builder()
                .bucket("discharge-bucket")
                .build();
        List<S3Object> objects = session.getS3Client().listObjects(listObjects).contents();

        // Define o arquivo da base de dados como o primeiro da lista e retorna
        return objects.getFirst();
    }

    public void instalarBaseDeDados(){
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket("discharge-bucket")
                .key(getBaseDeDados().key())
                .build();

        try {
            session.getS3Client().getObject(getObjectRequest, Paths.get(caminhoParaInstalacao));
        }
        catch (SdkClientException e) {
            throw new RuntimeException("Arquivo já instalado no diretório!");
        }
    }


}
