package calendar.image;

import calendar.image.dto.UploadImagesDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
     * Upload an array of images to the server.
     * @param files An array of files to upload.
     * @return HTTP response of a body containing a JSON object with the generated path
     */
    // TODO: maximum number of files to upload
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<UploadImagesDTO> uploadImages(@RequestParam("files") MultipartFile[] files) {
        String path;
        UploadImagesDTO uploadFailed = new UploadImagesDTO("", false);

        do {
            path = getRandomHexString(16);
        } while (dao.pathExists(path));

        for (MultipartFile file : files) {
            String name = file.getOriginalFilename();

            try {
                InputStream is = new BufferedInputStream(file.getInputStream());
                String mimeType = URLConnection.guessContentTypeFromStream(is);

                if (!ACCEPTED_FILE_TYPES.contains(mimeType)) {
                    dao.deleteAllImages(path);
                    return new ResponseEntity<>(uploadFailed, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
                }
                if (!dao.saveImage(name, file, path, mimeType)) {
                    // If saving an image fails for some reason, delete all previously uploaded images and
                    // return 500
                    dao.deleteAllImages(path);
                    return new ResponseEntity<>(uploadFailed, HttpStatus.INTERNAL_SERVER_ERROR);
                }
            } catch (IOException e) {
                e.printStackTrace();
                dao.deleteAllImages(path);
                return new ResponseEntity<>(uploadFailed, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<>(new UploadImagesDTO(path, true), HttpStatus.OK);
    }

    @RequestMapping(path = "/{path:.+}/{name:.+}", method = RequestMethod.GET)
    public ResponseEntity<byte[]> getImage(@PathVariable("path") String path, @PathVariable("name") String name) {
        Image image = dao.getImage(path, name);

        if(image != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_TYPE, image.getType());

            return new ResponseEntity<>(image.getFile(), headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /*@RequestMapping(path = "/{path:.+}/{name:.+}", method = RequestMethod.DELETE)
    public ResponseEntity deleteImage(@PathVariable("path") String path, @PathVariable("name") String name) {

        if(dao.deleteImage(path, name)) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }*/

    private String getRandomHexString(int length){
        Random r = new Random();
        StringBuilder sb = new StringBuilder();
        while(sb.length() < length) {
            sb.append(Integer.toHexString(r.nextInt()));
        }

        return sb.toString().substring(0, length);
    }
}
