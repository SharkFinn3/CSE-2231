import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.map.Map;
import components.map.Map.Pair;

/**
 * JUnit test fixture for {@code Map<String, String>}'s constructor and kernel
 * methods.
 *
 * @author Shafin Alam & Yanqing Xu
 *
 */
public abstract class MapTest {

    /**
     * Invokes the appropriate {@code Map} constructor for the implementation
     * under test and returns the result.
     *
     * @return the new map
     * @ensures constructorTest = {}
     */
    protected abstract Map<String, String> constructorTest();

    /**
     * Invokes the appropriate {@code Map} constructor for the reference
     * implementation and returns the result.
     *
     * @return the new map
     * @ensures constructorRef = {}
     */
    protected abstract Map<String, String> constructorRef();

    /**
     *
     * Creates and returns a {@code Map<String, String>} of the implementation
     * under test type with the given entries.
     *
     * @param args
     *            the (key, value) pairs for the map
     * @return the constructed map
     * @requires <pre>
     * [args.length is even]  and
     * [the 'key' entries in args are unique]
     * </pre>
     * @ensures createFromArgsTest = [pairs in args]
     */
    private Map<String, String> createFromArgsTest(String... args) {
        assert args.length % 2 == 0 : "Violation of: args.length is even";
        Map<String, String> map = this.constructorTest();
        for (int i = 0; i < args.length; i += 2) {
            assert !map.hasKey(args[i]) : ""
                    + "Violation of: the 'key' entries in args are unique";
            map.add(args[i], args[i + 1]);
        }
        return map;
    }

    /**
     *
     * Creates and returns a {@code Map<String, String>} of the reference
     * implementation type with the given entries.
     *
     * @param args
     *            the (key, value) pairs for the map
     * @return the constructed map
     * @requires <pre>
     * [args.length is even]  and
     * [the 'key' entries in args are unique]
     * </pre>
     * @ensures createFromArgsRef = [pairs in args]
     */
    private Map<String, String> createFromArgsRef(String... args) {
        assert args.length % 2 == 0 : "Violation of: args.length is even";
        Map<String, String> map = this.constructorRef();
        for (int i = 0; i < args.length; i += 2) {
            assert !map.hasKey(args[i]) : ""
                    + "Violation of: the 'key' entries in args are unique";
            map.add(args[i], args[i + 1]);
        }
        return map;
    }

    // TODO - add test cases for constructor, add, remove, removeAny, value,
    // hasKey, and size
    /**
     * Test for empty default constructor.
     */
    @Test
    public final void testDefaultConstructor() {
        Map<String, String> t = this.constructorTest();
        Map<String, String> r = this.constructorRef();
        assertEquals(r, t);
    }

    /**
     * Test for non-empty constructor.
     */
    @Test
    public final void testDefaultConstructor2() {
        Map<String, String> t = this.createFromArgsTest("M", "9", "D", "8");
        Map<String, String> r = this.createFromArgsRef("M", "9", "D", "8");
        assertEquals(r, t);
    }

    /**
     * Test for adding pair to empty map.
     */
    @Test
    public final void testAddToEmpty() {
        Map<String, String> m = this.createFromArgsTest();
        m.add("Fortnite", "Game");
        Map<String, String> mExpected = this.createFromArgsRef("Fortnite",
                "Game");
        assertEquals(mExpected, m);
    }

    /**
     * Test for adding pair to map with one pair already in it.
     */
    @Test
    public final void testAddToOne() {
        Map<String, String> m = this.createFromArgsTest("Fortnite", "Game");
        m.add("Interstellar", "Movie");
        Map<String, String> mExpected = this.createFromArgsRef("Fortnite",
                "Game", "Interstellar", "Movie");
        assertEquals(mExpected, m);
    }

    /**
     * Test for adding pair to map with more than 2 pairs in it already.
     */
    @Test
    public final void testAddTo2orMore() {
        Map<String, String> m = this.createFromArgsTest("Apex", "Game", "Lost",
                "Song", "OSU", "University");
        m.add("Soccer", "Sport");
        Map<String, String> mExpected = this.createFromArgsRef("Apex", "Game",
                "Lost", "Song", "OSU", "University", "Soccer", "Sport");
        assertEquals(mExpected, m);
    }

    /**
     * Test for removing pair from map with only one pair.
     */
    @Test
    public final void testRemoveFromOne() {
        Map<String, String> m = this.createFromArgsTest("basket", "ball");
        m.remove("basket");
        Map<String, String> mExpected = this.createFromArgsRef();
        assertEquals(mExpected, m);
    }

