package fooddelivery;

import fooddelivery.model.DeliveryTaskTest;
import fooddelivery.model.OrderTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        DeliveryTaskTest.class,
        OrderTest.class,
})
// run from this file to perform unit tests
public class ModelTestSuite {
    // leave class body empty
}
