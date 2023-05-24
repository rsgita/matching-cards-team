import javax.swing.*;

public class Main extends JFrame {
    private JPanel currentScene;
    private LeaderboardFrame leaderboardFrame;

    public Main() {
        setSize(700, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    
        // 순위 프레임
        leaderboardFrame =new LeaderboardFrame();
        
        // 현재 패널 결정
        currentScene = new TitleScene(this);
        this.setContentPane(currentScene);

        setVisible(true);
    }

    public void showLeaderboard() {
        leaderboardFrame.showLeaderboard();
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