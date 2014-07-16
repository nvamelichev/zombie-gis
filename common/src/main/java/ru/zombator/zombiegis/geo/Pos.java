package ru.zombator.zombiegis.geo;

import static java.lang.Double.doubleToLongBits;
import static java.lang.Math.PI;
import static java.lang.Math.toRadians;
import static java.lang.Math.toDegrees;

import com.google.common.base.Preconditions;
import net.jcip.annotations.Immutable;

/**
 * Неизменяемое географическое положение (широта + долгота).
 *
 * @author nvamelichev
 */
@Immutable
public final class Pos {
    /**
     * Минимальное допустимое значение широты в градусах.
     */
    public static final double LAT_MIN_VALUE = -90;
    /**
     * Максимальное допустимое значение широты в градусах.
     */
    public static final double LAT_MAX_VALUE = 90;
    /**
     * Минимальное допустимое значение долготы в градусах.
     */
    public static final double LON_MIN_VALUE = -180;
    /**
     * Максимальное допустимое значение долготы в градусах.
     */
    public static final double LON_MAX_VALUE = 180;

    /**
     * Минимальное допустимое значение широты в радианах.
     */
    public static final double RLAT_MIN_VALUE = -Math.PI / 2;
    /**
     * Максимальное допустимое значение широты в радианах.
     */
    public static final double RLAT_MAX_VALUE = Math.PI / 2;
    /**
     * Минимальное допустимое значение долготы в радианах.
     */
    public static final double RLON_MIN_VALUE = -Math.PI;
    /**
     * Максимальное допустимое значение долготы в радианах.
     */
    public static final double RLON_MAX_VALUE = Math.PI;

    /**
     * Радиус Земли в метрах согласно EPSG:3857.
     */
    public static final double EARTH_RADIUS = 6378137;

    /**
     * Широта в градусах. [&minus;90&deg;, +90&deg;].
     * Положительные значения соответствуют северной широте, отрицательные - южной.
     *
     * @see #LAT_MIN_VALUE
     * @see #LAT_MAX_VALUE
     */
    public final double lat;
    /**
     * Долгота в градусах. [&minus;180&deg;, +180&deg;].
     * Положительные значения соответствуют восточной долготе, отрицательные - западной.
     *
     * @see #LON_MIN_VALUE
     * @see #LON_MAX_VALUE
     */
    public final double lon;
    /**
     * Широта в радианах. [&minus;&pi;/2&deg;, +&pi/2&deg;].
     * Положительные значения соответствуют северной широте, отрицательные - южной.
     *
     * @see #RLAT_MIN_VALUE
     * @see #RLAT_MAX_VALUE
     */
    public final double rLat;
    /**
     * Долгота в радианах. [&minus;&pi;&deg;, +&pi&deg;].
     * Положительные значения соответствуют северной широте, отрицательные - южной.
     *
     * @see #RLON_MIN_VALUE
     * @see #RLON_MAX_VALUE
     */
    public final double rLon;

    /**
     * Создает географическое положение, соответствующее указанной широте и долготе в градусах.
     *
     * @param lat широта в градусах, {@code LAT_MIN_VALUE <= lat <= LAT_MAX_VALUE}
     * @param lon долгота в градусах, {@code LON_MIN_VALUE <= lon <= LON_MAX_VALUE}
     *
     * @return географическое положение
     *
     * @throws IllegalArgumentException {@code lat < LAT_MIN_VALUE || lat > LAT_MAX_VALUE ||
     *                                         lon < LON_MIN_VALUE || lon > LON_MAX_VALUE}
     * @see #LAT_MIN_VALUE
     * @see #LAT_MAX_VALUE
     * @see #LON_MIN_VALUE
     * @see #LON_MAX_VALUE
     */
    public static Pos fromDeg(double lat, double lon) {
        Preconditions.checkArgument(lat >= LAT_MIN_VALUE && lat <= LAT_MAX_VALUE,
                "latitude must lie between %s and %s degrees", LAT_MIN_VALUE, LAT_MAX_VALUE);
        Preconditions.checkArgument(lon >= LON_MIN_VALUE && lon <= LON_MAX_VALUE,
                "longitude must lie between %s and %s degrees", LON_MIN_VALUE, LON_MAX_VALUE);

        return new Pos(lat, lon, toRadians(lat), toRadians(lon));
    }

