import config.DbConnection;
import domaine.Client;
import domaine.Projet;
import domaine.ProjetStatus;
import service.ClientService;
import service.ProjectService;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;


import domaine.Material;
import domaine.MainDouvre;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws SQLException {
        DbConnection dbConnection = DbConnection.getInstance();
        dbConnection.getConnection();

        ClientService clientService = new ClientService();
        ProjectService projectService = new ProjectService();
        Scanner inp = new Scanner(System.in);

        int choix;
        do {
            choix = displayMainMenu(inp);
            switch (choix) {
                case 1:
                    manageClients(clientService, inp);
                    break;
                case 2:
                    manageProjects(projectService, inp, clientService);
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
                    System.out.println("retour au menu principal");
                    break;
                default:
                    System.out.println("choix invalide");
                    break;
            }
        } while (choixClient != 6);
    }


    private static void manageProjects(ProjectService projectService, Scanner inp, ClientService clientService) throws SQLException {
        int choixProject;
        do {
            choixProject = projectMenu(inp);
            switch (choixProject) {
                case 1:
                    addProject(projectService, inp, clientService);
                    break;
                case 2:
//                    System.out.println('detail projet by name');
                    break;
                case 3:
//                    System.out.println('delete projet');
                    break;
                case 4:
//                   System.out.println('update projet');
                    break;
                case 5:
                    System.out.println("retour au menu principal");
                    break;
                default:
                    System.out.println("choix invalide");
                    break;
            }
        } while (choixProject != 5);
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
        System.out.println("| 3 - delete projet                                            |");
        System.out.println("| 4 - update projet                                            |");
        System.out.println("| 5 - exit                                                     |");
        System.out.println("+--------------------------------------------------------------+");

        return inp.nextInt();
    }

    private static void addClient(ClientService clientService, Scanner inp) throws SQLException {
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

                System.out.println("ce client est professionnel? \n true / false");
                boolean isProfessional = inp.nextBoolean();

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

    private static void detailClientByname(ClientService clientService, Scanner inp) throws SQLException {
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

    private static void deleteClient(ClientService clientService, Scanner inp) throws SQLException {
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

    private static void updateClient(ClientService clientService, Scanner inp) throws SQLException {
        System.out.println("enter name de client vous voulez modifier");
        String nameUpdate = inp.next();

        inp.nextLine();

        System.out.println("enter nouveau name");
        String nouvName = inp.nextLine();

        System.out.println("enter nouveau phone");
        String nouvPhone = inp.nextLine();

        System.out.println("enter nouveau address");
        String nouvAddress = inp.nextLine();

        System.out.println("ce client est un professionnel? \n true / false");
        boolean nouvIsProfessional = inp.nextBoolean();

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



    private static void addProject(ProjectService projectService, Scanner inp, ClientService clientService) throws SQLException {
        System.out.println("enter name de projet");
        String projectName = inp.next();

        System.out.println("enter name de client pour associer a ce projet");
        String clientName = inp.next();

        System.out.println("enter marge benifit");
        double margeBenifit = inp.nextDouble();

        try {
            Client client = clientService.findByname(clientName).orElseThrow(
                    () -> new IllegalArgumentException("client not found")
            );

            Projet project =new Projet(projectName, margeBenifit, 0, ProjetStatus.INPROGRESS, client, new ArrayList<>());
            int projectId = projectService.addProject(project);

            boolean addMoreComposants=true;
            while (addMoreComposants) {
                System.out.println("vous voulez ajouter un composant a ce projet ? \n oui/non");
                String response = inp.next().toLowerCase();
                if (response.equals("oui")) {
                    addComposantToProject(projectService, inp, projectId);
                } else {
                    addMoreComposants= false;
                }
            }

            System.out.println("Projet ajouté avec succès");
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout du projet: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void addComposantToProject(ProjectService projectService, Scanner inp, int projectId) throws SQLException {
        System.out.println("enter type de composant (1 - materiel, 2 - main d'œuvre):");
        int componentType= inp.nextInt();

        System.out.println("enter name de composant");
        String componentName = inp.next();

        System.out.println("enter taux TVA:");
        double tauxTva = inp.nextDouble();

        if (componentType == 1) {
            System.out.println("enter le cout unitaire:");
            double coutUnitaire =inp.nextDouble();

            System.out.println("enter la quantite:");
            double quantite= inp.nextDouble();

            System.out.println("enter cout de transport:");
            double coutTransport = inp.nextDouble();

            System.out.println("Entrez le coefficient de qualité du matériau (1.0 = standard, > 1.0 = haute qualité)");
            double coefficientQualite = inp.nextDouble();

            Material material = new Material(componentName, "Matériel", tauxTva, null, coutUnitaire, quantite, coutTransport, coefficientQualite);
            projectService.addComposantToProject(projectId, material);
        } else if (componentType == 2) {
            System.out.println("enter type d'ouvrier (1 - ouvrier de base, 2 - specialiste):");
            String typeOuvrier = inp.nextInt() == 1 ? "ouvrier de base" : "spécialiste";

            System.out.println("enter taux horaire:");
            double tauxHoraire = inp.nextDouble();

            System.out.println("enter nombre d'heures de travail:");
            double heuresTravail = inp.nextDouble();

            System.out.println("Entrez le facteur de productivité (1.0 = standard, > 1.0 = haute productivité)");
            double productiviteOuvrier = inp.nextDouble();

            MainDouvre mainDouvre = new MainDouvre(componentName, "Main d'œuvre", tauxTva, null, typeOuvrier, tauxHoraire, heuresTravail, productiviteOuvrier);
            projectService.addComposantToProject(projectId, mainDouvre);
        }

        System.out.println("composant ajoute avec succes");
    }



































//    private static void addProject(ProjectService projectService, Scanner inp, ClientService clientService) throws SQLException {
//        System.out.println("enter name de project");
//        String projectName = inp.next();
//
//        System.out.println("enter name de clien pour ce project");
//        String clientName = inp.next();
//
//        System.out.println("enter marge benifit");
//        double margeBenifit = inp.nextDouble();
//
//        try {
//
//            Client client = clientService.findByname(clientName).orElseThrow(
//                    () -> new IllegalArgumentException("client not found")
//            );
//
//            Projet project = new Projet(projectName, margeBenifit, 0, ProjetStatus.INPROGRESS, client, null);
//            projectService.addProject(project);
//            System.out.println("projet ajouter succes");
//        } catch (SQLException e) {
//            System.out.println("error de ajouter projet: " + e.getMessage());
//        } catch (IllegalArgumentException e) {
//            System.out.println(e.getMessage());
//        }
//
//        System.out.println("vous voulez ajouter des composants pour ce projet? \n yes / no");
//        String reponse = inp.next();
//        if (reponse.equals("yes")) {
//            System.out.println("enter type de composant \n 1 - material | 2 - main ouvre");
//            int typeComposant = inp.nextInt();
//            if (typeComposant == 1) {
//                System.out.println("enter name de material");
//                String nameMaterial = inp.next();
//
//                System.out.println("enter taux tva");
//                double tauxTva = inp.nextDouble();
//
//                System.out.println("enter cout unitaire");
//                double coutUnitaire = inp.nextDouble();
//
//                System.out.println("Entrez la quantité de ce matériau (en m²)");
//                double quantite = inp.nextDouble();
//
//                System.out.println("enter cout transport");
//                double coutTransport = inp.nextDouble();
//
//                System.out.println("Entrez le coefficient de qualité du matériau (1.0 = standard, > 1.0 = haute qualité) : 1.1");
//                double coefficientQualite = inp.nextDouble();
//
//                try {
////                    stocker composants dans list des composants de projet
//                    System.out.println("material ajouter avec succes");
//                } catch (SQLException e) {
//                    System.out.println("error de ajouter material: " + e.getMessage());
//                }
////
//
//
//            } else if (typeComposant == 2) {
//                System.out.println("enter name de main ouvre");
//                String nameMainOuvre = inp.next();
//
//                System.out.println("enter taux tva");
//                double tauxTva = inp.nextDouble();
//
//                System.out.println("Entrez le type de main-d'œuvre \n ( 1 - Ouvrier de base | 2 - Spécialiste");
//                int choixMainOuvre = inp.nextInt();
//
//                if (choixMainOuvre == 1) {
//                    String typeMainOuvre = "Ouvrier de base";
//
//                } else if (choixMainOuvre == 2) {
//                    String typeMainOuvre = "Spécialiste";
//                }
//
//                System.out.println("Entrez le taux horaire de l'ouvrier (en €/h) : ");
//                double tauxHoraire = inp.nextDouble();
//
//                System.out.println("Entrez le nombre d'heures de travail de l'ouvrier : ");
//                double heuresTravail = inp.nextDouble();
//
//                System.out.println("Entrez le facteur de productivité (1.0 = standard, > 1.0 = haute productivité)");
//                double productiviteOuvrier = inp.nextDouble();
//
//                try {
////                    stocker composants dans list des composants de projet
//                    System.out.println("main ouvre ajouter avec succes");
//                } catch (SQLException e) {
//                    System.out.println("error de ajouter main ouvre: " + e.getMessage());
//                }
//
//
//            }
//        }else if (reponse.equals("no")){
//            System.out.println("project ajouter avec succes");
//        }
//    }
}