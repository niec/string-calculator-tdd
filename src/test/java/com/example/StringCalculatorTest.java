package com.example;

import com.example.exceptions.NegativesNotAllowedException;
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
        Assert.assertEquals(0, calculator.add(""));
    }

    @Test
    public void shouldReturnNumberForASingleNumberString() {
        Assert.assertEquals(1, calculator.add("1"));
    }

    @Test
    public void shouldReturnNumbersSumForADoubleNumberString() {
        Assert.assertEquals(3, calculator.add("1,2"));
    }

    @Test
    public void shouldReturnNumbersSumForAMultipleNumberString() {
        Assert.assertEquals(306, calculator.add("1,2,3,100,200"));
    }

    @Test
    public void shouldSupportNewLineCharacterAsDelimiter() {
        Assert.assertEquals(6, calculator.add("1\n2,3"));
    }

    @Test
    public void shouldSupportSettingDelimiterInFirstLine() {
        Assert.assertEquals(3, calculator.add("//;\n1;2"));
    }

    @Test
    public void shouldThrowNegativesNotAllowedExceptionWhenNegativeNumbersPassed() {
        exceptionRule.expect(NegativesNotAllowedException.class);
        exceptionRule.expectMessage("Negatives not allowed: -3,-4");

        calculator.add("//;\n1;2;-3;-4");
    }

    @Test
    public void shouldIgnoreNumbersGreaterThan1000() {
        Assert.assertEquals(1035, calculator.add("//;\n15;20;1001;1000"));
    }

    @Test
    public void shouldSupportSettingLongerThan1CharacterDelimiter() {
        Assert.assertEquals(6, calculator.add("//[***]\n1***2***3"));
    }

    @Test
    public void shouldSupportSettingMultipleSingleCharacterDelimiters() {
        Assert.assertEquals(6, calculator.add("//[*][%]\n1*2%3"));
    }

    @Test
    public void shouldSupportSettingMultipleMultipleCharacterDelimiters() {
        Assert.assertEquals(100, calculator.add("//[**][##%]\n20**30##%50"));
    }
}