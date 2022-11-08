package mvc.controller;

import mvc.models.Simulation;
import mvc.view.View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller {

    private View view;

    // Constructorul care primeste ca parametru un view
    public Controller(View view) {

        this.view = view;

        // Punem listener pe buton pentru a porni simularea la apasare
        this.view.addStartListener(new StartListener());
    }

    class StartListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                // Preluare date de pe interfata
                int nrCozi, nrClienti, timpMaxSimulare, tMinArrival, tMaxArrival, tMinService, tMaxService;
                nrCozi = Integer.parseInt(view.getCoziTextField());
                nrClienti = Integer.parseInt(view.getClientiTextField());
                timpMaxSimulare = Integer.parseInt(view.getSimulareTextField());
                tMinArrival = Integer.parseInt(view.getMinArrivalTextField());
                tMaxArrival = Integer.parseInt(view.getMaxArrivalTextField());
                tMinService = Integer.parseInt(view.getMinServiceTextField());
                tMaxService = Integer.parseInt(view.getMaxServiceTextField());

                // Pornim simularea executiei cozilor cu parametrii preluati de pe interfata
                Simulation simulation = new Simulation(view, nrCozi, nrClienti, timpMaxSimulare, tMinArrival, tMaxArrival, tMinService, tMaxService);
                simulation.start();

            } catch (NumberFormatException exception) {
                exception.printStackTrace();
            }
        }
    }
}
