package GUI;

import Model.QueueStatus;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class AnimationGui extends JFrame {

    int noOfQueues, noOfClients, simulationInterval, k = 0;
    private java.util.List<QueueStatus> queueStatusList = new ArrayList<QueueStatus>();

    BufferedImage img1 = ImageIO.read(new File("C:\\Users\\Alex\\Documents\\probleme_Java\\pt2022_30223_alex_tiugan_assignment_2\\src\\main\\java\\coada.png"));
    BufferedImage img2 = ImageIO.read(new File("C:\\Users\\Alex\\Documents\\probleme_Java\\pt2022_30223_alex_tiugan_assignment_2\\src\\main\\java\\client.png"));

    ImageIcon coada = new ImageIcon(img1);
    ImageIcon client = new ImageIcon(img2);

    Timer t;
    JTable table;
    String[] columns = {"QueueNo", "Client", "Client", "Client", "Client", "Client", "Client", "Client"};

    JPanel leftPanel, rightPanel, UpPanel1, UpPanel2;
    JPanel mainPanel = new JPanel();
    boolean isStarted = false;

    public AnimationGui(int nC, int nQ, int sM, java.util.List<QueueStatus> q) throws IOException, InterruptedException {
        noOfClients = nC;
        noOfQueues = nQ;
        simulationInterval = sM;
        queueStatusList = q;

        this.setTitle("Animation Scene");
        this.setSize(1300, 800);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        leftPanel = new JPanel(new FlowLayout());
        rightPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(2, 2));
        UpPanel1 = new JPanel();
        UpPanel2 = new JPanel();
        UpPanel1.setPreferredSize(new Dimension(375, 150));
        UpPanel2.setPreferredSize(new Dimension(375, 150));
        leftPanel.setPreferredSize(new Dimension(375, 500));
        rightPanel.setPreferredSize(new Dimension(375, 500));
        JLabel timerLabel = new JLabel("Timer: 0");

        //timer
        timerLabel.setHorizontalAlignment(SwingConstants.HORIZONTAL);
        timerLabel.setBorder(new LineBorder(Color.BLACK));
        Font newLabelFont = new Font("Serif", Font.BOLD + Font.ITALIC, 35);
        timerLabel.setFont(newLabelFont);

        JButton startButton = new JButton("Press here to start");
        startButton.setFont(newLabelFont);
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isStarted = true;
            }
        });

        UpPanel1.add(startButton);

        t = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (k <= simulationInterval && isStarted) {
                        try {
                            updateDisplay(queueStatusList.get(k));
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    timerLabel.setText("Timer: " + k);
                    k++;
                }
            }
        });
        t.start();

        UpPanel2.add(timerLabel);

//tabel
        Object[][] data = new Object[noOfQueues][1];
        String[] columns = {"QueueNo", "Client", "Client", "Client", "Client", "Client", "Client", "Client"};

        for (int i = 0; i < noOfQueues; i++) {
            data[i][0] = coada;
        }

        DefaultTableModel model = new DefaultTableModel(data, columns);

        table = new JTable(model) {
            public Class getColumnClass(int column) {
                return (column >= 0) ? Icon.class : Object.class;
            }
        };

        table.setBounds(30, 40, 200, 300);
        table.setRowHeight(50);
        JScrollPane scrollPane = new JScrollPane(table);
        getContentPane().add(scrollPane);


        leftPanel.add(table);
        addClients(noOfClients);

        mainPanel.add(UpPanel1);
        mainPanel.add(UpPanel2);
        mainPanel.add(leftPanel);
        mainPanel.add(rightPanel);
        this.add(mainPanel);

    }

    public void addClients(int num) throws IOException {

        for (int i = 0; i < num; i++) {
            JLabel pic2 = new JLabel(new ImageIcon(img2));
            rightPanel.add(pic2);
        }

        repaint();
    }

    public void updateDisplay(QueueStatus q) throws IOException {
        leftPanel.remove(table);
        rightPanel.removeAll();

        java.util.List<Integer> queueSizes = q.getStatus();

        int sum = 0;
        for(int i : queueSizes)
            sum += i;

        Object[][] data1 = new Object[noOfQueues][20];

        for (int i = 0; i < noOfQueues; i++) {
            data1[i][0] = coada;
        }

        for(int i = 0; i < noOfQueues; i++)
            for(int j = 0; j < queueSizes.get(i); j++)
                data1[i][j + 1] = client;

        DefaultTableModel model1 = new DefaultTableModel(data1, columns);

        table = new JTable(model1) {
            public Class getColumnClass(int column) {
                return (column >= 0) ? Icon.class : Object.class;
            }
        };

        table.setRowHeight(50);

        invalidate();
        validate();
        repaint();


        addClients(q.getClientsNo());
        rightPanel.updateUI();
        leftPanel.updateUI();
        leftPanel.add(table);
    }
}
