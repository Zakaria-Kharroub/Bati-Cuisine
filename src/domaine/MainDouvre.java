package domaine;

public class MainDouvre extends Composant{
    private int id;
    private String typeOuvrier;
    private double tauxHoraire;
    private double heuresTravail;

    private double productiviteOuvrier;

    public MainDouvre(String name, String composantType, double tauxTva, Projet projet,String typeOuvrier ,double tauxHoraire, double heuresTravail, double productiviteOuvrier) {
        super(name, composantType, tauxTva, projet);
        this.tauxHoraire = tauxHoraire;
        this.heuresTravail = heuresTravail;
        this.productiviteOuvrier = productiviteOuvrier;
        this.typeOuvrier = typeOuvrier;
    }
//    geters


    public String getTypeOuvrier() {
        return typeOuvrier;
    }

    public double getTauxHoraire() {
        return tauxHoraire;
    }

    public double getHeuresTravail() {
        return heuresTravail;
    }

    public double getProductiviteOuvrier() {
        return productiviteOuvrier;
    }

//    setters


    public void setTypeOuvrier(String typeOuvrier) {
        this.typeOuvrier = typeOuvrier;
    }

    public void setTauxHoraire(double tauxHoraire) {
        this.tauxHoraire = tauxHoraire;
    }

    public void setHeuresTravail(double heuresTravail) {
        this.heuresTravail = heuresTravail;
    }

    public void setProductiviteOuvrier(double productiviteOuvrier) {
        this.productiviteOuvrier = productiviteOuvrier;
    }


}
