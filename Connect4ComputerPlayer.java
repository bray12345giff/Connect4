/**
 * Handles acting as a player if the user chooses to play against the computer.
 * 
 * @author Brayden Giffen
 * @version 2/10/19
 */
package Connect4;
import java.util.Random;
public class Connect4ComputerPlayer {
public int play() {
	Random randNum = new Random();
	int rand = randNum.nextInt((7 - 1) + 1) + 1;
	return rand;
}}