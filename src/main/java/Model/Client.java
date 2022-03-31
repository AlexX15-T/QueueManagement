package Model;

public class Client implements Comparable {
    private int ID;
    private int arrivalTime;
    private int serviceTime;

    Client()
    {
        this.ID = 0;
        this.arrivalTime = 0;
        this.serviceTime = 0;
    }

    public Client(int ID, int arrivalTime, int serviceTime) {
        this.ID = ID;
        this.arrivalTime = arrivalTime;
        this.serviceTime = serviceTime;
    }

    public int getTotalTime()
    {
        return arrivalTime + serviceTime;
    }

    @Override
    public String toString() {
        return " (" + ID + "," + arrivalTime + "," + serviceTime + ") ";
    }

    public int getID() {
        return ID;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getServiceTime() {
        return serviceTime;
    }

    @Override
    public int compareTo(Object o) {
        return this.arrivalTime - ((Client)o).arrivalTime;
    }
}
