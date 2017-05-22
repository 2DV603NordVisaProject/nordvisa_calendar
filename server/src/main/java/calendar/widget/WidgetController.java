package calendar.widget;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
@RequestMapping("/api/widget")
public class WidgetController {
    // TODO: Diagrams
    @RequestMapping(value = "/generate", method = RequestMethod.GET)
    public String generateWidget() {
            String strToken = generateRandomString();
            Token token = new Token();
            token.setToken(strToken);

            WidgetGeneratorDao dao = new WidgetGeneratorDaoMongo();
            dao.addApiToken(token);

            return strToken;
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
