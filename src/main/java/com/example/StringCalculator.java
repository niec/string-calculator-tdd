package com.example;

import com.example.util.DelimiterUtil;
import com.example.util.NumberUtil;

public class StringCalculator {
    int add(String numbers) {
        return numbers.isEmpty() ? 0 :
                NumberUtil.extractNumbers(DelimiterUtil.processInput(numbers))
                        .stream()
                        .reduce(Integer::sum)
                        .orElse(0);
    }
}