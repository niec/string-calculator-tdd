package com.example;

public class StringCalculator {

    int add(String numbers) {
        if (numbers.isEmpty()) {
            return 0;
        } else if (numbers.length() == 1) {
            return Integer.parseInt(numbers);
        } else {
            String[] numberArray = numbers.split(",");
            return Integer.parseInt(numberArray[0]) + Integer.parseInt(numberArray[1]);
        }
    }
}
