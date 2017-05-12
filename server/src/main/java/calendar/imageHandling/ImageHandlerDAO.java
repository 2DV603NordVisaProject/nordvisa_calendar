package calendar.imageHandling;

import org.springframework.web.multipart.MultipartFile;

interface ImageHandlerDAO {
    void updateEvent(String eventID, String filename);
    byte[] getImage(String name);
    boolean saveImage(String name, MultipartFile file);
}
