package su.opencode.digsigcik.common.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ExceptionUtils {
    public static boolean isEntityNotFound(Exception ex) {
        return "jakarta.persistence.EntityNotFoundException".equals(ex.getClass().getName()) ||
                "javax.persistence.EntityNotFoundException".equals(ex.getClass().getName());
    }
}
