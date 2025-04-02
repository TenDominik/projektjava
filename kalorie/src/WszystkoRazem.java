import javax.swing.*;
import java.awt.*;

public class WszystkoRazem extends JFrame {
    private GUIFormularz panelFormularza;
    private GUIPrzyciski panelListy;
    private Kalorie panelPodsumowania;

    public WszystkoRazem() {
        super("Śledzenie kalorii i makroskładników");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLayout(new BorderLayout(10, 10));

        panelFormularza = new GUIFormularz();
        panelListy = new GUIPrzyciski();
        panelPodsumowania = new Kalorie();

        add(panelFormularza, BorderLayout.NORTH);
        add(panelListy, BorderLayout.CENTER);
        add(panelPodsumowania, BorderLayout.SOUTH);
    }

    public GUIFormularz getPanelFormularza() { return panelFormularza; }
    public GUIPrzyciski getPanelListy() { return panelListy; }
    public Kalorie getPanelPodsumowania() { return panelPodsumowania; }
}