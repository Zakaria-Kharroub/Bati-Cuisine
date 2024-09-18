import config.DbConnection;
import domaine.Client;
import service.ClientService;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {
        DbConnection dbConnection = DbConnection.getInstance();
        dbConnection.getConnection();

        ClientService clientService = new ClientService();
        Scanner inp=new Scanner(System.in);


//            clientService.addClient("John Doe", "123456789", "1234 rue de la rue", true);
//            clientService.addClient("hinsan", "987654321", "4321 rue de la rue", false);

        int choix;
        do {


            System.out.println("+--------------------------------------------------------------+");
            System.out.println("|                    Menu Principal                            |");
            System.out.println("+--------------------------------------------------------------+");
            System.out.println("| 1 - ajouter client                                           |");
            System.out.println("| 2 - afficher tous les clients                                |");
            System.out.println("| 3 - detail client by name                                    |");
            System.out.println("| 4 - supprimer un client                                      |");
            System.out.println("| 5 - modifier un client                                       |");
            System.out.println("| 6 - exit                                                     |");
            System.out.println("+--------------------------------------------------------------+");

            choix = inp.nextInt();
            switch (choix) {
                case 1:
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
                    break;
                case 2:
                    System.out.println("---------------------------------------------------------------------------------------");
                    System.out.println("                             liste des clients                                       ");
                    List<Client> clients = clientService.findAllClients();
                    for (Client client : clients) {
                        System.out.println("--------------------------------------------------------------------------------------");
                        System.out.println("id: " + client.getId() + "| name: " + client.getName() + "| phone: " + client.getPhone() + "| address: " + client.getAddress() + "| professional: " + (client.getIsProfessional() ? "oui" : "non"));
                    }
                    System.out.println("--------------------------------------------------------------------------------------");
                    break;
                case 3:
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
                    break;
                case 4:
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

                    break;
                case 5:
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
                    break;







                case 6:
                    System.out.println("Merci et aurevoir");
                    break;
                default:
                    System.out.println("choix invalide");
                    break;
            }

        }while (choix != 6);


    }
}