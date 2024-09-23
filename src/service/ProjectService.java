package service;

import config.DbConnection;
import domaine.Composant;
import domaine.Projet;
import repository.ComposantRepository;
import repository.ProjectRepository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class ProjectService {
    private Connection connection = DbConnection.getInstance().getConnection();
    private ProjectRepository projectRepository;
    private ComposantRepository composantRepository;

    public ProjectService() {
        this.projectRepository = new ProjectRepository(connection);
        this.composantRepository = new ComposantRepository(connection);
    }

    public int addProject(Projet project) throws SQLException {
        return projectRepository.addProject(project);
    }

    public void addComposantToProject(int projectId, Composant composant) throws SQLException {
        composantRepository.addComposant(composant, projectId);
    }

    public Optional<Projet> getProjectByName(String projectName) throws SQLException {
        return projectRepository.getProjectByName(projectName);
    }

//    public void updateProjectCoutTotal(Projet project) throws SQLException {
//        projectRepository.updateCoutTotalProject(project);
//    }






}