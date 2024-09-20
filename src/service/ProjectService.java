package service;

import config.DbConnection;
import domaine.Projet;
import repository.ProjectRepository;

import java.sql.Connection;
import java.sql.SQLException;

public class ProjectService {
    private Connection connection = DbConnection.getInstance().getConnection();
    private ProjectRepository projectRepository;

    public ProjectService() {
        this.projectRepository = new ProjectRepository(connection);
    }

    public void addProject(Projet project) throws SQLException {
        projectRepository.addProject(project);
    }


}
