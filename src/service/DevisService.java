package service;

import config.DbConnection;
import domaine.Devis;
import domaine.Projet;
import repository.DevisRepository;

import java.sql.Connection;
import java.sql.SQLException;

public class DevisService {
    private Connection connection = DbConnection.getInstance().getConnection();
    private DevisRepository devisRepository;

    public DevisService() {
        this.devisRepository = new DevisRepository(connection);
    }
    public void saveDevis(Devis devis, Projet project) throws SQLException {
        devisRepository.saveDevis(devis);
    }



}
