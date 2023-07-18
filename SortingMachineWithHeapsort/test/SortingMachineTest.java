import static org.junit.Assert.assertEquals;

import java.util.Comparator;

import org.junit.Test;

import components.sortingmachine.SortingMachine;

/**
 * JUnit test fixture for {@code SortingMachine<String>}'s constructor and
 * kernel methods.
 *
 * @author Shafin Alam and Yanqing Xu
 *
 */
public abstract class SortingMachineTest {

    /**
     * Invokes the appropriate {@code SortingMachine} constructor for the
     * implementation under test and returns the result.
     *
     * @param order
     *            the {@code Comparator} defining the order for {@code String}
     * @return the new {@code SortingMachine}
     * @requires IS_TOTAL_PREORDER([relation computed by order.compare method])
     * @ensures constructorTest = (true, order, {})
     */
    protected abstract SortingMachine<String> constructorTest(
            Comparator<String> order);

    /**
     * Invokes the appropriate {@code SortingMachine} constructor for the
     * reference implementation and returns the result.
     *
     * @param order
     *            the {@code Comparator} defining the order for {@code String}
     * @return the new {@code SortingMachine}
     * @requires IS_TOTAL_PREORDER([relation computed by order.compare method])
     * @ensures constructorRef = (true, order, {})
     */
    protected abstract SortingMachine<String> constructorRef(
            Comparator<String> order);

    /**
     *
     * Creates and returns a {@code SortingMachine<String>} of the
     * implementation under test type with the given entries and mode.
     *
     * @param order
     *            the {@code Comparator} defining the order for {@code String}
     * @param insertionMode
     *            flag indicating the machine mode
     * @param args
     *            the entries for the {@code SortingMachine}
     * @return the constructed {@code SortingMachine}
     * @requires IS_TOTAL_PREORDER([relation computed by order.compare method])
     * @ensures <pre>
     * createFromArgsTest = (insertionMode, order, [multiset of entries in args])
     * </pre>
     */
    private SortingMachine<String> createFromArgsTest(Comparator<String> order,
            boolean insertionMode, String... args) {
        SortingMachine<String> sm = this.constructorTest(order);
        for (int i = 0; i < args.length; i++) {
            sm.add(args[i]);
        }
        if (!insertionMode) {
            sm.changeToExtractionMode();
        }
        return sm;
    }

    /**
     *
     * Creates and returns a {@code SortingMachine<String>} of the reference
     * implementation type with the given entries and mode.
     *
     * @param order
     *            the {@code Comparator} defining the order for {@code String}
     * @param insertionMode
     *            flag indicating the machine mode
     * @param args
     *            the entries for the {@code SortingMachine}
     * @return the constructed {@code SortingMachine}
     * @requires IS_TOTAL_PREORDER([relation computed by order.compare method])
     * @ensures <pre>
     * createFromArgsRef = (insertionMode, order, [multiset of entries in args])
     * </pre>
     */
    private SortingMachine<String> createFromArgsRef(Comparator<String> order,
            boolean insertionMode, String... args) {
        SortingMachine<String> sm = this.constructorRef(order);
        for (int i = 0; i < args.length; i++) {
            sm.add(args[i]);
        }
        if (!insertionMode) {
            sm.changeToExtractionMode();
        }
        return sm;
    }

    /**
     * Comparator<String> implementation to be used in all test cases. Compare
     * {@code String}s in lexicographic order.
     */
    private static class StringLT implements Comparator<String> {

        @Override
        public int compare(String s1, String s2) {
            return s1.compareToIgnoreCase(s2);
        }

    }

    /**
     * Comparator instance to be used in all test cases.
     */
    private static final StringLT ORDER = new StringLT();

    /*
     * Sample test cases.
     */

    /**
     * Test for SortingMachineKernel constructor.
     */
    @Test
    public final void testConstructor() {
        SortingMachine<String> m = this.constructorTest(ORDER);
        SortingMachine<String> mExpected = this.constructorRef(ORDER);
        assertEquals(mExpected, m);
    }

