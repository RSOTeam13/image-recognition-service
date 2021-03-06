package si.fri.rso.albify.imagerecognitionservice.models.entities;

import org.bson.types.ObjectId;
import java.util.Date;

public class ImageEntity {

    private ObjectId id;
    private Date createdAt;
    private ObjectId ownerId;
    private String url;

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public ObjectId getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(ObjectId ownerId) {
        this.ownerId = ownerId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}