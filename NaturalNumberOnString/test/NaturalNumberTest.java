import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.naturalnumber.NaturalNumber;
import components.naturalnumber.NaturalNumber2;

/**
 * JUnit test fixture for {@code NaturalNumber}'s constructors and kernel
 * methods.
 *
 * @author Yanqing Xu, Shafin Alam
 *
 */
public abstract class NaturalNumberTest {

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * implementation under test and returns the result.
     *
     * @return the new number
     * @ensures constructorTest = 0
     */
    protected abstract NaturalNumber constructorTest();

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * implementation under test and returns the result.
     *
     * @param i
     *            {@code int} to initialize from
     * @return the new number
     * @requires i >= 0
     * @ensures constructorTest = i
     */
    protected abstract NaturalNumber constructorTest(int i);

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * implementation under test and returns the result.
     *
     * @param s
     *            {@code String} to initialize from
     * @return the new number
     * @requires there exists n: NATURAL (s = TO_STRING(n))
     * @ensures s = TO_STRING(constructorTest)
     */
    protected abstract NaturalNumber constructorTest(String s);

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * implementation under test and returns the result.
     *
     * @param n
     *            {@code NaturalNumber} to initialize from
     * @return the new number
     * @ensures constructorTest = n
     */
    protected abstract NaturalNumber constructorTest(NaturalNumber n);

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * reference implementation and returns the result.
     *
     * @return the new number
     * @ensures constructorRef = 0
     */
    protected abstract NaturalNumber constructorRef();

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * reference implementation and returns the result.
     *
     * @param i
     *            {@code int} to initialize from
     * @return the new number
     * @requires i >= 0
     * @ensures constructorRef = i
     */
    protected abstract NaturalNumber constructorRef(int i);

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * reference implementation and returns the result.
     *
     * @param s
     *            {@code String} to initialize from
     * @return the new number
     * @requires there exists n: NATURAL (s = TO_STRING(n))
     * @ensures s = TO_STRING(constructorRef)
     */
    protected abstract NaturalNumber constructorRef(String s);

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * reference implementation and returns the result.
     *
     * @param n
     *            {@code NaturalNumber} to initialize from
     * @return the new number
     * @ensures constructorRef = n
     */
    protected abstract NaturalNumber constructorRef(NaturalNumber n);

    // TODO - add test cases for four constructors, multiplyBy10, divideBy10, isZero

    //test cases for constructors

    /**
     * Test for Default Constructor.
     */
    @Test
    public final void testDefaultConstructor() {
        /*
         * Instantiate variables
         */
        NaturalNumber t = this.constructorTest();
        NaturalNumber r = this.constructorRef();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(t, r);
    }

    /**
     * Test for Int Constructor for 0.
     */
    @Test
    public final void testIntConstructorfor0() {
        /*
         * Instantiate variables
         */
        NaturalNumber t = this.constructorTest(0);
        NaturalNumber r = this.constructorRef(0);
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(t, r);
    }

    /**
     * Test for Int Constructor for non-0.
     */
    @Test
    public final void testIntConstructorforNon0() {
        /*
         * Instantiate variables
         */
        NaturalNumber t = this.constructorTest(1);
        NaturalNumber r = this.constructorRef(1);
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(t, r);
    }

    /**
     * Test for Int Constructor for double digit.
     */
    @Test
    public final void testIntConstructorforDoubleDigit() {
        /*
         * Instantiate variables
         */
        NaturalNumber t = this.constructorTest(88);
        NaturalNumber r = this.constructorRef(88);
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(t, r);
    }

    /**
     * Test for String Constructor for string one digit.
     */
    @Test
    public final void testStringConstructorforStringOneDigit() {
        /*
         * Instantiate variables
         */
        NaturalNumber t = this.constructorTest("8");
        NaturalNumber r = this.constructorRef("8");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(t, r);
    }

    /**
     * Test for String Constructor for string has 2 digit.
     */
    @Test
    public final void testStringConstructorforStringTwoDigit() {
        /*
         * Instantiate variables
         */
        NaturalNumber t = this.constructorTest("88");
        NaturalNumber r = this.constructorRef("88");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(t, r);
    }

    /**
     * Test for String Constructor for NaturalNumber type.
     */
    @Test
    public final void testStringConstructorforNNtype() {
        /*
         * Instantiate variables
         */
        NaturalNumber one = new NaturalNumber2(9);
        NaturalNumber two = new NaturalNumber2(9);

        NaturalNumber t = this.constructorTest(one);
        NaturalNumber r = this.constructorRef(two);
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(t, r);
    }

    //Test cases for multiplyBy10()

    /**
     * Test for multiplyBy10, from empty to still empty.
     */
    @Test
    public final void testMultiplyBy10EmptyAdd0() {
        /*
         * Instantiate variables
         */
        NaturalNumber t = this.constructorTest();
        NaturalNumber r = this.constructorRef();
        /*
         * Call method under test
         */
        t.multiplyBy10(0);
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(t, r);
    }

    /**
     * Test for multiplyBy10, from empty/zero to single digit.
     */
    @Test
    public final void testMultiplyBy10EmptyToSingleDigit() {
        /*
         * Instantiate variables
         */
        NaturalNumber t = this.constructorTest();
        NaturalNumber r = this.constructorRef(1);
        /*
         * Call method under test
         */
        t.multiplyBy10(1);
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(t, r);
    }

