public class ConnectFour {
    private char[][] grid;
    private char currentSymbol;

    public ConnectFour() {
        grid = new char[6][7];
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                grid[i][j] = ' ';
            }
        }
        currentSymbol = 'X';
    }

    public boolean makeMove(int column) {
        if (column < 0 || column >= 7) return false;
        for (int row = 5; row >= 0; row--) {
            if (grid[row][column] == ' ') {
                grid[row][column] = currentSymbol;
                currentSymbol = (currentSymbol == 'X') ? 'O' : 'X';
                return true;
            }
        }
        return false;
    }

    public boolean isGameOver(String[] result) {
        char winner = checkWinner();
        if (winner != ' ') {
            result[0] = winner == 'X' ? "A" : "B";
            return true;
        }
        if (isBoardFull()) {
            result[0] = "DRAW";
            return true;
        }
        return false;
    }

    private boolean isBoardFull() {
        for (int j = 0; j < 7; j++) {
            if (grid[0][j] == ' ') return false;
        }
        return true;
    }

    private char checkWinner() {
        for (int r = 0; r < 6; r++) {
            for (int c = 0; c < 7; c++) {
                char curr = grid[r][c];
                if (curr == ' ') continue;
                if (c <= 3 && curr == grid[r][c+1] && curr == grid[r][c+2] && curr == grid[r][c+3]) return curr;
                if (r <= 2 && curr == grid[r+1][c] && curr == grid[r+2][c] && curr == grid[r+3][c]) return curr;
                if (r <= 2 && c <= 3 && curr == grid[r+1][c+1] && curr == grid[r+2][c+2] && curr == grid[r+3][c+3]) return curr;
                if (r <= 2 && c >= 3 && curr == grid[r+1][c-1] && curr == grid[r+2][c-2] && curr == grid[r+3][c-3]) return curr;
            }
        }
        return ' ';
    }

    public void printBoard() {
        for (char[] row : grid) {
            for (char cell : row) {
                System.out.print("|" + cell);
            }
            System.out.println("|");
        }
        System.out.println(" 0 1 2 3 4 5 6");
    }

    public char getCurrentSymbol() {
        return currentSymbol;
    }
}
