package calendar.image;

import org.apache.tomcat.util.http.fileupload.FileUploadBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;

import javax.naming.SizeLimitExceededException;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * Image handling class.
 *
 * @author Francis Menkes (fmenkes)
 */
@RestController
@RequestMapping("/api/upload")
public class ImageController {



    @Autowired
    private ImageDAO dao;

    private static final Set<String> ACCEPTED_FILE_TYPES = new HashSet<>(Arrays.asList(
           "image/png", "image/jpeg", "image/gif"
    ));



    /**
     * Upload an image to the server. Returns a boolean meaning whether the upload was successful or not.
     * @param file
     * @return
     */
    // TODO: better error handling
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity uploadImage(@RequestParam("file") MultipartFile file) {
        String name = file.getOriginalFilename();

        try {
            // TODO: duplicated name checking
            String path = getRandomHexString(16);

            InputStream is = new BufferedInputStream(file.getInputStream());
            String mimeType = URLConnection.guessContentTypeFromStream(is);

            if (!ACCEPTED_FILE_TYPES.contains(mimeType)) {
                return new ResponseEntity(HttpStatus.UNSUPPORTED_MEDIA_TYPE);
            }
            dao.saveImage(name, file, path, mimeType);
        } catch(IOException e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(path = "/{path:.+}/{name:.+}", method = RequestMethod.GET)
    public HttpEntity<byte[]> getImage(@PathVariable("path") String path, @PathVariable("name") String name) {
        Image image = dao.getImage(path, name);

        if(image != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_TYPE, image.getType());

            return new HttpEntity<>(image.getFile(), headers);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(path = "/{path:.+}/{name:.+}", method = RequestMethod.DELETE)
    public HttpEntity<byte[]> deleteImage(@PathVariable("path") String path, @PathVariable("name") String name) {
        String res;

        if(dao.deleteImage(path, name)) {
            res = "File " + name + " deleted successfully.";
            return new HttpEntity<>(res.getBytes());
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
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