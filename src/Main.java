import javax.swing.*;

public class Main extends JFrame {
    private JPanel currentScene;
    private LeaderboardScene leaderboardScene;
    private JFrame leaderboardFrame;

    public Main() {
        setSize(700, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        currentScene = new TitleScene(this);
        setContentPane(currentScene);

        setVisible(true);

        leaderboardScene = new LeaderboardScene(this);
        showLeaderboard();
    }

    public void showLeaderboard() {
        leaderboardFrame = new JFrame("Leaderboard");
        leaderboardFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        leaderboardFrame.setSize(500, 400);
        leaderboardFrame.setLocationRelativeTo(null);
        leaderboardFrame.setContentPane(leaderboardScene);
        leaderboardFrame.setVisible(true);
    }
    public void closeLeaderboard() {
        leaderboardFrame.dispose();
    }

    public void updateScene() {
        setContentPane(currentScene);
        revalidate();
    }

    public void setGameOverScene(int time) {
        currentScene = new GameOverScene(this, time);
        updateScene();
    }

    public void setInGameScene(int difficulty) {
        currentScene = new InGameScene(this, difficulty);
        updateScene();
    }

    public void setTitleScene() {
        currentScene = new TitleScene(this);
        updateScene();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Main();
        });
    }

}