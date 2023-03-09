package zh.arss.utilities;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class PasswordHasher {
    private PasswordHasher() {
    }

    public static String hashingPassword(String password) {
        try {
            byte[] bytes = password.getBytes(StandardCharsets.UTF_8);
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] hash = md5.digest(bytes);
            BigInteger bigInteger = new BigInteger(1, hash);
            password = bigInteger.toString(16);
        } catch (Exception exception) {
            return password;
        }
        return password;
    }
}
