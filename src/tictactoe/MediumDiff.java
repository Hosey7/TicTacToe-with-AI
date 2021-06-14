package tictactoe;

import java.util.*;

public class MediumDiff extends TicTacToe {

    public static void medBotRound(){
        nextMark();
        int[] win = checkForPossWin(XO);
        if (Arrays.equals(win, new int[]{0,0})) {
            int[] loss = checkForLoss(XO);
            if (Arrays.equals(loss, new int[]{0, 0})) {
                Random rand = new Random();
                int row = rand.nextInt(3);
                int col = rand.nextInt(3);
                while(!validCoordsBot(row, col)) {
                    row = rand.nextInt(3);
                    col = rand.nextInt(3);
                }
                board[row][col] = XO;
            } else {
                board[loss[0]][loss[1]] = XO;
            }
        } else {
            board[win[0]][win[1]] = XO;
        }
    }

    public static void medBotVsUser(){
        emptyBoard();
        displayBoard();
        while(!gameOver) {
            medBotRound();
            displayBoard();
            if(checkWin(XO)){
                displayBoard();
                gameState(XO);
            } else if (numX + numO == 9) {
                checkDraw();
            } else {
                boolean t = false;
                while(!t) {
                    t = round();
                    if (t) {
                        displayBoard();
                        if (checkWin(XO)) {
                            gameState(XO);
                        }
                        checkDraw();
                    }
                }
            }
        }
        numX = 0;
        numO = 0;
    }

    public static void userVsMedBot() {
        emptyBoard();
        displayBoard();
        while(!gameOver) {
            boolean t = round();
            if (t) {
                displayBoard();
                if (checkWin(XO)) {
                    gameState(XO);
                } else if (numO + numX == 9) {
                    checkDraw();
                } else {
                    medBotRound();
                    if (checkWin(XO)) {
                        gameState(XO);
                    }
                    displayBoard();
                    checkDraw();
                }
            }
        }
        numX = 0;
        numO = 0;
    }

    public static void medBotVsMedBot() {
        emptyBoard();
        displayBoard();
        while(!gameOver) {
            medBotRound();
            displayBoard();
            if (checkWin(XO)) {
                gameState(XO);
            }
            checkDraw();
            gameState(XO);

        }
        numX = 0;
        numO = 0;
    }

    public static void medBotVsEasyBot() {
        emptyBoard();
        displayBoard();
        while(!gameOver) {
            medBotRound();
            displayBoard();
            if (checkWin(XO)) {
                gameState(XO);
            } else {
                EasyDiff.easyBot();
                displayBoard();
                if (checkWin(XO)) {
                    gameState(XO);
                }
                checkDraw();
                gameState(XO);
            }
        }
    }

    public static int[] checkForPossWin(String XO) {
        int[] coords = new int[]{0, 0};
        for (int i = 0; i < board.length; i++) {
            for (int k = 0; k < board.length; k++) {
                if (checkWin(XO, i, k)) {
                    coords[0] = i;
                    coords[1] = k;
                }
            }
        }
        if (!validCoordsBot(coords[0], coords[1])) {
            return new int[]{0,0};
        }
        return coords;
    }

    public static int[] checkForLoss(String XO) {
        if (XO.equals("X")) {
            XO  = "O";
        } else if (XO.equals("O")) {
            XO = "X";
        }
        int[] ret = checkForPossWin((XO));
        return ret;
    }

    public static boolean isEmpty(int row, int col){
        row -= 1;
        col -= 1;
        return board[row][col].equals("_");
    }
}
