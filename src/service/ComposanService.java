package service;

import config.DbConnection;
import repository.ComposantRepository;

import java.sql.Connection;

public class ComposanService {
    private Connection connection = DbConnection.getInstance().getConnection();

    private ComposantRepository composantRepository;
    public ComposanService() {
        this.composantRepository = new ComposantRepository(connection);
    }



}
