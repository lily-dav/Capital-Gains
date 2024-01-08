/* 
   * Desc:
   *        This is a node class that contains and defines the individual elements of the lines of the trades file
   * that are stored as their own individual arrays.

   */
public class Trades {
    public String stockName;

    public int numberOfShares;

    public int date;

    public Trades(String stName, int numShares, int dates) {
        this.stockName = stName;
        this.numberOfShares = numShares;
        this.date = dates;
    }

    @Override
    public String toString() {
        return stockName + " ," + numberOfShares + " ," + date;
    }
}
