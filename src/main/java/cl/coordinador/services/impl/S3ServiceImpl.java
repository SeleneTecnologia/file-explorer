package cl.coordinador.services.impl;

import cl.coordinador.services.S3Service;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.nio.file.Paths;

@ApplicationScoped
public class S3ServiceImpl implements S3Service {

    private final S3Client s3Client;

    @ConfigProperty(name = "quarkus.s3.bucket.name")
    String bucketName;

    @Inject
    public S3ServiceImpl(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    @Override
    public void uploadFile(String fileName, String filePath) {
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(fileName)
                .build();
        s3Client.putObject(putObjectRequest, Paths.get(filePath));
    }
}
