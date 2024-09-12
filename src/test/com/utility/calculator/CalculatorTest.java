package com.utility.calculator;

import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class CalculatorTest {
    // Test Setup
    private final Map<Operation, OperationStrategy> strategyMap = Map.of(
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

    private final Calculator calculator = new Calculator(strategyMap);

    @Test
    public void testOperations() {
        assertEquals(5, calculator.calculate(Operation.ADD, 2, 3));
        assertEquals(7, calculator.calculate(Operation.MULTIPLY, 1, 7));
        assertEquals(3, calculator.calculate(Operation.SUBTRACT, 5, 2));
        assertEquals(2.5, calculator.calculate(Operation.DIVIDE, 5, 2));
    }

    @Test
    public void testDivisionByZero() {
        assertThrows(ArithmeticException.class, () -> calculator.calculate(Operation.DIVIDE, 5, 0));
    }

    @Test
    public void testChainingOperations() {
        double result = calculator.start(10)
                .operate(Operation.ADD, 5)
                .operate(Operation.MULTIPLY, 2)
                .operate(Operation.SUBTRACT, 10)
                .operate(Operation.DIVIDE, 2)
                .getResult();

        assertEquals(10.0, result, 0.0001);
    }

    @Test
    public void testNegativeNumbers() {
        assertEquals(-1, calculator.calculate(Operation.ADD, -3, 2));
        assertEquals(-5, calculator.calculate(Operation.SUBTRACT, -3, 2));
        assertEquals(6, calculator.calculate(Operation.MULTIPLY, -3, -2));
        assertEquals(-1.5, calculator.calculate(Operation.DIVIDE, -3, 2));
    }

    @Test
    public void testFloatingPointOperations() {
        assertEquals(5.5, calculator.calculate(Operation.ADD, 3.2, 2.3).doubleValue(), 0.0001);
        assertEquals(0.9, calculator.calculate(Operation.SUBTRACT, 3.2, 2.3).doubleValue(), 0.0001);
        assertEquals(7.36, calculator.calculate(Operation.MULTIPLY, 3.2, 2.3).doubleValue(), 0.0001);
        assertEquals(1.3913, calculator.calculate(Operation.DIVIDE, 3.2, 2.3).doubleValue(), 0.0001);
    }

    @Test
    public void testUnsupportedOperation() {
        assertThrows(IllegalArgumentException.class,
                () -> calculator.calculate(Operation.valueOf("POW"), 2, 3));
    }

    @Test
    public void testChainingWithNegativeAndFloatingPointNumbers() {
        double result = calculator.start(-5.5)
                .operate(Operation.ADD, 3.2)
                .operate(Operation.MULTIPLY, -2)
                .operate(Operation.DIVIDE, 2)
                .operate(Operation.SUBTRACT, 1.1)
                .getResult();

        assertEquals(1.2, result, 0.0001);
    }

    @Test
    public void testMultiplicationWithZero() {
        assertEquals(0, calculator.calculate(Operation.MULTIPLY, 5, 0));
        assertEquals(0, calculator.calculate(Operation.MULTIPLY, 0, 5));
    }

    @Test
    public void testAdditionAndSubtractionWithZero() {
        assertEquals(5, calculator.calculate(Operation.ADD, 5, 0));
        assertEquals(5, calculator.calculate(Operation.ADD, 0, 5));
        assertEquals(5, calculator.calculate(Operation.SUBTRACT, 5, 0));
        assertEquals(-5, calculator.calculate(Operation.SUBTRACT, 0, 5));
    }
}
