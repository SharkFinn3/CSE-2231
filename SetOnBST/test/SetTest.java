import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.set.Set;

/**
 * JUnit test fixture for {@code Set<String>}'s constructor and kernel methods.
 *
 * @author Shafin Alam & Yanqing Xu
 *
 */
public abstract class SetTest {

    /**
     * Invokes the appropriate {@code Set} constructor for the implementation
     * under test and returns the result.
     *
     * @return the new set
     * @ensures constructorTest = {}
     */
    protected abstract Set<String> constructorTest();

    /**
     * Invokes the appropriate {@code Set} constructor for the reference
     * implementation and returns the result.
     *
     * @return the new set
     * @ensures constructorRef = {}
     */
    protected abstract Set<String> constructorRef();

    /**
     * Creates and returns a {@code Set<String>} of the implementation under
     * test type with the given entries.
     *
     * @param args
     *            the entries for the set
     * @return the constructed set
     * @requires [every entry in args is unique]
     * @ensures createFromArgsTest = [entries in args]
     */
    private Set<String> createFromArgsTest(String... args) {
        Set<String> set = this.constructorTest();
        for (String s : args) {
            assert !set.contains(
                    s) : "Violation of: every entry in args is unique";
            set.add(s);
        }
        return set;
    }

    /**
     * Creates and returns a {@code Set<String>} of the reference implementation
     * type with the given entries.
     *
     * @param args
     *            the entries for the set
     * @return the constructed set
     * @requires [every entry in args is unique]
     * @ensures createFromArgsRef = [entries in args]
     */
    private Set<String> createFromArgsRef(String... args) {
        Set<String> set = this.constructorRef();
        for (String s : args) {
            assert !set.contains(
                    s) : "Violation of: every entry in args is unique";
            set.add(s);
        }
        return set;
    }

    // TODO - add test cases for constructor, add, remove, removeAny, contains, and size

    /**
     * Test for constructor.
     */
    @Test
    public final void testConstructor() {
        Set<String> s = this.constructorTest();
        Set<String> sExpected = this.constructorRef();
        assertEquals(sExpected, s);
    }

    /**
     * Boundary case for add, adds value to empty set.
     */
    @Test
    public final void testAddToEmptyTree() {
        Set<String> s = this.createFromArgsTest();
        Set<String> sExpected = this.createFromArgsRef("a");
        s.add("a");
        assertEquals(sExpected, s);
    }

    /**
     * Routine case for add, adds value to set with one value.
     */
    @Test
    public final void testAddToOne() {
        Set<String> s = this.createFromArgsTest("a");
        Set<String> sExpected = this.createFromArgsRef("a", "b");
        s.add("b");
        assertEquals(sExpected, s);
    }

    /**
     * Case for add, adds value to right when one value on left.
     */
    @Test
    public final void testAddWithOneLeft() {
        Set<String> initial = this.createFromArgsTest("red", "yellow");
        initial.add("blue");
        Set<String> expected = this.createFromArgsRef("red", "yellow", "blue");
        assertEquals(initial, expected);
    }

    /**
     * Case for add, adds value to left when one value on right.
     */
    @Test
    public final void testAddWithOneRight() {
        Set<String> initial = this.createFromArgsTest("red", "blue");
        initial.add("yellow");
        Set<String> expected = this.createFromArgsRef("red", "yellow", "blue");
        assertEquals(initial, expected);
    }

    /**
     * "Challenging" case for add, adds value to set with more than one value.
     */
    @Test
    public final void testAddWith2orMore() {
        Set<String> initial = this.createFromArgsTest("red", "yellow");
        initial.add("blue");
        Set<String> expected = this.createFromArgsRef("red", "yellow", "blue");
        assertEquals(initial, expected);
    }

    /**
     * Boundary case for remove, removes value from set with one value.
     */
    @Test
    public final void testRemoveWith1() {
        Set<String> initial = this.createFromArgsTest("red");
        Set<String> expected = this.createFromArgsTest();
        String str = initial.remove("red");
        assertEquals(initial, expected);
        assertEquals(str, "red");
    }

    /**
     * Case for remove, removes the left value on tree.
     */
    @Test
    public final void testRemoveWithOneLeft() {
        Set<String> initial = this.createFromArgsTest("red", "yellow");
        Set<String> expected = this.createFromArgsRef("red");
        String str = initial.remove("yellow");
        assertEquals(initial, expected);
        assertEquals(str, "yellow");
    }

    /**
     * Case for remove, removes the right value on tree.
     */
    @Test
    public final void testRemoveWithOneRight() {
        Set<String> initial = this.createFromArgsTest("red", "blue");
        Set<String> expected = this.createFromArgsRef("red");
        String str = initial.remove("blue");
        assertEquals(initial, expected);
        assertEquals(str, "blue");
    }

