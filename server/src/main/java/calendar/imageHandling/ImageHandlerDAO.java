package calendar.imageHandling;

import org.springframework.web.multipart.MultipartFile;

interface ImageHandlerDAO {
    Image getImage(String name);
    boolean saveImage(String name, MultipartFile file, String type);
    boolean deleteImage(String name);
}
