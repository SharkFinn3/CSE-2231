
import java.util.Comparator;

import components.map.Map;
import components.map.Map1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.sortingmachine.SortingMachine;
import components.sortingmachine.SortingMachine1L;

/**
 * Program that turns a user input txt file into a tag cloud based on user input
 * number.
 *
 * @author Shafin Alam & Yanqing Xu
 *
 */
public final class TagCloudGenerator {

    /**
     * The maximum times a word may occur.
     */
    private static int maxTimes = Integer.MAX_VALUE;

    /**
     * The minimum times a word may occur.
     */
    private static int minTimes = Integer.MIN_VALUE;

    /**
     * The Maximum font size allowed.
     */
    private static final int MAX_FONT_SIZE = 48;

    /**
     * The Minimum font size allowed.
     */
    private static final int MIN_FONT_SIZE = 11;

    /**
     * The separators.
     */
    private static final String SEPARATORS = " \t\n\r,-.!?[]';:/()~!@#$%^&*()`_+=\\|\""
            + "<>{}123456789";

    /**
     * Compare {@code Map.Pair}s in order of count frequency.
     */
    private static Comparator<Map.Pair<String, Integer>> COUNT = new Comparator<Map.Pair<String, Integer>>() {
        @Override
        public int compare(Map.Pair<String, Integer> p1,
                Map.Pair<String, Integer> p2) {
            return p2.value() - p1.value();
        }
    };

    /**
     * Compare {@code Map.Pair}s in alphabetical order.
     */
    private static Comparator<Map.Pair<String, Integer>> ALPHABET = new Comparator<Map.Pair<String, Integer>>() {
        @Override
        public int compare(Map.Pair<String, Integer> p1,
                Map.Pair<String, Integer> p2) {
            return p1.key().toLowerCase().compareTo(p2.key().toLowerCase());
        }
    };

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private TagCloudGenerator() {
    }

    /**
     * Calculate the font size for a word.
     *
     * @param times
     *            the times of the word to be sized.
     * @return Font size of the word.
     */
    private static int fontSize(int times) {
        int range = MAX_FONT_SIZE - MIN_FONT_SIZE;
        int size = 0;
        if (times == minTimes) {
            size = MIN_FONT_SIZE;
        } else if (times == maxTimes) {
            size = MAX_FONT_SIZE;
        } else {
            double ratio = 1.0 * (times - minTimes) / (maxTimes - minTimes);
            size = (int) (ratio * range + MIN_FONT_SIZE);
        }
        return size;
    }

    /**
     * Return the first "word" (maximal length string of characters not in
     * {@code separators}) or "separator string" (maximal length string of
     * characters in {@code separators}) in the given {@code text} starting at
     * the given {@code position}.
     *
     * @param text
     *            the {@code String} from which to get the word or separator
     *            string
     * @param position
     *            the starting index
     * @return the first word or separator string found in {@code text} starting
     *         at index {@code position}
     * @requires 0 <= position < |text|
     * @ensures <pre>
     * nextWordOrSeparator =
     *   text[position, position + |nextWordOrSeparator|)  and
     * if entries(text[position, position + 1)) intersection separators = {}
     * then
     *   entries(nextWordOrSeparator) intersection separators = {}  and
     *   (position + |nextWordOrSeparator| = |text|  or
     *    entries(text[position, position + |nextWordOrSeparator| + 1))
     *      intersection separators /= {})
     * else
     *   entries(nextWordOrSeparator) is subset of separators  and
     *   (position + |nextWordOrSeparator| = |text|  or
     *    entries(text[position, position + |nextWordOrSeparator| + 1))
     *      is not subset of separators)
     * </pre>
     */

    private static String nextWordOrSeparator(String text, int position) {
        assert text != null : "Violation of: text is not null";
        assert 0 <= position : "Violation of: 0 <= position";
        assert position < text.length() : "Violation of: position < |text|";

        int count = 0;
        char tempChar;
        String result = "";
        if (SEPARATORS.contains(String.valueOf(text.charAt(position)))) {
            while (count < text.substring(position, text.length()).length()) {
                tempChar = text.charAt(position + count);
                if (SEPARATORS.contains(String.valueOf(tempChar))) {
                    result = result.concat(String.valueOf(tempChar));
                    count++;
                } else {
                    count = text.substring(position, text.length()).length();
                }
            }
            count = 0;
        } else {
            while (count < text.substring(position, text.length()).length()) {
                tempChar = text.charAt(position + count);
                if (!SEPARATORS.contains(String.valueOf(tempChar))) {
                    result = result.concat(String.valueOf(tempChar));
                    count++;
                } else {
                    count = text.substring(position, text.length()).length();
                }
            }
            count = 0;
        }
        return result;
    }

