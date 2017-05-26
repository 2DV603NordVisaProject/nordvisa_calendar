package calendar.image;

import org.springframework.web.multipart.MultipartFile;

/**
 * Interface ImageDAO
 *
 * The interface between the application and the database driver. Any database driver should
 * implement this interface.
 *
 * @author Francis Menkes (fmenkes)
 */
interface ImageDAO {
    Image getImage(String path, String name);
    boolean saveImage(String name, MultipartFile file, String path, String type);
    boolean deleteImage(String path, String name);
    void deleteAllImages(String path);
    boolean pathExists(String path);
}
