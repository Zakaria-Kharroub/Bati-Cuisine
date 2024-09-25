import config.DbConnection;
import domaine.*;
import service.ClientService;
import service.ComposanService;
import service.DevisService;
import service.ProjectService;
import validation.ValidateInputs;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;


import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws SQLException {
        DbConnection dbConnection = DbConnection.getInstance();
        dbConnection.getConnection();

        ClientService clientService = new ClientService();
        ProjectService projectService = new ProjectService();
        ComposanService composanService = new ComposanService();
        Scanner inp = new Scanner(System.in);



        int choix;
        do {
            choix = displayMainMenu(inp);
            switch (choix) {
                case 1:
                    manageClients(clientService, inp);
                    break;
                case 2:
                    manageProjects(projectService, composanService, inp, clientService);
                    break;
                case 3:
                    System.out.println("Merci et au revoir");
                    break;
                default:
                    System.out.println("Choix invalide");
                    break;
            }
        } while (choix != 3);
    }

    private static int displayMainMenu(Scanner inp) {
        System.out.println("+--------------------------------------------------------------+");
        System.out.println("|                    Menu Principal                            |");
        System.out.println("+--------------------------------------------------------------+");
        System.out.println("| 1 - gestion des clients                                      |");
        System.out.println("| 2 - gestion des projets                                      |");
        System.out.println("| 3 - exit                                                     |");
        System.out.println("+--------------------------------------------------------------+");
        return inp.nextInt();
    }

    private static void manageClients(ClientService clientService, Scanner inp) throws SQLException {
        int choixClient;
        do {
            choixClient = clientMenu(inp);
            switch (choixClient) {
                case 1:
                    addClient(clientService, inp);
                    break;
                case 2:
                    afficheAllClients(clientService);
                    break;
                case 3:
                    detailClientByname(clientService, inp);
                    break;
                case 4:
                    deleteClient(clientService, inp);
                    break;
                case 5:
                    updateClient(clientService, inp);
                    break;
                case 6:
                    System.out.println("retour au menu principal");
                    break;
                default:
                    System.out.println("choix invalide");
                    break;
            }
        } while (choixClient != 6);
    }


    private static void manageProjects(ProjectService projectService, ComposanService composanService, Scanner inp, ClientService clientService) throws SQLException {
        int choixProject;
        do {
            choixProject = projectMenu(inp);
            switch (choixProject) {
                case 1:
                    addProject(projectService,composanService, inp, clientService, new DevisService());
                    break;
                case 2:
                    detailProject(projectService, composanService, inp);
                    break;
                case 3:
                    findAllProjects(projectService);
                    break;
                case 4:
                    updateProjectStatus(projectService, inp);
                    break;
                case 5:
                    System.out.println("retour au menu principal");
                    break;
                default:
                    System.out.println("choix invalide");
                    break;
            }
        } while (choixProject != 6);
    }



    private static int clientMenu(Scanner inp) {
        System.out.println("+--------------------------------------------------------------+");
        System.out.println("|                    Menu Client                               |");
        System.out.println("+--------------------------------------------------------------+");
        System.out.println("| 1 - ajouter client                                           |");
        System.out.println("| 2 - afficher tous les clients                                |");
        System.out.println("| 3 - detail client by name                                    |");
        System.out.println("| 4 - supprimer un client                                      |");
        System.out.println("| 5 - modifier un client                                       |");
        System.out.println("| 6 - exit                                                     |");
        System.out.println("+--------------------------------------------------------------+");

        return inp.nextInt();
    }

    private static int projectMenu(Scanner inp) {
        System.out.println("+--------------------------------------------------------------+");
        System.out.println("|                    Menu Project                              |");
        System.out.println("+--------------------------------------------------------------+");
        System.out.println("| 1 - ajouter projet                                           |");
        System.out.println("| 2 - detail projet by name                                    |");
        System.out.println("| 3 - afficher les project existants                           |");
        System.out.println("| 4 - update projet status                                     |");
        System.out.println("| 5 - retour au menu principal                                 |");
        System.out.println("+--------------------------------------------------------------+");

        return inp.nextInt();
    }

    private static void addClient(ClientService clientService, Scanner inp) {
        System.out.println("enter name de client");
        String name = inp.next();

        try {
            if (clientService.clienExist(name)) {
                System.out.println("Client avec name " + name + " deja existe");
            } else {
                inp.nextLine();

                System.out.println("entrer phone");
                String phone = inp.nextLine();

                System.out.println("entrer address");
                String address = inp.nextLine();

                boolean isProfessional = ValidateInputs.validateBoolean("ce client est professionnel? \n true / false");

                clientService.addClient(name, phone, address, isProfessional);
                System.out.println(name + " ajoute avec succes");
            }
        } catch (SQLException e) {
            System.out.println("erreur lors de ajouter client" + e.getMessage());
        }


    }

    private static void afficheAllClients(ClientService clientService) throws SQLException {
        System.out.println("---------------------------------------------------------------------------------------");
        System.out.println("                             liste des clients                                       ");
        List<Client> clients = clientService.findAllClients();
        for (Client client : clients) {
            System.out.println("--------------------------------------------------------------------------------------");
            System.out.println("id: " + client.getId() + "| name: " + client.getName() + "| phone: " + client.getPhone() + "| address: " + client.getAddress() + "| professional: " + (client.getIsProfessional() ? "oui" : "non"));
        }
        System.out.println("--------------------------------------------------------------------------------------");
    }

    private static void detailClientByname(ClientService clientService, Scanner inp){
        System.out.println("entrer name de client");
        String nameClient = inp.next();
        try {
            Client client = clientService.findByname(nameClient).orElseThrow(
                    () -> new IllegalArgumentException("client non trouve")
            );
            System.out.println("--------------------------------------------------------------------------------------");
            System.out.println("id: " + client.getId() + "| name: " + client.getName() + "| phone: " + client.getPhone() + "| address: " + client.getAddress() + "| professional: " + (client.getIsProfessional() ? "oui" : "non"));
            System.out.println("--------------------------------------------------------------------------------------");
        } catch (SQLException e) {
            System.out.println("error de afficher detail client");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void deleteClient(ClientService clientService, Scanner inp) {
        System.out.println("enter name de client que vous voulez supprimer");
        String nameDelete = inp.next();
        try {
            boolean isDelete = clientService.deleteClient(nameDelete);
            if (isDelete) {
                System.out.println("client supprime avec succes");
            } else {
                System.out.println("client not found");
            }
        } catch (SQLException e) {System.out.println("error de suppression");}
    }

    private static void updateClient(ClientService clientService, Scanner inp) {
        System.out.println("enter name de client vous voulez modifier");
        String nameUpdate = inp.next();

        inp.nextLine();

        System.out.println("enter nouveau name");
        String nouvName = inp.nextLine();

        System.out.println("enter nouveau phone");
        String nouvPhone = inp.nextLine();

        System.out.println("enter nouveau address");
        String nouvAddress = inp.nextLine();

        boolean nouvIsProfessional = ValidateInputs.validateBoolean("ce client est professionnel? \n true / false");

        try {
            boolean isUpdate = clientService.updateClient(nameUpdate, nouvName, nouvPhone, nouvAddress, nouvIsProfessional);
            if (isUpdate) {
                System.out.println("client modifie avec succes");
            } else {
                System.out.println("client not found");
            }
        } catch (SQLException e) {
            System.out.println("error d'update" + e.getMessage());
        }
    }

    private static void findAllProjects(ProjectService projectService) throws SQLException {
        System.out.println("---------------------------------------------------------------------------------------");
        System.out.println("                             liste des projects                                       ");
        List<Projet> projects = projectService.findAllProjects();
        for (Projet project : projects) {
            System.out.println("--------------------------------------------------------------------------------------");
                        System.out.println("id: " + project.getId() + "| name: " + project.getName() + "|client : " + project.getClient().getName() + "| cout total: " + project.getCoutTotal() + "| status: " + project.getProjetStatus());

        }
        System.out.println("--------------------------------------------------------------------------------------");
    }
    private static void addProject(ProjectService projectService, ComposanService composanService, Scanner inp, ClientService clientService, DevisService devisService) {
        System.out.println("enter name de projet");
        String projectName = inp.next();

        System.out.println("enter name de client pour associer a ce projet");
        String clientName = inp.next();

        double margeBenifit = ValidateInputs.validateDouble("enter marge benifit en %");

        try {
            Client client = clientService.findByname(clientName).orElseThrow(
                    () -> new IllegalArgumentException("client not found")
            );

            Projet project = new Projet(projectName, margeBenifit, 0, ProjetStatus.INPROGRESS, client, new ArrayList<>());
            int projectId = projectService.addProject(project);
            project.setId(projectId);
            System.out.println("project ajoute avec ID : " + projectId);

            boolean addMoreComposants = true;
            while (addMoreComposants) {
                System.out.println("vous voulez ajouter composant a ce projet ? \n oui/non");
                String response = inp.next().toLowerCase();
                if (response.equals("oui")) {
                    addComposantToProject(projectService, inp, projectId);
                } else {
                    addMoreComposants = false;
                }
            }


            double coutTotalMaterial = composanService.calculCoutTotalMaterial(project.getName());
            double coutTotalMainDouvre = composanService.calculCoutTotalMainDouvre(project.getName());
            double coutTotal = coutTotalMaterial + coutTotalMainDouvre;

            double coutTotalAvecMarge = coutTotal + (coutTotal * margeBenifit / 100);


            double coutTotalAvecRemise = 0;
            if (client.getIsProfessional()) {
                System.out.println("ce client est professionnel , vous voulez appliquer une remise? \n oui/non");
                String remise = inp.next().toLowerCase();
                if (remise.equals("oui")) {

                    double montantRemise = ValidateInputs.validateDouble("entrer le montant de remise en %");
                    coutTotalAvecRemise = coutTotalAvecMarge - (coutTotalAvecMarge * montantRemise / 100);
                }
            }
//            System.out.println("fffffffffffff  ..|  " + coutTotal);
            project.setCoutTotal(coutTotal);
            projectService.updateProjectCoutTotal(project);

            System.out.println(" ------------ Resultat du Calcul --------------");
            System.out.println("name de projet : " + project.getName());
            System.out.println("client : " + project.getClient().getName());
            System.out.println("cout total material : " + coutTotalMaterial);
            System.out.println("cout total main d'oeuvre : " + coutTotalMainDouvre);
            System.out.println("cout total avec marge : " + coutTotalAvecMarge);

            System.out.println("cout total avec remise : " + coutTotalAvecRemise);

            System.out.println("---------- generation de devis ----------");
            inp.nextLine();

            LocalDate dateEmission = ValidateInputs.validateDateEmission("enter date emission de devis (YYYY-MM-DD)");
            inp.nextLine();

            LocalDate dateValidite = ValidateInputs.validateDateValidite("enter date validite de devis (YYYY-MM-DD):", dateEmission);

            double montanEstime = coutTotalAvecRemise == 0 ? coutTotalAvecMarge : coutTotalAvecRemise;

            boolean isAccepted = false;

            System.out.println( "------------ Devis----------");
            System.out.println("name de projet : " + project.getName());
            System.out.println("client : " + project.getClient().getName());
            System.out.println("date emission : " + dateEmission);
            System.out.println("date validite : " + dateValidite);
            System.out.println("montant estime : " + montanEstime);
            System.out.println("accepter ce devis? \n oui/non");
            String response = inp.next().toLowerCase();
            if (response.equals("oui")) {
                isAccepted = true;
            }
            System.out.println("devis status : " + (isAccepted ? "accepte" : "non accepte"));
//            System.out.println("-----"+project.getId());
            Devis devis = new Devis(dateEmission, dateValidite, montanEstime, isAccepted, project);
            if(devis.isAccept()){
                project.setProjetStatus(ProjetStatus.FINISHED);
                projectService.updateProjectStatus(project);
            }else {
                project.setProjetStatus(ProjetStatus.CANCELLED);
                projectService.updateProjectStatus(project);
            }

            if (project.getId() == 0) {
                throw new SQLException("devis ne pas ajouter correct, id project manquant.");
            }

            devisService.saveDevis(devis, project);


        } catch (SQLException e) {
            System.out.println("error : " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }


    private static void addComposantToProject(ProjectService projectService, Scanner inp, int projectId) throws SQLException {
        System.out.println("enter type de composant (1 - materiel, 2 - main d'œuvre):");
        int componentType= inp.nextInt();

        System.out.println("enter name de composant");
        String componentName = inp.next();


        double tauxTva = ValidateInputs.validateDouble("enter taux TVA en %");

        if (componentType == 1) {
            double coutUnitaire =ValidateInputs.validateDouble("enter cout unitaire en €");

            double quantite= ValidateInputs.validateDouble("enter quantite");

            double coutTransport = ValidateInputs.validateDouble("enter cout de transport en €");

            double coefficientQualite = ValidateInputs.validateDouble("Entrez le coefficient de qualité du matériau (1.0 = standard, > 1.0 = haute qualité)");

            Material material = new Material(componentName, "Matériel", tauxTva, null, coutUnitaire, quantite, coutTransport, coefficientQualite);
            projectService.addComposantToProject(projectId, material);
        } else if (componentType == 2) {
            System.out.println("enter type d'ouvrier (1 - ouvrier de base, 2 - specialiste):");
            String typeOuvrier = inp.nextInt() == 1 ? "ouvrier de base" : "spécialiste";

            double tauxHoraire = ValidateInputs.validateDouble("enter taux horaire en €");

            double heuresTravail = ValidateInputs.validateDouble("enter nombre heures de travail");

            double productiviteOuvrier = ValidateInputs.validateDouble("Entrez le facteur de productivité (1.0 = standard, > 1.0 = haute productivité)");

            MainDouvre mainDouvre = new MainDouvre(componentName, "Main d'œuvre", tauxTva, null, typeOuvrier, tauxHoraire, heuresTravail, productiviteOuvrier);
            projectService.addComposantToProject(projectId, mainDouvre);
        }

        System.out.println("composant ajoute avec succes");
    }


    public static void detailProject(ProjectService projectService, ComposanService composanService, Scanner inp) throws SQLException {
        System.out.println("enter name de projet:");
        String projectName = inp.next();

        Optional<Projet> projectOpt = projectService.getProjectByName(projectName);
        if (projectOpt.isPresent()) {
            Projet project = projectOpt.get();
            System.out.println("--- detail de projet ---");
            System.out.println("===============================================================================================");
            System.out.println("Name: " + project.getName() + " |client :" + project.getClient().getName() + " |cout total:" + project.getCoutTotal() + " |status:" + project.getProjetStatus());

            List<Material> materials = composanService.getAllMaterialByProjectName(projectName);
            if (materials.isEmpty()) {
                System.out.println("aucun material trouve pour ce projet");
            }
            System.out.println("======================================= composants =============================================");
            materials.stream()
                    .forEach(material -> {
                        System.out.println("type : " + material.getComposantType() + " |name : " + material.getName() + " |taux TVA : " + material.getTauxTva());
                        System.out.println("cout Unitaire: " + material.getCoutUnitaire() + " |quatite: " + material.getQuantite() + " |cout de transport: " + material.getCoutTransport() + " |coefficient de qualite: " + material.getCoefficientQualite());
                        System.out.println("--------------------------------------------------------------------------------------------");
                    });

            List<MainDouvre> mainDouvres = composanService.getAllMainDouvreByProjectName(projectName);
            if (mainDouvres.isEmpty()) {
                System.out.println("aucun main ouvre trouve pour ce projet");
            }
            mainDouvres.stream()
                    .forEach(mainDouvre -> {
                        System.out.println("type : " + mainDouvre.getComposantType() + " |name : " + mainDouvre.getName() + " |taux TVA : " + mainDouvre.getTauxTva());
                        System.out.println("type ouvrier: " + mainDouvre.getTypeOuvrier() + " | taux horaire :" + mainDouvre.getTauxHoraire() + " | heures de travail: " + mainDouvre.getHeuresTravail() + " | productivite ouvrier: " + mainDouvre.getProductiviteOuvrier());
                        System.out.println("--------------------------------------------------------------------------------------------");
                    });

            System.out.println("======================================== calcul cout total =====================================");
            System.out.println("cout total material: " + composanService.calculCoutTotalMaterial(projectName));
            System.out.println("cout total main d'œuvre: " + composanService.calculCoutTotalMainDouvre(projectName));
            System.out.println("cout total: " + project.getCoutTotal());
            double coutTotalAvecMarge = project.getCoutTotal() + (project.getCoutTotal() * project.getMargeBenifit() / 100);
            System.out.println("cout total avec marge :" + coutTotalAvecMarge);
            System.out.println("===============================================================================================");



        } else {
            System.out.println("Project not found");
        }
    }

   public static void updateProjectStatus(ProjectService projectService, Scanner inp) throws SQLException {
    System.out.println("enter name de projet:");
    String projectName = inp.next();

    Optional<Projet> projectOpt = projectService.getProjectByName(projectName);
    if (projectOpt.isPresent()) {
        Projet project = projectOpt.get();
        System.out.println("status actuel  projet: " + project.getProjetStatus());

        System.out.println("entrez nouveau status du projet: \n 1 - INPROGRESS, 2 - FINISHED, 3 - CANCELED");
        int status = inp.nextInt();
        ProjetStatus newStatus = null;
        switch (status) {
            case 1:
                newStatus = ProjetStatus.INPROGRESS;
                break;
            case 2:
                newStatus = ProjetStatus.FINISHED;
                break;
            case 3:
                newStatus = ProjetStatus.CANCELLED;
                break;
            default:
                System.out.println("status invalide");
                return;
        }
        project.setProjetStatus(newStatus);
        projectService.updateProjectStatus(project);
        System.out.println("status de project updated");
    } else {
        System.out.println("project not found");
    }
}







    }