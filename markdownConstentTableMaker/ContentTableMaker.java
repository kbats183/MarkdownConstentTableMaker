package markdownConstentTableMaker;

import java.io.*;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;

public class ContentTableMaker {
    private final ArrayList<String> fileCopy;
    private final ArrayList<String> tableOfContent;

    public ContentTableMaker(final Reader inputReader) throws IOException {
        final BufferedReader reader = new BufferedReader(inputReader);
        Deque<Integer> headerCounter = new ArrayDeque<>();
        fileCopy = new ArrayList<>();
        tableOfContent = new ArrayList<>();
        for (final LineSource lineSource = new LineSource(reader); lineSource.hasNextLine(); lineSource.nextLine()) {
            fileCopy.add(lineSource.getLine());
            final MarkdownHeader header = MarkdownParser.parseHeader(lineSource.getLine());
            if (header != null) {
                while (headerCounter.size() < header.getLevel()) {
                    headerCounter.addLast(0);
                }
                while (headerCounter.size() > header.getLevel()) {
                    headerCounter.removeLast();
                }
                headerCounter.addLast(headerCounter.removeLast() + 1);

                final String headerAnchor = "[" + header.getText() + "](#" + stringToLinksAnchor(header.getText()) + ")";
                tableOfContent.add(String.format("%s%d. %s",
                        "\t".repeat(headerCounter.size() - 1), headerCounter.getLast(), headerAnchor));
            }
        }
    }

    public void print(PrintStream outputStream) {
        for (final String line : tableOfContent) {
            outputStream.println(line);
        }
        outputStream.println();
        for (final String line : fileCopy) {
            outputStream.println(line);
        }
    }

    private static String stringToLinksAnchor(final String input) {
        StringBuilder output = new StringBuilder();
        for (int pos = 0; pos < input.length(); pos++) {
            final char ch = input.charAt(pos);
            if (Character.isAlphabetic(ch) || Character.isDigit(ch)) {
                output.append(Character.toLowerCase(ch));
            } else {
                output.append("-");
            }
        }
        return output.toString();
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            System.err.println("Add path for .md file in program arguments");
            return;
        }

        final String inputFileName = args[0];

        try (final Reader reader = new FileReader(inputFileName)) {
            new ContentTableMaker(reader).print(System.out);
        } catch (FileNotFoundException e) {
            System.err.println("Input file " + inputFileName + " not found");
        } catch (IOException exception) {
            System.err.println(exception.getMessage());
        }
    }
}
