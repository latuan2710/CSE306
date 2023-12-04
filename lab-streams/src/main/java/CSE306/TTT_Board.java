package CSE306;

public abstract class TTT_Board {
    char[] board = new char[9];
    private final int[][] winners = { { 0, 1, 2 }, { 3, 4, 5 }, { 6, 7, 8 }, { 0, 3, 6 }, { 1, 4, 7 },
            { 2, 5, 8 }, { 0, 4, 8 }, { 2, 4, 6 } };

    public TTT_Board() {
        initialize();
    }

    public void initialize() {
        for (int i = 0; i < this.board.length; i++) {
            this.board[i] = '-';
        }
    }

    public boolean checkMove(int cell) {
        return this.board[cell] == '-';
    }

    public int checkStatus(char player) {
        int status = 0;

        for (int[] winner : winners) {
            if (checkWinner(winner, player)) {
                status = 1;
                break;
            }
        }
        return status;
    }

    public int checkBoard() {
        int status = 1;

        for (char c : this.board) {
            if (c == '-') {
                status = 0;
                break;
            }
        }
        return status;
    }

    public boolean checkWinner(int[] winner, char player) {
        boolean check = true;
        for (int cell : winner) {
            if (this.board[cell] != player) {
                check = false;
                break;
            }
        }
        return check;
    }

    public void updateBoard(int cell) {
        this.board[cell] = 'o';
    }

    public abstract void makeMove();

    public String encodeBoard() {
        StringBuilder builder = new StringBuilder();
        for (char c : this.board) {
            builder.append(c).append('.');
        }
        return builder.toString();
    }

}
