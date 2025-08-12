package com.example.demo.common;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Average {
    private final Double firstNumber;
    private final Double secondNumber;

    public Double calc() {
        return (firstNumber + secondNumber) / 2.0;
    }

}
