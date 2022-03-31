package BusinessLogic;

import GUI.AnimationGui;
import GUI.SimulationFrame;
import Model.Client;
import Model.LogWritter;
import Model.Producator;
import Model.QueueStatus;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;

public class SimulationManager implements Runnable {
    public int timeLimit;
    public int minArrivalTime;
    public int maxArrivalTime;
    public int maxProcessingTime;
    public int minProcessingTime;
    public int numberOfServers;
    public int numberOfClients;
    public SelectionPolicy selectionPolicy;

    private SimulationFrame frame;
    private AnimationGui frame2;
    private List < Client > generatedClients;

    public SimulationManager()
    {
        frame = new SimulationFrame();
        frame.setVisible(true);
        addListeners();
    };

    public SimulationManager(List<Client> generatedClients) {
        this.generatedClients = generatedClients;
        frame = new SimulationFrame();
        frame.setVisible(true);
    }

    public void addListeners()
    {
        frame.addStartListener(new addStartSimulation());
    }

    public void setFromFrame(SimulationFrame frame)
    {
        int[] settings = frame.getData();
        numberOfClients = settings[0];
        numberOfServers = settings[1];
        timeLimit = settings[2];
        minProcessingTime = settings[3];
        maxProcessingTime = settings[4];
        minArrivalTime = settings[5];
        maxArrivalTime = settings[6];
        selectionPolicy = frame.getStrategy();
    }

    public class addStartSimulation implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(frame.getValidated())
            {
                setFromFrame(frame);

                List < QueueStatus > queueStatuses = new ArrayList<QueueStatus>();

                LogWritter l = new LogWritter(new int[]{numberOfClients, minArrivalTime, maxArrivalTime, minProcessingTime, maxProcessingTime}, timeLimit, numberOfServers );

                if(selectionPolicy == SelectionPolicy.SHORTEST_TIME) {
                    try {
                        l.writeInLogs("interface.txt");
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }

                else
                {
                    l.setQueueStrategy();
                    try {
                        l.writeInLogs("interface.txt");
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }

                queueStatuses = l.getStats();

                try {
                    frame2 = new AnimationGui(numberOfClients, numberOfServers, timeLimit, queueStatuses);
                } catch (IOException | InterruptedException ex) {
                    ex.printStackTrace();
                }

                frame2.setVisible(true);

            }
            else
                JOptionPane.showMessageDialog(frame,"Try to validate data first!");
        }
    }

    public void setGeneratedClients() throws InterruptedException {
        Producator p = new Producator(new int[] {numberOfClients, minArrivalTime, maxArrivalTime, minProcessingTime, maxProcessingTime});
        p.start();
        sleep(100);
        generatedClients = p.getClients();
    }

    @Override
    public void run() {
        int currentTime = 0;
        while(currentTime < timeLimit)
        {
            try {


                currentTime++;
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public static void main(String[] args) {
        SimulationManager gen = new SimulationManager();
        Thread t = new Thread(gen);
        t.start();
    }

}
