package application;

/**
 * Homework 8: Symbol Class
 * @author Matthew M
 * @version Homework 08: Opening and Closing Symbols
 *
 */
public class Symbol {
	
	/**Instance variable containing the symbol char stored in this object*/
	private char symbol;
	
	/**Instance variable containing the line location of the symbol stored in the object*/
	private int line;
	
	/**
	 * Parametered constructor takes in the values of the symbol upon creation of the object
	 * @param newsymbol The symbol to be contained within this object
	 * @param newline The line that the symbol lies within
	 */
	public Symbol(char newsymbol, int newline) {
		setSymbol(newsymbol);
		setLine(newline);
	}
	
	/**
	 * Method to set the symbol contained within this symbol object
	 * @param newsymbol The new symbol to be contained within this object
	 */
	public void setSymbol(char newsymbol) {
		symbol = newsymbol;
	}
	
	/**
	 * Method to set the line where the symbol can be found
	 * @param newline The new line where the symbol is found
	 */
	public void setLine(int newline) {
		line = newline;
	}
	
	/**
	 * Method to return the symbol stored within this object
	 * @return The symbol contained within this Symbol object
	 */
	public char getSymbol() {
		return symbol;
	}
	
	/**
	 * Method to return the line on which this object's symbol can be found
	 * @return The line in which this object's symbol is found
	 */
	public int getLine() {
		return line;
	}
	
	/**
	 * toString Method Override
	 */
	public String toString() {
		String s = "";
		s = s + symbol + " on line " + line;
		return s;
	}
	
	

}