    /**
     * Создает географическое положение, соответствующее указанной широте и долготе в радианах.
     *
     * @param rLat широта в радианах, {@code RLAT_MIN_VALUE <= rLat <= RLAT_MAX_VALUE}
     * @param rLon долгота в радианах, {@code RLON_MIN_VALUE <= rLon <= RLON_MAX_VALUE}
     *
     * @return географическое положение
     *
     * @throws IllegalArgumentException {@code rLat < RLAT_MIN_VALUE || rLat > RLAT_MAX_VALUE ||
     *                                         rLon < RLON_MIN_VALUE || rLon > RLON_MAX_VALUE}
     * @see #RLAT_MIN_VALUE
     * @see #RLAT_MAX_VALUE
     * @see #RLON_MIN_VALUE
     * @see #RLON_MAX_VALUE
     */
    public static Pos fromRad(double rLat, double rLon) {
        Preconditions.checkArgument(rLat >= RLAT_MIN_VALUE && rLat <= RLAT_MAX_VALUE,
                "latitude must lie between %s and %s radians", RLAT_MIN_VALUE, RLAT_MAX_VALUE);
        Preconditions.checkArgument(rLon >= RLON_MIN_VALUE && rLon <= RLON_MAX_VALUE,
                "longitude must lie between %s and %s radians", RLON_MIN_VALUE, RLON_MAX_VALUE);

        return new Pos(toDegrees(rLat), toDegrees(rLon), rLat, rLon);
    }

    private Pos(double lat, double lon, double rLat, double rLon) {
        this.lat = lat;
        this.lon = lon;
        this.rLat = rLat;
        this.rLon = rLon;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 23 * hash + (int) (doubleToLongBits(this.lat) ^ (doubleToLongBits(this.lat) >>> 32));
        hash = 23 * hash + (int) (doubleToLongBits(this.lon) ^ (doubleToLongBits(this.lon) >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }

        final Pos other = (Pos) obj;
        return doubleToLongBits(this.lat) == doubleToLongBits(other.lat) &&
               doubleToLongBits(this.lon) == doubleToLongBits(other.lon);
    }

    /**
     * Вычисляет расстояние до указанной точки при движении по траектории постоянного курса (локсодроме).
     *
     * @param dest конечная точка
     *
     * @return расстояние до точки при движении постоянным курсом, м
     */
    public double rlDistance(Pos dest) {
        double lat1 = rLat;
        double lat2 = dest.rLat;

        double dLat = toRadians(Math.abs(dest.lat - lat));
        double dLon = toRadians(Math.abs(dest.lon - lon));

        double dPhi = Math.log(Math.tan(PI / 4 + lat2 / 2) / Math.tan(PI / 4 + lat1 / 2));
        // dPhi == 0 при движении строго с востока на запад:
        double q = !(Double.isInfinite(dLat / dPhi) || Double.isNaN(dLat / dPhi)) ? dLat / dPhi : Math.cos(lat1);

        // если dLon больше 180°, есть более короткая локсодрома:
        if (Math.abs(dLon) > PI) {
            dLon = dLon > 0 ? -(2 * PI - dLon) : (2 * PI + dLon);
        }

        double d = Math.sqrt(dLat * dLat + q * q * dLon * dLon) * EARTH_RADIUS;

        return d;
    }

    /**
     * Вычисляет пеленг из одной точки на другую.
     *
     * @param dest конечная точка
     *
     * @return пеленг в градусах
     */
    public double azimuth(Pos dest) {
        double phi1 = rLat;
        double lambda0 = rLon;
        double phi = dest.rLat;
        double lambda = dest.rLon;

        double ldiff = lambda - lambda0;
        double cosphi = Math.cos(phi);

        double bearing = Math.atan2(cosphi * Math.sin(ldiff), (Math.cos(phi1) * Math.sin(phi) - Math.sin(phi1) * cosphi * Math.cos(ldiff)));
        if (bearing < 0) {
            bearing = bearing + Math.PI * 2;
        }
        bearing = Math.toDegrees(bearing);
        return bearing;
    }

    /**
     * Вычисляет кратчайшее расстояние до указанной точки.
     *
     * @param dest конечная точка
     *
     * @return кратчайшее расстояние до точки, м
     */
    public double distance(Pos dest) {
        double phi1 = rLat;
        double lambda0 = rLon;
        double phi = dest.rLat;
        double lambda = dest.rLon;

        double pdiff = Math.sin(((phi - phi1) / 2.0));
        double ldiff = Math.sin((lambda - lambda0) / 2.0);
        double rval = Math.sqrt((pdiff * pdiff) + Math.cos(phi1) * Math.cos(phi) * (ldiff * ldiff));

        return 2.0 * Math.asin(rval) * EARTH_RADIUS;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append('(')
                .append(lat).append('°')
                .append(',').append(' ')
                .append(lon).append('°')
                .append(')')
                .toString();
    }
}
