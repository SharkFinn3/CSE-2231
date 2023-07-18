import java.util.Iterator;

import components.binarytree.BinaryTree;
import components.binarytree.BinaryTree1;
import components.set.Set;
import components.set.SetSecondary;

/**
 * {@code Set} represented as a {@code BinaryTree} (maintained as a binary
 * search tree) of elements with implementations of primary methods.
 *
 * @param <T>
 *            type of {@code Set} elements
 * @mathdefinitions <pre>
 * IS_BST(
 *   tree: binary tree of T
 *  ): boolean satisfies
 *  [tree satisfies the binary search tree properties as described in the
 *   slides with the ordering reported by compareTo for T, including that
 *   it has no duplicate labels]
 * </pre>
 * @convention IS_BST($this.tree)
 * @correspondence this = labels($this.tree)
 *
 * @author Shafin Alam & Yanqing Xu
 *
 */
public class Set3a<T extends Comparable<T>> extends SetSecondary<T> {

    /*
     * Private members --------------------------------------------------------
     */

    /**
     * Elements included in {@code this}.
     */
    private BinaryTree<T> tree;

    /**
     * Returns whether {@code x} is in {@code t}.
     *
     * @param <T>
     *            type of {@code BinaryTree} labels
     * @param t
     *            the {@code BinaryTree} to be searched
     * @param x
     *            the label to be searched for
     * @return true if t contains x, false otherwise
     * @requires IS_BST(t)
     * @ensures isInTree = (x is in labels(t))
     */
    private static <T extends Comparable<T>> boolean isInTree(BinaryTree<T> t,
            T x) {
        assert t != null : "Violation of: t is not null";
        assert x != null : "Violation of: x is not null";

        //default boolean is false, in case x is not in tree
        boolean bool = false;
        //create left and right trees
        BinaryTree<T> left = t.newInstance();
        BinaryTree<T> right = t.newInstance();
        //if the tree is not empty
        if (t.size() != 0) {
            //check the root of the tree
            T root = t.disassemble(left, right);
            //if root is less than x, check right tree
            if (x.compareTo(root) > 0) {
                bool = isInTree(right, x);
                //if root is more than x, check left tree
            } else if (x.compareTo(root) < 0) {
                bool = isInTree(left, x);
            } else {
                //if root equals x, it has been found
                bool = true;
            }
            t.assemble(root, left, right);
        }

        // return whether or not x is in tree
        return bool;
    }

    /**
     * Inserts {@code x} in {@code t}.
     *
     * @param <T>
     *            type of {@code BinaryTree} labels
     * @param t
     *            the {@code BinaryTree} to be searched
     * @param x
     *            the label to be inserted
     * @aliases reference {@code x}
     * @updates t
     * @requires IS_BST(t) and x is not in labels(t)
     * @ensures IS_BST(t) and labels(t) = labels(#t) union {x}
     */
    private static <T extends Comparable<T>> void insertInTree(BinaryTree<T> t,
            T x) {
        assert t != null : "Violation of: t is not null";
        assert x != null : "Violation of: x is not null";

        //create left and right trees
        BinaryTree<T> left = t.newInstance();
        BinaryTree<T> right = t.newInstance();
        //as long as tree is not empty
        if (t.size() == 0) {
            //add x and left and right trees to t
            t.assemble(x, left, right);
        } else {
            //otherwise get root and left and right trees
            T root = t.disassemble(left, right);
            //if x is smaller than root, go to the right tree
            if (x.compareTo(root) > 0) {
                //add x into the right tree
                insertInTree(right, x);
                //otherwise, go to the left tree
            } else {
                //add x into the left tree
                insertInTree(left, x);
            }
            //reassemble tree
            t.assemble(root, left, right);
        }
    }

    /**
     * Removes and returns the smallest (left-most) label in {@code t}.
     *
     * @param <T>
     *            type of {@code BinaryTree} labels
     * @param t
     *            the {@code BinaryTree} from which to remove the label
     * @return the smallest label in the given {@code BinaryTree}
     * @updates t
     * @requires IS_BST(t) and |t| > 0
     * @ensures <pre>
     * IS_BST(t)  and  removeSmallest = [the smallest label in #t]  and
     *  labels(t) = labels(#t) \ {removeSmallest}
     * </pre>
     */
    private static <T> T removeSmallest(BinaryTree<T> t) {
        assert t != null : "Violation of: t is not null";
        assert t.size() > 0 : "Violation of: |t| > 0";

        //create left and right trees
        BinaryTree<T> left = t.newInstance();
        BinaryTree<T> right = t.newInstance();
        //find root and left and right tree of t
        T root = t.disassemble(left, right);
        //make root the smallest value
        T smallestValue = root;
        //if it can go to the left more
        if (left.size() > 0) {
            //make smallest value even smaller by getting the lest tree
            smallestValue = removeSmallest(left);
            //restore t
            t.assemble(root, left, right);
        } else {
            //if left is empty, get from right tree
            t.transferFrom(right);
        }
        //return the smallest value in the tree
        return smallestValue;
    }

