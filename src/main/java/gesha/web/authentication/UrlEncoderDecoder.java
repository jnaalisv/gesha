package gesha.web.authentication;


import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class UrlEncoderDecoder {

    static String decode(Object o) {
        return URLDecoder.decode((String) o, StandardCharsets.UTF_8);
    }

    public static String encode(String stringToEncode) {
        return URLEncoder.encode(stringToEncode, StandardCharsets.UTF_8);
    }
}
