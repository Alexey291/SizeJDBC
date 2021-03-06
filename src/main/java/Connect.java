import java.nio.file.Path;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Connect {
    public static Connection connection;
    private static StringBuilder insertQuery = new StringBuilder();
    private static String dbName = "jdbc:mysql://localhost:3306/test_file?useSSL=false&serverTimezone=UTC";
    private static String dbUser = "root";
    private static String dbPass = "14101410";
    static Long time = System.currentTimeMillis();



    public static Connection getConnection(String dbName, String dbUser, String dbPass, List<Path> all) {
        {
            try {
                List<String> stringList = putFile(all);
                connection = DriverManager.getConnection(dbName,dbUser,dbPass);
                connection.createStatement().execute("DROP TABLE IF EXISTS file_information");
                connection.createStatement().execute("CREATE TABLE file_information(" +
                        "id INT NOT NULL AUTO_INCREMENT, " +
                        "name TEXT, " +
                        "size BIGINT UNSIGNED, " +
                        "time_modify DATE, " +
                        "extension TINYTEXT, " +
                        "hidden TINYINT(1), " +
                        "PRIMARY KEY(id))");

                for (String string : stringList){
                    connection.createStatement().execute(string);
                }
                System.out.println("Сканирование завершено за: " + (System.currentTimeMillis() - time)/1000 + " секунд");
                System.out.println("Доступные команды: " + "\n" +
                        "   max size" + "\n" +
                        "   min size" + "\n" +
                        "   sum size" + "\n" +
                        "   old date" + "\n" +
                        "   new date" + "\n" +
                        "   extension" + "\n" +
                        "   extension 10%" + "\n" +
                        "   not access 10%" + "\n" +
                        "   open file 10%" + "\n" +
                        "   size 10%" + "\n");
                System.out.println("Введите команду: ");

            } catch (SQLException | ParseException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }



    public static List<String> putFile(List<Path> all) throws SQLException, ParseException {
        System.out.println("Сканирование файла может занять несколько минут...");
       List<String> stringList = new ArrayList<>();
        String extension;
        StringBuilder builder = new StringBuilder();
        for (Path path : all){
            long size = path.toFile().length();
            String name = path.getFileName().toString();
            boolean hidden = path.toFile().isHidden();
            if (name.contains(".")){
                extension = name.substring(name.lastIndexOf("."));}
            else {
                extension = "not available";
            }
            long time = path.toFile().lastModified();
            Calendar date = Calendar.getInstance();
            date.setTimeInMillis(time);
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            insertQuery.append("'")
                    .append(name).append("'")
                    .append(", ").append(size)
                    .append(",").append("'")
                    .append(formatter.format(date.getTime()))
                    .append("'").append(",")
                    .append("'")
                    .append(extension)
                    .append("'")
                    .append(",")
                    .append(hidden);
            builder.append("INSERT INTO file_information (name, size, time_modify, extension, hidden) VALUE")
                    .append("(")
                    .append(insertQuery)
                    .append(")");


            stringList.add(builder.toString());
            builder.setLength(0);
            insertQuery.setLength(0);
        }
        return stringList;
    }
}
