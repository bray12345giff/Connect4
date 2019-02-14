/**
 * Provides the GUI if requested by the user.
 * 
 * @author Brayden Giffen
 * @version 2/10/19
 */
package Connect4;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Shape;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;
public class Connect4GUI extends Application {
	Connect4 game = new Connect4();
	Connect4ComputerPlayer play = new Connect4ComputerPlayer();
	private static final int TILE_SIZE = 80;
	private static final int COLUMNS = 7;
	private static final int ROWS = 6;
	private boolean redMove = true;
	private Disc[] [] grid=new Disc[COLUMNS][ROWS];
	private int how(){
		Alert how = new Alert(AlertType.CONFIRMATION);
		how.setTitle("Connect4");
		how.setHeaderText("Welcome to the Connect4 GUI!\nAre you playing against the computer or another player?");
		how.setContentText("Choose your option.");
		int how1 = 0;
		ButtonType buttonOne = new ButtonType("Computer");
		ButtonType buttonTwo = new ButtonType("Player");
		how.getButtonTypes().setAll(buttonOne, buttonTwo);
		Optional<ButtonType> result = how.showAndWait();
		if (result.get() == buttonOne){
			how1=1;
		} else if (result.get() == buttonTwo) {
		    how1=2;
		}
		return how1;
	}
	private Pane discRoot = new Pane();
	private Parent createContent() {
		Pane root = new Pane();
		root.getChildren().add(discRoot);
		Shape gridShape = makeGrid();
		root.getChildren().add(gridShape);
		root.getChildren().addAll(makeColumns());
		return root;
	}
	private Shape makeGrid() {
		Shape shape = new Rectangle((COLUMNS+1) * TILE_SIZE, (ROWS+1) * TILE_SIZE);
		for (int y = 0; y< ROWS; y++) {
			for (int x = 0; x < COLUMNS; x++) {
				Circle circle = new Circle(TILE_SIZE/2);
				circle.setCenterX(TILE_SIZE/2);
				circle.setCenterY(TILE_SIZE/2);
				circle.setTranslateX(x*(TILE_SIZE+5) + TILE_SIZE/4);
				circle.setTranslateY(y*(TILE_SIZE+5) + TILE_SIZE/4);
				
				shape = Shape.subtract(shape, circle);
			}
		}
		shape.setFill(Color.GREEN);
		return shape;
	}
	private List<Rectangle> makeColumns(){
		List <Rectangle> list = new ArrayList<>();
		int how1 = how();
		for (int x=0;x<COLUMNS; x++) {
			Rectangle rect = new Rectangle(TILE_SIZE, (ROWS+1)*TILE_SIZE);
			rect.setTranslateX(x*(TILE_SIZE+5)+TILE_SIZE/4);
			rect.setFill(Color.TRANSPARENT);
			rect.setOnMouseEntered(e -> rect.setFill(Color.rgb(200,200,50, 0.3)));
			rect.setOnMouseExited(e -> rect.setFill(Color.TRANSPARENT));
			final int column = x;
			rect.setOnMouseClicked(e -> placeDisc(new Disc(redMove), column, how1));
			list.add(rect);
		}
		return list;
	}
	private void computerplay() {
		int column = play.play();
		
		column--;
		System.out.print("The computer chose to play on column " + (column+1) + ".\n");
		placeDisc(new Disc(redMove), column, 0);
		redMove = !redMove;
	}
	private void placeDisc(Disc disc, int column, int how1) {
		int row = ROWS-1;
		do {
			if (!getDisc(column, row).isPresent()) {
				break;
			}
			row--;
		}while (row>=0);
		game.drop((redMove ? 'X' : 'O'), column+1);
		game.printBoard(); // remove this if you don't want the board to print along with the GUI
		if (row < 0) {
			return;
		}
		grid[column][row] = disc;
		discRoot.getChildren().add(disc);
		disc.setTranslateX(column*(TILE_SIZE+5)+TILE_SIZE/4);
		TranslateTransition animation = new TranslateTransition(Duration.seconds(0.5), disc);
		animation.setToY(row*(TILE_SIZE+5)+TILE_SIZE/4);
		animation.setOnFinished(e ->{
			if (how1 == 1) {
			redMove = !redMove;
			computerplay();
			if (game.checkWin() == true) {
				gameOver();
			}
		}
		if (how1 == 2) {
			if (game.checkWin() == true) {
				gameOver();
			}
			redMove = !redMove;
		}
		});
		animation.play();
	}
	private boolean gameOver() {
		System.out.print("Game Over! "+ (redMove ? "Red" : "Yellow") + " player won!");
		System.exit(1);
		return true;
	}
	private Optional<Disc> getDisc(int column, int row){
		if (column < 0 || column >= COLUMNS || row < 0 || row >= ROWS) {
			return Optional.empty();
		}
		return Optional.ofNullable(grid[column][row]);
	}
	private static class Disc extends Circle{
		private final boolean red;
		public Disc(boolean red) {
			super(TILE_SIZE/2, red ? Color.RED : Color.YELLOW);
			this.red=red;
			setCenterX(TILE_SIZE/2);
			setCenterY(TILE_SIZE/2);
		}
		}
	@Override
	public void start(Stage stage) throws Exception{
		stage.setScene(new Scene(createContent()));
		stage.show();
	}}