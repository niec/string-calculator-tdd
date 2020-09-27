package com.example;

import com.example.domain.dto.NumberInput;

import java.util.Arrays;
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

            return Arrays.stream(
                    input.getNumbers()
                            .replaceAll("\n", ",")
                            .split(delimiter))
                    .map(Integer::parseInt)
                    .reduce((n1, n2) -> n1 + n2)
                    .get();
        }
    }

    private String getDelimiter(NumberInput input) {
        this.matcher = customDelimiterPattern.matcher(input.getNumbers());
        if (matcher.find()) {
            input.setNumbers(input.getNumbers().substring(input.getNumbers().indexOf('\n') + 1)); //remove first line
            return obtainSingleDelimiter(matcher.group(0));
        }
        return DEFAULT_DELIMITER;
    }

    private String obtainSingleDelimiter(String firstLine) {
        return firstLine.substring(firstLine.indexOf("//") + 2, firstLine.indexOf("\n"));
    }

}
