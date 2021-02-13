import java.io.IOException;
import java.nio.file.*;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.*;

public class Main {
    private static String dbName = "jdbc:mysql://localhost:3306/test_file?useSSL=false&serverTimezone=UTC";
    private static String dbUser = "root";
    private static String dbPass = "14101410";
    public static void main(String[] args) throws IOException, SQLException, ParseException {
        System.out.println("Введите адрес папки: ");
        Scanner scanner = new Scanner(System.in);
        String file = scanner.nextLine();
        Path path = Paths.get(file);
        List<Path> all = new ArrayList<>();
        AddTree.newTree(path,all);
        Connect.getConnection(dbName,dbUser,dbPass,all);
        while (true){
        Scanner scanner1 = new Scanner(System.in);
        String query = scanner1.nextLine();
        Result.getResult(query);}

    }

}