    /**
     * Test for removing pair from map that contains 2 or more pairs already.
     */
    @Test
    public final void testRemoveFrom2orMore() {
        Map<String, String> m = this.createFromArgsTest("soccer", "ball",
                "basket", "ball");
        m.remove("basket");
        Map<String, String> mExpected = this.createFromArgsRef("soccer",
                "ball");
        assertEquals(mExpected, m);
    }

    /**
     * Test for value of key with map that has only one pair.
     */
    @Test
    public final void testValueFromOne() {
        Map<String, String> m = this.createFromArgsTest("basket", "ball");
        String v = m.value("basket");
        String vExpected = "ball";
        assertEquals(vExpected, v);
    }

    /**
     * Test for value of key with map that has one pair w/ empty value.
     */
    @Test
    public final void testValueEmptyValue() {
        Map<String, String> m = this.createFromArgsTest("basket", "");
        String v = m.value("basket");
        String vExpected = "";
        assertEquals(vExpected, v);
    }

    /**
     * Test for value of key with map that has 2 or more pairs.
     */
    @Test
    public final void testValueFrom2orMore() {
        Map<String, String> m = this.createFromArgsTest("basket", "ball",
                "soccer", "ball");
        String v = m.value("soccer");
        String vExpected = "ball";
        assertEquals(vExpected, v);
    }

    /**
     * Test for hasKey w/ empty map.
     */
    @Test
    public final void testHasKeyFromNoneFalse() {
        Map<String, String> m = this.createFromArgsTest();
        boolean p = m.hasKey("football");
        boolean pExpected = false;
        assertEquals(pExpected, p);
    }

    /**
     * Test for hasKey with map that has only one pair, should result in true.
     */
    @Test
    public final void testHasKeyFromOneTrue() {
        Map<String, String> m = this.createFromArgsTest("football", "Bengals");
        boolean p = m.hasKey("football");
        boolean pExpected = true;
        assertEquals(pExpected, p);
    }

    /**
     * Test for hasKey with map that has only one pair, should result in false.
     */
    @Test
    public final void testHasKeyFromOneFalse() {
        Map<String, String> m = this.createFromArgsTest("football", "Bengals");
        boolean p = m.hasKey("soccer");
        boolean pExpected = false;
        assertEquals(pExpected, p);
    }

    /**
     * Test for hasKey with map that has 2 or more pairs, should result in true.
     */
    @Test
    public final void testHasKeyFrom2orMoreTrue() {
        Map<String, String> m = this.createFromArgsTest("football", "Bengals",
                "soccer", "ball");
        boolean p = m.hasKey("football");
        boolean pExpected = true;
        assertEquals(pExpected, p);
    }

    /**
     * Test for hasKey w/ map that has 2 or more pairs, should result in false.
     */
    @Test
    public final void testHasKeyFrom2orMoreFalse() {
        Map<String, String> m = this.createFromArgsTest("football", "Bengals",
                "soccer", "ball");
        boolean p = m.hasKey("tennis");
        boolean pExpected = false;
        assertEquals(pExpected, p);
    }

    /**
     * Test for size w/ empty map.
     */
    @Test
    public final void testSizeFromNone() {
        Map<String, String> m = this.createFromArgsTest();
        int length = m.size();
        int lengthExpected = 0;
        assertEquals(lengthExpected, length);
    }

    /**
     * Test for size with map that contains only one pair.
     */
    @Test
    public final void testSizeFromOne() {
        Map<String, String> m = this.createFromArgsTest("football", "Bengals");
        int length = m.size();
        int lengthExpected = 1;
        assertEquals(lengthExpected, length);
    }

    /**
     * Test for size with map that contains 2 or more pairs.
     */
    @Test
    public final void testSizeFrom2orMore() {
        Map<String, String> m = this.createFromArgsTest("football", "Bengals",
                "soccer", "ball");
        int length = m.size();
        int lengthExpected = 2;
        assertEquals(lengthExpected, length);
    }

    /**
     * Test for removeAny, with map that only has one pair.
     */
    @Test
    public final void testRemoveAnyFromOne() {
        Map<String, String> m = this.createFromArgsTest("basket", "ball");
        Pair<String, String> p = m.removeAny();
        String key = p.key();
        String keyExpected = "basket";
        String value = p.value();
        String valueExpected = "ball";
        assertEquals(keyExpected, key);
        assertEquals(valueExpected, value);
    }

    /**
     * Test for removeAny, with map that has 2 or more pairs.
     */
    @Test
    public final void testRemoveAnyFrom2OrMore() {
        Map<String, String> m = this.createFromArgsTest("basket", "ball",
                "soccer", "ball");
        Pair<String, String> p = m.removeAny();
        String key = p.key();
        String keyExpected = "basket";
        String value = p.value();
        String valueExpected = "ball";
        assertEquals(keyExpected, key);
        assertEquals(valueExpected, value);
    }
}
