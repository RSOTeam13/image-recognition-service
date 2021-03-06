package si.fri.rso.albify.imagerecognitionservice.api.v1.resources;

import com.kumuluz.ee.cors.annotations.CrossOrigin;
import org.eclipse.microprofile.metrics.annotation.Metered;
import si.fri.rso.albify.imagerecognitionservice.lib.ImagePath;
import si.fri.rso.albify.imagerecognitionservice.services.clients.AmazonRekognitionClient;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;
import java.util.logging.Logger;

@ApplicationScoped
@Path("/recognition")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@CrossOrigin(name = "image-recognition-resource")
public class ImageRecognitionResource {

    private Logger log = Logger.getLogger(ImageRecognitionResource.class.getName());

    @Inject
    private AmazonRekognitionClient recognitionClient;

    @Context
    protected UriInfo uriInfo;

    @Metered(name = "recognition_requests")
    @POST
    public Response imageRecognition(ImagePath imagePath) {
        try {

            List<String> tags = recognitionClient.getTags(imagePath.getImagePath());
            return Response.status(Response.Status.OK)
                    .entity(tags)
                    .build();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return  Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }

}
