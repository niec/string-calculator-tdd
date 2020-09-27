package com.example;

import com.example.domain.dto.NumberInput;
import com.example.exceptions.NegativesNotAllowedException;
import com.google.common.base.Joiner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringCalculator {
    private final Pattern firstLineWithDelimiterPattern = Pattern.compile("\\/\\/.+\\\n");
    private Matcher matcher;
    private static final String DEFAULT_DELIMITER = ",";
    private final Pattern delimiterInSquareBracesPattern = Pattern.compile("\\[[^\\[\\]]+\\]");

    int add(String numbers) {
        var input = new NumberInput(numbers);

        if (input.getNumbers().isEmpty()) {
            return 0;
        } else if (input.getNumbers().length() == 1) {
            return Integer.parseInt(input.getNumbers());
        } else {
            final List<String> delimiter = getDelimiters(input);

            Optional<Integer> result = processNumbers(
                    replaceCustomDelimitersWithDefaultDelimiter(delimiter, input.getNumbers()).replaceAll("\n", DEFAULT_DELIMITER))
                    .stream()
                    .reduce((n1, n2) -> n1 + n2);

            return result.isPresent() ? result.get() : 0;
        }
    }

    private List<String> getDelimiters(NumberInput input) {
        matcher = firstLineWithDelimiterPattern.matcher(input.getNumbers());
        if (matcher.find()) {
            input.setNumbers(input.getNumbers().replace(matcher.group(0), "")); //remove first line
            return obtainDelimiters(matcher.group(0));
        }
        return new ArrayList<>();
    }

    private List<String> obtainDelimiters(String firstLine) {
        final List<String> delimiters = new ArrayList<>();

        matcher = delimiterInSquareBracesPattern.matcher(firstLine);
        if (matcher.find()) {
            do {
                delimiters.add(obtainSingleDelimiterInSquareBracket(matcher.group(0)));
            } while (matcher.find());

            return delimiters;
        }

        delimiters.add(firstLine.substring(firstLine.indexOf("//") + 2, firstLine.indexOf("\n")));
        return delimiters;
    }

    private String obtainSingleDelimiterInSquareBracket(String delimiterInSquareBracket) {
        return delimiterInSquareBracket.substring(1, delimiterInSquareBracket.length() - 1);
    }

    private List<Integer> processNumbers(String numbers) {
        final List<Integer> numberList = new ArrayList<>();
        final List<Integer> negativeNumberList = new ArrayList<>();

        for (String number : numbers.split(DEFAULT_DELIMITER)) {
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

    private String replaceCustomDelimitersWithDefaultDelimiter(List<String> customDelimiters, String numbers) {
        for (String customDelimiter : customDelimiters) {
            numbers = (numbers.replaceAll(Pattern.quote(customDelimiter), DEFAULT_DELIMITER));
        }
        return numbers;
    }
}
