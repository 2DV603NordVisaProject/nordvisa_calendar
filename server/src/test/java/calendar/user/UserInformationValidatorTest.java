package calendar.user;

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
    UserInformationValidator sut;
    UserDAO dao;
    RegistrationDTO regDTO;
    UserUpdateDTO updateDTO;

    @Before
    public void setup() {
        dao = mock(UserDAO.class);
        regDTO = mock(RegistrationDTO.class);
        updateDTO = mock(UserUpdateDTO.class);

        sut = new UserInformationValidator(dao);
    }

    @Test
    public void registrationDTOValid() throws Exception {
        when(dao.getUserByEmail("test@test.com")).thenReturn(null);

        String password = "valid_password";

        registrationDTOMockSetup(
                "test@test.com",
                password,
                password,
                ""
        );

        try{
            sut.validate(regDTO);
        } catch (Exception expt) {
            fail();
        }
    }

    @Test
    public void registrationDTOInvalidEmailAddress() {
        String password = "valid_password";

        registrationDTOMockSetup(
                "abcdefghijk...if",
                password,
                password,
                ""
        );

        try {
            sut.validate(regDTO);
            fail();
        } catch (Exception expt) {

        }
    }

    @Test
    public void registrationDTOValidEmailAlreadyRegistered() throws Exception{
        when(dao.getUserByEmail("test@test.com")).thenReturn(mock(User.class));

        String password = "valid_password";

        registrationDTOMockSetup(
                "test@test.com",
                password,
                password,
                ""
        );

        try {
            sut.validate(regDTO);
            fail();
        } catch (Exception expt) {

        }
    }

    @Test
    public void registrationDTOPasswordConfirmationMismatch() throws Exception {
        when(dao.getUserByEmail("test@test.com")).thenReturn(null);

        registrationDTOMockSetup(
                "test@test.com",
                "valid_password",
                "mismatched_password",
                ""
        );

        try {
            sut.validate(regDTO);
            fail();
        } catch (Exception expt) {

        }
    }

    @Test
    public void registraitonDTOPasswordShort() throws Exception {
        when(dao.getUserByEmail("test@test.com")).thenReturn(null);

        String password = "9letpassw";

        registrationDTOMockSetup(
                "test@test.com",
                password,
                password,
                ""
        );

        try {
            sut.validate(regDTO);
            fail();
        } catch (Exception expt) {

        }
    }

    @Test
    public void registraitonDTOPasswordMinimum() throws Exception {
        when(dao.getUserByEmail("test@test.com")).thenReturn(null);

        String password = "10letpassw";

        registrationDTOMockSetup(
                "test@test.com",
                password,
                password,
                ""
        );

        try {
            sut.validate(regDTO);
        } catch (Exception expt) {
            fail();
        }
    }

    @Test
    public void registrationDTOPasswordLong() throws Exception {
        when(dao.getUserByEmail("test@test.com")).thenReturn(null);

        String password = "NVSDDLIlfSizpUiYuRqxlVEPIlKdeJVowLIiTblBJgBhwfMWzazGSQKQZshPcbgTgzfVwg" +
                "duNCSjLYWsSEJOKTyyUmkGNkzPZnltAVgapaTlTVFqKoeZDsxOuZMuJLiYnxZUxNJIXvsjHFCpIjbJHJ" +
                "hMTQfIvnnLgOnuApVevoERazzHwtwwFDqJvmVhCuxmrFGCkycoBMmtWdGxkpovgBMUbRnCNFkgTycEDN" +
                "wjWPsrqxztmwOoaCRzPVJgPWpi";

        registrationDTOMockSetup(
                "test@test.com",
                password,
                password,
                ""
        );

        try {
            sut.validate(regDTO);
            fail();
        } catch (Exception expt) {
        }
    }

    @Test
    public void registrationDTOPasswordMaximum() throws Exception {
        when(dao.getUserByEmail("test@test.com")).thenReturn(null);

        String password = "NVSDDLIlfSizpUiYuRqxlVEPIlKdeJVowLIiTblBJgBhwfMWzazGSQKQZshPcbgTgzfVwg" +
                "duNCSjLYWsSEJOKTyyUmkGkzPZnltAVgapaTlTVFqKoeZDsxOuZMuJLiYnxZUxNJIXvsjHFCpIjbJHJ" +
                "hMTQfIvnnLgOnuApVevoERazzHwtwwFDqJvmVhCuxmrFGCkycoBMmtWdGxkpovgBMUbRnCNFkgTycEDN" +
                "wjWPsrqxztmwOoaCRzPVJgPWpi";

        registrationDTOMockSetup(
                "test@test.com",
                password,
                password,
                ""
        );

        try {
            sut.validate(regDTO);
        } catch (Exception expt) {
            fail();
        }
    }

    private void registrationDTOMockSetup(String email, String password,
                                                     String passwordConfirmation,
                                                     String orgnization) {
        when(regDTO.getEmail()).thenReturn(email);
        when(regDTO.getPassword()).thenReturn(password);
        when(regDTO.getPasswordConfirmation()).thenReturn(passwordConfirmation);
        when(regDTO.getOrganization()).thenReturn(orgnization);
    }

    private void userUpdateDTOMockSetup(String email, String oldPassword, String password,
                                                 String passwordConfirmation, String organization) {
        when(updateDTO.getEmail()).thenReturn(email);
        when(updateDTO.getOldPassword()).thenReturn(oldPassword);
        when(updateDTO.getPassword()).thenReturn(password);
        when(updateDTO.getPasswordConfirmation()).thenReturn(passwordConfirmation);
        when(updateDTO.getOrganization()).thenReturn(organization);

    }
}
