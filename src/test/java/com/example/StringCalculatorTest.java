package com.example;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class StringCalculatorTest {
    private static StringCalculator calculator;

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

}