    /**
     * Boundary test for add method, when SortingMachine is empty.
     */
    @Test
    public final void testAddEmpty() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "green");
        m.add("green");
        assertEquals(mExpected, m);
    }

    // TODO - add test cases for add, changeToExtractionMode, removeFirst,
    // isInInsertionMode, order, and size

    /*
     * Test cases for add kernel method.
     */

    /**
     * Test add to SortingMachine containing one string.
     */
    @Test
    public final void testAddToOne() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "a");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "a", "b");
        m.add("b");
        assertEquals(mExpected, m);
    }

    /**
     * Test add to SortingMachine containing multiple strings.
     */
    @Test
    public final void testAddToMultiple() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "1",
                "2");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "1", "2", "3");
        m.add("3");
        assertEquals(mExpected, m);
    }

    /*
     * Test cases for removeFirst kernel method.
     */

    /**
     * Test removeFirst of SortingMachine containing one string.
     */
    @Test
    public final void testRemoveFirstOne() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false, "a");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false);
        String first = m.removeFirst();
        String firstExpected = "a";
        assertEquals(mExpected, m);
        assertEquals(firstExpected, first);
    }

    /**
     * Test for removeFirst when SortingMachine contains same strings.
     */
    @Test
    public final void testRemoveFirstSameTerm() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false, "abc",
                "abc");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false,
                "abc");
        String remove = m.removeFirst();
        assertEquals("abc", remove);
        assertEquals(mExpected, m);
    }

    /**
     * Test removeFirst of SortingMachine containing multiple, ordered, strings.
     */
    @Test
    public final void testRemoveFirstMultipleOrdered() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false, "d",
                "e", "f", "g");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false,
                "e", "f", "g");
        String first = m.removeFirst();
        String firstExpected = "d";
        assertEquals(mExpected, m);
        assertEquals(firstExpected, first);
    }

    /**
     * Test removeFirst of SortingMachine containing multiple, unordered,
     * strings.
     */
    @Test
    public final void testRemoveFirstMultipleUnordered() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false, "e",
                "f", "d", "g");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false,
                "e", "f", "g");
        String first = m.removeFirst();
        String firstExpected = "d";
        assertEquals(mExpected, m);
        assertEquals(firstExpected, first);
    }

    /*
     * Test cases for changeToExtractionMode kernel method.
     */

    /**
     * Test for changeToExtractionMode with empty SortingMachine.
     */
    @Test
    public final void testchangeToExtractionModeEmpty() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false);
        m.changeToExtractionMode();
        assertEquals(mExpected, m);
    }

    /**
     * Test for changeToExtractionMode with SortingMachine w/ one string.
     */
    @Test
    public final void testchangeToExtractionModeWithOne() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "a");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false,
                "a");
        m.changeToExtractionMode();
        assertEquals(mExpected, m);
    }

    /**
     * Test for changeToExtractionMode with SortingMachine w/ multiple strings.
     */
    @Test
    public final void testchangeToExtractionModeWithMultiple() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "a",
                "b", "c", "d");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false,
                "a", "b", "c", "d");
        m.changeToExtractionMode();
        assertEquals(mExpected, m);
    }

    /*
     * Test cases for isInInsertionMode when true.
     */

    /**
     * Test for isInInsertionMode when true and SortingMachine is empty.
     */
    @Test
    public final void testIsInInsertionTrueEmpty() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true);
        boolean bool = m.isInInsertionMode();
        boolean boolExpected = true;
        assertEquals(boolExpected, bool);
    }

    /**
     * Test for isInInsertionMode when true and SortingMachine has one string.
     */
    @Test
    public final void testIsInInsertionTrueOne() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "a");
        boolean bool = m.isInInsertionMode();
        boolean boolExpected = true;
        assertEquals(boolExpected, bool);
    }

    /**
     * Test for isInInsertionMode when true and SortingMachine has multiple
     * strings.
     */
    @Test
    public final void testIsInInsertionTrueMultiple() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "a",
                "b", "c", "d");
        boolean bool = m.isInInsertionMode();
        boolean boolExpected = true;
        assertEquals(boolExpected, bool);
    }

    /*
     * Test cases for isInInsertionMode when false.
     */

    /**
     * Test for isInInsertionMode when false and SortingMachine contains the
     * same strings.
     */
    @Test
    public final void testIsInInsertionTrueTwoSameLetter() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "a",
                "a");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "a", "a");
        assertEquals(true, m.isInInsertionMode());
        assertEquals(mExpected, m);
    }

    /**
     * Test for isInInsertionMode when false and SortingMachine is empty.
     */
    @Test
    public final void testIsInInsertionFalseEmpty() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false);
        boolean bool = m.isInInsertionMode();
        boolean boolExpected = false;
        assertEquals(boolExpected, bool);
    }

    /**
     * Test for isInInsertionMode when false and SortingMachine has one string.
     */
    @Test
    public final void testIsInInsertionFalseOne() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false, "a");
        boolean bool = m.isInInsertionMode();
        boolean boolExpected = false;
        assertEquals(boolExpected, bool);
    }

    /**
     * Test for isInInsertionMode when false and SortingMachine has multiple
     * strings.
     */
    @Test
    public final void testIsInInsertionFalseMultiple() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false, "a",
                "b", "c", "d");
        boolean bool = m.isInInsertionMode();
        boolean boolExpected = false;
        assertEquals(boolExpected, bool);
    }
    /*
     * Test cases for order kernel method.
     */

    /**
     * Test for order when SortingMachine is empty.
     */
    @Test
    public final void testOrderEmpty() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true);
        SortingMachine<String> mExpected = this.createFromArgsTest(ORDER, true);
        m.order();
        assertEquals(mExpected, m);
    }

    /**
     * Test for order when SortingMachine contains two values.
     */
    @Test
    public final void testOrderTwo() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "b",
                "a");
        SortingMachine<String> mExpected = this.createFromArgsTest(ORDER, true,
                "a", "b");
        m.order();
        assertEquals(mExpected, m);
    }

    /**
     * Test for order when SortingMachine contains multiple values.
     */
    @Test
    public final void testOrderMultiple() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "d",
                "c", "b", "a");
        SortingMachine<String> mExpected = this.createFromArgsTest(ORDER, true,
                "a", "b", "c", "d");
        m.order();
        assertEquals(mExpected, m);
    }

    /**
     * Test for order when SortingMachine contains two SAME values and is in
     * extraction mode.
     */
    @Test
    public final void testOrderTwoSameEntries() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "a",
                "a");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "a", "a");
        assertEquals(mExpected, m);
        assertEquals(ORDER, m.order());
    }

    /**
     * Test for order when SortingMachine contains two values and is in
     * extraction mode.
     */
    @Test
    public final void testOrderTwoExtractMode() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false, "b",
                "a");
        SortingMachine<String> mExpected = this.createFromArgsTest(ORDER, false,
                "a", "b");
        m.order();
        assertEquals(mExpected, m);
    }

    /**
     * Test for order when SortingMachine contains multiple values and is in
     * extraction mode.
     */
    @Test
    public final void testOrderMultipleExtractMode() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false, "d",
                "c", "b", "a");
        SortingMachine<String> mExpected = this.createFromArgsTest(ORDER, false,
                "a", "b", "c", "d");
        m.order();
        assertEquals(mExpected, m);
    }

    /*
     * Test cases for size kernel method.
     */

    /**
     * Test for size when SortingMachine is empty.
     */
    @Test
    public final void testSizeEmpty() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true);
        int size = m.size();
        int sizeExpected = 0;
        assertEquals(sizeExpected, size);
    }

    /**
     * Test for size when SortingMachine contains one string.
     */
    @Test
    public final void testSizeOne() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "a");
        int size = m.size();
        int sizeExpected = 1;
        assertEquals(sizeExpected, size);
    }

    /**
     * Test for size when SortingMachine contains multiple strings.
     */
    @Test
    public final void testSizeMultiple() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "a",
                "b", "c", "d");
        int size = m.size();
        int sizeExpected = 4;
        assertEquals(sizeExpected, size);
    }

    /**
     * Test for size when SortingMachine contains the same strings.
     */
    @Test
    public final void testSizeTwoSameWord() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "abc",
                "abc");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "abc", "abc");
        int s = m.size();
        assertEquals(mExpected, m);
        assertEquals(2, s);
    }

    /**
     * Test for size when SortingMachine contains empty strings.
     */
    @Test
    public final void testSizeFalseEmptyContent() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false, "");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false,
                "");
        int s = m.size();
        assertEquals(mExpected, m);
        assertEquals(1, s);
    }

    /**
     * Test for size when SortingMachine is empty and is in extraction mode.
     */
    @Test
    public final void testSizeEmptyExtractMode() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false);
        int size = m.size();
        int sizeExpected = 0;
        assertEquals(sizeExpected, size);
    }

    /**
     * Test for size when SortingMachine contains two strings and is in
     * extraction mode.
     */
    @Test
    public final void testSizeOneExtractMode() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false, "a");
        int size = m.size();
        int sizeExpected = 1;
        assertEquals(sizeExpected, size);
    }

    /**
     * Test for size when SortingMachine contains multiple strings and is in
     * extraction mode.
     */
    @Test
    public final void testSizeMultipleExtractMode() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false, "a",
                "b", "c", "d");
        int size = m.size();
        int sizeExpected = 4;
        assertEquals(4, size);
    }

}
