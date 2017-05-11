package calendar.user;

import calendar.user.dto.ChangePasswordDTO;
import calendar.user.dto.RegistrationDTO;
import calendar.user.dto.UserDetailsUpdateDTO;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Class UserInformationValidatorTest
 *
 * @author Axel Nilsson (axnion)
 */
public class UserInformationValidatorTest {
    private UserInformationValidator sut;
    private UserDAO dao;
    private RegistrationDTO registrationDTO;
    private UserDetailsUpdateDTO userDetailsUpdateDTO;
    private ChangePasswordDTO changePasswordDTO;

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
        dao = mock(UserDAO.class);
        registrationDTO = mock(RegistrationDTO.class);
        userDetailsUpdateDTO = mock(UserDetailsUpdateDTO.class);
        changePasswordDTO = mock(ChangePasswordDTO.class);

        sut = new UserInformationValidator(dao);
    }

    @Test
    public void registrationDTOValid() throws Exception {
        when(dao.getUserByEmail("test@test.com")).thenReturn(null);

        registrationDTOMockSetup(
                "test@test.com",
                validPassword,
                validPassword,
                ""
        );

        try{
            sut.validate(registrationDTO);
        } catch (Exception expt) {
            fail();
        }
    }

    @Test
    public void registrationDTOInvalidEmailAddress() throws Exception {
        registrationDTOMockSetup(
                "abcdefghijk...if",
                validPassword,
                validPassword,
                ""
        );

        try {
            sut.validate(registrationDTO);
            fail();
        } catch (Exception expt) {
            expt.getMessage();
        }
    }

    @Test
    public void registrationDTOValidEmailAlreadyRegistered() throws Exception{
        when(dao.getUserByEmail("test@test.com")).thenReturn(mock(User.class));

        registrationDTOMockSetup(
                "test@test.com",
                validPassword,
                validPassword,
                ""
        );

        try {
            sut.validate(registrationDTO);
            fail();
        } catch (Exception expt) {
            expt.getMessage();
        }
    }

    @Test
    public void registrationDTOPasswordConfirmationMismatch() throws Exception {
        when(dao.getUserByEmail("test@test.com")).thenReturn(null);

        registrationDTOMockSetup(
                "test@test.com",
                validPassword,
                "mismatched_password",
                ""
        );

        try {
            sut.validate(registrationDTO);
            fail();
        } catch (Exception expt) {

            expt.getMessage();
        }
    }

    @Test
    public void registraitonDTOPasswordShort() throws Exception {
        when(dao.getUserByEmail("test@test.com")).thenReturn(null);

        registrationDTOMockSetup(
                "test@test.com",
                shortPassword,
                shortPassword,
                ""
        );

        try {
            sut.validate(registrationDTO);
            fail();
        } catch (Exception expt) {
            expt.getMessage();
        }
    }

    @Test
    public void registraitonDTOPasswordMinimum() throws Exception {
        when(dao.getUserByEmail("test@test.com")).thenReturn(null);

        registrationDTOMockSetup(
                "test@test.com",
                minimumPassword,
                minimumPassword,
                ""
        );

        try {
            sut.validate(registrationDTO);
        } catch (Exception expt) {
            fail();
        }
    }

    @Test
    public void registrationDTOPasswordLong() throws Exception {
        when(dao.getUserByEmail("test@test.com")).thenReturn(null);

        registrationDTOMockSetup(
                "test@test.com",
                longPassword,
                longPassword,
                ""
        );

        try {
            sut.validate(registrationDTO);
            fail();
        } catch (Exception expt) {
            expt.getMessage();
        }
    }

    @Test
    public void registrationDTOPasswordMaximum() throws Exception {
        when(dao.getUserByEmail("test@test.com")).thenReturn(null);

        registrationDTOMockSetup(
                "test@test.com",
                maximumPassword,
                maximumPassword,
                ""
        );

        try {
            sut.validate(registrationDTO);
        } catch (Exception expt) {
            fail();
        }
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
        userDetailsUpdateDTOMockSetup(
                id,
                email,
                org
        );

        try {
            sut.validate(userDetailsUpdateDTO);
        }
        catch (Exception expt) {
            fail(expt.getMessage());
        }
    }

    @Test
    public void userDetailsUpdateDTOValidChangeEmail() throws Exception {
        String id = "0";
        String org = "my_org";

        User userMock = mock(User.class);
        when(userMock.getEmail()).thenReturn("test1@test.com");

        when(dao.getUserById(id)).thenReturn(userMock);
        when(dao.getUserByEmail("test1@test.com")).thenReturn(userMock);
        userDetailsUpdateDTOMockSetup(
                id,
                "test2@test.com",
                org
        );

        try {
            sut.validate(userDetailsUpdateDTO);
        }
        catch (Exception expt) {
            fail(expt.getMessage());
        }
    }

    @Test
    public void userDetailsUpdateDTOUserDoesNotExist() throws Exception {
        String id = "0";
        String email = "test@test.com";
        String org = "my_org";

        User userMock = mock(User.class);
        when(userMock.getEmail()).thenReturn(email);

        when(dao.getUserById(id)).thenReturn(null);
        when(dao.getUserByEmail(email)).thenReturn(userMock);
        userDetailsUpdateDTOMockSetup(
                id,
                email,
                org
        );

        try {
            sut.validate(userDetailsUpdateDTO);
            fail();
        }
        catch (Exception expt) {
            expt.getMessage();
        }
    }

    @Test
    public void userDetailsUpdateDTOInvalidEmailFormat() throws Exception {
        String id = "0";
        String email = "test@test.com";
        String org = "my_org";

        User userMock = mock(User.class);
        when(userMock.getEmail()).thenReturn(email);

        when(dao.getUserById(id)).thenReturn(userMock);
        when(dao.getUserByEmail(email)).thenReturn(userMock);
        userDetailsUpdateDTOMockSetup(
                id,
                "notValidEmail",
                org
        );

        try {
            sut.validate(userDetailsUpdateDTO);
            fail();
        }
        catch (Exception expt) {
            expt.getMessage();
        }
    }

    @Test
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
        userDetailsUpdateDTOMockSetup(
                id1,
                email2,
                ""
        );

        try {
            sut.validate(userDetailsUpdateDTO);
            fail();
        }
        catch (Exception expt) {
            expt.getMessage();
        }
    }

    @Test
    public void changePasswordDTOValid() throws Exception {
        User userMock = mock(User.class);
        when(userMock.getPassword()).thenReturn(
                "$2a$06$qsEYqk81cRBD.00f1vC4duYX/wh5/6KUkxno.lvQnXttnbi7TeYJu"
        );

        when(dao.getUserById("0")).thenReturn(userMock);

        changePasswordDTOMockSetup(
                "0",
                "old_password",
                validPassword,
                validPassword
        );

        try {
            sut.validate(changePasswordDTO);
        }
        catch (Exception expt) {
            fail(expt.getMessage());
        }
    }

    @Test
    public void changePasswordDTOInvalidOldPassword() throws Exception {
        User userMock = mock(User.class);
        when(userMock.getPassword()).thenReturn(
                "$2a$06$qsEYqk81cRBD.00f1vC4duYX/wh5/6KUkxno.lvQnXttnbi7TeYJu"
        );

        when(dao.getUserById("0")).thenReturn(userMock);

        changePasswordDTOMockSetup(
                "0",
                "invalid_old_password",
                validPassword,
                validPassword
        );

        try {
            sut.validate(changePasswordDTO);
            fail();
        }
        catch (Exception expt) {
            expt.getMessage();
        }
    }

    @Test
    public void changePasswordDTOPasswordDoesNotMatch() throws Exception {
        User userMock = mock(User.class);
        when(userMock.getPassword()).thenReturn(
                "$2a$06$qsEYqk81cRBD.00f1vC4duYX/wh5/6KUkxno.lvQnXttnbi7TeYJu"
        );

        when(dao.getUserById("0")).thenReturn(userMock);

        changePasswordDTOMockSetup(
                "0",
                "old_password",
                validPassword,
                "mismatch_password"
        );

        try {
            sut.validate(changePasswordDTO);
            fail();
        }
        catch (Exception expt) {
            expt.getMessage();
        }
    }

    @Test
    public void changePasswordDTOShortPassword() throws Exception {
        User userMock = mock(User.class);
        when(userMock.getPassword()).thenReturn(
                "$2a$06$qsEYqk81cRBD.00f1vC4duYX/wh5/6KUkxno.lvQnXttnbi7TeYJu"
        );

        when(dao.getUserById("0")).thenReturn(userMock);

        changePasswordDTOMockSetup(
                "0",
                "old_password",
                shortPassword,
                shortPassword
        );

        try {
            sut.validate(changePasswordDTO);
            fail();
        }
        catch (Exception expt) {
            expt.getMessage();
        }
    }

    @Test
    public void changePasswordDTOMinimumPassword() throws Exception {
        User userMock = mock(User.class);
        when(userMock.getPassword()).thenReturn(
                "$2a$06$qsEYqk81cRBD.00f1vC4duYX/wh5/6KUkxno.lvQnXttnbi7TeYJu"
        );

        when(dao.getUserById("0")).thenReturn(userMock);

        changePasswordDTOMockSetup(
                "0",
                "old_password",
                minimumPassword,
                minimumPassword
        );

        try {
            sut.validate(changePasswordDTO);
        }
        catch (Exception expt) {
            fail(expt.getMessage());
        }
    }

    @Test
    public void changePasswordDTOLongPassword() throws Exception {
        User userMock = mock(User.class);
        when(userMock.getPassword()).thenReturn(
                "$2a$06$qsEYqk81cRBD.00f1vC4duYX/wh5/6KUkxno.lvQnXttnbi7TeYJu"
        );

        when(dao.getUserById("0")).thenReturn(userMock);

        changePasswordDTOMockSetup(
                "0",
                "old_password",
                longPassword,
                longPassword
        );

        try {
            sut.validate(changePasswordDTO);
            fail();
        }
        catch (Exception expt) {
            expt.getMessage();
        }
    }

    @Test
    public void changePasswordDTOMaximumPassword() throws Exception {
        User userMock = mock(User.class);
        when(userMock.getPassword()).thenReturn(
                "$2a$06$qsEYqk81cRBD.00f1vC4duYX/wh5/6KUkxno.lvQnXttnbi7TeYJu"
        );

        when(dao.getUserById("0")).thenReturn(userMock);

        changePasswordDTOMockSetup(
                "0",
                "old_password",
                maximumPassword,
                maximumPassword
        );

        try {
            sut.validate(changePasswordDTO);
        }
        catch (Exception expt) {
            fail(expt.getMessage());
        }
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
