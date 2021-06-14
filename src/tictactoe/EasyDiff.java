package tictactoe;

import java.util.Random;

public class EasyDiff extends TicTacToe{

    /**
     * Easy Level bot
     * Placement determined at random
     */
    protected static void easyBot(){
        Random rand = new Random();
        nextMark();
        System.out.println("Making move level \"easy\"");
        int row = rand.nextInt(3);
        int col = rand.nextInt(3);
        while(!validCoordsBot(row, col)) {
            row = rand.nextInt(3);
            col = rand.nextInt(3);
        }
        board[row][col] = XO;
    }

    protected static void easyBotVsEasyBot() {
        emptyBoard();
        displayBoard();
        while(!gameOver) {
            easyBot();
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

    protected static void levelEasyBotVsUser() {
        emptyBoard();
        displayBoard();
        while(!gameOver) {
            easyBot();
            displayBoard();
            if(checkWin(XO)){
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

    protected static void levelEasyUserVsBot() {

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
                    easyBot();
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
    public static void easyBotVsMedBot() {
        emptyBoard();
        displayBoard();
        while(!gameOver) {
            easyBot();
            displayBoard();
            if (checkWin(XO)) {
                gameState(XO);

            } else {
                MediumDiff.medBotRound();
                displayBoard();
                if (checkWin(XO)) {
                    gameState(XO);
                }
                checkDraw();

            }
        }
    }
}
