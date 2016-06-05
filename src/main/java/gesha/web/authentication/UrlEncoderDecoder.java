package gesha.web.authentication;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import com.auth0.jwt.internal.org.apache.commons.codec.CharEncoding;

public class UrlEncoderDecoder {

    static String decode(Object o) {
        try {
            return URLDecoder.decode((String) o, CharEncoding.UTF_8);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public static String encode(String stringToEncode) {
        try {
            return URLEncoder.encode(stringToEncode, CharEncoding.UTF_8);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}
