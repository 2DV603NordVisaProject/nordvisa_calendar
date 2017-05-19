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
import java.nio.file.Files;

import static org.junit.Assert.assertEquals;
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
        MockMultipartFile file1 = new MockMultipartFile("file", "test.jpg", "image/jpeg", testImage);
        MockMultipartFile[] files = { file1 };

        when(dao.saveImage(eq("test.jpg"), eq(file1), anyString(), eq("image/jpeg"))).thenReturn(true);

        assertEquals(HttpStatus.OK, sut.uploadImages(files).getStatusCode());
    }

    @Test
    public void uploadImages() throws Exception {
        FileInputStream testImage1 = new FileInputStream(resources + "test.jpg");
        FileInputStream testImage2 = new FileInputStream(resources + "test.png");
        MockMultipartFile file1 = new MockMultipartFile("file", "test.jpg", "image/jpeg", testImage1);
        MockMultipartFile file2 = new MockMultipartFile("file", "test.png", "image/png", testImage2);

        MockMultipartFile[] files = { file1, file2 };

        when(dao.saveImage(eq("test.jpg"), eq(file1), anyString(), eq("image/jpeg"))).thenReturn(true);
        when(dao.saveImage(eq("test.png"), eq(file2), anyString(), eq("image/png"))).thenReturn(true);

        assertEquals(HttpStatus.OK, sut.uploadImages(files).getStatusCode());
    }

    @Test
    public void uploadImageWrongFileType() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "hello world!".getBytes());

        MockMultipartFile[] files = { file };

        assertEquals(HttpStatus.UNSUPPORTED_MEDIA_TYPE, sut.uploadImages(files).getStatusCode());
    }

    @Test
    public void uploadImageWrongImageType() throws Exception {
        FileInputStream testImage = new FileInputStream(resources + "test.bmp");
        MockMultipartFile file = new MockMultipartFile("file", testImage);

        MockMultipartFile[] files = { file };

        assertEquals(HttpStatus.UNSUPPORTED_MEDIA_TYPE, sut.uploadImages(files).getStatusCode());
    }

    @Test
    public void deleteImagesWhenUploadFails() throws Exception {
        FileInputStream testImage1 = new FileInputStream(resources + "test.jpg");
        FileInputStream testImage2 = new FileInputStream(resources + "test.bmp");

        MockMultipartFile file1 = new MockMultipartFile("file", "test.jpg", "image/jpeg", testImage1);
        MockMultipartFile file2 = new MockMultipartFile("file", "test.bmp", "image/bmp", testImage2);

        MockMultipartFile[] files = { file1, file2 };

        when(dao.saveImage(eq("test.jpg"), eq(file1), anyString(), eq("image/jpeg"))).thenReturn(true);

        assertEquals(HttpStatus.UNSUPPORTED_MEDIA_TYPE, sut.uploadImages(files).getStatusCode());

        verify(dao, times(1)).deleteAllImages(anyString());
    }

    @Test
    public void getImage() throws Exception {
        Image image = mock(Image.class);

        byte[] file = Files.readAllBytes(new File(resources + "test.jpg").toPath());

        when(image.getFile()).thenReturn(file);
        when(image.getPath()).thenReturn("123456789abcdef");
        when(image.getName()).thenReturn("test.jpg");

        when(dao.getImage("123456789abcdef", "test.jpg")).thenReturn(image);

        assertEquals(file, sut.getImage("123456789abcdef", "test.jpg").getBody());

        verify(dao, times(1)).getImage("123456789abcdef", "test.jpg");
    }

    @Test
    public void getNonexistentImage() throws Exception {
        when(dao.getImage("123456789abcdef", "test.jpg")).thenReturn(null);

        assertEquals(HttpStatus.NOT_FOUND, sut.getImage("123456789abcdef", "test.jpg").getStatusCode());

        verify(dao, times(1)).getImage("123456789abcdef", "test.jpg");
    }

    /*@Test
    public void deleteImage() throws Exception {
        when(dao.deleteImage("123456789abcdef", "test.jpg")).thenReturn(true);

        assertEquals(HttpStatus.NO_CONTENT, sut.deleteImage("123456789abcdef", "test.jpg").getStatusCode());

        verify(dao, times(1)).deleteImage("123456789abcdef", "test.jpg");
    }

    @Test
    public void deleteNonexistentImage() throws Exception {
        when(dao.deleteImage("123456789abcdef", "test.jpg")).thenReturn(false);

        assertEquals(HttpStatus.NOT_FOUND, sut.deleteImage("123456789abcdef", "test.jpg").getStatusCode());

        verify(dao, times(1)).deleteImage("123456789abcdef", "test.jpg");
    }*/
}
