/**
 * The client for the game server.
 * 
 * @author Brayden Giffen
 * @version 2/10/19
 */
package Connect4;

import java.io.*; 
import java.net.*; 
import java.util.Scanner; 
public class Connect4Client 
{ 
	public static void main(String[] args) throws IOException 
	{ 
		try
		{ 
			Scanner scn = new Scanner(System.in); 
			InetAddress ip = InetAddress.getByName("localhost"); 
			Socket s = new Socket(ip, 7784); 
			DataInputStream dis = new DataInputStream(s.getInputStream()); 
			DataOutputStream dos = new DataOutputStream(s.getOutputStream()); 
			while (true) 
			{ 
				String recieved;
				System.out.println(dis.readUTF()); //1
				String tosend = scn.nextLine();
				String choice = tosend;
				dos.writeUTF(tosend); //2
		
				int not2 = 1;
		        while (not2 == 1) {
		        	if (choice.equals("T")) {
		        		not2 = 0;
		        	}
		        	else if (choice.equals("G")) {
		        		not2 = 0;
		        	}
		        	else {
		        		System.out.println("Invalid Input. Enter 'T' to use the text console or 'G' to use the GUI.\n");
		        		tosend = scn.nextLine();//3
		        		dos.writeUTF(tosend);
		        		choice = tosend;
		        	}
		        }
		        if (null != choice) switch (choice){ 
				
				case "T" : 
					System.out.println("Text Console: Begin Game!");
					recieved = dis.readUTF();//4
					System.out.print(recieved);
					System.out.println("\nEnter 'P' if you want to play against another player, enter 'C' to play against computer.\n");
					tosend = scn.nextLine();
					String choice2 = tosend;
					dos.writeUTF(tosend);//5
					int not1 = 1;
			        while (not1 == 1) {
			        	if (choice2.equals("P")) {
			        		not1 = 0;
			        	}
			        	else if (choice2.equals("C")) {
			        		not1 = 0;
			        	}
			        	else {
			        		System.out.println("Invalid Input. Enter 'P' to play against another player or 'C' to play the computer.\n");
			        		tosend = scn.nextLine();
			        		dos.writeUTF(tosend);//6
			        		choice2 = tosend;
			        	}
			        }
			        if (null != choice2) switch (choice2){
			        case "P" :
			        	System.out.println("Start game against other player.\n");
			        	recieved = dis.readUTF();//7
					System.out.print(recieved);
					tosend = scn.nextLine();
	        			dos.writeUTF(tosend);//8
			        	break;
			        case "C" :
			        	
			        	break;
			        }
					break; 
					
				case "G" : 
					System.out.println("GUI Launching"); 
					javafx.application.Application.launch(Connect4GUI.class);
					break; 
			} 
			} 
		}catch(Exception e){ 
			e.printStackTrace(); 
		} 
	} 
} 