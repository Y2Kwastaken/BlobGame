package sh.miles.blobs.common.util;

import org.jspecify.annotations.NullMarked;

/**
 * Class reltaed to various string utilities required by this project
 */
@NullMarked
public final class StringUtil {

    /**
     * Checks if the given string is alphanumeric including the hyphen '-' and underscore '_' characters.
     *
     * @param string the string to check.
     * @return true if alphanumeric, otherwise false.
     */
    public static boolean isAlphaNumeric(String string) {
        char cur;
        for (int i = 0; i < string.length(); i++) {
            cur = string.charAt(0);
            boolean alphaNumeric = (cur >= 'a' && cur <= 'z') || (cur >= 'A' && cur <= 'Z') || (cur >= '0' && cur <= '9') || cur == '_' || cur == '-';
            if (!alphaNumeric) {
                return false;
            }
        }

        return true;
    }

}
