import javax.swing.*;
import java.awt.*;

public class GUIPrzyciski extends JPanel {
    private final JList<String> listaProduktow;
    private final DefaultListModel<String> modelListy;
    private final JButton przyciskUsun;
    private final JButton przyciskReset;

    public abstract class PrzyciskSpecjalny extends JButton {
        public PrzyciskSpecjalny(String tekst) {
            super(tekst + " (SPECJALNY)");
        }
    }

    public GUIPrzyciski() {
        setLayout(new BorderLayout());

        modelListy = new DefaultListModel<>();
        listaProduktow = new JList<>(modelListy);
        add(new JScrollPane(listaProduktow), BorderLayout.CENTER);

        JPanel panelPrzyciskow = new JPanel(new FlowLayout());
        przyciskUsun = new JButton("Usuń zaznaczone");
        przyciskReset = new JButton("Resetuj dzisiejszą listę");

        panelPrzyciskow.add(przyciskUsun);
        panelPrzyciskow.add(przyciskReset);
        add(panelPrzyciskow, BorderLayout.SOUTH);
    }

    public JList<String> getListaProduktow() { return listaProduktow; }
    public DefaultListModel<String> getModelListy() { return modelListy; }
    public JButton getPrzyciskUsun() { return przyciskUsun; }
    public JButton getPrzyciskReset() { return przyciskReset; }
}