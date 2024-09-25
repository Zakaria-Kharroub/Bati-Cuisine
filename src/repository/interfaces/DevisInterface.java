package repository.interfaces;

import domaine.Devis;

import java.sql.SQLException;

public interface DevisInterface {
    void saveDevis(Devis devis) throws SQLException;

}
