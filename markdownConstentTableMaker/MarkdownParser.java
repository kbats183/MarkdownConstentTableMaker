package markdownConstentTableMaker;

public class MarkdownParser {
    private static final char headerSymbol = '#';

    public static MarkdownHeader parseHeader(final String input) {
        int currentPosition = skipWS(input, 0);

        int headerLevel = 0;
        while (currentPosition < input.length() && input.charAt(currentPosition) == headerSymbol) {
            currentPosition++;
            headerLevel++;
        }

        currentPosition = skipWS(input, currentPosition);
        if (currentPosition == input.length() || headerLevel > 6 || headerLevel == 0) {
            return null;
        }

        return new MarkdownHeader(headerLevel, input.substring(currentPosition));
    }


    private static int skipWS(final String input, int position) {
        while (position < input.length() && Character.isWhitespace(input.charAt(position))){
            position++;
        }
        return position;
    }
}