    /**
     * Count the times of appearance of each word from the input file.
     *
     * @param file
     *            the input file.
     * @param m
     *            map containing all the words and their counts.
     * @updates m
     */
    private static void wordCount(SimpleReader file, Map<String, Integer> m) {
        while (!file.atEOS()) {
            String line = file.nextLine();
            line = line.toLowerCase();
            int i = 0;
            while (i < line.length()) {
                String words = nextWordOrSeparator(line, i).toLowerCase();
                if (!SEPARATORS.contains(String.valueOf(words.charAt(0)))) {
                    if (m.hasKey(words)) {
                        int count = m.value(words);
                        count = count + 1;
                        m.replaceValue(words, count);
                    } else {
                        m.add(words, 1);
                    }
                }
                i += words.length();
            }
        }
    }

    /**
     * Output the "opening" tags in the generated HTML file.
     *
     * @param fileOut
     *            the output stream
     * @param fileName
     *            the file name of the input text file
     * @param size
     *            the size of cloud entered by user
     */
    private static void createHeader(SimpleWriter fileOut, String fileName,
            int size) {
        assert fileOut != null : "Violation of: out is not null";
        assert fileOut.isOpen() : "Violation of: out.is_open";

        fileOut.println("<html>");
        fileOut.println("<head>");
        fileOut.print("<title>");
        fileOut.print("Top " + size + " words in " + fileName);
        fileOut.println("</title>");
        fileOut.println(
                "<link href=\"http://web.cse.ohio-state.edu/software/2231/web-sw2/assignments/projects/tag-cloud-generator/data/tagcloud.css\" rel=\"stylesheet\" type=\"text/css\">");
        fileOut.print("</head>");
        fileOut.println("<body>");
        fileOut.print("<h2>Top " + size + " words in " + fileName + "</h2>");
        fileOut.println("<hr>");
        fileOut.println("<div class=\"cdiv\">");
        fileOut.println("<p class=\"cbox\">");

    }

    /**
     * Create the body with its size and count as one table row.
     *
     * @param fileOut
     *            the output stream
     * @param pair
     *            the pair containing the word string and the number of count
     */
    private static void createBody(SimpleWriter fileOut,
            Map.Pair<String, Integer> pair) {

        assert fileOut != null : "Violation of: out is not null";
        assert fileOut.isOpen() : "Violation of: out.is_open";

        fileOut.print("<span style=\"cursor:default\" class=\"f");
        fileOut.print(fontSize(pair.value()));
        fileOut.println("\" title=\"count: " + pair.value() + "\">" + pair.key()
                + "</span>");
    }

    /**
     * Output the "closing" tags in the generated HTML file.
     *
     * @param fileOut
     *            the output stream
     */
    private static void createFooter(SimpleWriter fileOut) {
        fileOut.println("\t</p>");
        fileOut.println("</div>");
        fileOut.println("</body>");
        fileOut.println("</html>");
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        /*
         * Put your main program code here
         */
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();
        Map<String, Integer> words = new Map1L<>();

        /*
         * Enter input, output file name Enter integer Size cannot be less or
         * equal to 0
         */
        out.println("Enter the name of an input file: ");
        String inputName = in.nextLine();

        out.println("Enter the name of an output file: ");
        String outputName = in.nextLine();
        if (!outputName.contains(".html")) {
            outputName = outputName + ".html";
        }
        SimpleWriter output = new SimpleWriter1L(outputName);
        out.println("Enter a positive integer N: ");
        int size = in.nextInteger();

        while (size < 0) {
            out.println("Please enter a positive integer N: ");
            size = in.nextInteger();
        }
        /*
         * Input file then count words.
         */
        SimpleReader file = new SimpleReader1L(inputName);
        wordCount(file, words);

        /*
         * Create two sorting machines to sort map.
         */
        SortingMachine<Map.Pair<String, Integer>> count = new SortingMachine1L(
                COUNT);
        SortingMachine<Map.Pair<String, Integer>> alphabet = new SortingMachine1L(
                ALPHABET);

        /*
         * Sort the map by word count. Size may be greater than the number of
         * words in the input file
         */
        int numWords = 0;
        for (Map.Pair<String, Integer> word : words) {
            count.add(word);
            numWords++;
        }
        words.clear();
        count.changeToExtractionMode();

        if (size > numWords) {
            for (int i = 0; i < numWords; i++) {
                Map.Pair<String, Integer> pair = count.removeFirst();
                words.add(pair.key(), pair.value());
                if (i == 0) {
                    maxTimes = pair.value();
                }
                if (i == size - 1) {
                    minTimes = pair.value();
                }
            }
        } else {
            for (int i = 0; i < size; i++) {
                Map.Pair<String, Integer> pair = count.removeFirst();
                words.add(pair.key(), pair.value());
                if (i == 0) {
                    maxTimes = pair.value();
                }
                if (i == size - 1) {
                    minTimes = pair.value();
                }
            }
        }

        /*
         * Alphabetical order.
         */
        for (Map.Pair<String, Integer> word : words) {
            alphabet.add(word);
        }

        /*
         * Output file.
         */
        createHeader(output, inputName, size);
        alphabet.changeToExtractionMode();
        for (int i = 0; i < size; i++) {
            Map.Pair<String, Integer> pair = alphabet.removeFirst();
            createBody(output, pair);
        }
        createFooter(output);

        /*
         * Close input and output streams
         */
        output.close();
        file.close();
        in.close();
        out.close();

    }

}
