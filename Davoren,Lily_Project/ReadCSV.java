import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;

/**
 * A class that knows how to read CSV files. 
 * Note that this class does not know
 * how to read general csv files, 
 * but only very simple ones.
 * 
 * ALSO, this class strongly assumes that the file (or URL) is actually a
 * CSV file. It will happily read ANY file and try to parse it as a CSV
 * without raising any alarms about the data being strange if it is 
 * not actually in CSV format.
 */
public class ReadCSV {

    /**
     * Example of the use of the class
     * @param args
     */
    public static void main(String[] args) {
        ReadCSV ec;
        try {
            // get a new instance of the class -- through a static maker.
            //ec = new ReadCSV("code.csv");
            ec = new ReadCSV("", 4);
        }
        catch (IOException ioe) {
            System.err.println("Ending. Cannot read. " + ioe.toString());
            return;
        }
        try {
            int lineCount = 0;
            int wc = 0;
            // read the file line by line
            while (ec.hasNext()) {
                lineCount++;
                if (lineCount > 100)
                    break;
                System.out.print(lineCount++ + "  ");
                String[] ss = ec.getLine();
                for (String s : ss) {
                    System.out.print(s + " ");
                }
                wc += ss.length;
                System.out.println();
            }
            System.out.println(lineCount + " " + wc);
        } catch (IOException ioe) {
            System.err.println("Problem while reading file " + ioe);
        }
    }

    // The things to read from
    private BufferedReader reader;

    // The maximum number of items allowed in a CSV Line
    private int maxSplit = 25;

    // the next line of the file being read, if that line exists and has not been used
    private String temp;

/**
 * Setup to read a CSV file.
 * @param either a file name or a URL (as a string).  The sourse of the CSV data.
 * @throws IOException
 */
public ReadCSV(String name) throws IOException {
        this(name, 25);
    }


/**
 * Setup to read a CSV file.
 * @param either a file name or a URL (as a string).  The sourse of the CSV data.
 * @param maxSplit the max number of items in a single CSV line.  If the line would slipt into more than this, then the final item will have the excess, including commas
 * @throws FileNotFoundException
 * @throws IOException
 */
    public ReadCSV(String name, int maxSplit) throws IOException {
        URL uu;
        if (name.contains("://")) {
            uu = new URL(name);
        } else {
            // assume this working with a file name
            File file = new File(name);
            URI uri = file.toURI();
            uu = uri.toURL();
        }
        reader = new BufferedReader(new InputStreamReader(uu.openStream()));
        this.maxSplit = maxSplit;
    }

    /**
     * Return true iff there is more to read
     * @return true iff there is more to read, false otherwise
     * @throws IOException
     */
    public boolean hasNext() throws IOException {
        if (temp == null) {
            temp = reader.readLine();
        }
        return temp != null;
    }

    /**
     * get the next line from the file and split it by commas.  
     * @return the line, split at each comma of the the return
     * is an array of strings.
     * @throws IOException
     */
    public String[] getLine() throws IOException {
        if (!hasNext()) {
            return null;
        }
        String s = temp;
        temp = null;
        return s.split(",", maxSplit);
    }

}