package com.example;

import java.util.Arrays;

public class StringCalculator {

    int add(String numbers) {
        if (numbers.isEmpty()) {
            return 0;
        } else if (numbers.length() == 1) {
            return Integer.parseInt(numbers);
        } else {
            numbers = numbers.replaceAll("\n", ",");

            return Arrays.stream(numbers.split(","))
                    .map(Integer::parseInt)
                    .reduce((n1, n2) -> n1 + n2)
                    .get();
        }
    }
}
