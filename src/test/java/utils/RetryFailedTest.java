package utils;



import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryFailedTest implements IRetryAnalyzer {
    private int count = 0;
    private static final int maxRetry = 0;

    @Override
    public boolean retry(ITestResult result) {
        if (count < maxRetry) {
            count++;
            System.out.println("Retrying " + result.getName() + " again: attempt " + count);
            return true;
        }
        return false;
    }
}
