import io.quarkiverse.cucumber.CucumberOptions;
import io.quarkiverse.cucumber.CucumberQuarkusTest;

@CucumberOptions(features = "classpath:features",
        plugin = {"pretty", "json:target/cucumber-report.json"})
public class QuarkusAwsSqsTest extends CucumberQuarkusTest {
}
