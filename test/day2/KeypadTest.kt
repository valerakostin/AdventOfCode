package day2

import org.testng.Assert
import org.testng.annotations.Test


internal class KeypadTest {
    @Test
    fun testStandardKeypad() {
        val input = listOf("ULL",
                "RRDDD",
                "LURDL",
                "UUUUD")
        Assert.assertEquals(Keypad.task1(input), "1985")
    }

    @Test
    fun testFancyKeypad() {
        val input = listOf("ULL",
                "RRDDD",
                "LURDL",
                "UUUUD")
        Assert.assertEquals(Keypad.task2(input), "5DB3")
    }
}