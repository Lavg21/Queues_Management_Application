package mvc.models;

import java.util.ArrayList;
import java.util.List;

// Fiecare coada o sa fie un thread ca sa preia clienti in paralel
public class Queue extends Thread {

    // Declararea campurilor necesare
    private List<Client> clients;

    private int queueNumber;
    private int waitingTime;
    private boolean open; // variabila care stabileste daca coada este deschisa sau nu

    // Constructor-ul clasei Queue care primeste ca parametru numarul cozii
    public Queue(int queueNumber) {

        this.queueNumber = queueNumber;
        this.clients = new ArrayList<>(); // initializam lista de clienti
        this.waitingTime = 0; // setam timpul de asteptare la coada cu 0
    }

    // Getters si setters

    public void setOpen(boolean open) {
        this.open = open;
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    public int getQueueNumber() {
        return queueNumber;
    }

    public List<Client> getClients() {
        return clients;
    }

    // Metoda de adaugare a unui client in coada
    public void adaugareClient(Client client) {

        this.clients.add(client);   // adaugam clientul in lista de clienti
        this.waitingTime += client.getServiceTime();    // adaugam timpul de procesare al noului client la timpul de asteptare a cozii
    }

    // Metoda de functionare a unei cozi
    @Override
    public void run() {

        // Cat timp coada e deschisa
        while (open) {
            // Si daca lista de clienti nu e vida
            if (clients.size() != 0) {

                Client primulClient = clients.get(0);   // primul client care asteapta la coada
                int timp = primulClient.getServiceTime(); // timpul de procesare a clientului care e primul la coada

                if (timp > 1) { // daca timpul de procesare e mai mare ca 1, adica daca inca mai trebuie sa fie procesat

                    primulClient.setServiceTime(timp - 1);  // ii decrementam primului client timpul de procesare

                    this.waitingTime--; // decrementam timpul de asteptare al cozii
                } else {  // timp == 1, adica terminam procesarea cu clientul curent

                    clients.remove(clients.get(0)); // stergem primul client din coada pentru ca timpul lui de procesare s-a incheiat

                    this.waitingTime--; // decrementam timpul de asteptare al cozii
                }
            }

            try {
                Thread.sleep(1000); // asteapta 1 secunda
            } catch (InterruptedException ex) {

                ex.printStackTrace();
            }
        }
    }

}
