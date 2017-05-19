package calendar.image;

import org.springframework.web.multipart.MultipartFile;

interface ImageDAO {
    Image getImage(String path, String name);
    boolean saveImage(String name, MultipartFile file, String path, String type);
    boolean deleteImage(String path, String name);
    void deleteAllImages(String path);
    boolean pathExists(String path);
}
