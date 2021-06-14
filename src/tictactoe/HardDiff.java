package tictactoe;

import java.util.*;

public class HardDiff extends TicTacToe {
    private static String[] newBoard;

    private static String ai;
    private static String human;

    public static void userVsHard(){
        ai = "O";
        human = "X";
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
                    nextMark();
                    hardBotRound();
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

    public static void hardVsUser(){
        ai = "X";
        human = "O";
        emptyBoard();
        displayBoard();
        while(!gameOver) {
            nextMark();
            hardBotRound();
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

    public static void hardVsEasy(){
        ai = "X";
        human = "O";
        emptyBoard();
        displayBoard();
        while(!gameOver) {
            nextMark();
            hardBotRound();
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
        numX = 0;
        numO = 0;
    }

    public static void easyVsHard(){
        ai = "O";
        human = "X";
        emptyBoard();
        displayBoard();
        while(!gameOver) {
            EasyDiff.easyBot();
            displayBoard();
            if (checkWin(XO)) {
                gameState(XO);
            } else {
                nextMark();
                hardBotRound();
                displayBoard();
                if (checkWin(XO)) {
                    gameState(XO);
                }
                checkDraw();
                gameState(XO);
            }
        }
        numX = 0;
        numO = 0;
    }

    public static void hardVsMedium() {
        ai = "X";
        human = "O";
        emptyBoard();
        displayBoard();
        while(!gameOver) {
            nextMark();
            hardBotRound();
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
                gameState(XO);
            }
        }
        numX = 0;
        numO = 0;
    }

    public static void mediumVsHard(){
        ai = "O";
        human = "X";
        emptyBoard();
        displayBoard();
        while(!gameOver) {
            MediumDiff.medBotRound();
            displayBoard();
            if (checkWin(XO)) {
                gameState(XO);
            } else {
                nextMark();
                hardBotRound();
                displayBoard();
                if (checkWin(XO)) {
                    gameState(XO);
                }
                checkDraw();
                gameState(XO);
            }
        }
        numX = 0;
        numO = 0;
    }


    public static void hardVsHard(){
        ai = "X";
        human = "O";
        emptyBoard();
        displayBoard();
        while(!gameOver) {
            nextMark();
            makeBoard(board);
            Score score = makeMove(newBoard, ai);
            int row = score.index / 3;
            int col = score.index % 3;
            board[row][col] = XO;
            displayBoard();
            if (checkWin(XO)) {
                gameState(XO);
            }
            checkDraw();
            gameState(XO);
            nextMark();
            makeBoard(board);
            if (!gameOver) {
                Score secondScore = makeMove(newBoard, human);
                int row2 = secondScore.index / 3;
                int col2 = secondScore.index % 3;
                board[row2][col2] = XO;
                displayBoard();
                if (checkWin(XO)) {
                    gameState(XO);
                }
                checkDraw();
                gameState(XO);
            }
        }
        numX = 0;
        numO = 0;
    }

    public static void hardBotRound(){
        makeBoard(board);
        Score score = makeMove(newBoard, ai);
        int row = score.index / 3;
        int col = score.index % 3;
        board[row][col] = XO;

    }

    public static Score makeMove(String[] board, String player) {
        String[] availIndex = emptyIndexies(board);
        String[] tempBoard = board.clone();
        ArrayList<Score> moves = new ArrayList<>();
        if (winning(board, ai)) {
            return new Score(10);
        } else if (winning(board, human)) {
            return new Score(-10);
        } else if (availIndex.length == 0) {
            return new Score(0);
        }
        for (int i = 0; i < availIndex.length; i++) {
            int x = Integer.parseInt(availIndex[i]);
            String hold = tempBoard[x];
            tempBoard[x] = player;
            Score state;
            if (player.equals(ai)) {
                state = makeMove(tempBoard, human);
                state.index = x;
            } else {
                state = makeMove(tempBoard, ai);
                state.index = x;
            }
            moves.add(state);
            tempBoard[x] = hold;
        }
        Score ret = new Score(1, 1);
        if (player.equals(ai)) {
            int i = -1000;
            for (Score s : moves) {
                if (s.score > i) {
                    i = s.score;
                    ret = s;
                }
            }
        } else {
            int i = 1000;
            for (Score s : moves) {
                if (s.score < i) {
                    i = s.score;
                    ret = s;
                }
            }
        }
        return ret;
    }

    private static void makeBoard(String[][] board) {
        newBoard = new String[9];
        for (int i = 0; i < board.length; i++) {
            for (int k = 0; k < board.length; k++) {
                int j = i * 3 + k;
                if (board[i][k].equals("_")) {
                    newBoard[j] = "" + j;
                } else {
                    newBoard[j] = board[i][k];
                }
            }
        }
    }

    private static String[] emptyIndexies(String[] board){
        board = Arrays.stream(board).filter(x -> !x.equals("X") && !x.equals("O")).toArray(String[]::new);
        return board;
    }
    private static boolean winning(String[] board, String player){
        return (board[0].equals(player) && board[1].equals(player) && board[2].equals(player)) ||
                (board[3].equals(player) && board[4].equals(player) && board[5].equals(player)) ||
                (board[6].equals(player) && board[7].equals(player) && board[8].equals(player)) ||
                (board[0].equals(player) && board[3].equals(player) && board[6].equals(player)) ||
                (board[1].equals(player) && board[4].equals(player) && board[7].equals(player)) ||
                (board[2].equals(player) && board[5].equals(player) && board[8].equals(player)) ||
                (board[0].equals(player) && board[4].equals(player) && board[8].equals(player)) ||
                (board[2].equals(player) && board[4].equals(player) && board[6].equals(player));
    }
}
class Score {
    int score;
    int index;

    public Score(int score, int index) {
        this.score = score;
        this.index = index;
    }

    public Score(int score) {
        this.score = score;
    }
}