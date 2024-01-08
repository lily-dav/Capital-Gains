import java.io.IOException;

/* 
   * Desc:
   *        This program contains three methods that, as a unit, complete a partial 
   * implementation of an array-based stack and use that stack to compute capital gains
   * on stock transactions.
   */

public class CapitalGains{
    //Stack storing all of the shares of a particular stock stored in a text file. 
    public ArrayStack<Trades> newArrayStack = new ArrayStack<>();

    //Array of doubles storing the price data read in the readFile() method.
    public double[] prices = new double[2923];
    
    //Probing hashtable that hold all of the price data for all of the stocks read in the readFile() method;
    public ProbeHTInc<String, double[]> htOfCompanies = new ProbeHTInc<>();

    /**
     * The first method, readFile(), reads the file(s), that contains the price data, line by line,
   * only storing the fourth element of the file, which is the closing price of a particular stock (A, B, or C) on a particular
   * date, in an array of doubles.
     * @param name
     */

    public void readFile(String name) {
        ReadCSV readFile;
        try {
            //Stores the text file in ReadCSV readFile.
            readFile = new ReadCSV(name);
        }
        catch (IOException ioe) {
            System.err.println("Ending. Cannot read. " + ioe.toString());
           return;
        }

        try {
            //Reads the first line of the file without doing anything with it because it does not contain price data.
            readFile.getLine();
            prices = new double[2923];
            int count = 0;
            //Reads the file while there are still lines of the file to read
            while (readFile.hasNext()) {
                //Stores the lines of the file.
                String[] lines = readFile.getLine();
                //Stores the fourth element of each line, which represents the relevant price data of the file, and stores in it in an array of doubles.
                prices[count] = Double.parseDouble(lines[4]);
                count++;
            }
        } catch (IOException ioe) {
            System.err.println("Problem while reading file " + ioe);
        }
    } 

    /**
     * The second method, readTrades(), reads the files that contain the number of shares
   * purchased and sold on a particular date. The date is given as a line number that corresponds to a line number in the
   * price data file for the specified stock. This method also calculates capital gains/losses for the sales of shares of each
   * stock.
     * @param name
     */

    public void readTrades(String name) {
        ReadCSV readTrades;
        try {
            //Stores the text file in ReadCSV readTrades.
            readTrades = new ReadCSV(name);
        }
        catch (IOException ioe) {
            System.err.println("Ending. Cannot read. " + ioe.toString());
            return;
        }

        try {
            double totalCapGains = 0;
            //Reads the file while there are still lines of the file to read
            while(readTrades.hasNext()) {
                //Stores the lines of the file.
                String[] purchases = readTrades.getLine();

                //If a purchase, push information onto a stack. If the number of shares is greater than zero, then it is a purchase.
                if (Integer.parseInt(purchases[1]) > 0) {
                    //Creating an object of Trades.java node class to call certain elements of the array storing trades file data.
                    Trades trades = new Trades(purchases[0], Integer.parseInt(purchases[1]), Integer.parseInt(purchases[2]));
                    newArrayStack.push(trades);
                }
                
                //If a sale, compute gains, popping the stack as needed to do so. If the number of shares is less than zero,then it is a sale.
                else if (Integer.parseInt(purchases[1]) < 0) {
                    int sales = Math.abs(Integer.parseInt(purchases[1]));
                    double capGains = 0;
                    //Compute while there are transactions
                    while (sales != 0) {
                        //Creates an object of the array we pop out of the stack using the node class so that we can call
                        //certain elements of that array later.
                        Trades poppedArray = newArrayStack.pop();
                        //The number of shares in the popped array
                        int shares = poppedArray.numberOfShares;

                        //If the number of shares that we want to sell is less than or equal to the number of shares that 
                        //we have, compute capital gains and push the popped array back into the stack.
                        if (sales <= shares) {
                            //Calculate capital gains by multiplying the number of shares we want to sell by the difference between the sale price
                            //and the purchase price
                            capGains = (double)sales * (prices[Integer.parseInt(purchases[2]) - 1] - prices[poppedArray.date - 1]);
                            //Push the popped array back into the stack.
                            newArrayStack.push(poppedArray); 
                            //Decrement the number of shares by the number of sales.
                            poppedArray.numberOfShares -= sales;
                            sales = 0;
                        }

                        //If the number of shares that we want to sell is greater than the number of shares that we have, 
                        //compute capital gains and push the popped array back into the stack.
                        else if (sales > shares) {
                            //Decrement the number of sales by the number of shares that we have. The item on the top of the purchase stack is exhausted
                            //and so removed from the stack.
                            sales -= shares;
                            //Calculate capital gains by multiplying the number of shares we want to sell by the difference 
                            //between the sale price and the purchase price
                            capGains = (double)shares * (prices[Integer.parseInt(purchases[2]) - 1] - prices[poppedArray.date - 1]);
                        }
                        //Add all of the calculated capital gains together to get the total for a stock
                        totalCapGains += capGains;
                    }
                    System.out.println("Company " + purchases[0] + ": " + purchases[2] + " SOLD " + 
                            Math.abs(Integer.parseInt(purchases[1])) + " net " + capGains);
                }
            }
            System.out.println("Total net Capital Gains = " + totalCapGains);
        } 
        catch (IOException ioe) {
            System.err.println("Problem while reading file " + ioe);
        }
    }

    /**
     * The third method is the main method reads price data files for various stocks using the readFile() method 
   * and stores all of the stocks and their price data in a probing hashtable so that the program can scale up easily
   * to handle 1000 (or more) companies. Then this method reads the trades files by calling the readTrades() method, which
   * uses the price data stored in the prices instance variable.
     * @param args
     */
    public static void main(String[] args) {
        CapitalGains cg = new CapitalGains();

        cg.readFile("");
        //Put the name and price of stock A in the probing hashtable
        cg.htOfCompanies.put("A", cg.prices);

        cg.readFile("");
        //Put the name and price of stock B in the probing hashtable
        cg.htOfCompanies.put("B", cg.prices);

        cg.readFile("");
        //Put the name and price of stock C in the probing hashtable
        cg.htOfCompanies.put("C", cg.prices);
        
        cg.readTrades("");
    }
}