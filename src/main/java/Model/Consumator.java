package Model;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

import static Model.LogWritter.globalTime;
import static Model.LogWritter.simulationInterval;

public class Consumator extends Thread {
    BlockingQueue<Client> queue;
    int waitingTime = 0;

    public Consumator()
    {
        queue = new LinkedBlockingDeque<Client>(100);
    }

    public int getWaitingTime()
    {
        return waitingTime;
    }

    public int size()
    {
        return queue.size();
    }

    public List <Client> getQueue()
    {
        return queue.stream().toList();
    }

    public void add(Client a)
    {
        queue.add(a);
        waitingTime += a.getServiceTime();
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i <= simulationInterval; i++) {
                while(!queue.isEmpty() && queue.peek().getTotalTime() == globalTime) {
                    Client aux = queue.take();
                    waitingTime -= aux.getServiceTime();
                    System.out.println("Client scos " + aux);
                }
            sleep(1000);
        }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
