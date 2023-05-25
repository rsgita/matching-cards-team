import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import requstor.Requestor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.ConnectException;
import java.util.Arrays;

class LeaderboardFrame extends JFrame {
    private Leaderboard leaderboard;
    private LeaderboardScene leaderboardScene;

    public LeaderboardFrame(Leaderboard leaderboard) {
        this.leaderboard = leaderboard;
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null);
    }

    // 강조 표시가 있으면 강조표시 존재하는 패널
    // 강조 표시 없으면 강조표시 없는 패널
    // 두개로 분리하여 생성
    public void showLeaderboard() {
        this.leaderboardScene = new LeaderboardScene(this, this.leaderboard);
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

    private LeaderboardFrame frame;
    private Leaderboard leaderboard;

    public LeaderboardScene(LeaderboardFrame frame, Leaderboard leaderboard) {
        this.frame = frame;
        this.leaderboard = leaderboard;

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
        Leaderboard leaderboard = LeaderboardScene.this.leaderboard;

        JSONParser jsonParser = new JSONParser();
        String rankString = leaderboard.get(difficulty);

        // 오류 발생시 임시값 리턴
        if (rankString == null) {
            return new Object[][]{
                    {1, "Player1", 120},
                    {2, "Player2", 150},
                    {3, "Player3", 180}
            };
        }

        try {
            JSONArray jsonArray = (JSONArray) jsonParser.parse(rankString);
            Object[][] rankArray = new Object[jsonArray.size()][];

            for (int i = 0; i < jsonArray.size(); i++) {
                var player = (JSONObject) jsonArray.get(i);
                rankArray[i] = new Object[]{i + 1, player.get("name"), player.get("sec")};
            }

            System.out.println(Arrays.deepToString(rankArray));

            return rankArray;

        } catch (Exception e) {
            e.printStackTrace();
        }


        return new Object[][]{
                {1, "Player1", 120},
                {2, "Player2", 150},
                {3, "Player3", 180}
        };
    }

    private void showExampleRankingForDifficulty(String difficulty) {
        Object[][] data = getExampleRankingForDifficulty(difficulty);
        tableModel.setDataVector(data, new String[]{"Rank", "Player", "Time"});
    }

    private void updateDifficultyLabel() {
        difficultyLabel.setText(currentDifficulty.toUpperCase());
    }
}
