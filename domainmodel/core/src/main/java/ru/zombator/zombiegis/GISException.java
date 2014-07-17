package ru.zombator.zombiegis;

/**
 * Исключение, происходящее при выполнении операции пользователя и мешающее успешному
 * выполнению этой операции. Например, исключение при получении списка карт или при открытии карты.
 *
 * @author nvamelichev
 */
public class GISException extends Exception {
    public GISException() {
        super();
    }

    public GISException(String message) {
        super(message);
    }

    public GISException(Throwable cause) {
        super(cause);
    }

    public GISException(String message, Throwable cause) {
        super(message, cause);
    }
}
