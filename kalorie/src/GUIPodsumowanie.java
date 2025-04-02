import java.util.List;

public class GUIPodsumowanie {
    private double sumaKalorii;
    private double sumaBialka;
    private double sumaTluszczu;
    private double sumaWeglowodanow;

    public GUIPodsumowanie(List<Jedzenie> listaProduktow) {
        obliczSumy(listaProduktow);
    }

    private void obliczSumy(List<Jedzenie> listaProduktow) {
        this.sumaKalorii = listaProduktow.stream().mapToDouble(Jedzenie::getKalorie).sum();
        this.sumaBialka = listaProduktow.stream().mapToDouble(Jedzenie::getProteiny).sum();
        this.sumaTluszczu = listaProduktow.stream().mapToDouble(Jedzenie::getTluszcz).sum();
        this.sumaWeglowodanow = listaProduktow.stream().mapToDouble(Jedzenie::getWegiel).sum();
    }

    public String getPodsumowanie() {
        return String.format(
                """
                        Dzisiejsze podsumowanie:
                        Kalorie: %.1f kcal
                        Białko: %.1f g
                        Tłuszcze: %.1f g
                        Węglowodany: %.1f g""",
                sumaKalorii, sumaBialka, sumaTluszczu, sumaWeglowodanow);
    }
}