package service;

public class IsNotAllowedToModifyCatalogueException extends Exception {
    public IsNotAllowedToModifyCatalogueException() {
    }

    public IsNotAllowedToModifyCatalogueException(String message) {
        super(message);
    }

    public IsNotAllowedToModifyCatalogueException(String message, Throwable cause) {
        super(message, cause);
    }

    public IsNotAllowedToModifyCatalogueException(Throwable cause) {
        super(cause);
    }

    public IsNotAllowedToModifyCatalogueException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
