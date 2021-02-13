
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class Result {
    private static Connection connection = Connect.connection;
    private static Statement statement;
    public static void getResult(String string) throws SQLException {
        try {
            if (string.equals("max size")){
                statement = connection.createStatement();
                ResultSet result = statement.executeQuery("SELECT size FROM file_information ORDER BY size DESC LIMIT 1;");
                if (result.next()){
                    long size = result.getLong(1);
                    System.out.println(size + " байт");
                }
                statement.close();
                result.close();
            }

            if(string.equals("min size")){
                statement = connection.createStatement();
                ResultSet result = statement.executeQuery("SELECT size FROM file_information ORDER BY size LIMIT 1;");
                if (result.next()){
                    long size = result.getLong(1);
                    System.out.println(size + " байт");
                }
                statement.close();
                result.close();
            }

            if(string.equals("sum size")){
                statement = connection.createStatement();
                ResultSet result = statement.executeQuery("SELECT SUM(size) as `total_sum` FROM file_information;");
                if (result.next()){
                    long size = result.getLong(1);
                    System.out.println(size + " байт");
                }
                statement.close();
                result.close();
            }

            if (string.equals("old date")){
                statement = connection.createStatement();
                ResultSet result = statement.executeQuery("SELECT `time_modify` FROM `file_information` ORDER BY `time_modify` LIMIT 1;");
                if (result.next()){
                    String date = result.getString(1);
                    System.out.println(date);
                }
                statement.close();
                result.close();
            }

            if (string.equals("new date")){
                statement = connection.createStatement();
                ResultSet result = statement.executeQuery("SELECT `time_modify` FROM `file_information` ORDER BY `time_modify` DESC LIMIT 1;");
                if (result.next()){
                    String date = result.getString(1);
                    System.out.println(date);
                }
                statement.close();
                result.close();
            }

            if(string.equals("extension")){
                statement = connection.createStatement();
                ResultSet result = statement.executeQuery("SELECT DISTINCT extension FROM file_information;");
                Set<String> extensions = new HashSet<>();
                while (result.next()){
                    extensions.add(result.getString("extension"));
                }
                System.out.println(extensions.size() + " уникальных типов файлов");
                statement.close();
                result.close();
            }

            if(string.equals("extension 10%")){
                statement = connection.createStatement();
                ResultSet result = statement.executeQuery("SELECT extension FROM file_information WHERE " +
                        "size > ((SELECT size FROM file_information ORDER BY size DESC LIMIT 1) * 0.9);");
                List<String> extensions = new ArrayList<>();
                while (result.next()){
                    extensions.add(result.getString("extension"));
                }
                HashMap<String, Integer> mapExtension = new HashMap<>();
                for (String extension : extensions){
                    mapExtension.put(extension,0);
                }
                for (String extension : extensions){
                    for (int i = 0; i < mapExtension.size(); i++){
                        if (mapExtension.containsKey(extension)){
                            mapExtension.replace(extension, mapExtension.get(extension),mapExtension.get(extension) + 1);
                        }
                    }
                }
                mapExtension.forEach((key, value) -> System.out.println(key + " " + value + " размером 90% от максимального"));
                statement.close();
                result.close();
            }

            if(string.equals("not access 10%")){
                statement = connection.createStatement();
                ResultSet result = statement.executeQuery("SELECT name FROM file_information WHERE " +
                        "size > ((SELECT size FROM file_information ORDER BY size DESC LIMIT 1) * 0.9) AND hidden = 1;");
                List<String> closeFile = new ArrayList<>();
                while (result.next()){
                    closeFile.add(result.getString("name"));
                }
                System.out.println(closeFile.size() + " закрытых файлов размером 90% от максимального");
                statement.close();
                result.close();
            }

            if(string.equals("open file 10%")){
                statement = connection.createStatement();
                ResultSet result = statement.executeQuery("SELECT name FROM file_information WHERE " +
                        "size > ((SELECT size FROM file_information ORDER BY size DESC LIMIT 1) * 0.9) AND hidden = 0;");
                List<String> openFile = new ArrayList<>();
                while (result.next()){
                    openFile.add(result.getString("name"));
                }
                System.out.println(openFile.size() + " открытых файлов размером 90% от максимального");
                statement.close();
                result.close();
            }

            if (string.equals("size 10%")){
                Statement statementA = connection.createStatement();
                ResultSet resultA = statementA.executeQuery("SELECT size FROM file_information " +
                        "WHERE size < 1000 AND size > ((SELECT size FROM file_information ORDER BY size DESC LIMIT 1) * 0.9);");
                Statement statementB = connection.createStatement();
                ResultSet resultB = statementB.executeQuery("SELECT size FROM file_information " +
                        "WHERE size < 10000 AND size > 1000 AND size > ((SELECT size FROM file_information ORDER BY size DESC LIMIT 1) * 0.9);");
                Statement statementC = connection.createStatement();
                ResultSet resultC = statementC.executeQuery("SELECT size FROM file_information " +
                        "WHERE size < 50000 AND size > 10000 AND size > ((SELECT size FROM file_information ORDER BY size DESC LIMIT 1) * 0.9);");
                Statement statementD = connection.createStatement();
                ResultSet resultD = statementD.executeQuery("SELECT size FROM file_information " +
                        "WHERE size < 1000000 AND size > 50000 AND size > ((SELECT size FROM file_information ORDER BY size DESC LIMIT 1) * 0.9);");
                Statement statementE = connection.createStatement();
                ResultSet resultE = statementE.executeQuery("SELECT size FROM file_information " +
                        "WHERE size > 1000000 AND size > ((SELECT size FROM file_information ORDER BY size DESC LIMIT 1) * 0.9);");
                List<Long> sizeA = new ArrayList<>();
                List<Long> sizeB = new ArrayList<>();
                List<Long> sizeC = new ArrayList<>();
                List<Long> sizeD = new ArrayList<>();
                List<Long> sizeE = new ArrayList<>();
                while (resultA.next()){
                    sizeA.add(resultA.getLong("size"));
                }
                while (resultB.next()){
                    sizeB.add(resultB.getLong("size"));
                }
                while (resultC.next()){
                    sizeC.add(resultC.getLong("size"));
                }
                while (resultD.next()){
                    sizeD.add(resultD.getLong("size"));
                }
                while (resultE.next()){
                    sizeE.add(resultE.getLong("size"));
                }
                System.out.println( "Количество файлов до 1Кб размером 90% от максимального: " + sizeA.size() + "\n"
                        + "Количество файлов до 10Кб размером 90% от максимального: " + sizeB.size() + "\n"
                        + "Количество файлов до 50Кб размером 90% от максимального: " + sizeC.size() + "\n"
                        + "Количество файлов до 1Мб размером 90% от максимального: " + sizeD.size() + "\n"
                        + "Количество файлов свыше 1Мб размером 90% от максимального: " + sizeE.size());
                statementA.close();
                statementB.close();
                statementC.close();
                statementD.close();
                statementE.close();
                resultA.close();
                resultB.close();
                resultC.close();
                resultD.close();
                resultE.close();
            }

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
