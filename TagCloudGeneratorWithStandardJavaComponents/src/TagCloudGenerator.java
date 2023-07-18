import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

/**
 * Program that turns a user input txt file into a tag cloud based on user input
 * number with standard Java components.
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
    private static Comparator<Map.Entry<String, Integer>> COUNT = new Comparator<Map.Entry<String, Integer>>() {
        @Override
        public int compare(Map.Entry<String, Integer> p1,
                Map.Entry<String, Integer> p2) {
            return p2.getValue() - p1.getValue();
        }
    };

    /**
     * Compare {@code Map.Pair}s in alphabetical order.
     */
    private static Comparator<Map.Entry<String, Integer>> ALPHABET = new Comparator<Map.Entry<String, Integer>>() {
        @Override
        public int compare(Map.Entry<String, Integer> p1,
                Map.Entry<String, Integer> p2) {
            return p1.getKey().toLowerCase()
                    .compareTo(p2.getKey().toLowerCase());
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
     * @param inFile
     *            the input file.
     * @param m
     *            map containing all the words and their counts.
     * @updates m
     */
    private static void wordCount(BufferedReader inFile,
            Map<String, Integer> m) {
        assert inFile != null : "Violation of: inFile is not null";
        try {
            assert inFile.ready() : "Violation of: inFile is not open";
        } catch (IOException e) {
            System.err.println("Error inFile cannot open.");
        }

        try {
            String line = null;
            while ((line = inFile.readLine()) != null) {
                int pos = 0;
                while (pos < line.length()) {
                    String word = nextWordOrSeparator(line, pos).toLowerCase();
                    if (!SEPARATORS.contains(String.valueOf(word.charAt(0)))) {
                        if (m.containsKey(word)) {
                            m.replace(word, m.get(word) + 1);
                        } else {
                            m.put(word, 1);
                        }
                    }
                    pos += word.length();
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading from file.");
            return;
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
    private static void createHeader(PrintWriter fileOut, String fileName,
            int size) {
        assert fileOut != null : "Violation of: out is not null";
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
    private static void createBody(PrintWriter fileOut,
            Map.Entry<String, Integer> pair) {
        assert fileOut != null : "Violation of: out is not null";

        fileOut.print("<span style=\"cursor:default\" class=\"f");
        fileOut.print(fontSize(pair.getValue()));
        fileOut.println("\" title=\"count: " + pair.getValue() + "\">"
                + pair.getKey() + "</span>");
    }

    /**
     * Output the "closing" tags in the generated HTML file.
     *
     * @param fileOut
     *            the output stream
     */
    private static void createFooter(PrintWriter fileOut) {
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
        Map<String, Integer> words = new TreeMap<String, Integer>();

        /**
         * No need to check for the exception here, since closing a PrintWriter
         * will not occur a sign.
         */
        Scanner input = new Scanner(System.in);

        /**
         * Enter the name of input file.
         */
        System.out.print("Please enter the name of the input file: ");
        String inputName = input.nextLine();

        /**
         * Enter the name of output file.
         */
        System.out.print("Please enter the name of the output file: ");
        String outputName = input.nextLine();
        //add extension, only if not present
        if (!outputName.contains(".html")) {
            outputName = outputName + ".html";
        }

        /**
         * Enter the size of cloud.
         */
        System.out.print(
                "Please enter the size of the words to generate cloud: ");
        int size = input.nextInt();

        /*
         * Size cannot be less or equal to 0
         */
        while (size < 0) {
            System.out.print("Please enter a positive value: ");
            size = input.nextInt();
        }

        /**
         * No need to check for the exception here, since closing a PrintWriter
         * will not occur a sign.
         */
        input.close();

        BufferedReader in;
        try {
            in = new BufferedReader(new FileReader(inputName));
        } catch (IOException e) {
            System.err.println("Error opening the BufferedReader.");
            return;
        }

        /*
         * Input file then count words.
         */
        wordCount(in, words);

        try {
            in.close();
        } catch (IOException e) {
            System.err.println("Error closing BufferedReader.");
        }

        /**
         * Since we can use sort(comparator) in ArrayList, it will be a good
         * choice for me. In this way, we can easily add and get the value.
         */
        ArrayList<Map.Entry<String, Integer>> count = new ArrayList<>();
        ArrayList<Map.Entry<String, Integer>> alphabet = new ArrayList<>();

        /*
         * Sort the map by word count.
         */
        /**
         * Initialize a variable numWord to record the exact words in the file
         */
        int numWord = 0;
        for (Map.Entry<String, Integer> word : words.entrySet()) {
            count.add(word);
            numWord++;
        }

        /**
         * Since we define the ArrayList, we need to sort the comparator COUNT
         * here
         */
        count.sort(COUNT);
        words.clear();
        if (size > numWord) {
            for (int i = 0; i < numWord; i++) {
                Map.Entry<String, Integer> pair = count.get(i);
                words.put(pair.getKey(), pair.getValue());
                if (i == 0) {
                    maxTimes = pair.getValue();
                }
                if (i == size - 1) {
                    minTimes = pair.getValue();
                }
            }
        } else {
            for (int i = 0; i < size; i++) {
                Map.Entry<String, Integer> pair = count.get(i);
                words.put(pair.getKey(), pair.getValue());
                if (i == 0) {
                    maxTimes = pair.getValue();
                }
                if (i == size - 1) {
                    minTimes = pair.getValue();
                }
            }
        }

        /*
         * Alphabetical order.
         */
        for (Map.Entry<String, Integer> word : words.entrySet()) {
            alphabet.add(word);
        }

        /**
         * Since we define the ArrayList, we have to sort the comparator
         * ALPHABET here
         */
        alphabet.sort(ALPHABET);

        /**
         * Using the PrintWriter, we need to check the exception now.
         */
        PrintWriter outFile;
        try {
            outFile = new PrintWriter(
                    new BufferedWriter(new FileWriter(outputName)));
        } catch (IOException e) {
            System.err.println("Error opening PrintWriter.");
            return;
        }

        /*
         * Output file.
         */
        createHeader(outFile, inputName, size);
        if (size > numWord) {
            for (int i = 0; i < numWord; i++) {
                Map.Entry<String, Integer> pair = alphabet.get(i);
                createBody(outFile, pair);
            }
        } else {
            for (int i = 0; i < size; i++) {
                Map.Entry<String, Integer> pair = alphabet.get(i);
                createBody(outFile, pair);
            }
        }
        createFooter(outFile);

        /**
         * No need to check for the exception here, since closing a PrintWriter
         * will not occur a sign.
         */
        outFile.close();
    }

}