package repository;

import domaine.Composant;
import domaine.MainDouvre;
import domaine.Material;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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








}
