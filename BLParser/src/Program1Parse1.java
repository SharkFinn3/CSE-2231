import components.map.Map;
import components.program.Program;
import components.program.Program1;
import components.queue.Queue;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.statement.Statement;
import components.utilities.Reporter;
import components.utilities.Tokenizer;

/**
 * Layered implementation of secondary method {@code parse} for {@code Program}.
 *
 * @author Shafin Alam, Yanqing Xu
 *
 */
public final class Program1Parse1 extends Program1 {

    /*
     * Private members --------------------------------------------------------
     */

    /**
     * Parses a single BL instruction from {@code tokens} returning the
     * instruction name as the value of the function and the body of the
     * instruction in {@code body}.
     *
     * @param tokens
     *            the input tokens
     * @param body
     *            the instruction body
     * @return the instruction name
     * @replaces body
     * @updates tokens
     * @requires <pre>
     * [<"INSTRUCTION"> is a prefix of tokens]  and
     *  [<Tokenizer.END_OF_INPUT> is a suffix of tokens]
     * </pre>
     * @ensures <pre>
     * if [an instruction string is a proper prefix of #tokens]  and
     *    [the beginning name of this instruction equals its ending name]  and
     *    [the name of this instruction does not equal the name of a primitive
     *     instruction in the BL language] then
     *  parseInstruction = [name of instruction at start of #tokens]  and
     *  body = [Statement corresponding to the block string that is the body of
     *          the instruction string at start of #tokens]  and
     *  #tokens = [instruction string at start of #tokens] * tokens
     * else
     *  [report an appropriate error message to the console and terminate client]
     * </pre>
     */
    private static String parseInstruction(Queue<String> tokens,
            Statement body) {
        assert tokens != null : "Violation of: tokens is not null";
        assert body != null : "Violation of: body is not null";
        assert tokens.length() > 0 && tokens.front().equals("INSTRUCTION") : ""
                + "Violation of: <\"INSTRUCTION\"> is proper prefix of tokens";

        // TODO - fill in body

        // Dequeue Instruction
        tokens.dequeue();
        // Get the name and dequeue
        String name = tokens.dequeue();
        Reporter.assertElseFatalError(Tokenizer.isIdentifier(name),
                "The instruction name has to be an identifier. ");
        // Check if the instruction name is the name of primitive call.
        Reporter.assertElseFatalError(
                !(name.equals("turnleft") || name.equals("turnright")
                        || name.equals("move") || name.equals("infect")
                        || name.equals("skip")),
                "Instruction name should be a primitive call . ");
        // Check whether the following string after instruction name is 'IS' or not.
        Reporter.assertElseFatalError(tokens.front().equals("IS"),
                "Here should be IS after instruction name . ");
        // Dequeue IS.
        tokens.dequeue();
        body.parseBlock(tokens);
        // The First element in queue should be END.
        Reporter.assertElseFatalError(tokens.front().equals("END"),
                "Here should be END. ");
        //Dequeue END.
        tokens.dequeue();
        /*
         * Check the end of the instruction name .
         */
        Reporter.assertElseFatalError(tokens.front().equals(name),
                "Instruction end name is not equal to the name at start. ");
        // Dequeue the end instruction name .
        tokens.dequeue();
        return name;
    }

    /*
     * Constructors -----------------------------------------------------------
     */

    /**
     * No-argument constructor.
     */
    public Program1Parse1() {
        super();
    }

    /*
     * Public methods ---------------------------------------------------------
     */

    @Override
    public void parse(SimpleReader in) {
        assert in != null : "Violation of: in is not null";
        assert in.isOpen() : "Violation of: in.is_open";
        Queue<String> tokens = Tokenizer.tokens(in);
        this.parse(tokens);
    }

    @Override
    public void parse(Queue<String> tokens) {
        assert tokens != null : "Violation of: tokens is not null";
        assert tokens.length() > 0 : ""
                + "Violation of: Tokenizer.END_OF_INPUT is a suffix of tokens";

        // TODO - fill in body
        // set  name
        Reporter.assertElseFatalError(tokens.dequeue().equals("PROGRAM"),
                "Error: Keyword \"PROGRAM\" expected");
        this.setName(tokens.dequeue());
        Reporter.assertElseFatalError(tokens.dequeue().equals("IS"),
                "Error: Keyword \"IS\" expected");

        Statement stm = this.newBody();
        Map<String, Statement> context = this.newContext();

        while (tokens.front().equals("INSTRUCTION")) {
            String instructionName = parseInstruction(tokens, stm);

            // report error
            Reporter.assertElseFatalError(!context.hasKey(instructionName),
                    "Error: Here already defined the instruction");

            context.add(instructionName, stm);
            stm = this.newBody();
        }
        this.swapContext(context);

        Reporter.assertElseFatalError(tokens.dequeue().equals("BEGIN"),
                "Error: Keyword \"BEGIN\" expected");

        // set the body of the program
        stm.parseBlock(tokens);
        this.swapBody(stm);

        // report error
        Reporter.assertElseFatalError(tokens.dequeue().equals("END"),
                "Error: Keyword \"END\" expected");
        Reporter.assertElseFatalError(tokens.dequeue().equals(this.name()),
                "Error: Beging instruction name matches end name");
        Reporter.assertElseFatalError(tokens.length() == 1,
                "Error: Quuires code after last line of the progeam");
    }

    /*
     * Main test method -------------------------------------------------------
     */

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();
        /*
         * Get input file name
         */
        out.print("Enter valid BL program file name: ");
        String fileName = in.nextLine();
        /*
         * Parse input file
         */
        out.println("*** Parsing input file ***");
        Program p = new Program1Parse1();
        SimpleReader file = new SimpleReader1L(fileName);
        Queue<String> tokens = Tokenizer.tokens(file);
        file.close();
        p.parse(tokens);
        /*
         * Pretty print the program
         */
        out.println("*** Pretty print of parsed program ***");
        p.prettyPrint(out);

        in.close();
        out.close();
    }

}
