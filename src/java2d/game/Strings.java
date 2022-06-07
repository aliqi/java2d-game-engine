// Creator: Aliqi
// Create at: 2022/4/16
package java2d.game;

public interface Strings {

    static boolean isEmpty(String value) {
        return value == null || "".equals(value);
    }

    static boolean isNotEmpty(String value) {
        return value != null && !"".equals(value);
    }

    static boolean isBlank(String value) {
        return value == null || "".equals(value.trim());
    }

    static boolean isNotBlank(String value) {
        return value != null && !"".equals(value.trim());
    }

    static boolean equals(String src, String dest) {
        if (src == null && dest == null) return true;
        if (src != null && dest != null) return src.equals(dest);
        return false;
    }
}
