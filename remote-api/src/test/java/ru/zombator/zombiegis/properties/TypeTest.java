package ru.zombator.zombiegis.properties;

import static org.fest.assertions.api.Assertions.*;

import org.junit.Test;

/**
 *
 * @author nvamelichev
 */
public final class TypeTest {
    @Test
    public void testGetImmediateParents() {
        // Type.SHIP --> Type.MOBILE --> Type.ANY
        assertThat(Type.SHIP.getParents()).containsExactly(Type.MOBILE);
        assertThat(Type.MOBILE.getParents()).containsExactly(Type.ANY);
    }

    @Test
    public void testGetAllParents() {
        // Type.SHIP --> Type.MOBILE --> Type.ANY
        assertThat(Type.SHIP.getAllParents()).contains(Type.MOBILE, Type.ANY);
    }
}
