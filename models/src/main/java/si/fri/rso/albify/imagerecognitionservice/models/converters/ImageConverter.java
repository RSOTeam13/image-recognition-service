package si.fri.rso.albify.imagerecognitionservice.models.converters;

import si.fri.rso.albify.imagerecognitionservice.lib.Image;
import si.fri.rso.albify.imagerecognitionservice.models.entities.ImageEntity;

public class ImageConverter {

    public static Image toDto(ImageEntity entity) {

        Image dto = new Image();
        dto.setId(entity.getId().toString());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setOwnerId(entity.getOwnerId().toString());
        dto.setUrl(entity.getUrl());

        return dto;

    }

    public static ImageEntity toEntity(Image dto) {

        ImageEntity entity = new ImageEntity();
        return entity;

    }

}
