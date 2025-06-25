package su.opencode.digsigcik.common.utils;

import lombok.experimental.UtilityClass;

import java.util.Set;

@UtilityClass
public class ExceptionUtils {

    private static final Set<String> ENTITY_NOT_FOUND_EXCEPTIONS = Set.of(
            "jakarta.persistence.EntityNotFoundException",
            "javax.persistence.EntityNotFoundException"
    );

    private static final Set<String> TLS_EXCEPTIONS = Set.of(
            "org.shredzone.acme4j.exception.AcmeException",
            "org.shredzone.acme4j.exception.AcmeNetworkException"
    );

    public boolean isEntityNotFound(Exception ex) {
        return ENTITY_NOT_FOUND_EXCEPTIONS.contains(ex.getClass().getName());
    }

    public boolean isTlsException(Exception ex) {
        return TLS_EXCEPTIONS.contains(ex.getClass().getName());
    }
}

