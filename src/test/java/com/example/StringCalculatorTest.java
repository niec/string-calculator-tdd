package com.example;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class StringCalculatorTest {
    private static StringCalculator calculator;

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @BeforeClass
    public static void setup() {
        calculator = new StringCalculator();
    }

    @Test
    public void shouldReturn0ForAnEmptyString() {
        int result = calculator.add("");

        Assert.assertEquals(0, result);
    }

    @Test
    public void shouldReturnNumberForASingleNumberString() {
        int result = calculator.add("1");

        Assert.assertEquals(1, result);
    }

    @Test
    public void shouldReturnNumbersSumForADoubleNumberString() {
        int result = calculator.add("1,2");

        Assert.assertEquals(3, result);
    }

    @Test
    public void shouldReturnNumbersSumForAMultipleNumberString() {
        int result = calculator.add("1,2,3,100,200");

        Assert.assertEquals(306, result);
    }

    @Test
    public void shouldSupportNewLineCharacterAsDelimiter() {
        int result = calculator.add("1\n2,3");

        Assert.assertEquals(6, result);
    }

    @Test
    public void shouldSupportSettingDelimiterInFirstLine() {
        int result = calculator.add("//;\n1;2");

        Assert.assertEquals(3, result);
    }

    @Test
    public void shouldThrowNegativesNotAllowedExceptionWhenNegativeNumbersPassed() {
        exceptionRule.expect(NegativesNotAllowedException.class);
        exceptionRule.expectMessage("Negatives not allowed: -3");

        calculator.add("//;\n1;2;-3");
    }
}
