package repository.interfaces;

import domaine.Projet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface ProjectInterface {
    int addProject(Projet project) throws SQLException;
    Optional<Projet> getProjectByName(String projectName) throws SQLException;
    void updateCoutTotalProject(Projet project) throws SQLException;
    void updateProjectStatus(Projet project) throws SQLException;
    List<Projet> findAllProjects() throws SQLException;
}
