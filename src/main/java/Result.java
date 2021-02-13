import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Result {
   private static Connection connection = Connect.connection;
   private static Statement statement;
    public static void getResult(String string) throws SQLException {
        try {
        if (string.equals("maxSize")){
            statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT size FROM file_information ORDER BY size DESC LIMIT 1;");
            if (result.next()){
                long size = result.getLong(1);
                System.out.println(size);
            }
            statement.close();
            result.close();
        }

        if(string.equals("minSize")){
            statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT size FROM file_information ORDER BY size LIMIT 1;");
            if (result.next()){
                long size = result.getLong(1);
                System.out.println(size);
            }
            statement.close();
            result.close();
        }

        if(string.equals("sumSize")){
            statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT SUM(size) as `total_sum` FROM file_information;");
            if (result.next()){
                long size = result.getLong(1);
                System.out.println(size);
            }
            statement.close();
            result.close();
        }

        if (string.equals("dateOld")){
            statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT `time_modify` FROM `file_information` ORDER BY `time_modify` LIMIT 1;");
            if (result.next()){
                String date = result.getString(1);
                System.out.println(date);
            }
            statement.close();
            result.close();
        }

            if (string.equals("dateNew")){
                statement = connection.createStatement();
                ResultSet result = statement.executeQuery("SELECT `time_modify` FROM `file_information` ORDER BY `time_modify` DESC LIMIT 1;");
                if (result.next()){
                    String date = result.getString(1);
                    System.out.println(date);
                }
                statement.close();
                result.close();
            }

    }catch (Exception ex){
            ex.printStackTrace();
        }
}
}
