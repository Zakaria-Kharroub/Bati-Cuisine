package repository;

import domaine.Client;
import domaine.Projet;
import domaine.ProjetStatus;
import repository.interfaces.ProjectInterface;

import java.beans.JavaBean;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProjectRepository implements ProjectInterface {
    private Connection connection;

    public ProjectRepository(Connection connection){
        this.connection = connection;
    }
    public int addProject(Projet project) throws SQLException {
        String query = "INSERT INTO projects(name, margeBenifit, coutTotal, projectstatus, client_id) VALUES (?, ?, 0, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, project.getName());
            preparedStatement.setDouble(2, project.getMargeBenifit());
            preparedStatement.setObject(3, project.getProjetStatus(), java.sql.Types.OTHER);
            preparedStatement.setInt(4, project.getClient().getId());

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("error creation project");
            }

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("error creation project failed, no ID obtained.");
                }
            }
        }
    }




    public Optional<Projet> getProjectByName(String projectName) throws SQLException {
        String query = "SELECT p.id, p.name, p.margeBenifit, p.coutTotal, p.projectstatus, " +
                "c.id as client_id, c.name as client_name, c.phone, c.address, c.isprofessional " +
                "FROM projects p " +
                "JOIN clients c ON p.client_id = c.id " +
                "WHERE p.name = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, projectName);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Client client = new Client(
                            rs.getString("client_name"),
                            rs.getString("phone"),
                            rs.getString("address"),
                            rs.getBoolean("isprofessional")
                    );
                    client.setId(rs.getInt("client_id"));

                    Projet projet = new Projet(
                            rs.getString("name"),
                            rs.getDouble("margeBenifit"),
                            rs.getDouble("coutTotal"),
                            ProjetStatus.valueOf(rs.getString("projectstatus")),
                            client,
                            new ArrayList<>()
                    );
                    projet.setId(rs.getInt("id"));

                    return Optional.of(projet);
                }
            }
        }

        return Optional.empty();
    }


    public void updateCoutTotalProject(Projet project) throws SQLException {
        String query = "UPDATE projects SET coutTotal = ? WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setDouble(1, project.getCoutTotal());
            preparedStatement.setInt(2, project.getId());
            preparedStatement.executeUpdate();
        }
    }


    public void updateProjectStatus(Projet project) throws SQLException {
        String query = "UPDATE projects SET projectstatus = ? WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setObject(1, project.getProjetStatus(), java.sql.Types.OTHER);
            preparedStatement.setInt(2, project.getId());
            preparedStatement.executeUpdate();
        }
    }

    public List<Projet> findAllProjects() throws SQLException {
        List<Projet> projects = new ArrayList<>();
        String query = "SELECT p.id, p.name, p.margeBenifit, p.coutTotal, p.projectstatus, " +
                "c.id as client_id, c.name as client_name, c.phone, c.address, c.isprofessional " +
                "FROM projects p " +
                "JOIN clients c ON p.client_id = c.id";

        try (Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(query)) {
            while (rs.next()) {
                Client client = new Client(
                        rs.getString("client_name"),
                        rs.getString("phone"),
                        rs.getString("address"),
                        rs.getBoolean("isprofessional")
                );
                client.setId(rs.getInt("client_id"));

                Projet projet = new Projet(
                        rs.getString("name"),
                        rs.getDouble("margeBenifit"),
                        rs.getDouble("coutTotal"),
                        ProjetStatus.valueOf(rs.getString("projectstatus")),
                        client,
                        new ArrayList<>()
                );
                projet.setId(rs.getInt("id"));

                projects.add(projet);
            }
        }

        return projects;
    }

}
