package rpcdemo.client

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class ControllerTest {
    @Test
    fun `medianOfPresorted should return correct median for odd-sized list`() {
        val list = listOf(1, 2, 3)
        assertEquals(2.0, list.medianOfPresorted())
    }

    @Test
    fun `medianOfPresorted should return correct median for even-sized list`() {
        val list = listOf(1, 2, 3, 4)
        assertEquals(2.5, list.medianOfPresorted())
    }

    @Test
    fun `medianOfPresorted should throw exception for empty list`() {
        val list = emptyList<Int>()
        assertThrows(IllegalArgumentException::class.java) {
            list.medianOfPresorted()
        }
    }
}
