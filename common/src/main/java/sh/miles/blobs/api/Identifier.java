package sh.miles.blobs.api;

import com.google.common.base.Preconditions;
import sh.miles.blobs.common.util.StringUtil;

public record Identifier(String name, String key) {

    public Identifier {
        Preconditions.checkArgument(StringUtil.isAlphaNumeric(name), "identifier name must contain only alphanumeric, hyphen, and underscore");
        Preconditions.checkArgument(StringUtil.isAlphaNumeric(key), "identifier key must contain only alphanumeric, hyphen, and undscore");
    }

    public static Identifier base(String key) {
        return new Identifier("blobs", key);
    }
}
