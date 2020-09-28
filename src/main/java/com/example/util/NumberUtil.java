package com.example.util;

import com.example.domain.dto.NumberInput;
import com.example.domain.exceptions.NegativesNotAllowedException;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class NumberUtil {
    private static final String DEFAULT_DELIMITER = ",";

    public static List<Integer> extractNumbers(NumberInput input) {
        return getNumberList(replaceDelimitersWithDefaultDelimiter(input.getDelimiters(), input.getNumbers()));
    }

    private static List<Integer> getNumberList(String numbers) {
        final List<Integer> numberList = new ArrayList<>();
        final List<Integer> negativeNumberList = new ArrayList<>();

        for (String number : numbers.split(DEFAULT_DELIMITER)) {
            processNumber(number, numberList, negativeNumberList);
        }

        if (!negativeNumberList.isEmpty()) {
            throw new NegativesNotAllowedException(negativeNumberList);
        }

        return numberList;
    }

    private static void processNumber(String number, List<Integer> numberList, List<Integer> negativeNumberList) {
        var i = Integer.parseInt(number);

        if (i <= 1000) {
            numberList.add(i);
        }

        if (i < 0) {
            negativeNumberList.add(i);
        }
    }

    private static String replaceDelimitersWithDefaultDelimiter(List<String> delimiters, String numbers) {
        for (var customDelimiter : delimiters) {
            numbers = numbers.replaceAll(Pattern.quote(customDelimiter), DEFAULT_DELIMITER);
        }
        return numbers;
    }
}