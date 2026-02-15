import java.util.Scanner;

public class Game {
    private String status;
    private String winnerPlayerName;
    private String playerNameA;
    private String playerNameB;
    private ConnectFour connectFour;

    public Game(String playerNameA, String playerNameB) {
        this.status = "IN_PROGRESS";
        this.winnerPlayerName = "";
        this.playerNameA = playerNameA;
        this.playerNameB = playerNameB;
        this.connectFour = new ConnectFour();
    }

    public String play() {
        Scanner scanner = new Scanner(System.in);
        String[] result = new String[1];
        while (!connectFour.isGameOver(result)) {
            connectFour.printBoard();
            String currentPlayer = connectFour.getCurrentSymbol() == 'X' ? playerNameA : playerNameB;
            System.out.print("Turno de " + currentPlayer + " (" + connectFour.getCurrentSymbol() + "): Ingrese columna (0-6): ");
            int column = scanner.nextInt();
            if (!connectFour.makeMove(column)) {
                System.out.println("Movimiento inválido. Intente otra vez.");
            }
        }

        connectFour.printBoard();
        if (result[0].equals("DRAW")) {
            status = "DRAW";
            winnerPlayerName = "";
            System.out.println("El juego terminó en empate.");
        } else {
            status = "VICTORY";
            winnerPlayerName = result[0].equals("A") ? playerNameA : playerNameB;
            System.out.println("Ganador: " + winnerPlayerName);
        }
        return winnerPlayerName;
    }

    public String getStatus() {
        return status;
    }
}
