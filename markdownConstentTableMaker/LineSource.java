package markdownConstentTableMaker;

import java.io.BufferedReader;
import java.io.IOException;

public class LineSource {
    final private BufferedReader reader;
    private String line;
    private boolean close;


    public LineSource(BufferedReader reader) throws IOException {
        this.reader = reader;
        nextLine();
    }

    public void nextLine() throws IOException {
        if (!close) {
            line = reader.readLine();
            if (line == null) {
                close=true;
            }
        }
    }

    public String getLine(){
        return line;
    }

    public boolean hasNextLine() {
        return !close;
    }
}
