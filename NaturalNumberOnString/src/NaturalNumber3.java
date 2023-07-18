import components.naturalnumber.NaturalNumber;
import components.naturalnumber.NaturalNumberSecondary;

/**
 * {@code NaturalNumber} represented as a {@code String} with implementations of
 * primary methods.
 *
 * @convention <pre>
 * [all characters of $this.rep are '0' through '9']  and
 * [$this.rep does not start with '0']
 * </pre>
 * @correspondence <pre>
 * this = [if $this.rep = "" then 0
 *         else the decimal number whose ordinary depiction is $this.rep]
 * </pre>
 *
 * @author Yanqing Xu, Shafin Alam
 *
 */
public class NaturalNumber3 extends NaturalNumberSecondary {

    /*
     * Private members --------------------------------------------------------
     */

    /**
     * Representation of {@code this}.
     */
    private String rep;

    /**
     * Creator of initial representation.
     */
    private void createNewRep() {

        //Creates new String representation of NaturalNumber
        this.rep = "";

    }

    /*
     * Constructors -----------------------------------------------------------
     */

    /**
     * No-argument constructor.
     */
    public NaturalNumber3() {

        //Creates new naturalNumber from empty String
        this.createNewRep();
    }

    /**
     * Constructor from {@code int}.
     *
     * @param i
     *            {@code int} to initialize from
     */
    public NaturalNumber3(int i) {
        assert i >= 0 : "Violation of: i >= 0";

        //Creates new NaturalNumber from int
        this.createNewRep();
        if (i == 0) {
            this.rep = "";
        } else {
            this.rep = Integer.toString(i);
        }
    }

    /**
     * Constructor from {@code String}.
     *
     * @param s
     *            {@code String} to initialize from
     */
    public NaturalNumber3(String s) {
        assert s != null : "Violation of: s is not null";
        assert s.matches("0|[1-9]\\d*") : ""
                + "Violation of: there exists n: NATURAL (s = TO_STRING(n))";

        // Creates new NaturalNumber from String
        this.createNewRep();
        if (s.equals("0")) {
            this.rep = "";
        } else {
            this.rep = s;
        }
    }

    /**
     * Constructor from {@code NaturalNumber}.
     *
     * @param n
     *            {@code NaturalNumber} to initialize from
     */
    public NaturalNumber3(NaturalNumber n) {
        assert n != null : "Violation of: n is not null";

        // Creates new NaturalNumber from existing NaturalNumber
        this.createNewRep();
        if (n.isZero()) {
            this.rep = "";
        } else {
            this.rep = n.toString();
        }
    }

    /*
     * Standard methods -------------------------------------------------------
     */

    @Override
    public final NaturalNumber newInstance() {
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
    public final void transferFrom(NaturalNumber source) {
        assert source != null : "Violation of: source is not null";
        assert source != this : "Violation of: source is not this";
        assert source instanceof NaturalNumber3 : ""
                + "Violation of: source is of dynamic type NaturalNumberExample";
        /*
         * This cast cannot fail since the assert above would have stopped
         * execution in that case.
         */
        NaturalNumber3 localSource = (NaturalNumber3) source;
        this.rep = localSource.rep;
        localSource.createNewRep();
    }

    /*
     * Kernel methods ---------------------------------------------------------
     */

    @Override
    public final void multiplyBy10(int k) {
        assert 0 <= k : "Violation of: 0 <= k";
        assert k < RADIX : "Violation of: k < 10";

//            Should just append the digit at the end of the
//            String representation, making it equal to x 10+k
        String strK = String.valueOf(k);
        this.rep = this.rep + strK;

    }

    @Override
    public final int divideBy10() {

        // k is int that gets returned
        int k = 0;
        //Check if this has a digit
        if (this.rep.length() > 0) {
            //assign remainder to char at end of string representation
            char s = this.rep.charAt(this.rep.length() - 1);
            //Check if it is a single digit
            if (this.rep.length() > 1) {
                //if it is a double digit or larger, it should remove last digit
                this.rep = this.rep.substring(0, this.rep.length() - 1);
            } else {
                //if it is a single digit it should be come 0, or empty string
                this.rep = "";
            }
            //should get the numeric value of the last digit
            k = Character.getNumericValue(s);
        } else {
            //k becomes 0 if the string representation was empty
            k = 0;
        }
        return k;

    }

    @Override
    public final boolean isZero() {

        // Checks if NaturalNumber String representation is length of 0
        return this.rep.length() == 0;
    }

}
