package ru.zombator.zombiegis.geo;

import static java.lang.Math.atan2;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;

import static ru.zombator.zombiegis.geo.Pos.EARTH_RADIUS;

import java.util.ArrayList;
import java.util.List;

import net.jcip.annotations.Immutable;

/**
 * Неизменяемое положение в пространстве, задаваемое трехмерным вектором. Начало координат - центр Земли.
 * <p>
 * Позволяет удобнее выполнять некоторые вычисления.
 *
 * @author nvamelichev
 */
@Immutable
public final class Pos3D {
    /**
     * X-координата.
     */
    public final double x;
    /**
     * Y-координата.
     */
    public final double y;
    /**
     * Z-координата.
     */
    public final double z;

    public Pos3D(Pos pos) {
        // @see http://www.jennessent.com/downloads/Graphics_Shapes_Online.pdf, p. 68
        double phi = Math.PI / 2 - pos.rLat;
        double theta = pos.rLon;

        this.x = EARTH_RADIUS * sin(phi) * cos(theta);
        this.y = EARTH_RADIUS * sin(phi) * sin(theta);
        this.z = EARTH_RADIUS * cos(phi);
    }

    public Pos3D(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * @return широта в радианах, соответствующая данному вектору
     *
     * @see http://www.jennessent.com/downloads/Graphics_Shapes_Online.pdf, p. 70
     */
    public double rLat() {
        double phi = atan2(sqrt(x * x + y * y), z);
        return Math.PI / 2 - phi;
    }

    /**
     * @return долгота в радианах, соответствующая данному вектору
     *
     * @see http://www.jennessent.com/downloads/Graphics_Shapes_Online.pdf, p. 70
     */
    public double rLon() {
        return atan2(y, x);
    }

    /**
     * @return норма вектора
     */
    public double norm() {
        return sqrt(x * x + y * y + z * z);
    }

    /**
     * Умножает вектор на скаляр.
     *
     * @param scalar скаляр
     *
     * @return вектор, умноженный на скаляр
     */
    public Pos3D mulScalar(double scalar) {
        return new Pos3D(scalar * x, scalar * y, scalar * z);
    }

    /**
     * Складывает данный вектор с указанным вектором.
     *
     * @param other второе слагаемое
     *
     * @return результат сложения векторов
     */
    public Pos3D add(Pos3D other) {
        return new Pos3D(x + other.x, y + other.y, z + other.z);
    }

    /**
     * Вычисляет расстояние до указанного вектора.
     *
     * @param other вектор, расстояние до которого мы определяем
     *
     * @return расстояние до указанного вектора
     */
    public double dist(Pos3D other) {
        return this.sub(other).norm();
    }

    /**
     * Вычитает указанный вектор из данного.
     *
     * @param other вычитаемое
     *
     * @return результат вычитания векторов
     */
    public Pos3D sub(Pos3D other) {
        return new Pos3D(x - other.x, y - other.y, z - other.z);
    }

    /**
     * Вычисляет центр масс указанных точек.
     *
     * @param points точки
     *
     * @return центр масс
     */
    public static Pos centroid(List<Pos> points) {
        Pos3D vecCentroid = pos3DCentroid(toCartesian(points));
        return Pos.fromRad(vecCentroid.rLat(), vecCentroid.rLon());
    }

    /**
     * Преобразует указанные точки (ш;д) в трехмерные вектора.
     *
     * @param points точки
     *
     * @return вектора
     */
    public static List<Pos3D> toCartesian(List<Pos> points) {
        List<Pos3D> vectors = new ArrayList<>(points.size());
        for (Pos pt: points) {
            vectors.add(new Pos3D(pt));
        }

        return vectors;
    }

    /**
     * Вычисляет центр масс фигуры, представленной указанными векторами.
     *
     * @param vectors вектора
     *
     * @return вектор центра масс
     *
     * @see http://www.jennessent.com/downloads/Graphics_Shapes_Online.pdf, p. 66
     */
    public static Pos3D pos3DCentroid(List<Pos3D> vectors) {
        // 2.
        double sumX = 0;
        double sumY = 0;
        double sumZ = 0;

        for (Pos3D vec: vectors) {
            sumX += vec.x;
            sumY += vec.y;
            sumZ += vec.z;
        }

        final int size = vectors.size();
        Pos3D meanVector = new Pos3D(sumX / size, sumY / size, sumZ / size);

        // 3.
        return meanVector.mulScalar(1 / meanVector.norm());
    }
}