    /**
     * Case for remove, removes the value on root.
     */
    @Test
    public final void testRemoveTheRoot() {
        Set<String> initial = this.createFromArgsTest("red", "yellow");
        Set<String> expected = this.createFromArgsRef("yellow");
        String str = initial.remove("red");
        assertEquals(initial, expected);
        assertEquals(str, "red");
    }

    /**
     * "Challenging" case for remove, removes value from set with more than one
     * value.
     */
    @Test
    public final void testRemoveWith2orMore() {
        Set<String> initial = this.createFromArgsTest("red", "yellow", "blue");
        String blue = initial.remove("blue");
        String expected = "blue";
        assertEquals(blue, expected);
    }

    /**
     * Boundary case for contains, checks if empty set contains value, should
     * return false.
     */
    @Test
    public final void testContainsEmptyFalse() {
        Set<String> initial = this.createFromArgsTest();
        Boolean contains = initial.contains("red");
        Boolean expected = false;
        assertEquals(contains, expected);
    }

    /**
     * Routine case for contains, checks if set with one value contains specific
     * value, should return true.
     */
    @Test
    public final void testContainsWith1True() {
        Set<String> initial = this.createFromArgsTest("red");
        Boolean contains = initial.contains("red");
        Boolean expected = true;
        assertEquals(contains, expected);
    }

    /**
     * Routine case for contains, checks if set with one value contains specific
     * value, should return false.
     */
    @Test
    public final void testContainsWith1False() {
        Set<String> initial = this.createFromArgsTest("yellow", "blue");
        Boolean contains = initial.contains("red");
        Boolean expected = false;
        assertEquals(contains, expected);
    }

    /**
     * "Challenging" case for contains, should check if larger set contains
     * specific value; should return true.
     */
    @Test
    public final void testContainsWith2orMoreTrue() {
        Set<String> initial = this.createFromArgsTest("red", "yellow", "blue");
        Boolean contains = initial.contains("red");
        Boolean expected = true;
        assertEquals(contains, expected);
    }

    /**
     * "Challenging" case for contains, should check if large set contains
     * specific value, should return false.
     */
    @Test
    public final void testContainsWith2orMoreFalse() {
        Set<String> initial = this.createFromArgsTest("red", "yellow", "blue");
        Boolean contains = initial.contains("green");
        Boolean expected = false;
        assertEquals(contains, expected);
    }

    /**
     * Boundary case for size, should return empty set's size of 0.
     */
    @Test
    public void testSizeWithEmpty() {
        Set<String> s = this.createFromArgsTest();
        int size = s.size();
        int expected = 0;
        assertEquals(size, expected);
    }

    /**
     * Routine case for size, should return set's size of 1.
     */
    @Test
    public void testSizeWith1() {
        Set<String> s = this.createFromArgsTest("red");
        int size = s.size();
        int expected = 1;
        assertEquals(size, expected);
    }

    /**
     * "Challenging" case for size, should return large set's size of 3.
     */
    @Test
    public final void testSizeWith2orMore() {
        Set<String> s = this.createFromArgsTest("red", "yellow", "blue");
        int size = s.size();
        int expected = 3;
        assertEquals(size, expected);
    }

    /**
     * Case for size, test with Empty String.
     */
    @Test
    public final void testSizeWithEmptyString() {
        Set<String> s = this.createFromArgsTest("");
        Set<String> sExpected = this.createFromArgsRef("");
        int size = s.size();
        assertEquals(1, size);
        assertEquals(sExpected, s);
    }

    /**
     * Boundary case for removeAny, should return "random" value from set with
     * one value(smallest value).
     */
    @Test
    public final void testRemoveAnyWith1() {
        Set<String> s = this.createFromArgsTest("red");
        String removed = s.removeAny();
        String expected = "red";
        assertEquals(removed, expected);
    }

    /**
     * Routine case for removeAny, should return "random" value from
     * set(smallest value).
     */
    @Test
    public final void testRemoveAnyWith2orMore() {
        Set<String> s = this.createFromArgsTest("red", "yellow", "blue");
        String removed = s.removeAny();
        String expected = "blue";
        assertEquals(removed, expected);
    }

    /**
     * "Challenging" case for removeAny, should return "random" value from
     * larger set(smallest value).
     */
    @Test
    public final void testRemoveAnyLargeSet() {
        Set<String> s = this.createFromArgsTest("red", "yellow", "blue",
                "green", "purple", "orange");
        String removed = s.removeAny();
        String expected = "blue";
        assertEquals(removed, expected);
    }
}
