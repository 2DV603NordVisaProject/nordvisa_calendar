package calendar;

import calendar.image.dto.UploadImagesDTO;
import org.apache.tomcat.util.http.fileupload.FileUploadBase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    // Exception Handler
    @ExceptionHandler(MultipartException.class)
    public ResponseEntity<UploadImagesDTO> handleMultipartException(Exception e){
        UploadImagesDTO dto = new UploadImagesDTO("", false);

        if(e.getCause() instanceof IllegalStateException && e.getCause().getCause() instanceof FileUploadBase.SizeLimitExceededException) {
            return ResponseEntity
                    .status(HttpStatus.PAYLOAD_TOO_LARGE)
                    .body(dto);
        } else {
            e.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(dto);
        }
    }
}