package notadomain.aeras.Base64POC.dto;

public class ImageDataRequest {
    private String imageID;
    private int chunkNumber;

    public void setImageID(String imageID) {
        this.imageID = imageID;
    }

    public void setChunkNumber(int chunkNumber) {
        this.chunkNumber = chunkNumber;
    }

    public String getImageID() {
        return imageID;
    }
    public int getChunkNumber() {
        return chunkNumber;
    }
}
