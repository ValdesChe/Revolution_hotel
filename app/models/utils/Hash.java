package models.utils;

import org.mindrot.jbcrypt.BCrypt;

import java.util.UUID;

/**
 * Password utility class.  This handles password encryption and validation.
 * <p/>
 * User: V@ldes
 * Date: 17/07/17
 */
public class Hash {
    public final char[] alphabet = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
    /**
     * Create an encrypted password from a clear string.
     *
     * @param clearString the clear string
     * @return an encrypted password of the clear string
     * @throws AppException APP Exception, from NoSuchAlgorithmException
     */
    public static String createPassword(String clearString) throws AppException {
        if (clearString == null) {
            throw new AppException("No password defined!");
        }
        return BCrypt.hashpw(clearString, BCrypt.gensalt());
    }

    /**
     * @param candidate         the clear text
     * @param encryptedPassword the encrypted password string to check.
     * @return true if the candidate matches, false otherwise.
     */
    public static boolean checkPassword(String candidate, String encryptedPassword) {
        if (candidate == null) {
            return false;
        }
        if (encryptedPassword == null) {
            return false;
        }
        return BCrypt.checkpw(candidate, encryptedPassword);
    }



}
