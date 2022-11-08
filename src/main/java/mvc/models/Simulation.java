package mvc.models;

import mvc.view.View;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Simulation extends Thread {

    // Declararea campurilor necesare
    private View view;

    private AtomicInteger timpCurent;

    private int numarCozi;
    private int timpAsteptareTotal;
    private int timpProcesareTotal;
    private int timpMaxSimulare;
    private int nrClientiProcesati;
    private int numarClienti;

    private int tMinArrival;
    private int tMaxArrival;
    private int tMinService;
    private int tMaxService;

    private int peakHour;
    private int peakHourClients;

    private BlockingQueue<Queue> queues;
    private BlockingQueue<Client> clients;

    // Constructor-ul clasei Simulation
    public Simulation(View view, int numarCozi, int numarClienti, int timpMaxSimulare, int tMinArrival, int tMaxArrival, int tMinService, int tMaxService) {

        this.view = view;
        this.timpCurent = new AtomicInteger();
        this.clients = new LinkedBlockingQueue<>();
        this.queues = new LinkedBlockingQueue<>();
        this.numarCozi = numarCozi;

        // Adaugam numarul de cozi primit ca parametru
        for (int i = 0; i < this.numarCozi; i++) {

            Queue q = new Queue(i + 1); // pentru ca indexarea incepe de la 0
            this.queues.add(q);
        }

        this.numarClienti = numarClienti;
        this.timpMaxSimulare = timpMaxSimulare;
        this.nrClientiProcesati = 0; // initializam cu 0 clientii care au fost in coada
        this.tMinArrival = tMinArrival;
        this.tMaxArrival = tMaxArrival;
        this.tMinService = tMinService;
        this.tMaxService = tMaxService;

        this.peakHour = 0; // initializam cu 0 ora de varf (cand sunt cei mai multi clienti la coada)

        this.generareClientiRandom(); // generam clienti random
    }

    private int generareNumarInterval(int a, int b) {

        Random random = new Random();

        return random.nextInt(b - a) + a; // generam numere random dintr-un interval
    }

    private void generareClientiRandom() {
        // Parcurgem lista de clienti pana la numarul de clienti primit de pe interfata
        for (int i = 0; i < this.numarClienti; i++) {

            // Generam random un timp de arrival si service
            int randomTArrival = this.generareNumarInterval(tMinArrival, tMaxArrival);
            int randomTService = this.generareNumarInterval(tMinService, tMaxService);

            // Clientul o sa aiba un id crescator si cele doua campuri de timp generate random
            Client client = new Client(i + 1, randomTArrival, randomTService);

            this.clients.add(client); // adaugam clientul generat in lista
        }
    }

    private Queue coadaOptima() {

        // Intr-un BlockingQueue, peek returneaza primul element din coada (in cazul nostru, un obiect de tipul Queue)
        Queue coadaMinima = this.queues.peek(); // initializam minimul cu prima coada

        int timpMinim = coadaMinima.getWaitingTime(); // si timpul minim al primului client din coada

        // Parcurgem cu for each lista de cozi
        for (Queue q : this.queues) {
            // Daca timpul de asteptare al cozii este mai mic decat timpul minim
            if (q.getWaitingTime() < timpMinim) {
                // Reactualizam timpul minim si coada care are timpul minim
                timpMinim = q.getWaitingTime();
                coadaMinima = q;
            }
        }

        this.timpAsteptareTotal += timpMinim; // crestem timpul de asteptare total al cozii

        return coadaMinima;
    }

    // Metoda de stergere a clientilor care au fost deserviti la coada
    private void stergereClientiGata() {

        List<Client> stersi = new ArrayList<>(); // initializam lista de clienti de sters

        // Parcurgem cu for each lista de clienti
        for (Client client : this.clients) {

            if (client.getArrivalTime() == this.timpCurent.get())
                stersi.add(client); // adaugam clientul in lista de sters
        }

        this.clients.removeAll(stersi); // stergem din lista de clienti, clientii care trebuie stersi
    }

    private void deschidereCozi() {

        // Parcurgem cozile cu for each
        for (Queue coada : this.queues) {
            // Pornim un thread pentru fiecare dintre acestea
            coada.start();
            coada.setOpen(true); // deschidem fiecare coada
        }
    }

    private void inchidereCozi() {

        // Parcurgem cozile
        for (Queue coada : this.queues)
            coada.setOpen(false); // inchidem fiecare coada
    }

    public int nrClientiInCozi() {

        int nrClienti = 0; // initializam cu 0 intial

        // Parcurgem cozile cu for each
        for (Queue q : this.queues) {
            nrClienti += q.getClients().size(); // crestem numarul de clienti
        }

        return nrClienti; // numarul total de clienti din cele doua cozi
    }

    // Metoda de scriere a rezultatelor in fisier
    private void scrieInFisier(String deScris) {

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("D:\\Facultate\\UTCN\\An II\\Semestrul 2\\Tehnici de programare\\Laboratoare\\PT2022_30228_Gavrilescu_AndreeaLavinia_2\\src\\main\\java\\mvc\\models\\log.txt"));
            bw.append(deScris);
            bw.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public synchronized void run() {

        String deScris = ""; // string care va fi folosit pentru a scrie rezultatul in fisier

        this.deschidereCozi(); // deschidem cozile

        // Metoda get() obtine valoarea unui AtomicInteger
        while (this.timpCurent.get() < this.timpMaxSimulare) {

            String str = "";

            System.out.println("Time " + timpCurent.get()); // afisare consola
            deScris += "Time " + timpCurent.get() + "\n"; // afisare in fisier
            str += "Time " + timpCurent.get() + "\n"; // afisare interfata

            // Parcurgem lista de clienti
            for (Client client : this.clients) {

                if (this.timpCurent.get() == client.getArrivalTime()) {

                    if (client.getArrivalTime() + client.getServiceTime() <= timpMaxSimulare) {

                        Queue coadaAleasa = this.coadaOptima();
                        coadaAleasa.adaugareClient(client); // adaugam clientul in coada cu timpul de asteptare cel mai mic

                        this.timpProcesareTotal += client.getServiceTime(); // timpul de service al tuturor clientilor

                        this.nrClientiProcesati++; // numarul de clienti deserviti
                    }

                }
            }

            this.stergereClientiGata(); // stergerea clientilor deserviti

            System.out.print("Waiting clients: ");
            deScris += "Waiting clients: ";
            str += "Waiting clients: ";

            // Daca lista de clienti e goala
            if (this.clients.size() == 0) {
                System.out.println("none");
                deScris += "none\n";
                str += "none\n";
            } else {
                // Parcurgem lista de clienti
                for (Client client : this.clients) {

                    // Afisam ca in cerinta
                    System.out.print(client.toString() + "; ");
                    deScris += client.toString() + "; ";
                    str += client.toString() + "; ";
                }

                System.out.println();
                deScris += "\n";
                str += "\n";
            }

            // Parcurgem cozile
            for (Queue coada : this.queues) {

                System.out.print("Queue " + coada.getQueueNumber() + ": ");
                deScris += "Queue " + coada.getQueueNumber() + ": ";
                str += "Queue " + coada.getQueueNumber() + ": ";

                // Daca coada e goala
                if (coada.getClients().size() == 0) {

                    System.out.println("closed");
                    deScris += "closed\n";
                    str += "closed\n";
                } else {

                    // Parcurgem lista de clienti
                    for (Client client : coada.getClients()) {

                        System.out.print(client.toString() + "; ");
                        deScris += client.toString() + "; ";
                        str += client.toString() + "; ";
                    }

                    System.out.println();
                    deScris += "\n";
                    str += "\n";
                }
            }

            try {

                wait(1000); // asteapta o secunda
            } catch (InterruptedException ex) {

                ex.printStackTrace();
            }

            // Metoda nrClientiInCozi() returneaza numarul total de clienti din toate cozile
            int currentClients = this.nrClientiInCozi();

            if (currentClients > this.peakHourClients) {

                this.peakHourClients = currentClients;
                this.peakHour = this.timpCurent.get();
            }

            // Metoda addAndGet(x) pentru un AtomicInteger, incrementeaza acel AtomicInteger cu x
            this.timpCurent.addAndGet(1);

            // Daca timpul curent din simulare este mai mare decat timpul maxim de simulare (tMAX Simulare),
            // inseamna ca s-a terminat simularea
            if (this.timpCurent.get() >= this.timpMaxSimulare)
                this.inchidereCozi();

            view.getAfisareTextField().setText(str); // afisam pe interfata modul de simulare
        }

        String str = "";
        System.out.println("Average waiting time: " + (float) this.timpAsteptareTotal / this.nrClientiProcesati);
        deScris += "Average waiting time: " + (float) this.timpAsteptareTotal / this.nrClientiProcesati + "\n";
        str += "Average waiting time: " + (float) this.timpAsteptareTotal / this.nrClientiProcesati + "\n";


        System.out.println("Average service time: " + (float) this.timpProcesareTotal / this.nrClientiProcesati);
        deScris += "Average service time: " + (float) this.timpProcesareTotal / this.nrClientiProcesati + "\n";
        str += "Average service time: " + (float) this.timpProcesareTotal / this.nrClientiProcesati + "\n";

        System.out.println("Peak Hour: " + this.peakHour + "\nNumber of clients in Peak Hour: " + this.peakHourClients);
        deScris += "Peak Hour: " + this.peakHour + "\nNumber of clients in Peak Hour: " + this.peakHourClients + "\n";
        str += "Peak Hour: " + this.peakHour + "\nNumber of clients in Peak Hour: " + this.peakHourClients + "\n";

        view.getAfisareTextField().setText(str); // afisam in interfata

        this.scrieInFisier(deScris); // afisam in fisier

    }
}
