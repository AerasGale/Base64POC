package notadomain.aeras.Base64POC.repository;

import notadomain.aeras.Base64POC.database.ImageDatabase;
import notadomain.aeras.Base64POC.model.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class ImageRepository {
    private static final String FILE_PATH_BASE = "/static/images";
    private final ImageDatabase database;
    public static final long CHUNK_SIZE = 262144;

    @Autowired
    public ImageRepository(ImageDatabase database){
        this.database = database;
    }
    public String getImageId() {
        List<Image> images = database.findImageBetweenDates();
        if(!images.isEmpty()){
            System.out.println("Get Image ID Called");
            return images.get(0).getId().toString();
        }
        else return "0";
    }
    public byte[] getImageData(String imageID, int chunkNumber){
        Optional<String> pathOptional = getPathOfImageId(imageID);
        String path = null;
        if(pathOptional.isPresent()) {
            path = pathOptional.get();
            System.out.println("Path found is: " + path);

            byte[] imageData = new byte[(int) CHUNK_SIZE];
            try (InputStream is = this.getClass().getResourceAsStream(path)) {
                long position = chunkNumber * CHUNK_SIZE;
                is.skip(position);
                imageData = is.readNBytes(imageData.length);
                return imageData;
            } catch (Exception e) {
                System.out.println(e.toString());
            }
        }
        return null;
    }
    public Image postImageToDB(String name, String path, LocalDateTime startDate, LocalDateTime endDate){
        Image image = new Image();
        image.setName(name);
        image.setPath(path);
        image.setStartDate(startDate);
        image.setEndDate(endDate);
        return database.save(image);
    }
    private Optional<String> getPathOfImageId(String imageId){
        Optional<Image> optionalImage = database.findById(Integer.parseInt(imageId));
        System.out.println("ID received:" + imageId);
        System.out.println(Integer.parseInt(imageId));
        if(optionalImage.isPresent()){
            String fullPath = optionalImage.get().getPath() + "/" + optionalImage.get().getName();
            return Optional.of(fullPath);
        }else {
            return Optional.empty();
        }
    }

}
