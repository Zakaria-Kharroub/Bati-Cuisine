package service;

import config.DbConnection;
import domaine.Composant;
import domaine.MainDouvre;
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

    public double calculCoutTotalMaterial(String name) throws SQLException {
        List<Material> materialList = composantRepository.getAllMaterialByProjectName(name);
        double coutTotalMaterial = 0;
        for (Material material : materialList) {
            coutTotalMaterial += material.calculCoutTotal();
        }
        return coutTotalMaterial;

    }

    public double calculCoutTotalMainDouvre(String name) throws SQLException {
        List<MainDouvre> mainDouvreList = composantRepository.getAllMainDouvreByProjectName(name);
        double coutTotalMainDouvre = 0;
        for (MainDouvre mainDouvre : mainDouvreList) {
            coutTotalMainDouvre += mainDouvre.calculCoutTotal();
        }
        return coutTotalMainDouvre;
    }

    public List<Material> getAllMaterialByProjectName(String name) throws SQLException {
        return composantRepository.getAllMaterialByProjectName(name);
    }

    public List<MainDouvre> getAllMainDouvreByProjectName(String name) throws SQLException {
        return composantRepository.getAllMainDouvreByProjectName(name);
    }





}
