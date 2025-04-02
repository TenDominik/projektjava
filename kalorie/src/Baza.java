import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Baza {
    private Connection connection;

    public Baza() throws SQLException {
        polaczenie();
        tworzbaze();
    }

    private void polaczenie() throws SQLException {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:kalorie.db");
        } catch (ClassNotFoundException e) {
            throw new SQLException("nie ma jdbc", e);
        }
    }

    private void tworzbaze() throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("CREATE TABLE IF NOT EXISTS produkty (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "nazwa TEXT NOT NULL, " +
                    "kalorie REAL NOT NULL, " +
                    "proteiny REAL NOT NULL, " +
                    "tluszcz REAL NOT NULL, " +
                    "wegiel REAL NOT NULL)");

            stmt.execute("CREATE TABLE IF NOT EXISTS dzisiaj (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "idproduktu INTEGER NOT NULL, " +
                    "FOREIGN KEY(idproduktu) REFERENCES produkty(id))");
        }
    }

    public int dodajProdukt(String nazwa, double kalorie, double proteiny, double tluszcz, double wegiel)
            throws SQLException, FoodException {
        if (nazwa == null || nazwa.trim().isEmpty()) {
            throw new FoodException("Nazwa produktu nie może być pusta", "VALIDATION");
        }
        if (kalorie <= 0 || proteiny < 0 || tluszcz < 0 || wegiel < 0) {
            throw new FoodException("Wartości odżywcze nie mogą być ujemne", "VALIDATION");
        }
        if (kalorie > 10000) {
            throw new FoodException("Zbyt duża wartość kalorii", "VALIDATION");
        }

        String sql = "INSERT INTO produkty (nazwa, kalorie, proteiny, tluszcz, wegiel) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, nazwa);
            stmt.setDouble(2, kalorie);
            stmt.setDouble(3, proteiny);
            stmt.setDouble(4, tluszcz);
            stmt.setDouble(5, wegiel);
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                return rs.next() ? rs.getInt(1) : -1;
            }
        }
    }

    public void dodajDoListy(int idproduktu) throws SQLException {
        String sql = "INSERT INTO dzisiaj (idproduktu) VALUES (?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idproduktu);
            stmt.executeUpdate();
        }
    }

    public List<Jedzenie> pobierzListe() throws SQLException {
        List<Jedzenie> items = new ArrayList<>();
        String sql = "SELECT p.id, p.nazwa, p.kalorie, p.proteiny, p.tluszcz, p.wegiel " +
                "FROM produkty p JOIN dzisiaj d ON p.id = d.idproduktu";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    try {
                        items.add(new Jedzenie(
                                rs.getInt("id"),
                                rs.getString("nazwa"),
                                rs.getDouble("kalorie"),
                                rs.getDouble("proteiny"),
                                rs.getDouble("tluszcz"),
                                rs.getDouble("wegiel")
                        ));
                    } catch (FoodException e) {
                        System.err.println("Nieprawidłowe dane w bazie: " + e.getMessage());
                    }
                }
            }
        }
        return items;
    }

    public void usunZListy(int idproduktu) throws SQLException {
        String sql = "DELETE FROM dzisiaj WHERE idproduktu = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idproduktu);
            stmt.executeUpdate();
        }
    }

    public void resetujListe() throws SQLException {
        String sql = "DELETE FROM dzisiaj";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.executeUpdate();
        }
    }
}