/**
 * Handles the background tasks making the Connect 4 game possible.
 * 
 * @author Brayden Giffen
 * @version 2/10/19
 */
package Connect4;
public class Connect4 {
    char[][] dimen;
    public Connect4() {
        dimen = new char[6][7];
        for(int i=0; i<dimen.length; i++) {
            for(int j=0; j<dimen[i].length; j++) {
                dimen[i][j] = ' ';
            }
        }
    }
    boolean isOver() {
        for(int i=0; i<dimen[0].length; i++) {
            if(dimen[0][i] == ' ') {
                return false;
            }
        }
        return true;
    }
    public void printBoard() {
        for(int i=0; i<dimen.length; i++) {
            System.out.printf("|");
            for(int j=0; j<dimen[i].length; j++) {
                System.out.printf("%c|", dimen[i][j]);
            }
            System.out.println();
        }
        for(int j=0; j<dimen[0].length; j++) {
            System.out.printf("--");
        }
        System.out.println("-");
    }
    public String boardToString() {
    	String ret = "";
        for(int i=0; i<dimen.length; i++) {
            ret += "|";
            for(int j=0; j<dimen[i].length; j++) {
            	ret += dimen[i][j] + "|";
            }
            ret += System.lineSeparator();
        }
        for(int j=0; j<dimen[0].length; j++) {
        	ret += "--";
        }
        ret += "-" + System.lineSeparator();
        return ret;
    }
    public boolean checkWin() {
        boolean findRow = false;
        boolean findCol = false;
        boolean findMjrD = false;
        boolean findMinD = false;
        for (int r = 0; r <= 5; r++) {
            for (int c = 0; c <= 3; c++) {
                if (dimen[r][c] == dimen[r][c + 1] && dimen[r][c] == dimen[r][c + 2] && dimen[r][c] == dimen[r][c + 3] && dimen[r][c] != ' ') {
                    findRow = true;
                    break;
                }
            }
        }
        for (int r = 0; r <= 2; r++) {
            for (int c = 0; c <= 6; c++) {
                if (dimen[r][c] == dimen[r + 1][c] && dimen[r][c] == dimen[r + 2][c] && dimen[r][c] == dimen[r + 3][c] && dimen[r][c] != ' ') {
                    findCol = true;
                    break;
                }
            }
        }
        for (int r = 0; r <= 2; r++) {
            for (int c = 0; c <= 3; c++) {
                if (dimen[r][c] == dimen[r + 1][c + 1] && dimen[r][c] == dimen[r + 2][c + 2] && dimen[r][c] == dimen[r + 3][c + 3] && dimen[r][c] != ' ') {
                    findMjrD = true;
                    break;
                }
            }
        }
        for (int r = 0; r <= 2; r++) {
            for (int c = 3; c <= 6; c++) {
                if (dimen[r][c] == dimen[r + 1][c - 1] && dimen[r][c] == dimen[r + 2][c - 2] && dimen[r][c] == dimen[r + 3][c - 3] && dimen[r][c] != ' ') {
                    findMinD = true;
                    break;
                }
            }
        }
        if (findRow || findCol || findMjrD || findMinD)
            return true;
        else
            return false;
    }
    public boolean drop(char player, int column) {
        column--;
        if(column < 0 || column > 6) {
            throw new ArrayIndexOutOfBoundsException("Invalid Column");
        }
        boolean put = false;
        for(int i=dimen.length - 1; i >= 0; i--) {
            if(dimen[i][column] == ' ') {
                dimen[i][column] = player;
                put = true;
                break;
            }
        }
        return put;
    }}