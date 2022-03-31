package GUI;

import BusinessLogic.SelectionPolicy;
import BusinessLogic.SimulationManager;
import BusinessLogic.Strategy;
import Exceptions.IncorrectInput;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.text.AbstractDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SimulationFrame extends JFrame {

    private JLabel titleLabel = new JLabel("Setting application...");
    private JButton validateButton = new JButton("Validate");
    private JButton startButton = new JButton("Start");
    private JButton exitButton = new JButton("Exit");

    private JTextField noOfClients = new JTextField(1);
    private JTextField noOfQueues = new JTextField(1);
    private JTextField simulationInterval = new JTextField(1);
    private JTextField minArrivalTime = new JTextField(1);
    private JTextField maxArrivalTime = new JTextField(1);
    private JTextField minServiceTime = new JTextField(1);
    private JTextField maxServiceTime = new JTextField(1);
    private JComboBox strategy = new JComboBox();

    private  int noClients, noQueues, simInterval , minArrTime , maxArrTime , minSerTime , maxSerTime;
    boolean validated = false;

    public SimulationFrame()
    {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Queues Management");
        this.setSize(500, 600);
        JPanel mainPanel = new JPanel();

        titleLabel.setHorizontalAlignment(SwingConstants.HORIZONTAL);
        titleLabel.setBorder(new LineBorder(Color.BLACK));
        Font newLabelFont=new Font("Serif", Font.BOLD + Font.ITALIC,35);
        titleLabel.setFont(newLabelFont);

        JPanel writePanel = new JPanel();
        writePanel.setLayout(new GridLayout(7,2));

        JLabel noOfClientsLabel = new JLabel("Number of clients");
        JLabel noOfQueuesLabel = new JLabel("Number of queues");
        JLabel simulationIntervalLabel = new JLabel("Simulation Interval");
        JLabel minArrivalTimeLabel = new JLabel("Minimum Arrival Time");
        JLabel maxArrivalTimeLabel = new JLabel("Maximum Arrival Time");
        JLabel minServiceTimeLabel = new JLabel("Minimum Service Time");
        JLabel maxServiceTimeLabel = new JLabel("Maximum Service Time");
        
        noOfClientsLabel.setFont(new Font("Serif", Font.BOLD, 18));
        noOfQueuesLabel.setFont(new Font("Serif", Font.BOLD, 18));
        simulationIntervalLabel.setFont(new Font("Serif", Font.BOLD, 18));
        minArrivalTimeLabel.setFont(new Font("Serif", Font.BOLD, 18));
        maxArrivalTimeLabel.setFont(new Font("Serif", Font.BOLD, 18));
        minServiceTimeLabel.setFont(new Font("Serif", Font.BOLD, 18));
        maxServiceTimeLabel.setFont(new Font("Serif", Font.BOLD, 18));

        noOfClientsLabel.setHorizontalAlignment(SwingConstants.HORIZONTAL);
        noOfQueuesLabel.setHorizontalAlignment(SwingConstants.HORIZONTAL);
        simulationIntervalLabel.setHorizontalAlignment(SwingConstants.HORIZONTAL);
        minArrivalTimeLabel.setHorizontalAlignment(SwingConstants.HORIZONTAL);
        maxArrivalTimeLabel.setHorizontalAlignment(SwingConstants.HORIZONTAL);
        minServiceTimeLabel.setHorizontalAlignment(SwingConstants.HORIZONTAL);
        maxServiceTimeLabel.setHorizontalAlignment(SwingConstants.HORIZONTAL);

        writePanel.add(noOfClientsLabel);
        writePanel.add(noOfClients);
        writePanel.add(noOfQueuesLabel);
        writePanel.add(noOfQueues);
        writePanel.add(simulationIntervalLabel);
        writePanel.add(simulationInterval);
        writePanel.add(minArrivalTimeLabel);
        writePanel.add(minArrivalTime);
        writePanel.add(maxArrivalTimeLabel);
        writePanel.add(maxArrivalTime);
        writePanel.add(minServiceTimeLabel);
        writePanel.add(minServiceTime);
        writePanel.add(maxServiceTimeLabel);
        writePanel.add(maxServiceTime);
        mainPanel.add(writePanel);

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout());
        buttonsPanel.add(validateButton);
        buttonsPanel.add(startButton);
        buttonsPanel.add(exitButton);

        JPanel strategyPanel= new JPanel();
        strategyPanel.setLayout(new FlowLayout());
        JLabel textStrategy = new JLabel("Choose your strategy");
        strategy.addItem("SHORTEST_QUEUE");
        strategy.addItem("SHORTEST_TIME");
        strategyPanel.add(textStrategy);
        strategyPanel.add(strategy);

        mainPanel.setLayout(new GridLayout(3,1));
        mainPanel.add(writePanel);
        mainPanel.add(strategyPanel);
        mainPanel.add(buttonsPanel);

        this.add(mainPanel);
        this.setListeners();
        this.addDocumentFilters();
    }

    public int[] getData()
    {
        int[] ret = new int[7];

        noClients = Integer.parseInt(noOfClients.getText());
        noQueues = Integer.parseInt(noOfQueues.getText());
        simInterval = Integer.parseInt(simulationInterval.getText());
        minArrTime = Integer.parseInt(minArrivalTime.getText());
        maxArrTime = Integer.parseInt(maxArrivalTime.getText());
        minSerTime = Integer.parseInt(minServiceTime.getText());
        maxSerTime = Integer.parseInt(maxServiceTime.getText());

        ret[0] = noClients;
        ret[1] = noQueues;
        ret[2] = simInterval;
        ret[3] = minSerTime;
        ret[4] = maxSerTime;
        ret[5] = minArrTime;
        ret[6] = maxArrTime;

        return ret;
    }

    public SelectionPolicy getStrategy()
    {
        if(strategy.getSelectedItem() == "SHORTEST_QUEUE")
            return SelectionPolicy.SHORTEST_QUEUE;

        return SelectionPolicy.SHORTEST_TIME;
    }

    public boolean getValidated()
    {
        return validated;
    }

    void addDocumentFilters()
    {
        AbstractDocument[] d = new AbstractDocument[7];

         d[0] = (AbstractDocument) noOfClients.getDocument();
         d[1] = (AbstractDocument) noOfQueues.getDocument();
         d[2] = (AbstractDocument) simulationInterval.getDocument();
         d[3] = (AbstractDocument) minArrivalTime.getDocument();
         d[4] = (AbstractDocument) maxArrivalTime.getDocument();
         d[5] = (AbstractDocument) minServiceTime.getDocument();
         d[6] = (AbstractDocument) maxServiceTime.getDocument();

         for(int i = 0; i < 7; i++)
             d[i].setDocumentFilter(new MyDocumentFilter());
    }

    public void addStartListener(ActionListener call)
    {
        startButton.addActionListener(call);
    }

    void setListeners()
    {
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    dispose();
            }
        });

        validateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try{
                    noClients = Integer.parseInt(noOfClients.getText());
                    noQueues = Integer.parseInt(noOfQueues.getText());
                    simInterval = Integer.parseInt(simulationInterval.getText());
                    minArrTime = Integer.parseInt(minArrivalTime.getText());
                    maxArrTime = Integer.parseInt(maxArrivalTime.getText());
                    minSerTime = Integer.parseInt(minServiceTime.getText());
                    maxSerTime = Integer.parseInt(maxServiceTime.getText());
                    JOptionPane.showMessageDialog(SimulationFrame.this, "Data Validated!" + '\n' + "Ready for simulation?");
                    validated = true;
                }catch(Exception ex)
                {
                    ex = new IncorrectInput("Error: Input Incorrect!" + '\n');
                    JOptionPane.showMessageDialog(SimulationFrame.this,ex);
                }

            }
        });

    }

}
