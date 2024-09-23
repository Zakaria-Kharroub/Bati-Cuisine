package service;

import config.DbConnection;
import domaine.Composant;
import domaine.Material;
import repository.ComposantRepository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ComposanService {
    private Connection connection = DbConnection.getInstance().getConnection();

    private ComposantRepository composantRepository;
    public ComposanService() {
        this.composantRepository = new ComposantRepository(connection);
    }

    public List<Composant> getComposantsByProjectName(String projectName) throws SQLException {
        return composantRepository.getComposantsByProjectName(projectName);
    }




}
