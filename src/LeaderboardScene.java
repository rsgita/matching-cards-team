import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class LeaderboardFrame extends JFrame {
    private LeaderboardScene leaderboardScene;

    public LeaderboardFrame() {
        this.leaderboardScene=new LeaderboardScene(this);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null);
    }

    // 강조 표시가 있으면 강조표시 존재하는 패널
    // 강조 표시 없으면 강조표시 없는 패널
    // 두개로 분리하여 생성
    public void showLeaderboard() {
        setContentPane(leaderboardScene);
        setVisible(true);
    }

    public void closeLeaderboard() {
        dispose();
    }
}

class LeaderboardScene extends JPanel {
    private String currentDifficulty;
    private int currentDifficultyIndex;
    private String[] difficultyList = {"easy", "normal", "hard"};

    private JTable table;
    private DefaultTableModel tableModel;

    private JLabel difficultyLabel; // 난이도 표시 레이블

    public LeaderboardScene(LeaderboardFrame frame) {
        setLayout(new BorderLayout());

        currentDifficultyIndex = 0;
        currentDifficulty = difficultyList[currentDifficultyIndex];

        JButton prevButton = new JButton("<"); // 이전 난이도로 변경하는 버튼
        JButton nextButton = new JButton(">"); // 다음 난이도로 변경하는 버튼

        difficultyLabel = new JLabel(currentDifficulty.toUpperCase()); // 현재 난이도 표시 레이블
        difficultyLabel.setHorizontalAlignment(SwingConstants.CENTER);
        difficultyLabel.setFont(difficultyLabel.getFont().deriveFont(20f)); // 글꼴 크기

        prevButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeDifficulty(-1); // 이전 난이도로 변경
                updateDifficultyLabel(); // 난이도 레이블 업데이트
                showExampleRankingForDifficulty(currentDifficulty); // 변경된 난이도에 해당하는 예시 순위 표시
            }
        });

        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeDifficulty(1); // 다음 난이도로 변경
                updateDifficultyLabel(); // 난이도 레이블 업데이트
                showExampleRankingForDifficulty(currentDifficulty); // 변경된 난이도에 해당하는 예시 순위 표시
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(prevButton);
        buttonPanel.add(difficultyLabel);
        buttonPanel.add(nextButton);
        add(buttonPanel, BorderLayout.NORTH);

        String[] columnNames = {"Rank", "Player", "Time"};
        Object[][] data = getExampleRankingForDifficulty(currentDifficulty);
        tableModel = new DefaultTableModel(data, columnNames);
        table = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.closeLeaderboard();
            }
        });
        add(closeButton, BorderLayout.SOUTH);
    }

    private void changeDifficulty(int offset) {
        currentDifficultyIndex += offset;

        if (currentDifficultyIndex < 0) {
            currentDifficultyIndex = difficultyList.length - 1;
        } else if (currentDifficultyIndex >= difficultyList.length) {
            currentDifficultyIndex = 0;
        }

        currentDifficulty = difficultyList[currentDifficultyIndex];
    }

    private Object[][] getExampleRankingForDifficulty(String difficulty) {
        if (difficulty.equals("easy")) {
            return new Object[][]{
                    {1, "Player1", 120},
                    {2, "Player2", 150},
                    {3, "Player3", 180}
            };
        } else if (difficulty.equals("normal")) {
            return new Object[][]{
                    {1, "Player4", 200},
                    {2, "Player5", 220},
                    {3, "Player6", 240}
            };
        } else if (difficulty.equals("hard")) {
            return new Object[][]{
                    {1, "Player7", 300},
                    {2, "Player8", 320},
                    {3, "Player9", 350}
            };
        }

        return new Object[][]{};
    }

    private void showExampleRankingForDifficulty(String difficulty) {
        Object[][] data = getExampleRankingForDifficulty(difficulty);
        tableModel.setDataVector(data, new String[]{"Rank", "Player", "Time"});
    }

    private void updateDifficultyLabel() {
        difficultyLabel.setText(currentDifficulty.toUpperCase());
    }
}
