package si.fri.rso.albify.imagerecognitionservice.services.beans;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.InsertOneResult;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.types.ObjectId;
import si.fri.rso.albify.imagerecognitionservice.lib.Image;
import si.fri.rso.albify.imagerecognitionservice.models.converters.ImageConverter;
import si.fri.rso.albify.imagerecognitionservice.models.entities.ImageEntity;

import javax.enterprise.context.RequestScoped;
import java.util.Date;
import java.util.logging.Logger;

import static com.mongodb.client.model.Filters.*;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

@RequestScoped
public class ImageBean {

    private Logger log = Logger.getLogger(ImageBean.class.getName());

    private CodecRegistry pojoCodecRegistry = fromRegistries(
            MongoClientSettings.getDefaultCodecRegistry(),
            fromProviders(PojoCodecProvider.builder().automatic(true).build()));

    private MongoClientSettings settings = MongoClientSettings.builder()
            .applyConnectionString(new ConnectionString(System.getenv("DB_URL")))
            .codecRegistry(pojoCodecRegistry)
            .build();


    private MongoClient mongoClient = MongoClients.create(settings);
    private MongoDatabase database = mongoClient.getDatabase("albify");
    private MongoCollection<ImageEntity> imagesCollection = database.getCollection("images", ImageEntity.class);


    /**
     * Returns image by its ID.
     * @param imageId Image ID.
     * @param userId User ID.
     * @return whether the provided image belongs to the provided user.
     */
    public boolean isOwner(String imageId, String userId) {
        try {
            ImageEntity entity = imagesCollection.find(
                    and(
                            eq("_id", new ObjectId(imageId)),
                            eq("ownerId", new ObjectId(userId))
                    )
            ).first();
            if (entity != null && entity.getId() != null) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Returns image by its ID.
     * @param imageId Image ID.
     * @return Image entity.
     */
    public ImageEntity getImage(String imageId) {
        try {
            ImageEntity entity = imagesCollection.find(eq("_id", new ObjectId(imageId))).first();
            if (entity != null && entity.getId() != null) {
                return entity;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * Creates new image.
     * @param image to create.
     * @return Newly created image.
     */
    public ImageEntity createImage(Image image) {
        try {
            ImageEntity entity = ImageConverter.toEntity(image);
            entity.setCreatedAt(new Date());
            entity.setOwnerId(new ObjectId(image.getOwnerId()));
            entity.setUrl(image.getUrl());

            InsertOneResult result = imagesCollection.insertOne(entity);
            if (result != null) {
                return entity;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Removes image.
     * @param imageId Image ID.
     * @return Deleted album.
     */
    public ImageEntity removeImage(String imageId) {
        try {
            ImageEntity entity = imagesCollection.findOneAndDelete(eq("_id", new ObjectId(imageId)));
            if (entity != null && entity.getId() != null) {
                return entity;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
