import javax.swing.*;
import java.sql.SQLException;
import java.util.List;

public class Funkcje {
    private final WszystkoRazem widok;
    private final Baza bazaKalorie;
    private List<Jedzenie> listaDzisiaj;

    public Funkcje(WszystkoRazem widok, Baza bazaKalorie) {
        this.widok = widok;
        this.bazaKalorie = bazaKalorie;
        wczytajListe();
        ustawAkcje();
    }

    private void wczytajListe() {
        try {
            listaDzisiaj = bazaKalorie.pobierzListe();
            aktualizujListeProduktow();
            aktualizujPodsumowanie();
        } catch (SQLException e) {
            pokazBlad("Błąd ładowania danych: " + e.getMessage());
        }
    }

    private void ustawAkcje() {
        GUIFormularz panelFormularza = widok.getPanelFormularza();
        GUIPrzyciski panelListy = widok.getPanelListy();

        panelFormularza.getPrzyciskDodaj().addActionListener(e -> {
            if (panelFormularza.walidujFormularz()) {
                dodajProdukt();
            }
        });

        panelListy.getPrzyciskUsun().addActionListener(e -> usunProdukt());
        panelListy.getPrzyciskReset().addActionListener(e -> resetujListe());
    }

    private void dodajProdukt() {
        try {
            String nazwa = widok.getPanelFormularza().getPoleNazwa().getText().trim();
            double kalorie = Double.parseDouble(widok.getPanelFormularza().getPoleKalorie().getText());
            double proteiny = Double.parseDouble(widok.getPanelFormularza().getPoleBialko().getText());
            double tluszcz = Double.parseDouble(widok.getPanelFormularza().getPoleTluszcze().getText());
            double wegiel = Double.parseDouble(widok.getPanelFormularza().getPoleWeglowodany().getText());

            int idProduktu = bazaKalorie.dodajProdukt(nazwa, kalorie, proteiny, tluszcz, wegiel);
            bazaKalorie.dodajDoListy(idProduktu);

            Jedzenie produkt = new Jedzenie(idProduktu, nazwa, kalorie, proteiny, tluszcz, wegiel);
            listaDzisiaj.add(produkt);

            widok.getPanelFormularza().wyczyscPola();
            aktualizujListeProduktow();
            aktualizujPodsumowanie();

        } catch (NumberFormatException e) {
            pokazBlad("Wprowadź poprawne wartości liczbowe");
        } catch (SQLException e) {
            pokazBlad("Błąd bazy danych: " + e.getMessage());
        } catch (FoodException e) {
            pokazBlad(e.getMessage());
        }
    }

    private void usunProdukt() {
        int wybranyIndeks = widok.getPanelListy().getListaProduktow().getSelectedIndex();
        if (wybranyIndeks == -1) {
            pokazBlad("Wybierz produkt do usunięcia");
            return;
        }

        Jedzenie produkt = listaDzisiaj.get(wybranyIndeks);
        try {
            bazaKalorie.usunZListy(produkt.getId());
            listaDzisiaj.remove(wybranyIndeks);
            aktualizujListeProduktow();
            aktualizujPodsumowanie();
        } catch (SQLException e) {
            pokazBlad("Błąd podczas usuwania: " + e.getMessage());
        }
    }

    private void resetujListe() {
        int potwierdzenie = JOptionPane.showConfirmDialog(widok,
                "Czy na pewno chcesz wyczyścić dzisiejszą listę?",
                "Potwierdzenie", JOptionPane.YES_NO_OPTION);

        if (potwierdzenie == JOptionPane.YES_OPTION) {
            try {
                bazaKalorie.resetujListe();
                listaDzisiaj.clear();
                aktualizujListeProduktow();
                aktualizujPodsumowanie();
            } catch (SQLException e) {
                pokazBlad("Błąd podczas resetowania: " + e.getMessage());
            }
        }
    }

    private void aktualizujListeProduktow() {
        DefaultListModel<String> model = widok.getPanelListy().getModelListy();
        model.clear();
        listaDzisiaj.forEach(produkt -> model.addElement(produkt.toString()));
    }

    private void aktualizujPodsumowanie() {
        GUIPodsumowanie podsumowanie = new GUIPodsumowanie(listaDzisiaj);
        widok.getPanelPodsumowania().aktualizujPodsumowanie(podsumowanie.getPodsumowanie());
    }

    private void pokazBlad(String wiadomosc) {
        JOptionPane.showMessageDialog(widok, wiadomosc, "Błąd", JOptionPane.ERROR_MESSAGE);
    }
}