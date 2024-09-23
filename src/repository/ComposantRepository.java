package repository;

import domaine.Composant;
import domaine.MainDouvre;
import domaine.Material;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ComposantRepository {

    private Connection connection;
    public ComposantRepository(Connection connection){
        this.connection = connection;
    }


    public void addComposant(Composant composant, int project_id) throws SQLException {
        if (composant instanceof Material) {
            addMaterial((Material) composant, project_id);
        } else if (composant instanceof MainDouvre) {
            addMainDouvre((MainDouvre) composant, project_id);
        }
    }

    private void addMaterial(Material material, int project_id) throws SQLException {
        String query = "INSERT INTO materials(name, composanttype, tauxtva, project_id, coutUnitaire, quantite, coutTransport, coefficientQualite) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, material.getName());
            preparedStatement.setString(2, material.getComposantType());
            preparedStatement.setDouble(3, material.getTauxTva());
            preparedStatement.setInt(4, project_id);
            preparedStatement.setDouble(5, material.getCoutUnitaire());
            preparedStatement.setDouble(6, material.getQuantite());
            preparedStatement.setDouble(7, material.getCoutTransport());
            preparedStatement.setDouble(8, material.getCoefficientQualite());
            preparedStatement.executeUpdate();
        }
    }

    private void addMainDouvre(MainDouvre mainDouvre, int project_id) throws SQLException {
        String query = "INSERT INTO main_douvre(name, composanttype, tauxtva, project_id,typeouvrier, tauxHoraire, heuresTravail, productiviteOuvrier) VALUES(?,? ,?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, mainDouvre.getName());
            preparedStatement.setString(2, mainDouvre.getComposantType());
            preparedStatement.setDouble(3, mainDouvre.getTauxTva());
            preparedStatement.setInt(4, project_id);
            preparedStatement.setString(5, mainDouvre.getTypeOuvrier());
            preparedStatement.setDouble(6, mainDouvre.getTauxHoraire());
            preparedStatement.setDouble(7, mainDouvre.getHeuresTravail());
            preparedStatement.setDouble(8, mainDouvre.getProductiviteOuvrier());
            preparedStatement.executeUpdate();
        }
    }




    public List<Composant> getComposantsByProjectName(String projectName) throws SQLException {
        String query = "SELECT c.id, c.name, c.composanttype, c.tauxtva, " +
                "m.coutUnitaire, m.quantite, m.coutTransport, m.coefficientQualite, " +
                "d.typeouvrier, d.tauxHoraire, d.heuresTravail, d.productiviteOuvrier " +
                "FROM composants c " +
                "LEFT JOIN materials m ON c.id = m.id " +
                "LEFT JOIN main_douvre d ON c.id = d.id " +
                "JOIN projects p ON c.project_id = p.id " +
                "WHERE p.name = ?";
        List<Composant> composants = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, projectName);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    String composantType = rs.getString("composanttype");
                    if ("Matériel".equals(composantType)) {
                        Material material = new Material(
                                rs.getString("name"),
                                composantType,
                                rs.getDouble("tauxtva"),
                                null,
                                rs.getDouble("coutUnitaire"),
                                rs.getDouble("quantite"),
                                rs.getDouble("coutTransport"),
                                rs.getDouble("coefficientQualite")
                        );
                        composants.add(material);
                    } else if ("Main d'œuvre".equals(composantType)) {
                        MainDouvre mainDouvre = new MainDouvre(
                                rs.getString("name"),
                                composantType,
                                rs.getDouble("tauxtva"),
                                null,
                                rs.getString("typeouvrier"),
                                rs.getDouble("tauxHoraire"),
                                rs.getDouble("heuresTravail"),
                                rs.getDouble("productiviteOuvrier")
                        );
                        composants.add(mainDouvre);
                    }
                }
            }
        }
        return composants;
    }












}
