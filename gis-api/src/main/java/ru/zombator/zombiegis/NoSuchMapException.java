package ru.zombator.zombiegis;

/**
 * Исключение "карта не найдена".
 *
 * @author nvamelichev
 */
public class NoSuchMapException extends GISException {
    public NoSuchMapException(long mapId) {
        super("No map with id: " + mapId);
    }
}
