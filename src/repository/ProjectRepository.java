package repository;

import domaine.Projet;

import java.beans.JavaBean;
import java.sql.*;
import java.util.Optional;

public class ProjectRepository {
    private Connection connection;

    public ProjectRepository(Connection connection){
        this.connection = connection;
    }
    public void addProject(Projet project) throws SQLException {
        String query = "INSERT INTO projects(name, margeBenifit, coutTotal, projectstatus, client_id) VALUES (?, ?, 0, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, project.getName());
            preparedStatement.setDouble(2, project.getMargeBenifit());
            preparedStatement.setObject(3, project.getProjetStatus(), java.sql.Types.OTHER);
            preparedStatement.setInt(4, project.getClient().getId());
            preparedStatement.executeUpdate();
        }
    }





}
