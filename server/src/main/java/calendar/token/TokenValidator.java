package calendar.token;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Class TokenValidator
 *
 * @author Axel Nilsson (axnion)
 */
@Component
public class TokenValidator {
    @Autowired
    private TokenDAO dao;

    public boolean validate(String tokenKey) {
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
