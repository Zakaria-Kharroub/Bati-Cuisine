package repository;

import domaine.Devis;
import repository.interfaces.DevisInterface;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class DevisRepository implements DevisInterface {

    private Connection connection;

    public DevisRepository(Connection connection) {
        this.connection = connection;
    }

public void saveDevis(Devis devis) throws SQLException {
    String query = "INSERT INTO devis (dateemission, datevalidite, montantestime, isaccept,project_id) VALUES (?,?,?,?,?)";

    try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
        preparedStatement.setDate(1, Date.valueOf(devis.getDateEmission()));
        preparedStatement.setDate(2, Date.valueOf(devis.getDateValidate()));
        preparedStatement.setDouble(3, devis.getMontantestime());
        preparedStatement.setBoolean(4, devis.isAccept());
        preparedStatement.setInt(5, devis.getProjet().getId());
        preparedStatement.executeUpdate();
    }
}


}
