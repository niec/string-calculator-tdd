package com.example.domain.exceptions;

import java.util.List;
import java.util.stream.Collectors;

public class NegativesNotAllowedException extends RuntimeException {
    public NegativesNotAllowedException(List<Integer> negativeNumberList) {
        super("Negatives not allowed: " + negativeNumberList.stream().map(number -> number.toString()).collect(Collectors.joining(",")));
    }
}
