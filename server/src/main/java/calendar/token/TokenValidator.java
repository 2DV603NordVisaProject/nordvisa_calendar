package calendar.token;

import org.springframework.stereotype.Component;

/**
 * Class TokenValidator
 *
 * @author Axel Nilsson (axnion)
 */
@Component
public class TokenValidator {
    private TokenDAO dao = new TokenDAOMongo();

    public boolean validate(String tokenKey) {
        if(tokenKey.equals("dashboard")) {
            return true;
        }

        Token token = dao.get(tokenKey);

        if(token == null) {
            return false;
        }

        if(token.getRequests() >= token.getMaxRequests()) {
            return false;
        }

        token.setRequests(token.getRequests() + 1);
        dao.update(token);
        return true;
    }
}
