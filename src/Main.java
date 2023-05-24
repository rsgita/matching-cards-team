import javax.swing.*;

public class Main extends JFrame {
    private JPanel currentScene;
    private LeaderboardFrame leaderboardFrame;
    private TitleScene titleScene;

    public Main() {
        setSize(700, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // 재사용가능한 프레임와 패널 생성
        this.titleScene=new TitleScene(this);
        this.leaderboardFrame =new LeaderboardFrame();
        
        // 현재 패널 결정
        currentScene=this.titleScene;
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

    public void setGameOverScene(int difficulty, int time) {
        currentScene = new GameOverScene(this, difficulty, time);
        updateScene();
    }

    public void setInGameScene(int difficulty) {
        currentScene = new InGameScene(this, difficulty);
        updateScene();
    }

    public void setTitleScene() {
        currentScene = this.titleScene;
        updateScene();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Main();
        });
    }

}