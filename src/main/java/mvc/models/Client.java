package mvc.models;

public class Client {

    // Declararea campurilor specifice unui client
    private int ID;
    private int serviceTime;
    private int arrivalTime;

    // Constructorul clasei Client
    public Client(int ID, int arrivalTime, int serviceTime) {

        this.ID = ID;
        this.arrivalTime = arrivalTime;
        this.serviceTime = serviceTime;
    }

    // Getters si setters

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getServiceTime() {
        return serviceTime;
    }

    public void setServiceTime(int serviceTime) {
        this.serviceTime = serviceTime;
    }

    // Metoda de afisare a datelor corespunzatoare unui client
    @Override
    public String toString() {
        return "(" + ID + "," + arrivalTime + "," + serviceTime + ")";
    }
}