package tictactoe;

import java.util.*;

/**
 * author Jose Santillan
 * Date 6/7/21
 * A recreation of the iconic childhood game
 * TikTacToe along with implementation of AI and
 * Difficulty settings
 */
public class TicTacToe {

    static String[][] board;
    static boolean gameOver;
    static int numX;
    static int numO;
    static String XO;

    public static void Driver() {
        System.out.println("start or exit?\n");
        String[] commandLine = getLine();
        while(!exit(commandLine[0])) {

            String playerOne = commandLine[1];
            String playerTwo = commandLine[2];
            if (playerOne == null || playerTwo == null) {
                System.out.println("Bad Parameters!");
                System.out.println("enter new command");
            } else {
                start(playerOne, playerTwo);
            }
            System.out.println("start or exit?\n");
            commandLine = getLine();

        }
    }
    private static void start(String playerOne, String playerTwo) {
        if (playerOne.equals("user") && playerTwo.equals("user")) {
            userVsUser();
        } else if (playerOne.equals("user") && playerTwo.equals("easy")) {
            EasyDiff.levelEasyUserVsBot();
        } else if (playerOne.equals("easy") && playerTwo.equals("user")) {
            EasyDiff.levelEasyBotVsUser();
        } else if (playerOne.equals("easy") && playerTwo.equals("easy")) {
            EasyDiff.easyBotVsEasyBot();
        } else if(playerOne.equals("medium") && playerTwo.equals("user")) {
            MediumDiff.medBotVsUser();
        } else if(playerOne.equals("user") && playerTwo.equals("medium")) {
            MediumDiff.userVsMedBot();
        } else if(playerOne.equals("medium") && playerTwo.equals("medium")) {
            MediumDiff.medBotVsMedBot();
        } else if(playerOne.equals("medium") && playerTwo.equals("easy")) {
            MediumDiff.medBotVsEasyBot();
        } else if(playerOne.equals("easy") && playerTwo.equals("medium")) {
            EasyDiff.easyBotVsMedBot();
        } else if (playerOne.equals("user") && playerTwo.equals("hard")) {
            HardDiff.userVsHard();
        } else if (playerOne.equals("hard") && playerTwo.equals("user")) {
            HardDiff.hardVsUser();
        } else if (playerOne.equals("hard") && playerTwo.equals("medium")) {
            HardDiff.hardVsMedium();
        } else if (playerOne.equals("hard") && playerTwo.equals("easy")) {
            HardDiff.hardVsEasy();
        } else if (playerOne.equals("hard") && playerTwo.equals("hard")) {
            HardDiff.hardVsHard();
        } else if (playerOne.equals("easy") && playerTwo.equals("hard")) {
            HardDiff.easyVsHard();
        } else if (playerOne.equals("medium") && playerTwo.equals("hard")) {
            HardDiff.mediumVsHard();
        } else {
            System.out.print("Bad parameters!");
        }
    }
    /**
     * Determines if the user entered exit
     * @param command the user input
     * @return does the command equal exit
     */
    protected static boolean exit(String command) {
        return "exit".equals(command);
    }
    /**
     * Asks for and creates initial board
     */
    protected static void initializeBoard() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Please input initial grid with X's, O's and _'s");
        String init = scan.next();
        while(!validGrid(init)) {
            System.out.println("INVALID. Please only use X's, O's and _'s\nAlso make sure there are equal amount of X's and O's");
            init = scan.next();
        }
        String[] split = init.split("");
        board = new String[3][3];
        int index = 0;
        for (int i = 0; i < board.length; i++) {
            for (int k = 0; k < board[i].length; k++) {
                String xo = split[index];
                board[i][k] = xo;
                index += 1;
                if ("O".equals(xo)) {
                    numO += 1;
                } else if ("X".equals(xo)) {
                    numX += 1;
                }
            }
        }

    }

    /**
     * Plays a round of the game
     * @return Whether the round was sucessful(True) or a bad input was made (false);
     */
    public static boolean round(){
        System.out.println("Please enter the coordinates:");
        Scanner scan = new Scanner(System.in);
        int xc = 0;
        int yc = 0;
        boolean valid = false;
        try {
            xc = scan.nextInt();
            yc = scan.nextInt();
            valid = true;
        } catch (Exception e) {
            System.out.println("You should enter numbers!");
        }
        if (valid && validCoords(xc - 1, yc - 1)) {
            nextMark();
            board[xc - 1][yc - 1] = XO;
            return true;
        }
        return false;
    }


    /**
     * Prints board for player to visually see
     */
    public static void displayBoard() {
        System.out.println("-".repeat(9));
        for (String[] row : board) {
            System.out.print("| ");
            for (String spot : row) {
                if (spot.equals("_")) {
                    System.out.print("  ");
                } else {
                    System.out.print(spot + " ");
                }
            }
            System.out.println("|");
        }
        System.out.println("-".repeat(9));
    }

    /**
     *
     * @param x x-coordinate
     * @param y y-coordinate
     * @return whether the cell is empty or open
     */
    protected static boolean validCoords(int x, int y) {
        if (x > 2 || x < 0 || y > 2|| y < 0) {
            System.out.println("Coordinates should be from 1 to 3!");
            return false;
        }else if (board[x][y].equals("O") || board[x][y].equals("X")) {
            System.out.println("This cell is occupied! Choose another one!");
            return false;
        } else return true;
    }

    /**
     * Determines whether the next mark is a X or an O
     * @return X or O
     */
    protected static void nextMark() {
        if (numX == numO) {
            numX += 1;
            XO = "X";
        } else if (numX > numO) {
            numO += 1;
            XO = "O";
        }
    }

    /**
     * Checks to see if intial Grid is valid
     * @param grid initial grid string
     * @return validity of initial grid
     */
    protected static boolean validGrid(String grid) {
        String left = grid.replaceAll("[XO_]", "");
        int diff = numX - numO;
        return (diff == 0 || diff == 1) && left.length() <= 0;
    }

    /**
     * Checks to see if game is over, whether by draw or by win
     */
    protected static void gameState(String XO) {
        if (numX + numO == 9 && !checkWin(XO)) {
            gameOver = true;
            System.out.println("Draw");
        } else if (checkWin(XO)) {
            System.out.println(XO + " wins");
            gameOver = true;
        }
    }

    /**
     * @return whether there are 3 X's in a row
     */
    protected static boolean checkWin(String XO) {
        int row = 0;
        int col = 0;
        int majorDia = 0;
        int minorDia = 0;
        for (int i= 0; i < board.length;  i++) {
            for (int k = 0; k < board.length; k++) {
                if (board[i][k].equals(XO)) {
                    row += 1;
                }
                if (board[k][i].equals(XO)) {
                    col += 1;
                }
                if (k == i && board[i][k].equals(XO)) {
                    majorDia++;
                }
                if (k + i == 2 && board[i][k].equals(XO)) {
                    minorDia++;
                }

            }
            if (row == 3 || col == 3 || majorDia == 3 || minorDia == 3) {
                return true;
            } else {
                row = 0;
                col = 0;
            }
        }
        return false;
    }

    protected static boolean checkWin(String XO, int r, int c) {
        String[][] ref = deepCopy(board);
        ref[r][c] = XO;
        int row = 0;
        int col = 0;
        int majorDia = 0;
        int minorDia = 0;
        for (int i= 0; i < board.length;  i++) {
            for (int k = 0; k < board.length; k++) {
                if (ref[i][k].equals(XO)) {
                    row += 1;
                }
                if (ref[k][i].equals(XO)) {
                    col += 1;
                }
                if (k == i && ref[i][k].equals(XO)) {
                    majorDia++;
                }
                if (k + i == 2 && ref[i][k].equals(XO)) {
                    minorDia++;
                }

            }
            if (row == 3 || col == 3 || majorDia == 3 || minorDia == 3) {
                return true;
            } else {
                row = 0;
                col = 0;
            }
        }
        return false;
    }

    protected static void emptyBoard(){
        gameOver = false;
        board = new String[3][3];
        for (int i = 0; i < board.length; i++) {
            Arrays.fill(board[i], "_");
        }
    }

    protected static boolean validCoordsBot(int x, int y) {
        if (x > 2 || x < 0 || y > 2 || y < 0 ) {
            return false;
        }
        return !board[x][y].equals("O") && !board[x][y].equals("X");
    }
    protected static void checkDraw() {
        if (numX + numO == 9) {
            gameOver = true;
            gameState(XO);
        }
    }

    private static void userVsUser(){
        Scanner scan = new Scanner(System.in);
        emptyBoard();
        displayBoard();
        while(!gameOver) {
            boolean t = round();
            if (t) {
                if (checkWin(XO)) {
                    gameState(XO);
                }
                checkDraw();
                displayBoard();
                gameState(XO);
            }
        }
        numX = 0;
        numO = 0;
    }


    private static String[] getLine() {
        Scanner scan = new Scanner(System.in);
        String command = scan.nextLine();
        String[] commandLine = command.split(" ");
        while (commandLine.length != 3 && !exit(commandLine[0])) {
            System.out.println("Bad Parameters");
            System.out.println("enter new command");
            command = scan.nextLine();
            commandLine = command.split(" ");
        }
        return commandLine;
    }

    protected static String[][] deepCopy(String[][] board) {
        String[][] ret = new String[3][3];
        for (int i = 0; i < board.length; i++) {
            ret[i] = Arrays.copyOf(board[i], board.length);
        }
        return ret;
    }
}