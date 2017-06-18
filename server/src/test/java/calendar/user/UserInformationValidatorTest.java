package calendar.user;

import calendar.user.dto.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Class UserInformationValidatorTest
 *
 * @author Axel Nilsson (axnion)
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserInformationValidatorTest {
    @Mock
    private RestTemplate rest;
    @Mock
    private UserDAO dao;

    @InjectMocks
    private UserInformationValidator sut;

    private RegistrationDTO registrationDTO;
    private UserDetailsUpdateDTO userDetailsUpdateDTO;
    private ChangePasswordDTO changePasswordDTO;
    private RecoverPasswordDTO recoverPasswordDTO;

    private final String validPassword = "valid_password";
    private final String shortPassword = "9letpassw";
    private final String minimumPassword = "10letpassw";
    private final String longPassword = "NVSDDLIlfSizpUiYuRqxlVEPIlKdeJVowLIiTblBJgBhwfMWzazGSQKQ" +
            "ZshPcbgTgzfVwgduNCSjLYWsSEJOKTyyUmkGNkzPZnltAVgapaTlTVFqKoeZDsxOuZMuJLiYnxZUxNJIXvsj" +
            "HFCpIjbJHJhMTQfIvnnLgOnuApVevoERazzHwtwwFDqJvmVhCuxmrFGCkycoBMmtWdGxkpovgBMUbRnCNFkg" +
            "TycEDNwjWPsrqxztmwOoaCRzPVJgPWpi";
    private final String maximumPassword = "NVSDDLIlfSizpUiYuRqxlVEPIlKdeJVowLIiTblBJgBhwfMWzazGSQKQ" +
            "ZshPcbgTgzfVwgduNCSjLYWsSEJOKTyyUmkGNkzPZnltAVgapaTlTVFqKoeZDsxOuZMuJLiYnxZUxNIXvsj" +
            "HFCpIjbJHJhMTQfIvnnLgOnuApVevoERazzHwtwwFDqJvmVhCuxmrFGCkycoBMmtWdGxkpovgBMUbRnCNFkg" +
            "TycEDNwjWPsrqxztmwOoaCRzPVJgPWpi";

    @Before
    public void setup() {
        registrationDTO = mock(RegistrationDTO.class);
        userDetailsUpdateDTO = mock(UserDetailsUpdateDTO.class);
        changePasswordDTO = mock(ChangePasswordDTO.class);
        recoverPasswordDTO = mock(RecoverPasswordDTO.class);

        User userMock = mock(User.class);
        when(userMock.getPassword()).thenReturn(
                "$2a$06$qsEYqk81cRBD.00f1vC4duYX/wh5/6KUkxno.lvQnXttnbi7TeYJu"
        );
        when(dao.getUserById("0")).thenReturn(userMock);
    }

    @Test
    public void registrationDTOValid() throws Exception {
        when(dao.getUserByEmail("test@test.com")).thenReturn(null);
        registrationDTOMockSetup("test@test.com", validPassword, validPassword, "");
        sut.validate(registrationDTO);
    }

    @Test(expected = Exception.class)
    public void registrationDTOInvalidEmailAddress() throws Exception {
        registrationDTOMockSetup("abcdefghijk...if", validPassword, validPassword, "");
        sut.validate(registrationDTO);
    }

    @Test(expected = Exception.class)
    public void registrationDTOValidEmailAlreadyRegistered() throws Exception{
        when(dao.getUserByEmail("test@test.com")).thenReturn(mock(User.class));
        registrationDTOMockSetup("test@test.com", validPassword, validPassword, "");
        sut.validate(registrationDTO);
    }

    @Test(expected = Exception.class)
    public void registrationDTOPasswordConfirmationMismatch() throws Exception {
        when(dao.getUserByEmail("test@test.com")).thenReturn(null);
        registrationDTOMockSetup("test@test.com", validPassword, "mismatched_password", "");
        sut.validate(registrationDTO);
    }

    @Test(expected = Exception.class)
    public void registraitonDTOPasswordShort() throws Exception {
        when(dao.getUserByEmail("test@test.com")).thenReturn(null);
        registrationDTOMockSetup("test@test.com", shortPassword, shortPassword, "");
        sut.validate(registrationDTO);
    }

    @Test
    public void registraitonDTOPasswordMinimum() throws Exception {
        when(dao.getUserByEmail("test@test.com")).thenReturn(null);
        registrationDTOMockSetup("test@test.com", minimumPassword, minimumPassword, "");
        sut.validate(registrationDTO);
    }

    @Test(expected = Exception.class)
    public void registrationDTOPasswordLong() throws Exception {
        when(dao.getUserByEmail("test@test.com")).thenReturn(null);
        registrationDTOMockSetup("test@test.com", longPassword, longPassword, "");
        sut.validate(registrationDTO);
    }

    @Test
    public void registrationDTOPasswordMaximum() throws Exception {
        when(dao.getUserByEmail("test@test.com")).thenReturn(null);
        registrationDTOMockSetup("test@test.com", maximumPassword, maximumPassword, "");
        sut.validate(registrationDTO);
    }

    @Test
    public void userDetailsUpdateDTOValidNoChange() throws Exception {
        String id = "0";
        String email = "test@test.com";
        String org = "my_org";

        User userMock = mock(User.class);
        when(userMock.getEmail()).thenReturn(email);

        when(dao.getUserById(id)).thenReturn(userMock);
        when(dao.getUserByEmail(email)).thenReturn(userMock);
        userDetailsUpdateDTOMockSetup(id, email, org);

        sut.validate(userDetailsUpdateDTO);
    }

    @Test
    public void userDetailsUpdateDTOValidChangeEmail() throws Exception {
        String id = "0";
        String org = "my_org";

        User userMock = mock(User.class);
        when(userMock.getEmail()).thenReturn("test1@test.com");

        when(dao.getUserById(id)).thenReturn(userMock);
        when(dao.getUserByEmail("test1@test.com")).thenReturn(userMock);
        userDetailsUpdateDTOMockSetup(id, "test2@test.com", org);

        sut.validate(userDetailsUpdateDTO);
    }

    @Test(expected = Exception.class)
    public void userDetailsUpdateDTOUserDoesNotExist() throws Exception {
        String id = "0";
        String email = "test@test.com";
        String org = "my_org";

        User userMock = mock(User.class);
        when(userMock.getEmail()).thenReturn(email);

        when(dao.getUserById(id)).thenReturn(null);
        when(dao.getUserByEmail(email)).thenReturn(userMock);
        userDetailsUpdateDTOMockSetup(id, email, org);

        sut.validate(userDetailsUpdateDTO);
    }

    @Test(expected = Exception.class)
    public void userDetailsUpdateDTOInvalidEmailFormat() throws Exception {
        String id = "0";
        String email = "test@test.com";
        String org = "my_org";

        User userMock = mock(User.class);
        when(userMock.getEmail()).thenReturn(email);

        when(dao.getUserById(id)).thenReturn(userMock);
        when(dao.getUserByEmail(email)).thenReturn(userMock);
        userDetailsUpdateDTOMockSetup(id, "notValidEmail", org);

        sut.validate(userDetailsUpdateDTO);
    }

    @Test(expected = Exception.class)
    public void userDetailsUpdateDTOEmailBelongsToSomeoneElse() throws Exception {
        String id1 = "1";
        String email1 = "test1@test.com";

        String id2 = "2";
        String email2 = "test2@test.com";

        User userMock = mock(User.class);
        when(userMock.getEmail()).thenReturn(email1);

        User otherUserMock = mock(User.class);
        when(otherUserMock.getEmail()).thenReturn(email2);

        when(dao.getUserById(id1)).thenReturn(userMock);
        when(dao.getUserByEmail(email1)).thenReturn(userMock);
        when(dao.getUserById(id2)).thenReturn(otherUserMock);
        when(dao.getUserByEmail(email2)).thenReturn(otherUserMock);
        userDetailsUpdateDTOMockSetup(id1, email2, "");

        sut.validate(userDetailsUpdateDTO);
    }

    @Test
    public void changePasswordDTOValid() throws Exception {
        changePasswordDTOMockSetup("0", "old_password", validPassword, validPassword);
        sut.validate(changePasswordDTO);
    }

    @Test(expected = Exception.class)
    public void changePasswordDTOInvalidOldPassword() throws Exception {
        changePasswordDTOMockSetup("0", "invalid_old_password", validPassword, validPassword);
        sut.validate(changePasswordDTO);
    }

    @Test(expected = Exception.class)
    public void changePasswordDTOPasswordDoesNotMatch() throws Exception {
        changePasswordDTOMockSetup("0", "old_password", validPassword, "mismatch_password");
        sut.validate(changePasswordDTO);
    }

    @Test(expected = Exception.class)
    public void changePasswordDTOShortPassword() throws Exception {
        changePasswordDTOMockSetup("0", "old_password", shortPassword, shortPassword);
        sut.validate(changePasswordDTO);
    }

    @Test
    public void changePasswordDTOMinimumPassword() throws Exception {
        changePasswordDTOMockSetup("0", "old_password", minimumPassword, minimumPassword);
        sut.validate(changePasswordDTO);
    }

    @Test(expected = Exception.class)
    public void changePasswordDTOLongPassword() throws Exception {
        changePasswordDTOMockSetup("0", "old_password", longPassword, longPassword);
        sut.validate(changePasswordDTO);
    }

    @Test
    public void changePasswordDTOMaximumPassword() throws Exception {
        changePasswordDTOMockSetup("0", "old_password", maximumPassword, maximumPassword);
        sut.validate(changePasswordDTO);
    }

    @Test
    public void recoverPasswordValid() throws Exception {
        recoverPasswordDTOMockSetup("", validPassword, validPassword);
        sut.validate(recoverPasswordDTO);
    }

    @Test(expected = Exception.class)
    public void recoverPasswordMismatch() throws Exception {
        recoverPasswordDTOMockSetup("", validPassword, "other_valid_password");
        sut.validate(recoverPasswordDTO);
    }

    @Test(expected = Exception.class)
    public void recoverPasswordShortPassword() throws Exception {
        recoverPasswordDTOMockSetup("", shortPassword, shortPassword);
        sut.validate(recoverPasswordDTO);
    }

    @Test
    public void recoverPasswordMinimumPassword() throws Exception {
        recoverPasswordDTOMockSetup("",minimumPassword, minimumPassword);
        sut.validate(recoverPasswordDTO);
    }

    @Test(expected = Exception.class)
    public void recoverPasswordLongPassword() throws Exception {
        recoverPasswordDTOMockSetup("", longPassword, longPassword);
        sut.validate(recoverPasswordDTO);
    }

    @Test
    public void recoverPasswordMaximumPassword() throws Exception {
        recoverPasswordDTOMockSetup("", maximumPassword, maximumPassword);
        sut.validate(recoverPasswordDTO);
    }

    @Test
    public void validateRecaptcha() throws Exception {
        String url = "http://someurl.com";
        String secret = "shhhhhh";
        String secretFromClient = "SSSSSSSSSSSSHHHH!!!";
        ReflectionTestUtils.setField(sut, "recaptchaUrl", url);
        ReflectionTestUtils.setField(sut, "recaptchaRes", secret);

        ResponseEntity<RecaptchaResponseDTO> responseEntity = mock(ResponseEntity.class);
        RecaptchaResponseDTO dto = mock(RecaptchaResponseDTO.class);

        when(rest.postForEntity(eq(url), any(MultiValueMap.class), eq(RecaptchaResponseDTO.class)))
                .thenReturn(responseEntity);
        when(responseEntity.getBody()).thenReturn(dto);

        RecaptchaResponseDTO response = sut.validateRecaptcha(secretFromClient);

        assertEquals(dto, response);

        ArgumentCaptor<MultiValueMap> captor = ArgumentCaptor.forClass(MultiValueMap.class);
        verify(rest, times(1)).postForEntity(eq(url), captor.capture(), eq(RecaptchaResponseDTO.class));
        MultiValueMap<String, String> body = captor.getValue();

        assertEquals(secretFromClient, body.getFirst("secret"));
        assertEquals(secret, body.getFirst("response"));
    }

    @Test
    public void validateRecaptchaConnectionError() {
        String url = "http://someurl.com";
        String secret = "shhhhhh";
        String secretFromClient = "SSSSSSSSSSSSHHHH!!!";
        ReflectionTestUtils.setField(sut, "recaptchaUrl", url);
        ReflectionTestUtils.setField(sut, "recaptchaRes", secret);

        when(rest.postForEntity(eq(url), any(MultiValueMap.class), eq(RecaptchaResponseDTO.class)))
                .thenThrow(new RestClientException("Connection failed"));
        try {
            sut.validateRecaptcha(secretFromClient);
            fail();
        }
        catch(Exception expt) {
            expt.getMessage();
        }
    }

    private void recoverPasswordDTOMockSetup(String urlId, String password, String passwordConf) {
        when(recoverPasswordDTO.getUrlId()).thenReturn(urlId);
        when(recoverPasswordDTO.getPassword()).thenReturn(password);
        when(recoverPasswordDTO.getPasswordConfirmation()).thenReturn(passwordConf);
    }

    private void registrationDTOMockSetup(String email, String password,
                                                     String passwordConfirmation,
                                                     String orgnization) {
        when(registrationDTO.getEmail()).thenReturn(email);
        when(registrationDTO.getPassword()).thenReturn(password);
        when(registrationDTO.getPasswordConfirmation()).thenReturn(passwordConfirmation);
        when(registrationDTO.getOrganization()).thenReturn(orgnization);
    }

    private void userDetailsUpdateDTOMockSetup(String id, String email, String organization) {
        when(userDetailsUpdateDTO.getId()).thenReturn(id);
        when(userDetailsUpdateDTO.getEmail()).thenReturn(email);
        when(userDetailsUpdateDTO.getOrganization()).thenReturn(organization);
    }

    private void changePasswordDTOMockSetup(String id, String oldPassword, String password,
                                            String passwordConfirmation) {
        when(changePasswordDTO.getId()).thenReturn(id);
        when(changePasswordDTO.getOldPassword()).thenReturn(oldPassword);
        when(changePasswordDTO.getPassword()).thenReturn(password);
        when(changePasswordDTO.getPasswordConfirmation()).thenReturn(passwordConfirmation);
    }
}
