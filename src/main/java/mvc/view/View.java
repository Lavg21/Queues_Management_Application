package mvc.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class View extends JFrame {

    // Declararea campurilor folosite
    private JLabel titleLabel;
    private JLabel clientiLabel;
    private JLabel coziLabel;
    private JLabel simulareLabel;
    private JLabel minArrivalLabel;
    private JLabel maxArrivalLabel;
    private JLabel minServiceLabel;
    private JLabel maxServiceLabel;
    private JLabel afisareLabel;

    private JTextField clientiTextField;
    private JTextField coziTextField;
    private JTextField simulareTextField;
    private JTextField minArrivalTextField;
    private JTextField maxArrivalTextField;
    private JTextField minServiceTextField;
    private JTextField maxServiceTextField;

    private JTextArea afisareTextField;

    private JButton startButton;

    // Constructor-ul clasei View
    public View() {
        // Marginile ferestrei interfetei
        this.setBounds(100, 100, 844, 648);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setLayout(null);

        // Etichetele din interfata
        titleLabel = new JLabel("QUEUES MANAGEMENT");
        titleLabel.setFont(new Font("Times New Roman", Font.PLAIN, 22));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setBounds(233, 10, 405, 49);
        this.getContentPane().add(titleLabel);

        clientiLabel = new JLabel("Numar clienti");
        clientiLabel.setHorizontalAlignment(SwingConstants.CENTER);
        clientiLabel.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        clientiLabel.setBounds(34, 110, 161, 51);
        this.getContentPane().add(clientiLabel);

        coziLabel = new JLabel("Numar cozi");
        coziLabel.setHorizontalAlignment(SwingConstants.CENTER);
        coziLabel.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        coziLabel.setBounds(23, 159, 172, 51);
        this.getContentPane().add(coziLabel);

        simulareLabel = new JLabel("Timp simulare");
        simulareLabel.setHorizontalAlignment(SwingConstants.CENTER);
        simulareLabel.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        simulareLabel.setBounds(23, 206, 172, 51);
        this.getContentPane().add(simulareLabel);

        minArrivalLabel = new JLabel("Min Arrival time");
        minArrivalLabel.setHorizontalAlignment(SwingConstants.CENTER);
        minArrivalLabel.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        minArrivalLabel.setBounds(23, 253, 172, 51);
        this.getContentPane().add(minArrivalLabel);

        maxArrivalLabel = new JLabel("Max Arrival time");
        maxArrivalLabel.setHorizontalAlignment(SwingConstants.CENTER);
        maxArrivalLabel.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        maxArrivalLabel.setBounds(23, 301, 172, 51);
        this.getContentPane().add(maxArrivalLabel);

        minServiceLabel = new JLabel("Min Service time");
        minServiceLabel.setHorizontalAlignment(SwingConstants.CENTER);
        minServiceLabel.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        minServiceLabel.setBounds(23, 347, 172, 51);
        this.getContentPane().add(minServiceLabel);

        maxServiceLabel = new JLabel("Max Service time");
        maxServiceLabel.setHorizontalAlignment(SwingConstants.CENTER);
        maxServiceLabel.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        maxServiceLabel.setBounds(23, 397, 172, 51);
        this.getContentPane().add(maxServiceLabel);

        afisareLabel = new JLabel("Afisare cozi");
        afisareLabel.setHorizontalAlignment(SwingConstants.CENTER);
        afisareLabel.setFont(new Font("Times New Roman", Font.PLAIN, 25));
        afisareLabel.setBounds(424, 101, 190, 49);
        this.getContentPane().add(afisareLabel);

        // Casetele text in care se vor introduce datele si se va afisa simularea cozilor
        clientiTextField = new JTextField();
        clientiTextField.setBounds(205, 126, 56, 24);
        this.getContentPane().add(clientiTextField);
        clientiTextField.setColumns(10);

        coziTextField = new JTextField();
        coziTextField.setColumns(10);
        coziTextField.setBounds(205, 175, 56, 24);
        this.getContentPane().add(coziTextField);

        simulareTextField = new JTextField();
        simulareTextField.setColumns(10);
        simulareTextField.setBounds(205, 222, 56, 24);
        this.getContentPane().add(simulareTextField);

        minArrivalTextField = new JTextField();
        minArrivalTextField.setColumns(10);
        minArrivalTextField.setBounds(205, 269, 56, 24);
        this.getContentPane().add(minArrivalTextField);

        maxArrivalTextField = new JTextField();
        maxArrivalTextField.setColumns(10);
        maxArrivalTextField.setBounds(205, 317, 56, 24);
        this.getContentPane().add(maxArrivalTextField);

        minServiceTextField = new JTextField();
        minServiceTextField.setColumns(10);
        minServiceTextField.setBounds(205, 363, 56, 24);
        this.getContentPane().add(minServiceTextField);

        maxServiceTextField = new JTextField();
        maxServiceTextField.setColumns(10);
        maxServiceTextField.setBounds(205, 413, 56, 24);
        this.getContentPane().add(maxServiceTextField);

        afisareTextField = new JTextArea();
        afisareTextField.setColumns(10);
        afisareTextField.setBounds(271, 152, 531, 459);
        this.getContentPane().add(afisareTextField);

        // Butonul de start care va porni simularea cozilor
        startButton = new JButton("START");
        startButton.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        startButton.setBounds(47, 479, 195, 49);
        this.getContentPane().add(startButton);

        // Afisarea ferestrei actuale
        this.setVisible(true);
    }

    // Getters si setters

    public String getClientiTextField() {
        return clientiTextField.getText();
    }

    public String getCoziTextField() {
        return coziTextField.getText();
    }

    public String getSimulareTextField() {
        return simulareTextField.getText();
    }

    public String getMinArrivalTextField() {
        return minArrivalTextField.getText();
    }

    public String getMaxArrivalTextField() {
        return maxArrivalTextField.getText();
    }

    public String getMinServiceTextField() {
        return minServiceTextField.getText();
    }

    public String getMaxServiceTextField() {
        return maxServiceTextField.getText();
    }

    public JTextArea getAfisareTextField() {
        return afisareTextField;
    }

    // Listener pentru butonul de start
    public void addStartListener(ActionListener actionListener) {
        this.startButton.addActionListener(actionListener);
    }
}
