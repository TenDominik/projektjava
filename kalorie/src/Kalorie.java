import javax.swing.*;
import java.awt.*;

public class Kalorie extends JPanel {
    private final JTextArea polePodsumowania;

    public Kalorie() {
        setLayout(new BorderLayout());
        polePodsumowania = new JTextArea(5, 50);
        polePodsumowania.setEditable(false);
        add(new JScrollPane(polePodsumowania), BorderLayout.CENTER);
    }

    public void aktualizujPodsumowanie(String tekstPodsumowania) {
        polePodsumowania.setText(tekstPodsumowania);
    }
}