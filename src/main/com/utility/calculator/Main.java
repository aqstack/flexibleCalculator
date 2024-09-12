package com.utility.calculator;

import java.util.Map;

public class Main {
    public static void main(String[] args) {
        // Normally, the operation strategies would be injected via an IoC container
        Map<Operation, OperationStrategy> strategies = Map.of(
                Operation.ADD, (a, b) -> a + b,
                Operation.SUBTRACT, (a, b) -> a - b,
                Operation.MULTIPLY, (a, b) -> a * b,
                Operation.DIVIDE, (a, b) -> {
                    if (b == 0) {
                        throw new ArithmeticException("Cannot divide by zero");
                    }
                    return a / b;
                }
        );

        Calculator calculator = new Calculator(strategies);

        System.out.println("2 + 3 = " + calculator.calculate(Operation.ADD, 2, 3));

        // Chaining multiple operations
        double result = calculator
                .start(5)                      // Start with 5
                .operate(Operation.ADD, 3)      // 5 + 3
                .operate(Operation.MULTIPLY, 2) // (5 + 3) * 2
                .getResult();                   // Get the result

        System.out.println("Final Result: " + result);  // Should output 16
    }
}
