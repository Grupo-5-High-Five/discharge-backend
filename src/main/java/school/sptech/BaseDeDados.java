package school.sptech;

import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.ListObjectsRequest;
import software.amazon.awssdk.services.s3.model.S3Object;

import java.io.InputStream;
import java.util.List;

public class BaseDeDados {

    private final S3Provider session;

    public BaseDeDados() {
        this.session = new S3Provider();
    }

    private final String bucketName = "discharge-bucket";

    public S3Object getBaseDeDados() {

        ListObjectsRequest listObjects = ListObjectsRequest.builder()
                .bucket(bucketName)
                .build();
        List<S3Object> objects = session.getS3Client().listObjects(listObjects).contents();

        return objects.getFirst();
    }


    public InputStream getInputStream() {
        S3Object baseDeDados = getBaseDeDados();

        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(baseDeDados.key())
                .build();

        ResponseInputStream<?> inputStream = session.getS3Client().getObject(getObjectRequest);

        return inputStream; //
    }


}
