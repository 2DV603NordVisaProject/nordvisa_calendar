package calendar.image;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.File;
import java.io.FileInputStream;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ImageControllerTest {
    private MockMvc mockMvc;

    private String resources = ImageController.class
            .getResource("../../../../resources/test/")
            .getPath();

    @Autowired
    private WebApplicationContext webApplicationContext;

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
    public void uploadImageTooLarge() throws Exception {
        FileInputStream testImage = new FileInputStream(resources + "test-large.jpg");
        MockMultipartFile file = new MockMultipartFile("test-large.jpg", testImage);

        //MediaType mediaType = new MediaType("multipart", "form-data");

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        mockMvc.perform(post("/api/upload")
                .content(file.getBytes()))
                .andExpect(status().isPayloadTooLarge());


        //assertEquals(HttpStatus.PAYLOAD_TOO_LARGE, sut.uploadImage(file).getStatusCode());
    }

    @Test
    public void getImage() throws Exception {
        String path = "123456789abcdef";
        String name = "test.jpg";

        sut.getImage(path, name);
        verify(dao, times(1)).getImage(path, name);
    }
}
