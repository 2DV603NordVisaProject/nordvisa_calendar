package calendar.image.dto;

/**
 * UploadImagesDTO class
 *
 * @author Francis Menkes (fmenkes)
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
