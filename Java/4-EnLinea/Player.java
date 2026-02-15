public class Player {
    private String playerName;
    private int wins;
    private int draws;
    private int losses;

    public Player(String playerName) {
        this.playerName = playerName;
        this.wins = 0;
        this.draws = 0;
        this.losses = 0;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void addWin() {
        wins++;
    }

    public void addDraw() {
        draws++;
    }
    public int getDraws() {
        return draws;
    }
    public void addLoss() {
        losses++;
    }
    public int getLosses() {
        return losses;
    }

    public int getWins() {
        return wins;
    }

    public double winRate() {
        int totalGames = wins + draws + losses;
        if (totalGames == 0) return 0.0;
        return (double) wins / totalGames;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return playerName.equals(player.playerName);
    }

    @Override
    public int hashCode() {
        return playerName.hashCode();
    }
}
