package application;

import java.io.File;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;

/**
 * Homework 8: Main
 * @version Homework 8: Opening and Closing Symbols
 * @author Dr. T
 * @modified Matthew M
 *
 */
public class Main extends Application implements EventHandler<ActionEvent>
{

	/** The left text area */
	private TextArea leftTA;

	/** The right text area */
	private TextArea rightTA;

	/** The open button */
	private Button openB;

	/** The reset button */
	private Button resetB;

	/** A pointer to the symbol parser class */
	private SymbolParser symbolParser;

	/** The main stage */
	private Stage mainStage;


	@Override
	public void start(Stage primaryStage) {
		try {

			mainStage = primaryStage;
			symbolParser = new SymbolParser();
			GridPane root = new GridPane();
			Scene scene = new Scene(root,600,600);
			primaryStage.setTitle("HW 8: Parsing for Matching Opening and Closing Symbols");
			

			root.setPrefSize(500,  500);
			
			// Adding the left text area
			leftTA = new TextArea();
			leftTA.setPrefSize(400,  600);
			ScrollPane leftSP = new ScrollPane(leftTA);
			root.add(leftSP,  0,  0);
			
			// Adding the left text area
			rightTA = new TextArea();
			rightTA.setPrefSize(400,  600);
			ScrollPane rightSP = new ScrollPane(rightTA);
			root.add(rightSP,  1,  0);			
			// Adding menu options
			openB = new Button("Open");
			openB.setOnAction(this);
			root.add(openB,  0, 1);
			resetB = new Button("Reset");
			resetB.setOnAction(this);
			root.add(resetB,  1,  1);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	public void handle(ActionEvent event) {
		if (event.getSource() == openB)
		{
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Open file");
			File selectedFile = fileChooser.showOpenDialog(mainStage);
			if (selectedFile != null) {
				symbolParser.parseFile(selectedFile);
				leftTA.setText(symbolParser.getOriginalString());
				rightTA.setText(symbolParser.getSymbolMatchingString());
			}
		}
		else
		{
			//Reset the symbol parser object
			symbolParser.reset();
			
			//Reset the two text areas
			leftTA.clear();
			rightTA.clear();
			
		}

	}
	public static void main(String[] args) {
		launch(args);
	}
}