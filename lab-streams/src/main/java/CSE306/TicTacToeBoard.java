package CSE306;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class TicTacToeBoard {
    private List<String> board = new ArrayList<>(Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9"));
    private List<Integer> numbers = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));

    public String displayBoard() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 1; i <= board.size(); i++) {
            stringBuilder.append(board.get(i - 1) + "  ");
            if (i % 3 == 0 && i != 9)
                stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    public boolean checkInput(int input) {
        return numbers.contains((Integer) input);
    }

    public void tickMatrix(int index, String tick) {
        board.set((index - 1), tick);
        numbers.remove((Integer) index);
    }

    public int getInputForServer() {
        Random rand = new Random();
        return numbers.get(rand.nextInt(numbers.size()));
    }

    public boolean checkDraw() {
        if (numbers.isEmpty()) {
            return true;
        }
        return false;
    }

    public boolean checkWin(String tick) {
        List<Integer> indexs = new ArrayList<>();

        for (int i = 0; i < board.size(); i++) {
            if (board.get(i).equals(tick)) {
                indexs.add(i);
            }
        }

        int count = 0, i = 0, j = 0;
        while (count < 3) {
            if (indexs.contains(i) && indexs.contains(i + 1) && indexs.contains(i + 2))
                return true;
            if (indexs.contains(j) && indexs.contains(j + 3) && indexs.contains(j + 6))
                return true;
            count++;
            i += 3;
            j++;
        }

        if (indexs.contains(0) && indexs.contains(4) && indexs.contains(8))
            return true;
        if (indexs.contains(2) && indexs.contains(4) && indexs.contains(6))
            return true;

        return false;
    }
}
