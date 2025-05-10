package jp.co.company.space.utils.shared.testScenario;

/**
 * A {@link TestScenario} specific {@link RuntimeException}.
 */
public class TestScenarioException extends RuntimeException {
    public TestScenarioException(String message) {
        super(message);
    }

    public TestScenarioException(String message, Throwable cause) {
        super(message, cause);
    }
}
