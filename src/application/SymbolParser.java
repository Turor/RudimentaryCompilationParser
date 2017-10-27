package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

/**
 * Homework 9: SymbolParser Class
 * @author Dr. T
 * @modified Matthew M
 * @version Homework 9: Opening and Closing Symbols
 *
 */
public class SymbolParser {

	/** This string gets built as the file is read in */
	private String originalText;

	/** This string gets built with all matching pairs */
	private String symbolMatchingText;
	
	/**Instance variable which determines whether or not the parser is in a block comment*/
	private boolean inBlockComment;
	
	/**Instance variable which controls whether or not the parser is in a line comment*/
	private boolean inLineComment;
	
	/**Instance variable which controls whether or not the parser is in a string*/
	private boolean inString;

	/** Constructor*/
	public SymbolParser(){
		originalText = "";
		symbolMatchingText = "";
		inBlockComment = false;
		inLineComment = false;
		inString = false;
	}

	/** This method gets called when a new file is opened 
	 * @param inputFile The file that has to be parsed
	 */
	public void parseFile(File inputFile){
		Scanner input;
		inBlockComment = false;
		inLineComment = false;
		inString = false;
		try {
			input = new Scanner(inputFile);
			String symbols = "";
			
			//I stand by this decision
			ArrayList<Integer> lines = new ArrayList<Integer>();

			
			// Reading in one line at a time
			int lineNumber = 1;
			while (input.hasNextLine()){

				// Read in a whole line
				String line = input.nextLine();
				
				//Reset the inLineComment state since this is a new line
				inLineComment = false;
				
				//Adds the line number to the beginning of each new line
				originalText += lineNumber + ": ";
				
				// Iterate a character at a time
				for (int i = 0; i < line.length(); i++){
					originalText = originalText + line.charAt(i); 
					
					if(inBlockComment||inLineComment||inString) {
						//Exit conditions for blocks and strings
						//Block -> String
						if(i > 0 && line.charAt(i-1) == '*' && line.charAt(i) == '/')
							inBlockComment = false;
						else if(line.charAt(i) == '"' )
							if(i > 0 && line.charAt(i-1) != 92)
								inString = false;
							else if( i == 0)
								inString = false;
					}else {
						//Entrance conditions for block and line comments, and strings.
						//Block -> Line -> String
						if(i > 0 && line.charAt(i-1) == '/' && line.charAt(i) == '*')
							inBlockComment = true;
						else if(i > 0 && line.charAt(i) == '/' && line.charAt(i-1) == '/')
							inLineComment = true;
						else if(line.charAt(i) == '"')
							inString = true;
					}
					
					//Updates the symbol string and the lines arrayList
					if(isSymbol(line.charAt(i))&&!inBlockComment&&!inLineComment&&!inString) {
						symbols = symbols + line.charAt(i);
						lines.add(lineNumber);
					}
				}

				
				originalText += "\n";
				//Increases the line number because now the loop is moving to the next line
				lineNumber++;
			}
			buildSymbolText(symbols, lines);
			

		}
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Returns the string to display in the left text area
	 * @return The string to be printed
	 */
	public String getOriginalString(){
		return originalText;

	}

	/**
	 * Returns the string to display in the rights text area
	 * @return The string to be printed
	 */
	public String getSymbolMatchingString(){
		return symbolMatchingText;

	}

	/**
	 * Method to reset the symbol parser
	 */
	public void reset() {
		originalText = "";
		symbolMatchingText = "";
	}

	/**
	 * Method to build the symbol matching text from a string of symbols and an array list containing the
	 * location of the corresponding symbols
	 * @param symbols A string containing the concatenated symbols
	 * @param lines An arrayList storing all the line data of the symbols
	 */
	private void buildSymbolText(String symbols, ArrayList<Integer> lines) {

		Stack<Symbol> yoloStack = new Stack<Symbol>();
		boolean broken = false;
		for(int i = 0; i < symbols.length();i++) {
			char symbol = symbols.charAt(i);
			int line = lines.get(i);

			if(symbol == '{'||symbol=='('||symbol =='[')
				yoloStack.push(new Symbol(symbol,line));
			else if(yoloStack.isEmpty()){
				symbolMatchingText = symbolMatchingText + "Parse has encountered a closing symbol without\n"
						+"any opening symbol present.";
			}else if(matches(symbol,yoloStack.peek().getSymbol())) {
				symbolMatchingText = symbolMatchingText + yoloStack.pop().toString() + " matches with "
						+ symbol + " on line " + line + "\n";
			}else {
				symbolMatchingText = symbolMatchingText + yoloStack.pop().toString() + " does not match "
						+ symbol + " on line " + line + "\n";
				broken = true;
				break;

			}
		}


		while(!yoloStack.isEmpty() && !broken) {
			symbolMatchingText = symbolMatchingText + "Parser has reached the end of file without\nfinding"
					+ " the closing symbol to match\n" + yoloStack.peek().getSymbol() + " on line "
					+ yoloStack.pop().getLine() + "\n";
		}


	}

	/**
	 * Method to return whether or not the symbol matches with the opening symbol
	 * @param closingSymbol The closing symbol encountered
	 * @param openingSymbol The opening symbol that is on top of the stack
	 * @return
	 */
	private boolean matches(char closingSymbol, char openingSymbol) {

		if(closingSymbol == openingSymbol+1) 		//Deal with parentheticals first
			return true;
		else if(closingSymbol == openingSymbol+2) 	//Now braces and brackets
			return true;
		else										//Now anything else.
			return false;

		//I feel better now. It's far more self contained now
	}

	/**
	 * Method to determine if the given char is actually one of symbols
	 * @param symbol The symbol to be checked
	 * @return True if the char is one of the opening or closing symbols
	 */
	private boolean isSymbol(char symbol) {
		if(symbol=='{'||symbol=='['||symbol=='('||symbol=='}'||symbol==']'||symbol==')')
			return true;
		else
			return false;
	}



}
