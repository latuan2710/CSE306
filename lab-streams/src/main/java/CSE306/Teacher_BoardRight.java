package CSE306;

public class Teacher_BoardRight extends Teacher_Board {

    @Override
    public void makeMove() {
        int end = this.board.length - 1;
        for (int i = end; i >= 0; i--) {
            if (this.board[i] == '-') {
                this.board[i] = 'x';
                break;
            }
        }
    }

}
