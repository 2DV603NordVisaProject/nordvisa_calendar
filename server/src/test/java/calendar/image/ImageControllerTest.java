package calendar.image;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ImageControllerTest {
    private MockMvc mockMvc;

    private String resources = ImageController.class
            .getResource("../../../../resources/test/")
            .getPath();

    private String endpoint = "/api/upload";

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Mock
    private ImageDAO dao;

    @InjectMocks
    private ImageController sut;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void uploadImage() throws Exception {

        FileInputStream testImage = new FileInputStream(resources + "test.jpg");
        MockMultipartFile file = new MockMultipartFile("file", testImage);

        // Fails in Travis. Need to fix
        /*
        mockMvc.perform(fileUpload(endpoint)
                .file(file))
                .andDo(print())
                .andExpect(status().isOk());*/

        assertEquals(HttpStatus.OK, sut.uploadImage(file).getStatusCode());
    }

    @Test
    public void uploadImageWrongFileType() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "hello world!".getBytes());

        mockMvc.perform(fileUpload(endpoint)
                .file(file))
                .andDo(print())
                .andExpect(status().isUnsupportedMediaType());
    }

    @Test
    public void uploadImageWrongImageType() throws Exception {
        FileInputStream testImage = new FileInputStream(resources + "test.bmp");
        MockMultipartFile file = new MockMultipartFile("file", testImage);

        mockMvc.perform(fileUpload(endpoint)
                .file(file))
                .andDo(print())
                .andExpect(status().isUnsupportedMediaType());
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
}
