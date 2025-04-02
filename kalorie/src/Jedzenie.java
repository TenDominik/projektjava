public class Jedzenie {
    private final int id;
    private final String nazwa;
    private final double kalorie;
    private final double proteiny;
    private final double tluszcz;
    private final double wegiel;

    public Jedzenie(int id, String nazwa, double kalorie, double proteiny, double tluszcz, double wegiel) throws FoodException {
        if (nazwa == null || nazwa.trim().isEmpty()) {
            throw new FoodException("Nazwa produktu nie może być pusta", "VALIDATION");
        }
        if (nazwa.length() > 50) {
            throw new FoodException("Nazwa produktu zbyt długa (max 50 znaków)", "VALIDATION");
        }
        if (kalorie <= 0 || proteiny < 0 || tluszcz < 0 || wegiel < 0) {
            throw new FoodException("Wartości odżywcze nie mogą być ujemne", "VALIDATION");
        }
        if (kalorie > 10000) {
            throw new FoodException("Zbyt duża wartość kalorii (max 10000 kcal)", "VALIDATION");
        }
        if (proteiny > 150 || tluszcz > 150 || wegiel > 150) {
            throw new FoodException("Zbyt duża wartość makroskładnika (max 150 g)", "VALIDATION");
        }

        this.id = id;
        this.nazwa = nazwa;
        this.kalorie = kalorie;
        this.proteiny = proteiny;
        this.tluszcz = tluszcz;
        this.wegiel = wegiel;
    }

    public int getId() { return id; }
    public String getNazwa() { return nazwa; }
    public double getKalorie() { return kalorie; }
    public double getProteiny() { return proteiny; }
    public double getTluszcz() { return tluszcz; }
    public double getWegiel() { return wegiel; }

    @Override
    public String toString() {
        return String.format("%s (%.0f kcal, P:%.1f, T:%.1f, W:%.1f)",
                nazwa, kalorie, proteiny, tluszcz, wegiel);
    }
}