    /**
     * Finds label {@code x} in {@code t}, removes it from {@code t}, and
     * returns it.
     *
     * @param <T>
     *            type of {@code BinaryTree} labels
     * @param t
     *            the {@code BinaryTree} from which to remove label {@code x}
     * @param x
     *            the label to be removed
     * @return the removed label
     * @updates t
     * @requires IS_BST(t) and x is in labels(t)
     * @ensures <pre>
     * IS_BST(t)  and  removeFromTree = x  and
     *  labels(t) = labels(#t) \ {x}
     * </pre>
     */
    private static <T extends Comparable<T>> T removeFromTree(BinaryTree<T> t,
            T x) {
        assert t != null : "Violation of: t is not null";
        assert x != null : "Violation of: x is not null";
        assert t.size() > 0 : "Violation of: x is in labels(t)";

        //create left and right trees
        BinaryTree<T> left = t.newInstance();
        BinaryTree<T> right = t.newInstance();
        //find root and left and right trees of t
        T root = t.disassemble(left, right);
        //set value to remove equal to the root
        T valueToRemove = root;
        //if the root is equal to x
        if (x.compareTo(root) == 0) {
            //if the right tree is not empty
            if (right.size() > 0) {
                //remove the smallest value of the right tree
                root = removeSmallest(right);
                //restore the tree t
                t.assemble(root, left, right);
            } else {
                //otherwise copy from the left tree, if right is empty
                t.transferFrom(left);
            }
            //if x is greater than the root
        } else if (x.compareTo(root) > 0) {
            //remove x from the right tree
            valueToRemove = removeFromTree(right, x);
            //restore tree t
            t.assemble(root, left, right);
        } else {
            //remove x from left tree
            valueToRemove = removeFromTree(left, x);
            //restore tree t
            t.assemble(root, left, right);
        }
        //return the value that was removed
        return valueToRemove;
    }

    /**
     * Creator of initial representation.
     */
    private void createNewRep() {

        //create new BInaryTree1 representation of this
        this.tree = new BinaryTree1<T>();
    }

    /*
     * Constructors -----------------------------------------------------------
     */

    /**
     * No-argument constructor.
     */
    public Set3a() {

        //create empty rep
        this.createNewRep();
    }

    /*
     * Standard methods -------------------------------------------------------
     */

    @SuppressWarnings("unchecked")
    @Override
    public final Set<T> newInstance() {
        try {
            return this.getClass().getConstructor().newInstance();
        } catch (ReflectiveOperationException e) {
            throw new AssertionError(
                    "Cannot construct object of type " + this.getClass());
        }
    }

    @Override
    public final void clear() {
        this.createNewRep();
    }

    @Override
    public final void transferFrom(Set<T> source) {
        assert source != null : "Violation of: source is not null";
        assert source != this : "Violation of: source is not this";
        assert source instanceof Set3a<?> : ""
                + "Violation of: source is of dynamic type Set3<?>";
        /*
         * This cast cannot fail since the assert above would have stopped
         * execution in that case: source must be of dynamic type Set3a<?>, and
         * the ? must be T or the call would not have compiled.
         */
        Set3a<T> localSource = (Set3a<T>) source;
        this.tree = localSource.tree;
        localSource.createNewRep();
    }

    /*
     * Kernel methods ---------------------------------------------------------
     */

    @Override
    public final void add(T x) {
        assert x != null : "Violation of: x is not null";
        assert !this.contains(x) : "Violation of: x is not in this";

        //Calls insertInTree to add x to the set
        insertInTree(this.tree, x);
    }

    @Override
    public final T remove(T x) {
        assert x != null : "Violation of: x is not null";
        assert this.contains(x) : "Violation of: x is in this";

        //Calls removeFromTree to remove x from the set
        return removeFromTree(this.tree, x);
    }

    @Override
    public final T removeAny() {
        assert this.size() > 0 : "Violation of: this /= empty_set";

        //Calls removeSmallest to remove the first (smallest) value in the tree
        return removeSmallest(this.tree);
    }

    @Override
    public final boolean contains(T x) {
        assert x != null : "Violation of: x is not null";

        //Calls isInTree to check if x is in the set
        return isInTree(this.tree, x);
    }

    @Override
    public final int size() {

        //checks the size of the tree representation of the set
        return this.tree.size();
    }

    @Override
    public final Iterator<T> iterator() {
        return this.tree.iterator();
    }

}
