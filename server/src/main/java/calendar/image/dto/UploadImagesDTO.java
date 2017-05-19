package calendar.image.dto;

/**
 * Created by francis on 5/19/17.
 */
public class UploadImagesDTO {
    private String path;
    private boolean success;

    public UploadImagesDTO(String path, boolean success) {
        this.path = path;
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
