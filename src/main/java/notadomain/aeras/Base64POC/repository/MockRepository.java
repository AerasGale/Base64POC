package notadomain.aeras.Base64POC.repository;

import notadomain.aeras.Base64POC.model.Image;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class MockRepository implements ImageRepository{
    public static final long CHUNK_SIZE = 262144;
    private final ArrayList<Image> mockData;
    public MockRepository(){
        System.out.println("Mock repo loaded");
        mockData = new ArrayList<>();
        populateMockData();
    }

    private void populateMockData() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        mockData.add(new Image(0, "default.png", "/static/images", null, null));
        mockData.add(new Image(1, "img1.png", "/static/images"
                , LocalDateTime.parse("2024/04/03 12:29:34", formatter)
                , LocalDateTime.parse("2024/04/03 15:29:34", formatter)));
        mockData.add(new Image(2, "img2.png", "/static/images"
                , LocalDateTime.parse("2024/04/03 16:09:34", formatter)
                , LocalDateTime.parse("2024/04/03 17:29:34", formatter)));
        mockData.add(new Image(3, "img3.png", "/static/images"
                , LocalDateTime.parse("2024/04/03 18:29:34", formatter)
                , LocalDateTime.parse("2024/04/03 19:29:34", formatter)));
        mockData.add(new Image(4, "img4.png", "/static/images"
                , LocalDateTime.parse("2024/04/04 08:29:34", formatter)
                , LocalDateTime.parse("2024/04/03 09:29:34", formatter)));
        mockData.add(new Image(5, "img5.png", "/static/images"
                , LocalDateTime.parse("2024/04/03 10:29:34", formatter)
                , LocalDateTime.parse("2024/04/03 11:29:34", formatter)));
        mockData.add(new Image(6, "img6.png", "/static/images"
                , LocalDateTime.parse("2024/04/03 12:29:34", formatter)
                , LocalDateTime.parse("2024/04/03 13:29:34", formatter)));
    }

    public String getImageId(){
        return "0";
    }
    public byte[] getImageData(String imageId, int chunkNumber){
        String path = getPathOfImageId(imageId);
        System.out.println("Path found is: " + path);

        byte[] imageData = new byte[(int) CHUNK_SIZE];
        try (InputStream is = this.getClass().getResourceAsStream(path)) {
            long position = chunkNumber * CHUNK_SIZE;
            if(is!=null) {
                long bytesSKipped = is.skip(position);
                System.out.println("Skipped " + bytesSKipped + " bytes");
                imageData = is.readNBytes(imageData.length);
            }
            return imageData;
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
        return null;
    }

    @Override
    public Image postImageToDB(String name, String path, LocalDateTime startDate, LocalDateTime endDate) {
        Image imageToAdd = new Image(mockData.size(), name, path, startDate, endDate);
        mockData.add(imageToAdd);
        return imageToAdd;
    }

    private String getPathOfImageId(String imageId){
        Image img = mockData.get(Integer.parseInt(imageId));
        return img.getPath().concat("/").concat(img.getName());
    }
}
