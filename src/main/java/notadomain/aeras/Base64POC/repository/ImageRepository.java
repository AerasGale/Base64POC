package notadomain.aeras.Base64POC.repository;

import notadomain.aeras.Base64POC.model.Image;

import java.time.LocalDateTime;

public interface ImageRepository {
    String getImageId();
    byte[] getImageData(String imageID, int chunkNumber);
    Image postImageToDB(String name, String path, LocalDateTime startDate, LocalDateTime endDate);
}
