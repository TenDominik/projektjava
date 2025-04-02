import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                Baza baza = new Baza();
                WszystkoRazem widok = new WszystkoRazem();
                new Funkcje(widok, baza);
                widok.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null,
                        "Błąd inicjalizacji aplikacji: " + e.getMessage(),
                        "Błąd", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}