import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class TitleScene extends JPanel {
    public TitleScene(Main main) {

        setLayout(null);


        JPanel p1 = new JPanel();

        p1.setBackground(Color.WHITE);
        p1.setLayout(new BorderLayout());
        p1.setBounds(150, 100, 400, 400);
        add(p1);



        JPanel p2 = new JPanel();
        p2.setBackground(Color.DARK_GRAY);
        p2.setLayout(new FlowLayout());
        p1.add(p2, BorderLayout.SOUTH);



        JLabel title = new JLabel("같은 그림 찾기");
        title.setFont(new Font("SansSerif", Font.PLAIN, 45));
        //title.setForeground(Color.);
        p1.add(title, BorderLayout.CENTER);



        JMenuItem btn_E = new JMenuItem("Easy");
        btn_E.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                main.setInGameScene(InGameScene.EASY);
            }
        });


        JMenuItem btn = new JMenuItem("Normal");
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                main.setInGameScene(InGameScene.NORMAL);
            }
        });


        JMenuItem btn_H = new JMenuItem("HARD");
        btn_H.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                main.setInGameScene(InGameScene.HARD);
            }
        });


        JMenuItem btn_R = new JMenuItem("Rank");
        btn_R.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });


        JMenuItem btn_Q = new JMenuItem("Exit");
        btn_Q.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });


        JMenuBar mb = new JMenuBar();
        JMenu m1 = new JMenu("Start");
        JMenu m2 = new JMenu("Option");

        m1.add(btn_E);
        m1.add(btn);
        m1.add(btn_H);
        m2.add(btn_R);
        m2.add(btn_Q);

        mb.add(m1);
        mb.add(m2);

        p2.add(mb, BorderLayout.SOUTH);
        mb.setPreferredSize(new Dimension(90, 50));
    }
}
