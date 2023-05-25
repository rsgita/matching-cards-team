import requstor.Requestor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameOverScene extends JPanel {
    private static class ImagePanel extends JPanel {
        private Image image;
        private float alpha = 0.0f;

        public ImagePanel(Image image) {
            this.image = image;
            setLayout(null);
        }

        public void setAlpha(float alpha) {
            this.alpha = Math.max(0.0f, Math.min(1.0f, alpha));
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
            g2d.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            g2d.dispose();
        }
    }

    private ImagePanel backgroundPanel;
    private Timer fadeInTimer;
    private float alpha = 0.0f;

    public Main main; // Main 클래스의 인스턴스 변수
    public int time; // int 값의 변수
    private String difficulty;
    private Leaderboard leaderboard;

    private static String DIFFICULTIES[] = {"easy", "normal", "hard"};

    public GameOverScene(Main main, Leaderboard leaderboard, int difficulty, int time) {
        this.main = main;
        this.leaderboard=leaderboard;
        this.time = time;
        this.difficulty = DIFFICULTIES[difficulty];

        setLayout(new BorderLayout());

        // 배경 이미지 패널
        backgroundPanel = new ImagePanel(new ImageIcon("image/gameover.jpg").getImage());
        backgroundPanel.setLayout(new BorderLayout());

        add(backgroundPanel);

        // 이미지 페이드 인 효과 타이머
        fadeInTimer = new Timer(20, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (alpha < 1.0f) {
                    alpha += 0.01f;
                    backgroundPanel.setAlpha(alpha);
                    backgroundPanel.repaint();
                } else {
                    fadeInTimer.stop();
                }
            }
        });
        fadeInTimer.start();

        // 닉네임 입력 패널
        JPanel nicknamePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        nicknamePanel.setOpaque(false);

        JLabel rankLabel = new JLabel("축하합니다! 당신의 기록은 " + time + "초입니다.");
        rankLabel.setFont(new Font("Maplestory Bold", Font.PLAIN, 30));
        rankLabel.setForeground(Color.BLACK);

        // 이미지 하단 가운데에 위치하기 위한 추가 코드
        backgroundPanel.setLayout(new BorderLayout());
        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        centerPanel.setOpaque(false);
        centerPanel.add(rankLabel);
        backgroundPanel.add(centerPanel, BorderLayout.PAGE_END);

        add(backgroundPanel);

        // 닉네임 레이블
        JLabel nicknameLabel = new JLabel("Enter Nickname:");
        nicknameLabel.setFont(new Font("Maplestory Bold", Font.PLAIN, 27));
        nicknameLabel.setForeground(Color.BLACK);
        nicknamePanel.add(nicknameLabel);

        // 닉네임 입력 필드
        JTextField nicknameField = new JTextField(15);
        nicknameField.setFont(new Font("Maplestory Bold", Font.PLAIN, 24));
        nicknameField.setBackground(Color.BLACK);
        nicknameField.setForeground(Color.WHITE);
        nicknameField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Leaderboard leaderboard = GameOverScene.this.leaderboard;

                String nickname = nicknameField.getText();
                leaderboard.register(GameOverScene.this.difficulty, nickname, GameOverScene.this.time);


                // 순위표를 표시하는 새로운 창 열음
                GameOverScene.this.main.showLeaderboard();
                GameOverScene.this.main.setTitleScene();
            }
        });
        nicknamePanel.add(nicknameField);

        add(nicknamePanel, BorderLayout.SOUTH);
    }

}