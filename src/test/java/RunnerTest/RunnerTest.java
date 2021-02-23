package RunnerTest;

import Runner.Runner;
import org.junit.Assert;
import org.junit.Test;

public class RunnerTest {

    @Test
    public void helloTest() {
        Assert.assertEquals("Hello. I'am grabber", Runner.hello());
    }
}