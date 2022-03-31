package GUI;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Animation extends JFrame {

    int noOfQueues, noOfClients, simulationInterval, k = 0;

    Timer t;
    JPanel leftPanel, rightPanel, UpPanel;
    JPanel mainPanel = new JPanel();
    boolean isStarted = false;

    public Animation(int nC, int nQ, int sM) throws IOException {
        noOfClients = nC;
        noOfQueues = nQ;
        simulationInterval = sM;

        this.setTitle("Animation Scene");
        this.setSize(750, 750);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        leftPanel = new JPanel();
        rightPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(2,2));

        ///timer
        UpPanel = new JPanel();
        JLabel timerLabel = new JLabel();

        timerLabel.setHorizontalAlignment(SwingConstants.HORIZONTAL);
        timerLabel.setBorder(new LineBorder(Color.BLACK));
        Font newLabelFont=new Font("Serif", Font.BOLD + Font.ITALIC,35);
        timerLabel.setFont(newLabelFont);

        JButton startButton = new JButton("Press here to start");
        startButton.setFont(newLabelFont);
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isStarted = true;
            }
        });

        UpPanel.add(startButton);

        t = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(k <= simulationInterval && isStarted)
                    timerLabel.setText("Timer: " + k);
                k++;
            }
        });
        t.start();


        ///table
        String[] columns = {"QueueNo", "Client", "Client", "Client"};

        Object[][] data = {
                {0, new ImageIcon("client.png"), new ImageIcon("client.png"),new ImageIcon("client.png")}
        };

        DefaultTableModel model = new DefaultTableModel(data, columns);

        JTable table = new JTable(model);

        table.setRowHeight(60);
        JScrollPane scrollPane = new JScrollPane(table);
        getContentPane().add(scrollPane);

        leftPanel.add(table);
        mainPanel.add(UpPanel);
        mainPanel.add(leftPanel);
        mainPanel.add(rightPanel);
        this.add(mainPanel);

    }



}
