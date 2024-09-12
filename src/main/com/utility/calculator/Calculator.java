package com.utility.calculator;

import java.util.Map;

public class Calculator {
    // Operation enum to operation strategies based mapping
    private final Map<Operation, OperationStrategy> operationStrategies;
    private double currentResult; // Internal state for chaining operations

    // Constructor-based dependency injection
    public Calculator(Map<Operation, OperationStrategy> operationStrategies) {
        this.operationStrategies = operationStrategies;
        this.currentResult = 0.0;
    }

    /**
     * Applies the given operation on two numbers and returns the result.
     *
     * <p>Returns an {@code Integer} if the result is a whole number, otherwise returns a {@code Double}.
     * Throws an {@code IllegalArgumentException} if the operation is not supported.
     *
     * @param operation the operation to apply (e.g., ADD, SUBTRACT, MULTIPLY, DIVIDE)
     * @param num1 the first number
     * @param num2 the second number
     * @return the result of the calculation as a {@code Number}
     * @throws IllegalArgumentException if the operation is not supported
     */
    public Number calculate(Operation operation, Number num1, Number num2) {
        double a = num1.doubleValue();
        double b = num2.doubleValue();
        double result;

        OperationStrategy strategy = operationStrategies.get(operation);
        if (strategy == null) {
            throw new IllegalArgumentException("Operation '" + operation + "' is not supported.");
        }

        result = strategy.execute(a, b);

        // Return an Integer if the result is a whole number, otherwise return Double
        if (result == Math.floor(result)) {
            return (int) result;
        }
        return result;
    }

    // Initialize value for chaining operations
    public Calculator start(double value) {
        this.currentResult = value;
        return this;
    }

    // Operate method to chain operations
    public Calculator operate(Operation operation, double value) {
        OperationStrategy strategy = operationStrategies.get(operation);
        if (strategy == null) {
            throw new IllegalArgumentException("Operation '" + operation + "' is not supported.");
        }

        currentResult = strategy.execute(currentResult, value);
        return this;
    }

    public double getResult() {
        return currentResult;
    }
}
