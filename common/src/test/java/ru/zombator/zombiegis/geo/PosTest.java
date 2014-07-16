package ru.zombator.zombiegis.geo;

import static org.junit.Assert.*;

import com.google.common.testing.EqualsTester;

import org.junit.Test;

/**
 *
 * @author nvamelichev
 */
public final class PosTest {
    private static final double ROUGH_EPS = 1e-3;

    // fromDeg() - initializing geographical position from degree values
    @Test
    public void testFromDegCorrectValuesAreAssignedToLatAndLon() {
        final double lat = 32;
        final double lon = 35;
        final Pos pos = Pos.fromDeg(lat, lon);

        assertEquals(lat, pos.lat, ROUGH_EPS);
        assertEquals(lon, pos.lon, ROUGH_EPS);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFromDegIncorrectLatLeftBoundCausesIAE() {
        Pos.fromDeg(Pos.LAT_MIN_VALUE - ROUGH_EPS, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFromDegIncorrectLatRightBoundCausesIAE() {
        Pos.fromDeg(Pos.LAT_MAX_VALUE + ROUGH_EPS, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFromDegIncorrectLatNaNCausesIAE() {
        Pos.fromDeg(Double.NaN, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFromDegIncorrectLatPlusInfCausesIAE() {
        Pos.fromDeg(Double.POSITIVE_INFINITY, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFromDegIncorrectLatMinusInfCausesIAE() {
        Pos.fromDeg(Double.NEGATIVE_INFINITY, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFromDegIncorrectLonLeftBoundCausesIAE() {
        Pos.fromDeg(0, Pos.LON_MIN_VALUE - ROUGH_EPS);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFromDegIncorrectLonRightBoundCausesIAE() {
        Pos.fromDeg(0, Pos.LON_MAX_VALUE + ROUGH_EPS);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFromDegIncorrectLonNaNCausesIAE() {
        Pos.fromDeg(0, Double.NaN);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFromDegIncorrectLonPlusInfCausesIAE() {
        Pos.fromDeg(0, Double.POSITIVE_INFINITY);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFromDegIncorrectLonMinusInfCausesIAE() {
        Pos.fromDeg(0, Double.NEGATIVE_INFINITY);
    }

    // fromRad() - initializing geographical position from radian values
    @Test
    public void testFromRadCorrectValuesAreAssignedToRLatAndRLon() {
        final double rLat = Math.PI / 6;
        final double rLon = Math.PI / 3;
        final Pos pos = Pos.fromRad(rLat, rLon);

        assertEquals(rLat, pos.rLat, ROUGH_EPS);
        assertEquals(rLon, pos.rLon, ROUGH_EPS);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFromRadIncorrectLatLeftBoundCausesIAE() {
        Pos.fromRad(Pos.RLAT_MIN_VALUE - ROUGH_EPS, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFromRadIncorrectLatRightBoundCausesIAE() {
        Pos.fromRad(Pos.RLAT_MAX_VALUE + ROUGH_EPS, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFromRadIncorrectLatNaNCausesIAE() {
        Pos.fromRad(Double.NaN, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFromRadIncorrectLatPlusInfCausesIAE() {
        Pos.fromRad(Double.POSITIVE_INFINITY, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFromRadIncorrectLatMinusInfCausesIAE() {
        Pos.fromRad(Double.NEGATIVE_INFINITY, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFromRadIncorrectLonLeftBoundCausesIAE() {
        Pos.fromRad(0, Pos.RLON_MIN_VALUE - ROUGH_EPS);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFromRadIncorrectLonRightBoundCausesIAE() {
        Pos.fromRad(0, Pos.RLON_MAX_VALUE + ROUGH_EPS);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFromRadIncorrectLonNaNCausesIAE() {
        Pos.fromRad(0, Double.NaN);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFromRadIncorrectLonPlusInfCausesIAE() {
        Pos.fromRad(0, Double.POSITIVE_INFINITY);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFromRadIncorrectLonMinusInfCausesIAE() {
        Pos.fromRad(0, Double.NEGATIVE_INFINITY);
    }

    // equals() and hashCode()
    @Test
    public void testEqualsAndHashCode() {
        new EqualsTester()
                .addEqualityGroup(Pos.fromDeg(90, 180))
                .addEqualityGroup(Pos.fromDeg(32, 35))
                .testEquals();
    }

    // edge cases for fromDeg() and fromRad()
    @Test
    public void testFromRadMatchesFromDegMinLatMinLon() {
        assertEquals(Pos.fromDeg(Pos.LAT_MIN_VALUE, Pos.LON_MIN_VALUE), Pos.fromRad(Pos.RLAT_MIN_VALUE, Pos.RLON_MIN_VALUE));
    }

    @Test
    public void testFromRadMatchesFromDegMinLatMaxLon() {
        assertEquals(Pos.fromDeg(Pos.LAT_MIN_VALUE, Pos.LON_MAX_VALUE), Pos.fromRad(Pos.RLAT_MIN_VALUE, Pos.RLON_MAX_VALUE));
    }

    @Test
    public void testFromRadMatchesFromDegMaxLatMinLon() {
        assertEquals(Pos.fromDeg(Pos.LAT_MAX_VALUE, Pos.LON_MIN_VALUE), Pos.fromRad(Pos.RLAT_MAX_VALUE, Pos.RLON_MIN_VALUE));
    }

    @Test
    public void testFromRadMatchesFromDegMaxLatMaxLon() {
        assertEquals(Pos.fromDeg(Pos.LAT_MAX_VALUE, Pos.LON_MAX_VALUE), Pos.fromRad(Pos.RLAT_MAX_VALUE, Pos.RLON_MAX_VALUE));
    }

    // calculations
    @Test
    public void gcDistanceToSelfIsZero() {
        Pos pos = Pos.fromDeg(35, 25);
        assertEquals(0.0, pos.distance(pos), ROUGH_EPS);
    }

    @Test
    public void rlDistanceToSelfIsZero() {
        Pos pos = Pos.fromDeg(35, 25);
        assertEquals(0.0, pos.rlDistance(pos), ROUGH_EPS);
    }

    @Test
    public void azimuthToSelfIsZero() {
        Pos pos = Pos.fromDeg(35, 25);
        assertEquals(0.0, pos.azimuth(pos), ROUGH_EPS);
    }
}
