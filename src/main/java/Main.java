import mvc.controller.Controller;
import mvc.view.View;

public class Main {

    public static void main(String[] args) {

        View view = new View(); // interfata

        Controller controller = new Controller(view); // controller-ul care primeste ca parametru intefata

    }
}
