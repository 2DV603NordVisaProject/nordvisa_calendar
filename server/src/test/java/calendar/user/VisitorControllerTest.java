package calendar.user;

import calendar.user.dto.PasswordRecoveryRequestDTO;
import calendar.user.dto.RecaptchaResponseDTO;
import calendar.user.dto.RecoverPasswordDTO;
import calendar.user.dto.RegistrationDTO;
import calendar.user.exceptions.InvalidLinkException;
import calendar.user.exceptions.UserDoesNotExistException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.omg.CORBA.DynAnyPackage.Invalid;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Class VisitorControllerTest
 *
 * // TODO: Add comments
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

    @Test
    public void requestPasswordRecoveryExistingUser() throws Exception {
        PasswordRecoveryRequestDTO dto = mock(PasswordRecoveryRequestDTO.class);
        AuthenticationLink authLink = mock(AuthenticationLink.class);
        User user = mock(User.class);

        when(dto.getEmail()).thenReturn("test@test.com");
        when(dao.getUserByEmail("test@test.com")).thenReturn(user);
        when(user.getEmail()).thenReturn("test@test.com");
        when(user.getResetPasswordLink()).thenReturn(authLink);
        when(authLink.getUrl()).thenReturn("random_string");

        sut.requestPasswordRecovery(dto);

        verify(user, times(1)).setResetPasswordLink(any(AuthenticationLink.class));
        verify(dao, times(1)).update(user);
        verify(email, times(1)).
                sendPasswordResetEmail("random_string", "test@test.com");
    }

    @Test
    public void requestPasswordRecoveryNonExistingUser() {
        PasswordRecoveryRequestDTO dto = mock(PasswordRecoveryRequestDTO.class);

        when(dto.getEmail()).thenReturn("test@test.com");
        when(dao.getUserByEmail("test@test.com")).thenReturn(null);

        try {
            sut.requestPasswordRecovery(dto);
            fail();
        }
        catch(UserDoesNotExistException expt) {
            assertEquals("No user with email test@test.com exists", expt.getMessage());
        }

        verify(dao, never()).update(any(User.class));
        verify(email, never()).sendPasswordResetEmail(anyString(), anyString());
    }

    @Test
    public void recoverPasswordExistingRequest() throws Exception {
        String password = "my_password";
        String urlId = "url_id";

        RecoverPasswordDTO dto = mock(RecoverPasswordDTO.class);
        User user = mock(User.class);
        AuthenticationLink resetPasswordLink = mock(AuthenticationLink.class);

        when(dto.getPassword()).thenReturn(password);
        when(dto.getUrlId()).thenReturn(urlId);
        when(dao.getUserByPasswordRecoveryLink(urlId)).thenReturn(user);
        when(user.getResetPasswordLink()).thenReturn(resetPasswordLink);
        when(resetPasswordLink.hasExpired()).thenReturn(false);

        sut.recoverPassword(dto);

        verify(validator, times(1)).validate(dto);
        ArgumentCaptor<AuthenticationLink> captor = ArgumentCaptor.forClass(AuthenticationLink.class);
        verify(user, times(1)).setResetPasswordLink(captor.capture());
        AuthenticationLink recoverPassLink = captor.getValue();
        assertEquals("", recoverPassLink.getUrl());
        assertEquals(0, recoverPassLink.getTimestamp());
        verify(user, times(1)).setPassword(password);
        verify(dao, times(1)).update(user);
    }

    @Test
    public void recoverPasswordNonExistingRequest() throws Exception {
        String password = "my_password";
        String urlId = "url_id";

        RecoverPasswordDTO dto = mock(RecoverPasswordDTO.class);

        when(dto.getPassword()).thenReturn(password);
        when(dto.getUrlId()).thenReturn(urlId);
        when(dao.getUserByPasswordRecoveryLink(urlId)).thenReturn(null);

        try {
            sut.recoverPassword(dto);
            fail();
        }
        catch(Exception expt) {
            expt.getMessage();
        }

        verify(validator, times(1)).validate(dto);
        verify(dao, never()).update(any(User.class));
    }

    @Test
    public void recoverPasswordRequestExpired() throws Exception {
        String password = "my_password";
        String urlId = "url_id";

        RecoverPasswordDTO dto = mock(RecoverPasswordDTO.class);
        User user = mock(User.class);
        AuthenticationLink resetPasswordLink = mock(AuthenticationLink.class);

        when(dto.getPassword()).thenReturn(password);
        when(dto.getUrlId()).thenReturn(urlId);
        when(dao.getUserByPasswordRecoveryLink(urlId)).thenReturn(user);
        when(user.getResetPasswordLink()).thenReturn(resetPasswordLink);
        when(resetPasswordLink.hasExpired()).thenReturn(true);

        try {
            sut.recoverPassword(dto);
            fail();
        }
        catch(Exception expt) {
            expt.getMessage();
        }

        verify(validator, times(1)).validate(dto);
        verify(user, never()).setResetPasswordLink(any(AuthenticationLink.class));
        verify(user, never()).setPassword(anyString());
        verify(dao, never()).update(any(User.class));
    }

    @Test
    public void recoverPasswordInvalidNewPassword() throws Exception {
        String password = "my_password";
        String urlId = "url_id";

        RecoverPasswordDTO dto = mock(RecoverPasswordDTO.class);
        User user = mock(User.class);
        AuthenticationLink resetPasswordLink = mock(AuthenticationLink.class);
        InvalidLinkException exception = mock(InvalidLinkException.class);

        when(dto.getPassword()).thenReturn(password);
        when(dto.getUrlId()).thenReturn(urlId);
        when(dao.getUserByPasswordRecoveryLink(urlId)).thenReturn(user);
        when(user.getResetPasswordLink()).thenReturn(resetPasswordLink);
        when(resetPasswordLink.hasExpired()).thenReturn(false);
        doThrow(exception).when(validator).validate(dto);

        try {
            sut.recoverPassword(dto);
            fail();
        }
        catch(Exception expt) {
            assertEquals(exception, expt);
        }

        verify(validator, times(1)).validate(dto);
        verify(user, never()).setResetPasswordLink(any(AuthenticationLink.class));
        verify(user, never()).setPassword(anyString());
        verify(dao, never()).update(any(User.class));
    }

    @Test
    public void verifyEmailAddressValid() throws Exception {
        String urlId = "url_id";
        String emailChange = "test2@test.com";

        User user = mock(User.class);
        HttpServletResponse httpRes = mock(HttpServletResponse.class);
        AuthenticationLink validationLink = mock(AuthenticationLink.class);

        when(dao.getUserByEmailVerificationLink(urlId)).thenReturn(user);
        when(user.getValidateEmailLink()).thenReturn(validationLink);
        when(user.getEmailChange()).thenReturn(emailChange);
        when(validationLink.hasExpired()).thenReturn(false);

        String response = sut.verifyEmailAddress(httpRes, urlId);

        ArgumentCaptor<AuthenticationLink> captor = ArgumentCaptor.forClass(AuthenticationLink.class);
        verify(user, times(1)).setValidateEmailLink(captor.capture());
        AuthenticationLink recoverPassLink = captor.getValue();
        assertEquals("", recoverPassLink.getUrl());
        assertEquals(0, recoverPassLink.getTimestamp());
        verify(user, times(1)).setEmail(emailChange);
        verify(user, times(1)).setEmailChange("");
        verify(dao, times(1)).update(user);
        verify(httpRes, times(1)).sendRedirect("/");
        assertEquals("", response);
    }

    @Test
    public void verifyEmailAddressInvalid() throws Exception {
        String urlId = "url_id";

        HttpServletResponse httpRes = mock(HttpServletResponse.class);

        when(dao.getUserByEmailVerificationLink(urlId)).thenReturn(null);

        String response = sut.verifyEmailAddress(httpRes, urlId);

        verify(dao, never()).update(any(User.class));
        verify(httpRes, never()).sendRedirect("/");
        assertEquals("This link is invalid", response);
    }

    @Test
    public void verifyEmailAddressExpired() throws Exception {
        String urlId = "url_id";
        String emailChange = "test2@test.com";

        User user = mock(User.class);
        HttpServletResponse httpRes = mock(HttpServletResponse.class);
        AuthenticationLink validationLink = mock(AuthenticationLink.class);

        when(dao.getUserByEmailVerificationLink(urlId)).thenReturn(user);
        when(user.getValidateEmailLink()).thenReturn(validationLink);
        when(user.getEmailChange()).thenReturn(emailChange);
        when(validationLink.hasExpired()).thenReturn(true);

        String response = sut.verifyEmailAddress(httpRes, urlId);

        verify(user, never()).setValidateEmailLink(any(AuthenticationLink.class));
        verify(user, never()).setEmail(anyString());
        verify(user, never()).setEmailChange(anyString());
        verify(dao, never()).update(any(User.class));
        verify(httpRes, never()).sendRedirect(anyString());
        assertEquals("This link has expired", response);
    }

    @Test
    public void getOrganizationsTest() {
        List<String> orgs = new ArrayList<>();
        when(dao.getOrganizations()).thenReturn(orgs);

        List<String> result = sut.getOrganizations();
        assertEquals(orgs, result);
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
