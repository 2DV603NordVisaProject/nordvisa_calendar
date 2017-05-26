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
 * Image handling class. Contains methods for uploading images and for retrieving them
 * from the server.
 *
 * @author Francis Menkes (fmenkes)
 */
@RestController
@RequestMapping("/api/upload")
public class ImageController {
    @Autowired
    private ImageDAO dao;

    // Make sure that only pngs and jpegs are accepted as event images.
    private static final Set<String> ACCEPTED_FILE_TYPES = new HashSet<>(Arrays.asList(
           "image/png", "image/jpeg"
    ));

    /**
     * Upload an array of images to the server. Currently, the frontend can only handle an image at a time,
     * but this method should only require a little modification if we wanted to make possible multiple uploads
     * at the same time.
     *
     * @param files     An array of files to upload.
     * @return          HTTP response of a body containing a JSON object with the generated path,
     *                  using the appropriate status code.
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<UploadImagesDTO> uploadImages(@RequestParam("files") MultipartFile[] files) {
        String path;
        UploadImagesDTO uploadFailed = new UploadImagesDTO("", false);

        // Generate a random hex string that doesn't already exist
        do {
            path = getRandomHexString(16);
        } while (dao.pathExists(path));

        for (MultipartFile file : files) {
            String name = file.getOriginalFilename();

            try {
                InputStream is = new BufferedInputStream(file.getInputStream());
                String mimeType = URLConnection.guessContentTypeFromStream(is);

                // Return if the image is not the right file type (or if it isn't even an image)
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

    /**
     * Get an image from the database and serve it to the client.
     *
     * @param path      The path variable that is saved in the event to uniquely identify its images
     * @param name      The name of the image that is requested, including the extension
     * @return          The requested image along with the correct header (Content-Type: image/*)
     */
    @RequestMapping(path = "/{path:.+}/{name:.+}", method = RequestMethod.GET)
    public ResponseEntity<byte[]> getImage(@PathVariable("path") String path, @PathVariable("name") String name) {
        Image image = dao.getImage(path, name);

        if(image != null) {
            // Set the correct headers
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_TYPE, image.getType());

            return new ResponseEntity<>(image.getFile(), headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Unused delete image code. Before implementing this method we need to make sure that only
    // authorized users have the ability to delete images.

    /*@RequestMapping(path = "/{path:.+}/{name:.+}", method = RequestMethod.DELETE)
    public ResponseEntity deleteImage(@PathVariable("path") String path, @PathVariable("name") String name) {

        if(dao.deleteImage(path, name)) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }*/

    /**
     * Generates a random hex string. Used to generate a unique path for event images.
     *
     * @param length    The length of the hex string wanted.
     * @return          A random hex string of length {length}.
     */
    private String getRandomHexString(int length){
        Random r = new Random();
        StringBuilder sb = new StringBuilder();
        while(sb.length() < length) {
            sb.append(Integer.toHexString(r.nextInt()));
        }

        return sb.toString().substring(0, length);
    }
}
