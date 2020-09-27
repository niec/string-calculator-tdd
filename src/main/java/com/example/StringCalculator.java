package com.example;

import com.example.domain.dto.NumberInput;
import com.example.exceptions.NegativesNotAllowedException;
import com.google.common.base.Joiner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringCalculator {
    private final Pattern customDelimiterPattern = Pattern.compile("\\/\\/.+\\\n");
    private Matcher matcher;
    private static final String DEFAULT_DELIMITER = ",";

    int add(String numbers) {
        var input = new NumberInput(numbers);

        if (input.getNumbers().isEmpty()) {
            return 0;
        } else if (input.getNumbers().length() == 1) {
            return Integer.parseInt(input.getNumbers());
        } else {
            final String delimiter = getDelimiter(input);

            Optional<Integer> result = processNumbers(input.getNumbers().replaceAll("\n", ","), delimiter)
                    .stream()
                    .reduce((n1, n2) -> n1 + n2);

            return result.isPresent() ? result.get() : 0;
        }
    }

    private String getDelimiter(NumberInput input) {
        matcher = customDelimiterPattern.matcher(input.getNumbers());
        if (matcher.find()) {
            input.setNumbers(input.getNumbers().replace(matcher.group(0), "")); //remove first line
            return obtainSingleDelimiter(matcher.group(0));
        }
        return DEFAULT_DELIMITER;
    }

    private String obtainSingleDelimiter(String firstLine) {
        return firstLine.substring(firstLine.indexOf("//") + 2, firstLine.indexOf("\n"));
    }

    private List<Integer> processNumbers(String numbers, String delimiter) {
        final List<Integer> numberList = new ArrayList<>();
        final List<Integer> negativeNumberList = new ArrayList<>();

        for (String number : numbers.split(delimiter)) {
            int i = Integer.parseInt(number);

            if (i <= 1000) {
                numberList.add(i);
            }

            if (i < 0) {
                negativeNumberList.add(i);
            }
        }

        if (!negativeNumberList.isEmpty()) {
            throw new NegativesNotAllowedException("Negatives not allowed: " + Joiner.on(',').join(negativeNumberList));
        }

        return numberList;
    }
}
