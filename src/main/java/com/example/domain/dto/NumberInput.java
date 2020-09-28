package com.example.domain.dto;

import java.util.List;

public class NumberInput {
    private String numbers;
    private List<String> delimiters;

    public NumberInput(String numbers, List<String> delimiters) {
        this.numbers = numbers;
        this.delimiters = delimiters;
    }

    public String getNumbers() {
        return numbers;
    }

    public List<String> getDelimiters() {
        return delimiters;
    }
}
