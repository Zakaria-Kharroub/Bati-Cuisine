package domaine;

public abstract class Composant {

    private int id;
    private String name;
    private String composantType;
    private double tauxTva;
    private Projet projet;

    public Composant(String name, String composantType, double tauxTva, Projet projet) {
        this.name = name;
        this.composantType = composantType;
        this.tauxTva = tauxTva;
        this.projet = projet;
    }
//    getters

    public String getName() {
        return name;
    }

    public String getComposantType() {
        return composantType;
    }

    public double getTauxTva() {
        return tauxTva;
    }

    public Projet getProjet() {
        return projet;
    }

//    setters
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setComposantType(String composantType) {
        this.composantType = composantType;
    }

    public void setTauxTva(double tauxTva) {
        this.tauxTva = tauxTva;
    }

    public void setProjet(Projet projet) {
        this.projet = projet;
    }

    public abstract double calculCoutTotal();





}
