package Model;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;

public class LogWritter {
    private Producator producer;
    private int noOfQueues;
    private List < QueueStatus > stats;

    private double averageServiceTime = 0;
    private double averageWaitingTime = 0;
    private double peekHour;

    public static int globalTime = 0;
    public static int simulationInterval;

    private boolean queueStrategy = false;

    public LogWritter(int[] data, int simulationInterval1, int noOfQueues) {
        this.producer = new Producator(data);
        simulationInterval = simulationInterval1;
        this.noOfQueues = noOfQueues;
    }

    public void writeInLogs(String fileName) throws InterruptedException, IOException {
        stats = new ArrayList<QueueStatus>();

        Consumator[] c = new Consumator[noOfQueues];
        for (int i = 0; i < noOfQueues; i++)
            c[i] = new Consumator();

        producer.start();
        sleep(10);

        List<Client> clients = producer.getClients();
        boolean[] waitingList = new boolean[clients.size()];

        for(Client a : clients)
            averageServiceTime += a.getServiceTime();

        FileWriter myWriter = new FileWriter(fileName);

        for (int i = 0; i < noOfQueues; i++)
            c[i].start();

        int maxNoOfClients = 0;

        while (globalTime <= simulationInterval) {

            stats.add(new QueueStatus(globalTime));

            myWriter.write("Time: " + globalTime + '\n');

            for (Client aux : clients) {
                if (aux.getArrivalTime() > globalTime)
                    break;

                int Min = Integer.MAX_VALUE, poz = 0;

                if (aux.getArrivalTime() == globalTime) {

                    if(!queueStrategy) {
                        for (int i = 0; i < noOfQueues; i++)
                            if (c[i].getWaitingTime() < Min) {
                                poz = i;
                                Min = c[i].getWaitingTime();
                            }
                    }

                    else
                        {
                            for (int i = 0; i < noOfQueues; i++)
                                if (c[i].size() < Min) {
                                    poz = i;
                                    Min = c[i].size();
                                }
                        }

                    int dif = 0;

                    if (c[poz].size() > 0)
                            dif = aux.getServiceTime() + (c[poz].getQueue()).get(c[poz].size() - 1).getTotalTime() - globalTime;

                    averageWaitingTime += dif;

                    if (dif > 0)
                        c[poz].add(new Client(aux.getID(), aux.getArrivalTime(), dif));
                    else
                        c[poz].add(aux);

                    waitingList[clients.indexOf(aux)] = true;
                }
            }

            writing(myWriter, clients, waitingList, c);

            int waitingClients = 0;
            for(int i = 0; i < clients.size(); i++)
                if(!waitingList[i]) waitingClients++;

            stats.get(stats.size() - 1).setStatus(getQueuesStatus(c));
            stats.get(stats.size() - 1).setClientsNo(waitingClients);

            if(getNoOfClientsInQueues(c) > maxNoOfClients)
            {
                maxNoOfClients = getNoOfClientsInQueues(c);
                peekHour = globalTime;
            }

            globalTime++;
            sleep(1000);
        }

        myWriter.write("Average waiting time " + averageWaitingTime / clients.size() + '\n');
        myWriter.write("Average service time " + averageServiceTime / clients.size() + '\n');
        myWriter.write("Peek Hour " + peekHour + '\n');
        globalTime = 0;
        myWriter.close();
    }

    public void writing(Writer m, List<Client> clients, boolean b[], Consumator c[]) throws IOException {
        m.write("Waiting Clients:");

        for (int i = 0; i < clients.size(); i++)
            if (!b[i])
                m.write(String.valueOf(clients.get(i)));

        m.write('\n');

        int currentQueue = 1;

        for (Consumator aux : c) {
            m.write("Queue " + currentQueue + ": ");
            if (aux.size() == 0)
                m.write("closed" + '\n');

            else {
                List<Client> auxList = aux.getQueue();
                for (Client clientFromQueue : auxList)
                    m.write(String.valueOf(clientFromQueue));
                m.write('\n');
            }

            currentQueue++;

        }

        m.write('\n');
        m.write('\n');
        m.write('\n');
    }

    public static int getNoOfClientsInQueues(Consumator c[])
    {
        int sum = 0;
            for(Consumator a : c)
                sum += a.size();

            return sum;
    }

    public static List<Integer> getQueuesStatus(Consumator c[])
    {
        List<Integer> queueStats = new ArrayList<Integer>();

        for(Consumator a : c)
            queueStats.add(a.size());

        return queueStats;

    }

    public List<QueueStatus> getStats() {
        return stats;
    }

    public void setQueueStrategy()
    {
        queueStrategy = true;
    }

}
