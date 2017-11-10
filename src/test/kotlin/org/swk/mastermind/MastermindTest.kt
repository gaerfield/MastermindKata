package org.swk.mastermind

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.swk.mastermind.Mastermind.Color.*
import org.swk.mastermind.Mastermind.EvaluationResult
import java.util.*
import java.util.stream.Stream

class MastermindTest{

    companion object {
        @JvmStatic
        fun secretAndGuessProvider() = Stream.of(
            // Fall 1 - eine korrekte Farbe
            Arguments.of( EvaluationResult(1,0),
                    arrayOf(RED),
                    arrayOf(RED) ),

            // Fall 2 - 4 Items und eine korrekte Farbe
            Arguments.of( EvaluationResult(1,0),
                    arrayOf(RED, RED, RED, RED),
                    arrayOf(RED, BLUE, YELLOW, PURPLE) ),

            // Fall 3 - korrekte Platzierungen und korrekte Farben werden gegeneinander aufgerechnet
            Arguments.of(EvaluationResult(2,0),
                    arrayOf(RED, RED, RED, RED),
                    arrayOf(PURPLE, RED, RED, PURPLE) ),

            // Fall 4 - 2 Farben, 4 Steine korrekte Farben aber deplatziert
            Arguments.of(EvaluationResult(2,0),
                    arrayOf(RED, RED, RED, RED),
                    arrayOf(PURPLE, RED, RED, PURPLE) ),

            // Fall 5 - 2 Farben, 4 korrekte Farben aber 2 Steine deplatziert
            Arguments.of(EvaluationResult(2,0),
                    arrayOf(RED, RED, RED, RED),
                    arrayOf(PURPLE, RED, RED, PURPLE) )
        )
    }

    @ParameterizedTest
    @MethodSource("secretAndGuessProvider")
    fun testWithSimpleMethodSource(expected: EvaluationResult, secret : Array<Mastermind.Color>, guess : Array<Mastermind.Color>) {
        assertEquals(expected, Mastermind(*secret).evaluate(*guess))
    }

    @TestFactory
    fun translateDynamicTestsFromStream() = Arrays.asList(
            DynamicTest.dynamicTest(
                    "Fall 1 - eine korrekte Farbe",
                    { assertEquals(
                            EvaluationResult(1,0),
                            Mastermind(RED).evaluate(RED))
                    }),
            DynamicTest.dynamicTest(
                    "Fall 2 - 4 Items und eine korrekte Farbe",
                    { assertEquals(
                            EvaluationResult(1,0),
                            Mastermind(RED, RED, RED, RED).evaluate(RED, BLUE, YELLOW, PURPLE))
                    }),
            DynamicTest.dynamicTest(
                    "Fall 3 - korrekte Platzierungen und korrekte Farben werden gegeneinander aufgerechnet",
                    { assertEquals(
                            EvaluationResult(2,0),
                            Mastermind(RED, RED, RED, RED).evaluate(PURPLE, RED, RED, PURPLE))
                    }),
            DynamicTest.dynamicTest(
                    "Fall 4 - 2 Farben, 4 Steine korrekte Farben aber deplatziert",
                    { assertEquals(
                            EvaluationResult(0,4),
                            Mastermind(RED, PURPLE, PURPLE, RED).evaluate(PURPLE, RED, RED, PURPLE))
                    }),
            DynamicTest.dynamicTest(
                    "Fall 5 - 2 Farben, 4 korrekte Farben aber 2 Steine deplatziert",
                    { assertEquals(
                            EvaluationResult(2,2),
                            Mastermind(RED, PURPLE, RED, PURPLE).evaluate(PURPLE, RED, RED, PURPLE))
                    })
        )

    @Test
    fun evaluateDifferentSecretAndGuessSizeShouldRaiseException(){
        val sut = Mastermind(RED, RED)
        assertThrows(IllegalArgumentException::class.java, { sut.evaluate(RED) })
    }
}