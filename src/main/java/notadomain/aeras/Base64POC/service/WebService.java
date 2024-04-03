package notadomain.aeras.Base64POC.service;

import notadomain.aeras.Base64POC.dto.ImageDataRequest;
import notadomain.aeras.Base64POC.dto.ImagePostBody;
import notadomain.aeras.Base64POC.model.Image;
import notadomain.aeras.Base64POC.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

@Service
public class WebService {
    private final ImageRepository repository;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    @Autowired
    public WebService(ImageRepository repository){
        this.repository = repository;
    }
    public String getImageId() {
        return repository.getImageId();
    }
    public ResponseEntity<String> getImageDataB64(ImageDataRequest request){
        byte[] imageBytes = getImageData(request);
        if(imageBytes == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        boolean lastChunk = imageBytes.length < ImageRepository.CHUNK_SIZE;
        String imageData = Base64.getEncoder().encodeToString(imageBytes);
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Last-Chunk", Boolean.toString(lastChunk));
        return new ResponseEntity<>( imageData, headers, HttpStatus.OK);
    }
    public ResponseEntity<byte[]> getImageDataByte(ImageDataRequest request){
        byte[] imageBytes = getImageData(request);
        if(imageBytes==null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        boolean lastChunk = imageBytes.length < ImageRepository.CHUNK_SIZE;
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/octet-stream");
        headers.add("X-Last-Chunk", Boolean.toString(lastChunk));
        return new ResponseEntity<>( imageBytes, headers, HttpStatus.OK);
    }
    public Image postImage(ImagePostBody postBody){
        String name = postBody.getImageName();
        String path = postBody.getImagePath();
        String start = postBody.getStartDate();
        String end  = postBody.getEndDate();
        LocalDateTime startDate =null;
        LocalDateTime endDate = null;
        if(start != null && end != null){
            startDate = LocalDateTime.parse(start,formatter);
            endDate = LocalDateTime.parse(end, formatter);

        }
        return repository.postImageToDB(name, path, startDate, endDate);
    }

    private byte[] getImageData(ImageDataRequest request){
        String imageID = request.getImageID();
        int chunkNumber = request.getChunkNumber();
        return repository.getImageData(imageID, chunkNumber);
    }
}
