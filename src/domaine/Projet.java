package domaine;

import java.util.List;

public class Projet {
    private int id;
    private String name;
    private double margeBenifit;
    private double coutTotal;
    private ProjetStatus projetStatus;
    private Client client;
    List<Composant> composants;

    public Projet(String name, double margeBenifit, double coutTotal, ProjetStatus projetStatus, Client client, List<Composant> composants) {
        this.name = name;
        this.margeBenifit = margeBenifit;
        this.coutTotal = coutTotal;
        this.projetStatus = projetStatus;
        this.client = client;
        this.composants = composants;
    }

//    getters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getMargeBenifit() {
        return margeBenifit;
    }

    public double getCoutTotal() {
        return coutTotal;
    }

    public ProjetStatus getProjetStatus() {
        return projetStatus;
    }

    public Client getClient() {
        return client;
    }

    public List<Composant> getComposants() {
        return composants;
    }


//    setters


    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMargeBenifit(double margeBenifit) {
        this.margeBenifit = margeBenifit;
    }

    public void setCoutTotal(double coutTotal) {
        this.coutTotal = coutTotal;
    }

    public void setProjetStatus(ProjetStatus projetStatus) {
        this.projetStatus = projetStatus;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void setComposants(List<Composant> composants) {
        this.composants = composants;
    }
}
