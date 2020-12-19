package si.fri.rso.albify.imagerecognitionservice.services.clients;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.EnvironmentVariableCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionClientBuilder;
import com.amazonaws.services.rekognition.model.*;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.stream.Collectors;


@ApplicationScoped
public class AmazonRekognitionClient {

    private AmazonRekognition rekognitionClient;

    @PostConstruct
    private void init() {

        AWSCredentials credentials;
        try {
            credentials = new EnvironmentVariableCredentialsProvider().getCredentials();
        } catch (Exception e) {
            throw new AmazonClientException("Cannot initialise the credentials.", e);
        }

        rekognitionClient = AmazonRekognitionClientBuilder
                .standard()
                .withRegion(Regions.EU_CENTRAL_1)
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .build();

    }
    public List<String> getTags(String s3Image) {
        DetectLabelsRequest request = new DetectLabelsRequest()
                .withImage(new Image().withS3Object(new S3Object().withName(s3Image).withBucket("albify")))
                .withMaxLabels(10).withMinConfidence(75F);

        try {
            DetectLabelsResult result = rekognitionClient.detectLabels(request);
            List<Label> labels = result.getLabels();

            System.out.println("Detected labels for " + s3Image + "\n");
            return labels.stream().map(label -> label.getName()).collect(Collectors.toList());
        } catch (AmazonRekognitionException e) {
            e.printStackTrace();
            return null;
        }
    }

}
