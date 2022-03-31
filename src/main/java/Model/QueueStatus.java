package Model;

import java.util.ArrayList;
import java.util.List;

public class QueueStatus {
    private int time;
    private int clientsNo;
    private List < Integer > status;

    public QueueStatus(int time) {
        status = new ArrayList<Integer>();
        this.time = time;
    }

    public int getTime() {
        return time;
    }

    public void setClientsNo(int clientsNo) {
        this.clientsNo = clientsNo;
    }

    public List<Integer> getStatus() {
        return status;
    }

    public void setStatus(List<Integer> status) {
        this.status = status;
    }

    public int getClientsNo() {
        return clientsNo;
    }

    @Override
    public String toString() {
        return "QueueStatus{" +
                "time=" + time +
                ", clientsNo=" + clientsNo +
                ", status=" + status +
                '}';
    }
}
