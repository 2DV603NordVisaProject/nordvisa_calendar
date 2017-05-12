package calendar.imageHandling;

import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Optional;
import java.util.Random;

/**
 * Image handling class.
 *
 * @author Francis Menkes (fmenkes)
 */
@RestController
@RequestMapping("/api/upload")
public class ImageHandler {

    /**
     * Upload an image to the server. Returns a boolean meaning whether the upload was successful or not.
     * @param file
     * @return
     */
    // TODO: make sure it's an image!
    @RequestMapping(method = RequestMethod.POST)
    public HttpEntity<byte[]> uploadImage(@RequestParam("file") MultipartFile file) {
        // Gets the original filename. Might be useful later.
        // String name = file.getOriginalFilename();
        ImageHandlerDAO dao = new ImageHandlerDAOMongo();

        String name = getRandomHexString(16);

        dao.saveImage(name, file);
        /*if(dao.saveImage(name, file)) {
            dao.updateEvent(eventID, name);
        }*/

        String resp = "File " + name + " uploaded.";

        return new HttpEntity<>(resp.getBytes());
    }

    @RequestMapping(path = "/{name:.+}", method = RequestMethod.GET)
    public HttpEntity<byte[]> getImage(@PathVariable("name") String name) {
        ImageHandlerDAO dao = new ImageHandlerDAOMongo();

        byte[] createdImage = dao.getImage(name);

        if(createdImage != null) {
            HttpHeaders headers = new HttpHeaders();
            // TODO: different file types
            headers.add(HttpHeaders.CONTENT_TYPE, "image/jpeg");

            return new HttpEntity<>(dao.getImage(name), headers);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public boolean deleteImage(String name) {

        return false;
    }

    private String getRandomHexString(int length){
        Random r = new Random();
        StringBuilder sb = new StringBuilder();
        while(sb.length() < length) {
            sb.append(Integer.toHexString(r.nextInt()));
        }

        return sb.toString().substring(0, length);
    }
}
