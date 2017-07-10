package calendar.token;

import calendar.Util;
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
    @Autowired
    private Util util;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public TokenDTO getToken() {
        Token token = new Token(util.getRandomHexString(20));
        dao.add(token);

        TokenDTO dto = new TokenDTO();
        dto.setToken(token.getToken());
        return dto;
    }
}
