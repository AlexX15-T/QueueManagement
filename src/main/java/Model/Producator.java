package Model;

import java.util.*;

public class Producator extends Thread{
    private List <Client> clients = new ArrayList<Client>();
    private int noOfClientsToBeProduced;
    private int minServTime, maxServTime, minArrTime, maxArrTime;

    public Producator(int[] n){
        noOfClientsToBeProduced = n[0];
        minArrTime = n[1];
        maxArrTime = n[2];
        minServTime = n[3];
        maxServTime = n[4];
    }

    public List<Client> getClients() {
        return clients;
    }

    @Override
    public String toString() {
        return "Producator{" +
                "clients=" + clients +
                '}';
    }

    @Override
    public void run() {
        Random rand = new Random();
        for(int i = 0; i < noOfClientsToBeProduced; i++) {

            if(maxServTime - minServTime <= 0 || maxArrTime - minArrTime <= 0)
                clients.add(new Client(100 + i, 0 , 0));
            else {
                int servTime = rand.nextInt(maxServTime - minServTime) + minServTime;
                int arvTime = rand.nextInt(maxArrTime - minArrTime) + minArrTime;
                clients.add(new Client(100 + i, arvTime, servTime));
            }
        }
        clients.sort(Client::compareTo);
    }
}
