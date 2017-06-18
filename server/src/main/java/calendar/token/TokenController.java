package calendar.token;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
@RequestMapping("/api/token")
public class TokenController {
    @Autowired
    private TokenDAO dao;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public TokenDTO getToken() {
        String strToken = generateRandomString();
        Token token = new Token(strToken,
                10000,
                DateTime.now().getMillis(),
                DateTime.now().plusYears(1).getMillis()
        );

        dao.add(token);

        TokenDTO dto = new TokenDTO();
        dto.setToken(strToken);

        return dto;
    }

    /**
     * Generates a random 20 character string
     *
     * @return A random string
     */
    private String generateRandomString() {
        String characters = "abcdefghijklmnopqrstuvxyzABCDEFGHIJKLMNOPQRSTUVXYZ1234567890";
        int length = 20;
        Random rnd = new Random();

        char[] text = new char[length];
        for (int i = 0; i < length; i++) {
            text[i] = characters.charAt(rnd.nextInt(characters.length()));
        }

        return new String(text);
    }
}
