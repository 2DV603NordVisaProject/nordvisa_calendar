//package calendar.user;

//import calendar.user.dto.UserIdDTO;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;

//import static org.junit.Assert.*;
//import static org.mockito.Mockito.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
///**
// * Class WebLayerTesting
// *
// * @author Axel Nilsson (axnion)
// */
////@RunWith(SpringRunner.class)
////@SpringBootTest
////public class WebLayerTesting {
////    @Mock
////    private UserDAO dao;
////    @Mock
////    private Email email;
////    @Mock
////    private UserInformationValidator validator;
////    @Mock
////    private CurrentUser currentUser;

////    @InjectMocks
////    private UserController sut;

////    private MockMvc mockMvc;

////    @Before
////    public void setup() {
////        MockitoAnnotations.initMocks(this);
////        this.mockMvc = MockMvcBuilders.standaloneSetup(sut).build();
////    }

////    @Test
////    public void getUserByIdGettingExistingUser() throws Exception {
////        User user = new User();

////        when(dao.getUserById("1")).thenReturn(user);

////        mockMvc.perform(get("/api/user?id=1"))
////                .andExpect(status().isOk());

////        verify(dao, times(1)).getUserById("1");
////    }

////    @Test
////    public void getUserByIdGettingNonexistentUser() throws Exception {
////        when(dao.getUserById("1")).thenReturn(null);

////        mockMvc.perform(get("/api/user?id=1"))
////                .andExpect(status().isOk());

////        verify(dao, times(1)).getUserById("1");
////    }

////    @Test
////    public void unregisterValid() throws Exception {
////        User actorMock = mock(User.class);
////        User targetMock = mock(User.class);

////        UserIdDTO idMock = new UserIdDTO();
////        idMock.setId("1");

////        when(currentUser.getEmailAddres()).thenReturn("test@test.com");
////        when(dao.getUserByEmail("test@test.com")).thenReturn(actorMock);
////        when(dao.getUserById("1")).thenReturn(targetMock);
////        when(actorMock.canManage(targetMock)).thenReturn(true);

////        ObjectMapper mapper = new ObjectMapper();

////        mockMvc.perform(post("/api/user/unregister")
////                .content(mapper.writeValueAsString(idMock)))
////                .andExpect(status().isOk());

////        verify(dao, times(1)).delete("1");
////    }
//}
