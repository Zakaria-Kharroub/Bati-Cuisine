package service;

import config.DbConnection;
import domaine.Client;
import repository.ClientRepository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class ClientService {

    private Connection connection = DbConnection.getInstance().getConnection();
    private ClientRepository clientRepository;

    public ClientService() {
        this.clientRepository = new ClientRepository(connection);
    }
    public boolean clienExist(String name) throws SQLException {
        return clientRepository.clienExist(name);
    }
    public void addClient(String name, String phone, String address, boolean isProfessional) throws SQLException {
        Client nouvClient = new Client(name, phone, address, isProfessional);
        clientRepository.addClient(nouvClient);
    }

    public List<Client> findAllClients() throws SQLException {
        return clientRepository.findAll();
    }
    public boolean updateClient(String originalName, String name, String phone, String address, boolean isProfessional) throws SQLException {
        Client client = new Client(name, phone, address, isProfessional);
        return clientRepository.updateClient(client, originalName);
    }

    public boolean deleteClient(String name) throws SQLException {
        return clientRepository.deleteClient(name);
    }

    public Optional<Client> findByname(String name) throws SQLException {
        return clientRepository.findByName(name);
    }


}
