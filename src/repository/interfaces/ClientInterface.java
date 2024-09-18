// src/repository/interfaces/ClientInterface.java
package repository.interfaces;

import domaine.Client;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface ClientInterface {
    void addClient(Client client) throws SQLException;
    List<Client> findAll() throws SQLException;
    Optional<Client> findByName(String name) throws SQLException;
    boolean updateClient(Client client, String originalName) throws SQLException;
    boolean deleteClient(String name) throws SQLException;
    boolean clienExist(String name) throws SQLException;
}