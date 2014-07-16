package ru.zombator.zombiegis.model.obj;

/**
 * Информационная модель для подвижного объекта карты.
 *
 * @author nvamelichev
 *
 * @see BasicMobDataModel стандартная реализация
 */
public interface MobDataModel extends DataModel {
    /**
     * Возвращает курс объекта карты (угол между направлением на север и текущим направлением объекта,
     * отсчитываемый по часовой стрелке).
     *
     * @return курс в градусах [0..360&deg;]
     */
    double getCourse();

    /**
     * Возвращает скорость объекта в узлах.
     *
     * @return скорость в узлах (&gt; 0)
     */
    double getSpeed();
}
