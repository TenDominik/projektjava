import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GUIFormularz extends JPanel {
    private final JTextField poleNazwa;
    private final JTextField poleKalorie;
    private final JTextField poleBialko;
    private final JTextField poleTluszcze;
    private final JTextField poleWeglowodany;
    private final JButton przyciskDodaj;
    private final JLabel etykietaBledu;

    public GUIFormularz() {
        setLayout(new GridLayout(7, 2, 5, 5));

        add(new JLabel("Nazwa produktu:"));
        poleNazwa = new JTextField();
        poleNazwa.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                walidujFormularz();
            }
        });
        add(poleNazwa);

        add(new JLabel("Kalorie (kcal):"));
        poleKalorie = new JTextField();
        poleKalorie.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                walidujFormularz();
            }
        });
        add(poleKalorie);

        add(new JLabel("Białko (g):"));
        poleBialko = new JTextField();
        poleBialko.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                walidujFormularz();
            }
        });
        add(poleBialko);

        add(new JLabel("Tłuszcze (g):"));
        poleTluszcze = new JTextField();
        poleTluszcze.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                walidujFormularz();
            }
        });
        add(poleTluszcze);

        add(new JLabel("Węglowodany (g):"));
        poleWeglowodany = new JTextField();
        poleWeglowodany.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                walidujFormularz();
            }
        });
        add(poleWeglowodany);

        etykietaBledu = new JLabel(" ");
        etykietaBledu.setForeground(Color.RED);
        add(etykietaBledu);

        przyciskDodaj = new JButton("Dodaj produkt");
        przyciskDodaj.setEnabled(false);
        add(przyciskDodaj);
    }

    public boolean walidujFormularz() {
        String nazwa = poleNazwa.getText().trim();
        String kalorie = poleKalorie.getText().trim();
        String bialko = poleBialko.getText().trim();
        String tluszcze = poleTluszcze.getText().trim();
        String weglowodany = poleWeglowodany.getText().trim();

        if (nazwa.isEmpty() || kalorie.isEmpty() || bialko.isEmpty() ||
                tluszcze.isEmpty() || weglowodany.isEmpty()) {
            etykietaBledu.setText("Wszystkie pola muszą być wypełnione");
            przyciskDodaj.setEnabled(false);
            return false;
        }

        try {
            double kcal = Double.parseDouble(kalorie);
            double bial = Double.parseDouble(bialko);
            double tlusz = Double.parseDouble(tluszcze);
            double wegl = Double.parseDouble(weglowodany);

            if (kcal <= 0 || bial < 0 || tlusz < 0 || wegl < 0) {
                etykietaBledu.setText("Wartości nie mogą być ujemne");
                przyciskDodaj.setEnabled(false);
                return false;
            }

            if (nazwa.length() > 50) {
                etykietaBledu.setText("Nazwa zbyt długa (max 50 znaków)");
                przyciskDodaj.setEnabled(false);
                return false;
            }

            if (kcal > 10000) {
                etykietaBledu.setText("Kalorie: max 10000 kcal");
                przyciskDodaj.setEnabled(false);
                return false;
            }

            if (bial > 150 || tlusz > 150 || wegl > 150) {
                etykietaBledu.setText("Makroskładniki: max 150 g");
                przyciskDodaj.setEnabled(false);
                return false;
            }

        } catch (NumberFormatException e) {
            etykietaBledu.setText("Wprowadź poprawne liczby");
            przyciskDodaj.setEnabled(false);
            return false;
        }

        etykietaBledu.setText(" ");
        przyciskDodaj.setEnabled(true);
        return true;
    }

    public JTextField getPoleNazwa() { return poleNazwa; }
    public JTextField getPoleKalorie() { return poleKalorie; }
    public JTextField getPoleBialko() { return poleBialko; }
    public JTextField getPoleTluszcze() { return poleTluszcze; }
    public JTextField getPoleWeglowodany() { return poleWeglowodany; }
    public JButton getPrzyciskDodaj() { return przyciskDodaj; }

    public void wyczyscPola() {
        poleNazwa.setText("");
        poleKalorie.setText("");
        poleBialko.setText("");
        poleTluszcze.setText("");
        poleWeglowodany.setText("");
        etykietaBledu.setText(" ");
        przyciskDodaj.setEnabled(false);
    }
}