/**
 * Handles presenting the UI side of the Connect 4 game to the players.
 * 
 * @author Brayden Giffen
 * @version 2/10/19
 */
package Connect4;
import java.util.Scanner;
public class Connect4TextConsole {
    public static void main(String[] args) {
    		Connect4 game = new Connect4();
    		Connect4ComputerPlayer comp = new Connect4ComputerPlayer();
    		Connect4GUI gui = new Connect4GUI();
        boolean turn = true;
        Scanner in = new Scanner(System.in);
        System.out.print("Welcome to Connect4! Enter 'T' to use the text console or 'G' to use the GUI.\n");
        String choice = in.next();
        int not2 = 1;
        while (not2 == 1) {
        	if (choice.equals("T")) {
        		not2 = 0;
        	}
        	else if (choice.equals("G")) {
        		not2 = 0;
        	}
        	else {
        		System.out.print("Invalid Input. Enter 'T' to use the text console or 'G' to use the GUI.\n");
        		choice = in.next();
        	}
        }
        if (null != choice) switch (choice) {
        case "T":
        game.printBoard();
        System.out.print("Begin Game. Enter 'P' if you want to play against another player, enter 'C' to play against computer.\n");
        String stat = in.next();
        int not = 1;
        while (not == 1) {
        	if (stat.equals("C")) {
        		not = 0;
        	}
        	else if (stat.equals("P")) {
        		not = 0;
        	}
        	else {
        		System.out.print("Invalid Input. Enter 'P' if you want to play against another player, enter 'C' to play against computer.\n");
        		stat = in.next();
        	}
        }
        if (null != stat) switch (stat) {
        case "P":
        	System.out.print("Start game against other player.\n");
        do {
            turn = !turn;
            char player;
            if(turn) {
                player = 'O';
            } else {
                player = 'X';
            }
            game.printBoard();
            System.out.print("Player " + player + " - your turn. Choose a column (1-7):\n");
            boolean status = false;
            while(!status) {
                try {
                    status = game.drop(player, in.nextInt());
                    if(!status) {
                        System.out.println("Invalid position or Position is already filled.");
                        System.out.print("Please Try Again: ");
                    }
                } catch(Exception e) {
                    System.out.println("Invalid input.");
                    System.out.print("Please Try Again: ");
                    in.nextLine();
                }
            }
            System.out.println();
        } while(!game.isOver() && !game.checkWin());
        game.printBoard();
        if(game.checkWin()) {
            System.out.printf("The player %s has won.", (turn ? "O" : "X"));
        } else {
            System.out.println("The game is over.");
        }
        in.close();
        case "C":
        	System.out.print("Start game against computer.\n");
        	do {
                turn = !turn;
                char player;
                if(turn) {
                    player = 'O';
                } else {
                    player = 'X';
                }
                switch (player) {
                case 'X':
                game.printBoard();
                System.out.print("It's your turn Player X. - Choose a column (1-7):\n");
                boolean status = false;
                while(!status) {
                    try {
                        status = game.drop(player, in.nextInt());
                        if(!status) {
                            System.out.println("Invalid position or Position is already filled.");
                            System.out.print("Please Try Again: ");
                        }
                    } catch(Exception e) {
                        System.out.println("Invalid input.");
                        System.out.print("Please Try Again: ");
                        in.nextLine();
                    }
                }
                System.out.println();
                break;
                case 'O':
                	game.printBoard();
                    status = false;
                    while(!status) {
                        try {
                        	int current = comp.play();
                        	System.out.println("Computer chooses to play in column " + current + ".");
                            status = game.drop(player, current);
                            if(!status) {
                                System.out.println("Computer chose invalid position or position is already filled.\n");
                            }
                        } catch(Exception e) {
                            System.out.println("Computer chose invalid input.\n");
                            int current = comp.play();
                        }
                    }
                break;
                }
            } while(!game.isOver() && !game.checkWin());
            game.printBoard();
            if(game.checkWin()) {
                System.out.printf("The player %s has won.", (turn ? "O" : "X"));
            } else {
                System.out.println("The game is over.");
            }
            in.close();
    }
        case "G":
        javafx.application.Application.launch(Connect4GUI.class); //launches the GUI
            }
    }
        
    }      	
