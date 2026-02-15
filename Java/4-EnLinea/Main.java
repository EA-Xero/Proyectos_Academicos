import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Scoreboard scoreboard = new Scoreboard();

        System.out.println("Bienvenido a Conecta 4");

        while (true) {
            System.out.println("\n1. Jugar partida");
            System.out.println("2. Mostrar jugadores en rango de victorias");
            System.out.println("3. Mostrar sucesores por victorias");
            System.out.println("4. Ver estadísticas de un jugador");
            System.out.println("5. Ver total de partidas jugadas");
            System.out.println("6. Salir");
            System.out.print("Seleccione una opción: ");
            int opcion = scanner.nextInt();
            scanner.nextLine();

            if (opcion == 1) {
                System.out.print("Nombre del Jugador A: ");
                String playerA = scanner.nextLine();
                System.out.print("Nombre del Jugador B: ");
                String playerB = scanner.nextLine();

                scoreboard.registerPlayer(playerA);
                scoreboard.registerPlayer(playerB);

                Game game = new Game(playerA, playerB);
                String winner = game.play();

                boolean draw = game.getStatus().equals("DRAW");

                // ✅ Corrección: registrar ganador real
                if (draw) {
                    scoreboard.addGameResult(playerA, playerB, true);
                } else if (winner.equals(playerA)) {
                    scoreboard.addGameResult(playerA, playerB, false);
                } else {
                    scoreboard.addGameResult(playerB, playerA, false);
                }

            } else if (opcion == 2) {
                System.out.print("Ingrese mínimo de victorias: ");
                int lo = scanner.nextInt();
                System.out.print("Ingrese máximo de victorias: ");
                int hi = scanner.nextInt();
                Player[] players = scoreboard.winRange(lo, hi);
                System.out.println("Jugadores en rango:");

                // ✅ Corrección: mensaje formateado correctamente
                for (Player p : players) {
                    int wins = p.getWins();
                    String victoriaTexto = (wins == 1) ? "victoria" : "victorias";
                    System.out.println("- " + p.getPlayerName() + ": " + wins + " " + victoriaTexto);
                }

            } else if (opcion == 3) {
                System.out.print("Ingrese valor de victorias: ");
                int wins = scanner.nextInt();
                Player[] players = scoreboard.winSuccessor(wins);
                if (players.length == 0) {
                    System.out.println("No hay sucesores para ese número de victorias.");
                } else {
                    System.out.println("Jugadores sucesores:");
                    for (Player p : players) {
                        System.out.println(p.getPlayerName() + " - Wins: " + p.getWins());
                    }
                }
            } else if (opcion == 4) {
                System.out.print("Ingrese nombre del jugador: ");
                String name = scanner.nextLine();
                if (scoreboard.checkPlayer(name)) {
                    System.out.println("Jugador no registrado.");
                } else {
                    Player p = scoreboard.getPlayer(name);
                    System.out.println("Estadísticas de " + name + ":");
                    System.out.println("  Ganadas: " + p.getWins());
                    System.out.println("  Empatadas: " + p.getDraws());
                    System.out.println("  Perdidas: " + p.getLosses());
                    System.out.printf("  Win Rate: %.2f%%\n", p.winRate() * 100);
                }
            } else if (opcion == 5) {
                System.out.println("Total de partidas jugadas: " + scoreboard.getTotalGames());
            } else if (opcion == 6) {
                System.out.println("Gracias por jugar. ¡Hasta la próxima!");
                break;
            } else {
                System.out.println("Opción inválida. Intente nuevamente.");
            }
        }

        scanner.close();
    }
}