import java.util.Comparator;

import components.map.Map;
import components.map.Map1L;
import components.queue.Queue;
import components.queue.Queue1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Simple Word Counting program (clear of Checkstyle and FindBugs warnings).
 *
 * @author Shafin Alam
 */
public final class WordCounter {

    /**
     * Default constructor--private to prevent instantiation.
     */
    private WordCounter() {
        // no code needed here
    }

    /**
     * This method should create and display the HTML page with the words and
     * their counts.
     *
     * @param file
     *            the HTML page being written to
     * @param outputFile
     *            the name of the output file
     * @param map
     *            the map of all the words and their counts
     * @param wordList
     *            A queue of all of the words
     */
    private static void outputHTMLPage(SimpleWriter file, String outputFile,
            Map<String, Integer> map, Queue<String> wordList) {
        file.println("<html><head><title>Word Counter</title></head><body>");
        file.println(
                "<style>table, th, td { border: 1px solid black;}</style><body>");
        file.println("<h2>Words Counted in " + outputFile + "</h2>");
        file.println("<table style= \" width: 25%\">");
        file.println("<tr><th>Words</th><th>Counts</th></tr>");
        wordList.dequeue();
        wordList.dequeue();
        for (int i = 0; i < map.size(); i++) {
            String word = wordList.front();
            int c = map.value(word);
            file.println("<tr><th>" + word + "</th><th>" + c + "</th></tr>");
            wordList.rotate(1);
        }
        file.println("</body></table></html>");
    }

    /**
     * This is method is meant to get all of the words in a given file, and then
     * count how many times they appear in that file.
     *
     * @param in
     *            reads the file that the user input
     * @return returns the map of words and their counts
     */
    public static Map<String, Integer> table(SimpleReader in) {
        Map<String, Integer> wordTable = new Map1L<>();
        String word = "";
        while (!in.atEOS()) {
            word = in.nextLine();
            StringBuilder term = new StringBuilder("");
            for (int i = 0; i < word.length(); i++) {
                char letter = word.charAt(i);
                term.append(word.charAt(i));
                if (letter == ' ' || letter == ',' || i == word.length() - 1) {
                    String termToWord = term.toString();
                    termToWord.replaceAll("[a-zA-Z0-9]", "").toLowerCase();
                    if (!wordTable.hasKey(termToWord)) {
                        wordTable.add(termToWord, 1);

                    } else {

                        int c = wordTable.value(termToWord);
                        wordTable.remove(termToWord);
                        wordTable.add(termToWord, c + 1);
                    }
                    term.delete(0, term.length() - 1);
                }
            }
        }
        return wordTable;
    }

    /**
     * Comparator used to order the words in alphabetic order.
     *
     */
    private static class StringLT implements Comparator<String> {
        @Override
        public int compare(String s1, String s2) {
            return s1.compareTo(s2);
        }
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments; unused here
     */
    public static void main(String[] args) {
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();
        out.println("Enter an input file: ");
        String inputFileName = in.nextLine();
        out.println("Enter the name for an output file: ");
        String outputFileName = in.nextLine();
        SimpleReader file = new SimpleReader1L(inputFileName);
        SimpleWriter output = new SimpleWriter1L(outputFileName + ".html");
        Map<String, Integer> wordTable = table(file);

        Comparator<String> sort = new StringLT();
        Queue<String> wordList = new Queue1L<String>();
        Map<String, Integer> map = new Map1L<String, Integer>();
        map.transferFrom(wordTable);
        while (map.size() > 0) {
            Map.Pair<String, Integer> pair = map.removeAny();
            wordList.enqueue(pair.key());
            wordTable.add(pair.key(), pair.value());
        }
        wordList.sort(sort);
        outputHTMLPage(output, inputFileName, wordTable, wordList);
        file.close();
        in.close();
        out.close();
    }

}
