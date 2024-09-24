package domaine;

import java.time.LocalDate;

public class Devis {

//    private int id;
    private LocalDate dateEmission;
    private LocalDate dateValidate;
    private double montantestime;
    private boolean isAccept;
    private Projet projet;



    public Devis(LocalDate dateEmission, LocalDate dateValidate, double montantestime, boolean isAccept, Projet projet) {
        this.dateEmission = dateEmission;
        this.dateValidate = dateValidate;
        this.montantestime = montantestime;
        this.isAccept = isAccept;
        this.projet = projet;
    }


    public LocalDate getDateEmission() {
        return dateEmission;
    }

    public void setDateEmission(LocalDate dateEmission) {
        this.dateEmission = dateEmission;
    }

    public LocalDate getDateValidate() {
        return dateValidate;
    }

    public void setDateValidate(LocalDate dateValidate) {
        this.dateValidate = dateValidate;
    }

    public double getMontantestime() {
        return montantestime;
    }

    public void setMontantestime(double montantestime) {
        this.montantestime = montantestime;
    }

    public boolean isAccept() {
        return isAccept;
    }

    public void setAccept(boolean accept) {
        isAccept = accept;
    }

    public Projet getProjet() {
        return projet;
    }

    public void setProjet(Projet projet) {
        this.projet = projet;
    }
}