    /**
     * Test for multiplyBy10, from single to double digit.
     */
    @Test
    public final void testMultiplyBy10SingleToDoubleDigit() {
        /*
         * Instantiate variables
         */
        NaturalNumber t = this.constructorTest(1);
        NaturalNumber r = this.constructorRef(12);
        /*
         * Call method under test
         */
        t.multiplyBy10(2);
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(t, r);
    }

    /**
     * Test for multiplyBy10, from double to triple digit.
     */
    @Test
    public final void testMultiplyBy10DoubleToTripleDigit() {
        /*
         * Instantiate variables
         */
        NaturalNumber t = this.constructorTest(12);
        NaturalNumber r = this.constructorRef(123);
        /*
         * Call method under test
         */
        t.multiplyBy10(3);
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(t, r);
    }

    /**
     * Test for multiplyBy10, from triple, adding 0.
     */
    @Test
    public final void testMultiplyBy10TripleAddZero() {
        /*
         * Instantiate variables
         */
        NaturalNumber t = this.constructorTest(123);
        NaturalNumber r = this.constructorRef(1230);
        /*
         * Call method under test
         */
        t.multiplyBy10(0);
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(t, r);
    }

    /**
     * Test for multiplyBy10, using max value of an int.
     */
    @Test
    public final void testMultiplyBy10IntegerMax() {
        /*
         * Instantiate variables
         */
        NaturalNumber t = this.constructorTest(Integer.MAX_VALUE);
        NaturalNumber r = this.constructorRef(Integer.MAX_VALUE + "3");
        /*
         * Call method under test
         */
        t.multiplyBy10(3);
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(t, r);
    }

    //Test cases for divideBy10()

    /**
     * Test for divideBy10, with empty/zero.
     */
    @Test
    public final void testdivideBy10Empty() {
        /*
         * Instantiate variables
         */
        NaturalNumber t = this.constructorTest();
        NaturalNumber r = this.constructorRef();
        /*
         * Call method under test
         */
        int remainder = t.divideBy10();
        int remainExpected = 0;
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(t, r);
        assertEquals(remainder, remainExpected);
    }

    /**
     * Test for divideBy10, with single digit to zero.
     */
    @Test
    public final void testdivideBy10SingleToEmpty() {
        /*
         * Instantiate variables
         */
        NaturalNumber t = this.constructorTest(5);
        NaturalNumber r = this.constructorRef();
        /*
         * Call method under test
         */
        int remainder = t.divideBy10();
        int remainExpected = 5;
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(t, r);
        assertEquals(remainder, remainExpected);
    }

    /**
     * Test for divideBy10, with double digit to single.
     */
    @Test
    public final void testdivideBy10DoubleToSingle() {
        /*
         * Instantiate variables
         */
        NaturalNumber t = this.constructorTest(56);
        NaturalNumber r = this.constructorRef(5);
        /*
         * Call method under test
         */
        int remainder = t.divideBy10();
        int remainExpected = 6;
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(t, r);
        assertEquals(remainder, remainExpected);
    }

    /**
     * Test for divideBy10, with triple digit to double.
     */
    @Test
    public final void testdivideBy10TripleToDouble() {
        /*
         * Instantiate variables
         */
        NaturalNumber t = this.constructorTest(568);
        NaturalNumber r = this.constructorRef(56);
        /*
         * Call method under test
         */
        int remainder = t.divideBy10();
        int remainExpected = 8;
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(t, r);
        assertEquals(remainder, remainExpected);
    }

    /**
     * Test for divideBy10, with multiple of 10.
     */
    @Test
    public final void testdivideBy10MultipleOf10() {
        /*
         * Instantiate variables
         */
        NaturalNumber t = this.constructorTest(420);
        NaturalNumber r = this.constructorRef(42);
        /*
         * Call method under test
         */
        int remainder = t.divideBy10();
        int remainExpected = 0;
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(t, r);
        assertEquals(remainder, remainExpected);
    }

    /**
     * Test for divideBy10, with integer max value.
     */
    @Test
    public final void testdivideBy10IntegerMax() {
        /*
         * Instantiate variables
         */
        NaturalNumber t = this.constructorTest(Integer.MAX_VALUE);
        NaturalNumber r = this.constructorRef(Integer.MAX_VALUE / 10);
        /*
         * Call method under test
         */
        int remainder = t.divideBy10();
        int remainExpected = 7;
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(t, r);
        assertEquals(remainder, remainExpected);
    }

    //Test cases for isZero()

    /**
     * Test for isZero(), with empty String / NaturalNumber of zero.
     */
    @Test
    public final void testisZeroEmpty() {
        /*
         * Instantiate variables
         */
        NaturalNumber t = this.constructorTest();
        /*
         * Call method under test
         */
        boolean tBool = t.isZero();
        boolean rBool = true;
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(tBool, rBool);
    }

    /**
     * Test for isZero(), with single digit.
     */
    @Test
    public final void testisZeroSingle() {
        /*
         * Instantiate variables
         */
        NaturalNumber t = this.constructorTest(6);
        /*
         * Call method under test
         */
        boolean tBool = t.isZero();
        boolean rBool = false;
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(tBool, rBool);
    }

    /**
     * Test for isZero(), with Integer max value.
     */
    @Test
    public final void testisZeroIntegerMax() {
        /*
         * Instantiate variables
         */
        NaturalNumber t = this.constructorTest(Integer.MAX_VALUE);
        /*
         * Call method under test
         */
        boolean tBool = t.isZero();
        boolean rBool = false;
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(tBool, rBool);
    }
}
