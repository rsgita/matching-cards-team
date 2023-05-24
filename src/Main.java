import javax.swing.*;

public class Main extends JFrame {
    private JPanel currentScene;

    public Main() {
        setSize(700, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        currentScene = new TitleScene(this);
        this.setContentPane(currentScene);

        setVisible(true);
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