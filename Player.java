public class Player {
    private final String Name;
    private int Score = 0;
    private final GamePanel gamePanel;

    public String getName() {
        return Name;
    }

    public void setScore(int score) {
        Score = score;
    }

    public int getScore() {
        return Score;
    }

    public GamePanel getGamePanel() {
        return gamePanel;
    }

    public Player(String name, GamePanel gamePanel) {
        Name = name;
        this.gamePanel = gamePanel;
    }
}
