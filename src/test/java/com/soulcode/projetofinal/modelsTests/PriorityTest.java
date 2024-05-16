package com.soulcode.projetofinal.modelsTests;

import com.soulcode.projetofinal.models.Priority;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PriorityTest {

    @Test
    public void testEquals_SameObject_ReturnsTrue() {
        Priority priority1 = new Priority("High");

        assertTrue(priority1.equals(priority1));
    }

    @Test
    public void testEquals_EqualObjects_ReturnsTrue() {
        Priority priority1 = new Priority("High");
        Priority priority2 = new Priority("High");

        assertTrue(priority1.equals(priority2));
    }

    @Test
    public void testEquals_NullObject_ReturnsFalse() {
        Priority priority1 = new Priority("High");

        assertFalse(priority1.equals(null));
    }

    @Test
    public void testEquals_DifferentClasses_ReturnsFalse() {
        Priority priority1 = new Priority("High");
        Object obj = new Object();

        assertFalse(priority1.equals(obj));
    }

    @Test
    public void testEquals_DifferentIds_ReturnsFalse() {
        Priority priority1 = new Priority("High");
        priority1.setId(1);
        Priority priority2 = new Priority("High");
        priority2.setId(2);

        assertFalse(priority1.equals(priority2));
    }

    @Test
    public void testEquals_DifferentNames_ReturnsFalse() {
        Priority priority1 = new Priority("High");
        Priority priority2 = new Priority("Low");

        assertFalse(priority1.equals(priority2));
    }

    @Test
    public void testHashCode_SameObjects_ReturnsSameHashCode() {
        Priority priority1 = new Priority("High");
        Priority priority2 = new Priority("High");

        assertEquals(priority1.hashCode(), priority2.hashCode());
    }
}
