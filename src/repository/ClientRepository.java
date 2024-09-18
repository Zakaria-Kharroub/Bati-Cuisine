// src/repository/ClientRepository.java
package repository;

import domaine.Client;
import repository.interfaces.ClientInterface;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClientRepository implements ClientInterface {
    private Connection connection;

    public ClientRepository(Connection connection){
        this.connection = connection;
    }

    public void addClient(Client client) throws SQLException {
        String query= "INSERT INTO clients(name,phone,address,isProfessional) VALUES (?,?,?,?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, client.getName());
            preparedStatement.setString(2, client.getPhone());
            preparedStatement.setString(3, client.getAddress());
            preparedStatement.setBoolean (4, client.getIsProfessional());
            preparedStatement.executeUpdate();

        }
    }

    public List<Client> findAll() throws SQLException {
        List<Client> clients = new ArrayList<>();
        String query = "SELECT * FROM clients";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet= preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Client client =new Client(
                        resultSet.getString("name"),
                        resultSet.getString("phone"),
                         resultSet.getString("address"),
                         resultSet.getBoolean("isProfessional")
                );

                client.setId(resultSet.getInt("id"));
                clients.add(client);
            }
        }
        return clients;
    }

    public boolean updateClient(Client client, String originalName) throws SQLException {
        String query = "UPDATE clients SET name = ?, phone = ?, address = ?, isProfessional = ? WHERE name = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, client.getName());
            preparedStatement.setString(2,client.getPhone());
            preparedStatement.setString(3,client.getAddress());
            preparedStatement.setBoolean(4, client.getIsProfessional());
            preparedStatement.setString(5,originalName);
            int rowsUpdated= preparedStatement.executeUpdate();

            return rowsUpdated >0;
        }
    }

    @Override
    public boolean deleteClient(String name) throws SQLException {

        String query = "DELETE FROM clients WHERE name =?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, name);

            int rowsDeleted = preparedStatement.executeUpdate();
            return rowsDeleted > 0;
        }

    }

    public boolean clienExist(String name) throws SQLException {
        String query= "SELECT * FROM clients WHERE name = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1,name);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {

                return resultSet.next();
            }
        }



    }

    @Override
    public Optional<Client> findByName(String name) throws SQLException {
        String query = "SELECT * FROM clients WHERE name = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)){

            preparedStatement.setString(1, name);
            try (ResultSet resultSet= preparedStatement.executeQuery()) {
                if (resultSet.next()) {

                    Client client =new Client(
                             resultSet.getString("name"),
                            resultSet.getString("phone"),
                            resultSet.getString("address"),
                             resultSet.getBoolean("isProfessional")
                    );
                    client.setId(resultSet.getInt("id"));
                    return Optional.of(client);
                } else {
                    return Optional.empty();
                }
            }



        }


    }












}