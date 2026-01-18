package akakcebot;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

// FOR ALL CLASSES RUN THAT ONE...
@Suite
@SelectClasses({
        SearchTest.class,
        PriceCompRedirectTest.class,
        FilterTest.class,
        FollowUnfollowTest.class,
        LoginTest.class
})
public class OrderedTestSuite {

}