package school.sptech;

import software.amazon.awssdk.auth.credentials.AwsSessionCredentials;
import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.ListObjectsRequest;
import software.amazon.awssdk.services.s3.model.S3Object;

import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

public class S3Provider {

    private final AwsSessionCredentials credentials;
    private String caminhoParaInstalacao = "C:/Users/Gusta/Downloads/base-de-dados.xlsx";

    private final String bucketName = "discharge-bucket";

    public S3Provider() {
        this.credentials = AwsSessionCredentials.create(
                System.getenv("AWS_ACCESS_KEY_ID"),
                System.getenv("AWS_SECRET_ACCESS_KEY"),
                System.getenv("AWS_SESSION_TOKEN")
        );
    }

    public S3Client getS3Client() {
        return S3Client.builder()
                .region(Region.US_EAST_1)
                .credentialsProvider(() -> credentials)
                .build();
    }

    public String getCaminhoParaInstalacao() {
        return caminhoParaInstalacao;
    }

    public S3Object buscarbaseDeDados() {

        ListObjectsRequest listObjects = ListObjectsRequest.builder()
                .bucket("discharge-bucket")
                .build();
        List<S3Object> objects = getS3Client().listObjects(listObjects).contents();

        // Define o arquivo da base de dados como o primeiro da lista e retorna
        return objects.getFirst();
    }

    public void instalarBaseDeDados(){
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket("discharge-bucket")
                .key(buscarbaseDeDados().key())
                .build();

        try {
            getS3Client().getObject(getObjectRequest, Paths.get(caminhoParaInstalacao));
        }
        catch (SdkClientException e) {
            throw new RuntimeException("Arquivo já instalado no diretório!");
        }
    }
}
