package calendar.user;

import calendar.user.dto.RecaptchaResponseDTO;
import calendar.user.dto.RegistrationDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Class VisitorControllerTest
 *
 * @author Axel Nilsson (axnion)
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class VisitorControllerTest {
    @Mock
    private UserDAO dao;
    @Mock
    private Email email;
    @Mock
    private UserInformationValidator validator;

    @InjectMocks
    private VisitorController sut;

    @Test
    public void registrationValidFirstUser() throws Exception {
        String captchaSecret = "secret";

        RegistrationDTO dto = mock(RegistrationDTO.class);
        RecaptchaResponseDTO recaptchaResponseDTO = mock(RecaptchaResponseDTO.class);
        ArrayList<User> existingUsers = new ArrayList<>();

        configureValidatorMock(false, null, recaptchaResponseDTO);

        when(dao.getAllUsers()).thenReturn(existingUsers);
        when(recaptchaResponseDTO.isSuccess()).thenReturn(true);
        when(dto.getRecaptcha()).thenReturn(captchaSecret);
        when(dto.getEmail()).thenReturn("test@test.com");
        when(dto.getPassword()).thenReturn("my_password");
        when(dto.getOrganization()).thenReturn("");

        RecaptchaResponseDTO response = sut.registration(dto);

        verify(validator, times(1)).validate(dto);
        verify(validator, times(1)).validateRecaptcha("secret");

        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(dao, times(1)).add(captor.capture());
        User createdUser = captor.getValue();
        assertEquals("SUPER_ADMIN", createdUser.getRole());
        assertTrue(createdUser.getOrganization().isApproved());

        verify(email, times(1))
                .sendVerificationEmail(createdUser.getValidateEmailLink().getUrl(), dto.getEmail());

        assertEquals(recaptchaResponseDTO, response);
    }

    @Test
    public void registrationValidWithOtherUsersInTheSystem() throws Exception {
        String captchaSecret = "secret";

        RegistrationDTO dto = mock(RegistrationDTO.class);
        RecaptchaResponseDTO recaptchaResponseDTO = mock(RecaptchaResponseDTO.class);
        ArrayList<User> existingUsers = new ArrayList<>();
        existingUsers.add(mock(User.class));
        existingUsers.add(mock(User.class));

        configureValidatorMock(false, null, recaptchaResponseDTO);

        when(dao.getAllUsers()).thenReturn(existingUsers);
        when(recaptchaResponseDTO.isSuccess()).thenReturn(true);
        when(dto.getRecaptcha()).thenReturn(captchaSecret);
        when(dto.getEmail()).thenReturn("test@test.com");
        when(dto.getPassword()).thenReturn("my_password");
        when(dto.getOrganization()).thenReturn("");

        RecaptchaResponseDTO response = sut.registration(dto);

        verify(validator, times(1)).validate(dto);
        verify(validator, times(1)).validateRecaptcha("secret");

        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(dao, times(1)).add(captor.capture());
        User createdUser = captor.getValue();
        assertEquals("USER", createdUser.getRole());
        assertFalse(createdUser.getOrganization().isApproved());

        verify(email, times(1))
                .sendVerificationEmail(createdUser.getValidateEmailLink().getUrl(), dto.getEmail());

        assertEquals(recaptchaResponseDTO, response);
    }

    @Test
    public void registrationInvalidUserInformation() throws Exception {
        String captchaSecret = "secret";

        RegistrationDTO dto = mock(RegistrationDTO.class);
        RecaptchaResponseDTO recaptchaResponseDTO = mock(RecaptchaResponseDTO.class);
        ArrayList<User> existingUsers = new ArrayList<>();
        Exception expt = new Exception();

        configureValidatorMock(true, expt, recaptchaResponseDTO);

        when(dao.getAllUsers()).thenReturn(existingUsers);
        when(recaptchaResponseDTO.isSuccess()).thenReturn(true);
        when(dto.getRecaptcha()).thenReturn(captchaSecret);
        when(dto.getEmail()).thenReturn("test@test.com");
        when(dto.getPassword()).thenReturn("my_password");
        when(dto.getOrganization()).thenReturn("");

        try {
            sut.registration(dto);
            fail();
        }
        catch(Exception e) {
            assertEquals(expt, e);
        }

        verify(validator, times(1)).validate(dto);
        verify(validator, never()).validateRecaptcha(anyString());
        verify(dao, never()).add(any(User.class));
        verify(email, never()).sendVerificationEmail(anyString(), anyString());
    }

    @Test
    public void registrationRecaptchaFailure() throws Exception {
        String captchaSecret = "secret";

        RegistrationDTO dto = mock(RegistrationDTO.class);
        RecaptchaResponseDTO recaptchaResponseDTO = mock(RecaptchaResponseDTO.class);
        ArrayList<User> existingUsers = new ArrayList<>();

        configureValidatorMock(false, null, recaptchaResponseDTO);

        when(dao.getAllUsers()).thenReturn(existingUsers);
        when(recaptchaResponseDTO.isSuccess()).thenReturn(false);
        when(dto.getRecaptcha()).thenReturn(captchaSecret);
        when(dto.getEmail()).thenReturn("test@test.com");
        when(dto.getPassword()).thenReturn("my_password");
        when(dto.getOrganization()).thenReturn("");

        RecaptchaResponseDTO response = sut.registration(dto);

        verify(validator, times(1)).validate(dto);
        verify(validator, times(1)).validateRecaptcha(captchaSecret);
        verify(dao, never()).add(any(User.class));
        verify(email, never()).sendVerificationEmail(anyString(), anyString());
        assertEquals(recaptchaResponseDTO, response);
    }

    public void configureValidatorMock(boolean doThrow, Exception expt, RecaptchaResponseDTO res) {
        try {
            if (doThrow) {
                doThrow(expt).doNothing().when(validator).validate(any(RegistrationDTO.class));
            } else {
                doNothing().when(validator).validate(any(RegistrationDTO.class));
            }

            when(validator.validateRecaptcha(anyString())).thenReturn(res);
        }
        catch (Exception e) {
            fail();
        }
    }
}
