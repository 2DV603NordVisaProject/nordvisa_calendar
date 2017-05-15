package calendar.image;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileInputStream;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ImageControllerTest {
    private String resources = ImageController.class
            .getResource("../../../../resources/test/")
            .getPath();

    @Mock
    private ImageDAO dao;

    @InjectMocks
    private ImageController sut;

    @Test
    public void uploadImage() throws Exception {
        FileInputStream testImage = new FileInputStream(resources + "test.jpg");
        MockMultipartFile file = new MockMultipartFile("test.jpg", testImage);

        assertEquals(HttpStatus.OK, sut.uploadImage(file).getStatusCode());
    }

    @Test
    public void uploadImageWrongFileType() throws Exception {
        FileInputStream testImage = new FileInputStream(resources + "test.bmp");
        MockMultipartFile file = new MockMultipartFile("test.bmp", testImage);

        assertEquals(HttpStatus.UNSUPPORTED_MEDIA_TYPE, sut.uploadImage(file).getStatusCode());
    }

    @Test
    public void getImage() throws Exception {
        String path = "123456789abcdef";
        String name = "test.jpg";

        sut.getImage(path, name);
        verify(dao, times(1)).getImage(path, name);
    }
}
