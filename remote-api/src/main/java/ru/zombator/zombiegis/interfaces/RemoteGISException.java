package ru.zombator.zombiegis.interfaces;

/**
 * Исключение, возникающее при доступе к удаленным сервисам по работе с картой.
 *
 * @author nvamelichev
 */
public final class RemoteGISException extends Exception {
    private final Reason reason;

    public RemoteGISException() {
        this(Reason.UNKNOWN);
    }

    public RemoteGISException(Reason reason) {
        super();
        this.reason = reason;
    }

    public RemoteGISException(String message) {
        this(Reason.UNKNOWN, message);
    }

    public RemoteGISException(Reason reason, String message) {
        super(message);
        this.reason = reason;
    }

    public RemoteGISException(String message, Throwable cause) {
        this(Reason.UNKNOWN, message, cause);
    }

    public RemoteGISException(Reason reason, String message, Throwable cause) {
        super(message, cause);
        this.reason = reason;
    }

    /**
     * @return причина ошибки
     */
    public Reason getReason() {
        return reason;
    }

    /**
     * Причина ошибки.
     */
    public static enum Reason {
        /**
         * Ошибка вызывающей стороны: нарушение бизнес-логики со стороны клиента, которое обнаружил сервер.
         */
        BUSINESS_LOGIC,
        /**
         * Внутренняя ошибка сервера.
         */
        INTERNAL,
        /**
         * Причина ошибки неизвестна.
         */
        UNKNOWN,
    }
}
