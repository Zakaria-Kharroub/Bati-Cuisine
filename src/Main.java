import config.DbConnection;

public class Main {
    public static void main(String[] args) {
        DbConnection dbConnection = DbConnection.getInstance();
        dbConnection.getConnection();



        System.out.printf("Hello and welcome!");

    }
}