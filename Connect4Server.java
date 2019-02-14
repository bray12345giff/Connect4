/**
 * The Connect4 game server.
 * 
 * @author Brayden Giffen
 * @version 2/10/19
 */
package Connect4;

import java.io.*; 
import java.text.*; 
import java.util.*; 
import java.net.*; 

public class Connect4Server 
{ 
	public static void main(String[] args) throws IOException 
	{ 
		ServerSocket ss = new ServerSocket(7784); 
		while (true) 
		{ 
			Socket s = null; 
			
			try
			{ 
				s = ss.accept(); 
				
				System.out.println("A new client is connected : " + s); 
				DataInputStream dis = new DataInputStream(s.getInputStream()); 
				DataOutputStream dos = new DataOutputStream(s.getOutputStream()); 
				System.out.println("Assigning new thread for this client"); 
				Thread t = new ClientHandler(s, dis, dos); 
				t.start(); 	
			} 
			catch (Exception e){ 
				s.close(); 
				e.printStackTrace(); 
			} 
		} 
	} 
} 
class ClientHandler extends Thread 
{ Connect4 game = new Connect4();
Connect4ComputerPlayer comp = new Connect4ComputerPlayer();
	final DataInputStream dis; 
	final DataOutputStream dos; 
	final Socket s; 
	public ClientHandler(Socket s, DataInputStream dis, DataOutputStream dos) 
	{ 
		this.s = s; 
		this.dis = dis; 
		this.dos = dos; 
	}

	@Override
	public void run() 
	{ 
		String received; 
		String toreturn; 
		while (true) 
		{ 
			try { 
				dos.writeUTF("Welcome to Connect4! Enter 'T' to use the text console or 'G' to use the GUI.\n"); //1
				received = dis.readUTF(); //2
				String choice = received;
				int not2 = 1;
				boolean turn = true;
		        while (not2 == 1) {
		        	if (choice.equals("T")) {
		        		not2 = 0;
		        	}
		        	else if (choice.equals("G")) {
		        		not2 = 0;
		        	}
		        	else {
		        		received = dis.readUTF();//3
		        		choice = received;
		        	}
		        }
		        if (null != choice) switch (choice){ 
				
					case "T" : 
						dos.writeUTF(game.boardToString());//4
						received = dis.readUTF();//5
						String choice2 = received;
						int not3 = 1;
						while (not3 == 1) {
				        	if (choice2.equals("P")) {
				        		not3 = 0;
				        	}
				        	else if (choice2.equals("C")) {
				        		not3 = 0;
				        	}
				        	else {
				        		received = dis.readUTF();//6
				        		choice2 = received;
				        	}
				        }
						if (null != choice2) switch (choice2){
						case "P" :
							do {
								turn = !turn;
					            char player;
					            if(turn) {
					                player = 'O';
					            } else {
					                player = 'X';
					            }
					         
					            dos.writeUTF("Player " + player + " - your turn. Choose a column (1-7):\n");//7
					            boolean status = false;
					            while(!status) {
					                try {
					                		received = dis.readUTF();//8
					                		int result = Integer.parseInt(received);
					                    status = game.drop(player, result);
					                    if(!status) {
					                        System.out.println("Invalid position or Position is already filled.");
					                        System.out.print("Please Try Again: ");
					                    }
					                } catch(Exception e) {
					                    System.out.println("Invalid input.");
					                    System.out.print("Please Try Again: ");
					                    received = dis.readUTF();
					                }
					            }
					            System.out.println();
					        
							} while(!game.isOver() && !game.checkWin());
					        if(game.checkWin()) {
					            System.out.printf("The player %s has won.", (turn ? "O" : "X"));
					        } else {
					            System.out.println("The game is over.");
					        }
							break;
							
						case "C" :
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
				                    	received = dis.readUTF();//8
				                		int result = Integer.parseInt(received);
				                    status = game.drop(player, result);
				                        if(!status) {
				                            System.out.println("Invalid position or Position is already filled.");
				                            System.out.print("Please Try Again: ");
				                        }
				                    } catch(Exception e) {
				                        System.out.println("Invalid input.");
				                        System.out.print("Please Try Again: ");
				                      
				                    }
				                
				                System.out.println();
							break;
				                }
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
				           
				    }
					case "G" : 
						game.printBoard();
						break;
				                }			
			} catch (IOException e) { 
				e.printStackTrace();     
		}       
	}        
}
}