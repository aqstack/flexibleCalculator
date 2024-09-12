package com.utility.calculator;

@FunctionalInterface
public interface OperationStrategy {
    double execute(double a, double b);
}
