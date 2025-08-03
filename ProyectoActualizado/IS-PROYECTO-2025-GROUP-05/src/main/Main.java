import javax.swing.SwingUtilities;
import controllers.AppController;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new AppController();
        });
    }
}