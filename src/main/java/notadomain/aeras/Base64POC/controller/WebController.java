package notadomain.aeras.Base64POC.controller;

import notadomain.aeras.Base64POC.dto.ImageDataRequest;
import notadomain.aeras.Base64POC.dto.ImagePostBody;
import notadomain.aeras.Base64POC.model.Image;
import notadomain.aeras.Base64POC.service.WebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class WebController {
    private WebService service;
    @Autowired
    public WebController(WebService service){
        this.service = service;
    }
    @GetMapping(value = "/greet")
    public ResponseEntity<String> greeting(){
        System.out.println("Greet endpoint called!");
        return new ResponseEntity<> ("Hello world!",HttpStatus.OK);
    }
    @GetMapping(value = "/getImageId")
    public Map<String, String> getImageId(){
        String imageId = service.getImageId();
        System.out.println("Got image id: "+ imageId);
        Map<String, String> response = new HashMap<>();
        response.put("imageId", imageId);
        return response;
    }
    @PostMapping(value = "/getImageDataB64", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> getImageDataB64(@RequestBody ImageDataRequest requestBody){
        return service.getImageDataB64(requestBody);
    }
    @PostMapping(value = "/getImageDataByte", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<byte[]> getImageDataByte(@RequestBody ImageDataRequest requestBody){
        return service.getImageDataByte(requestBody);
    }
    @PostMapping(value = "/postImage", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Image> postImage(@RequestBody ImagePostBody postBody){
        return new ResponseEntity<>(service.postImage(postBody), HttpStatus.CREATED);
    }

}
