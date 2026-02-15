import java.util.ArrayList;
import java.util.HashSet;

public class Scoreboard {
    private BST<Integer, String> winTree;
    private HashST<String, Player> players;
    private int playedGames;

    public Scoreboard() {
        this.winTree = new BST<>();
        this.players = new HashST<>();
        this.playedGames = 0;
    }

    public void registerPlayer(String playerName) {
        if (checkPlayer(playerName)) {
            players.put(playerName, new Player(playerName));
        }
    }

    public boolean checkPlayer(String playerName) {
        return !players.contains(playerName);
    }

    public void addGameResult(String winner, String loser, boolean draw) {
        if (checkPlayer(winner)) registerPlayer(winner);
        if (checkPlayer(loser)) registerPlayer(loser);

        Player p1 = players.get(winner);
        Player p2 = players.get(loser);

        if (draw) {
            p1.addDraw();
            p2.addDraw();
        } else {
            p1.addWin();
            p2.addLoss();
        }

        playedGames++;

        winTree.insert(p1.getWins(), winner);
        winTree.insert(p2.getWins(), loser);
    }

    public Player[] winRange(int lo, int hi) {
        ArrayList<String> names = winTree.range(lo, hi);
        HashSet<String> uniqueNames = new HashSet<>(names);
        ArrayList<Player> result = new ArrayList<>();
        for (String name : uniqueNames) {
            Player p = players.get(name);
            if (p != null) result.add(p);
        }
        return result.toArray(new Player[0]);
    }

    public Player[] winSuccessor(int wins) {
        ArrayList<String> names = winTree.successor(wins);
        HashSet<String> uniqueNames = new HashSet<>(names);
        ArrayList<Player> result = new ArrayList<>();
        for (String name : uniqueNames) {
            Player p = players.get(name);
            if (p != null) result.add(p);
        }
        return result.toArray(new Player[0]);
    }

    public int getTotalGames() {
        return playedGames;
    }

    public Player getPlayer(String name) {
        return players.get(name);
    }
}