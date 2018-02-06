package by.kazimirov.validator;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.function.Predicate;

/**
 *
 */
@RunWith(Parameterized.class)
public class AccountValidatorTest {
    private enum TestTarget {
        LOGIN(AccountValidator::validateLogin),
        EMAIL(AccountValidator::validateEmail),
        PASSWORD(AccountValidator::validatePassword);

        private Predicate<String> test;
        TestTarget(Predicate<String> test) {
            this.test = test;
        }
    }

    private String testString;
    private TestTarget target;
    private boolean assertResult;

    public AccountValidatorTest(String testString, TestTarget target, boolean assertResult) {
        this.testString = testString;
        this.target = target;
        this.assertResult = assertResult;
    }

    @Parameterized.Parameters(name = "{index}: {1}({0}) : {2}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"екеке", TestTarget.LOGIN, true},
                {"1р1крк", TestTarget.LOGIN, false},
                {"tttt  ", TestTarget.LOGIN, false},
                {"_khdhk", TestTarget.LOGIN, false},
                {"$%&^$&", TestTarget.EMAIL, false},
                {"dlfs8j", TestTarget.EMAIL, false},
                {"a@a.", TestTarget.EMAIL, false},
                {"a@.a", TestTarget.EMAIL, false},
                {"asd//..asd@asd,,a.a;sok", TestTarget.EMAIL, true},
                {"", TestTarget.PASSWORD, false},
                {"artyu", TestTarget.PASSWORD, false},
                {"ads1adasd", TestTarget.PASSWORD, true},
                {"ASDADAD", TestTarget.PASSWORD, false},
                {"123143", TestTarget.PASSWORD, false},
        });
    }

    @Test
    public void validateInput() throws Exception {
        Assert.assertEquals(target.test.test(testString), assertResult);
    }
}
