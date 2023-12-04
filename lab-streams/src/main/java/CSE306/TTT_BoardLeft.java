package CSE306;

public class TTT_BoardLeft extends TTT_Board {

    @Override
    public void makeMove() {
        for (int i = 0; i < board.length; i++) {
            if (board[i] == '-') {
                board[i] = 'x';
                break;
            }
        }
    }
}
