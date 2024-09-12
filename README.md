# Flexible Calculator

A simple, extensible Java-based calculator that supports basic arithmetic operations (addition, subtraction, multiplication, division) with chaining functionality and IoC compatibility.

## Features

- **Basic Operations**: Add, Subtract, Multiply, Divide.
- **Chaining Operations**: Perform a series of operations sequentially.
- **Extensibility**: Easily add new operations without modifying existing code.
- **Inversion of Control (IoC)**: Supports external management of operation strategies through dependency injection.
- **Error Handling**: Throws `IllegalArgumentException` for unsupported operations.

## Usage

### Example: Basic Calculation

```java
Calculator calculator = new Calculator();
double result = calculator.calculate(Operation.ADD, 2, 3);
System.out.println(result); // Output: 5.0
```

### Example: Chaining Operations

```java
double result = calculator.start(10)
    .operate(Operation.ADD, 5)
    .operate(Operation.MULTIPLY, 2)
    .operate(Operation.SUBTRACT, 10)
    .operate(Operation.DIVIDE, 2)
    .getResult();

System.out.println(result); // Output: 10.0
```

### IoC Example

```java
Map<Operation, OperationStrategy> strategies = Map.of(
    Operation.ADD, (a, b) -> a + b,
    Operation.SUBTRACT, (a, b) -> a - b
);

Calculator calculator = new Calculator(strategies); // Inject strategies
```

## Running Tests

To run the tests using JUnit:
1. Ensure JUnit is on your classpath.
2. Use your IDE (e.g., IntelliJ, Eclipse) to run the tests, or use the CLI.

## Requirements

- Java 8 or higher
- JUnit 4.x for testing
