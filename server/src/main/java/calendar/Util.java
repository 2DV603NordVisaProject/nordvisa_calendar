package calendar;

import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * Class Util
 *
 * TODO: Add Comments
 *
 * @author Axel Nilsson (axnion)
 */
@Component
public class Util {
    /**
     * Generates a random hex string. Used to generate a unique path for event images.
     *
     * @param length    The length of the hex string wanted.
     * @return          A random hex string of length {length}.
     */
    public String getRandomHexString(int length){
        Random r = new Random();
        StringBuilder sb = new StringBuilder();
        while(sb.length() < length) {
            sb.append(Integer.toHexString(r.nextInt()));
        }

        return sb.toString().substring(0, length);
    }
}
