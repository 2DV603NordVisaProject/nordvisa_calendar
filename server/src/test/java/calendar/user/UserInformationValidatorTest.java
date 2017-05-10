package calendar.user;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
/**
 * Class UserInformationValidatorTest
 *
 * @author Axel Nilsson (axnion)
 */
public class UserInformationValidatorTest {
    private UserInformationValidator sut;

    @Test
    public void validRegistrationDTO() {
        

        sut = new UserInformationValidator();
    }

    @Test
    public void validRegistrationDTOEmailAlreadyRegistered() {

    }
}
