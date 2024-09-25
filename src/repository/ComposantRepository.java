package repository;

import domaine.Composant;
import domaine.MainDouvre;
import domaine.Material;
import repository.interfaces.ComposantInterface;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ComposantRepository implements ComposantInterface {

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

    public void addMaterial(Material material, int project_id) throws SQLException {
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

    public void addMainDouvre(MainDouvre mainDouvre, int project_id) throws SQLException {
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


    public List<Material> getAllMaterialByProjectName(String name){
        List <Material> materials = new ArrayList<>();
        try{
            String query = "SELECT * FROM materials WHERE project_id = (SELECT id FROM projects WHERE name = ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,name);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Material material = new Material(
                        resultSet.getString("name"),
                        resultSet.getString("composanttype"),
                        resultSet.getDouble("tauxtva"),
                        null,
                        resultSet.getDouble("coutUnitaire"),
                        resultSet.getDouble("quantite"),
                        resultSet.getDouble("coutTransport"),
                        resultSet.getDouble("coefficientQualite")
                );
                materials.add(material);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return materials;
    }

    public List<MainDouvre> getAllMainDouvreByProjectName(String name){
        List <MainDouvre> mainDouvreList = new ArrayList<>();
        try{
            String query = "SELECT * FROM main_douvre WHERE project_id = (SELECT id FROM projects WHERE name = ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,name);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                MainDouvre mainDouvre = new MainDouvre(
                        resultSet.getString("name"),
                        resultSet.getString("composanttype"),
                        resultSet.getDouble("tauxtva"),
                        null,
                        resultSet.getString("typeouvrier"),
                        resultSet.getDouble("tauxHoraire"),
                        resultSet.getDouble("heuresTravail"),
                        resultSet.getDouble("productiviteOuvrier")
                );
                mainDouvreList.add(mainDouvre);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return mainDouvreList;
    }



}
