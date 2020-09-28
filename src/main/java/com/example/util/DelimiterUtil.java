package com.example.util;

import com.example.domain.dto.NumberInput;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DelimiterUtil {
    private static final Pattern firstLineWithDelimiterPattern = Pattern.compile("\\/\\/.+\\\n");
    private static final Pattern delimiterInSquareBracesPattern = Pattern.compile("\\[[^\\[\\]]+\\]");
    private static final List<String> obligatoryDelimiterList = Collections.singletonList("\n");
    private static Matcher matcher;

    public static NumberInput processInput(String numbers) {
        matcher = firstLineWithDelimiterPattern.matcher(numbers);
        if (matcher.find()) {
            return new NumberInput(
                    getNumbersWithoutDelimiterDefinitions(numbers, matcher.group(0)),
                    Stream.concat(obligatoryDelimiterList.stream(), extractDelimiters(matcher.group(0)).stream()).collect(Collectors.toList())
            );
        }

        return new NumberInput(numbers, obligatoryDelimiterList);
    }

    private static String getNumbersWithoutDelimiterDefinitions(String numbers, String delimiterDefinitions) {
        return numbers.replace(delimiterDefinitions, "");
    }

    private static List<String> extractDelimiters(String firstLine) {
        matcher = delimiterInSquareBracesPattern.matcher(firstLine);
        if (matcher.find()) {
            return extractMultipleDelimitersInSquareBrackets(matcher);
        }

        return Collections.singletonList(extractSimpleDelimiter(firstLine));
    }

    private static String extractSimpleDelimiter(String firstLine) {
        return firstLine.substring(firstLine.indexOf("//") + 2, firstLine.indexOf("\n"));
    }

    private static List<String> extractMultipleDelimitersInSquareBrackets(Matcher matcher) {
        final List<String> delimiters = new ArrayList<>();

        do {
            delimiters.add(extractDelimiterInSquareBracket(matcher.group(0)));
        } while (matcher.find());

        return delimiters;
    }

    private static String extractDelimiterInSquareBracket(String delimiterInSquareBracket) {
        return delimiterInSquareBracket.substring(1, delimiterInSquareBracket.length() - 1);
    }
}