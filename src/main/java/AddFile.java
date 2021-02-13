/*import java.nio.file.Path;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AddFile {
    private static StringBuilder insertQuery = new StringBuilder();
    private static String dbName = "jdbc:mysql://localhost:3306/test_file?useSSL=false&serverTimezone=UTC";
    private static String dbUser = "root";
    private static String dbPass = "14101410";
    public static void putFile(List<Path> all) throws SQLException, ParseException {
        for (Path path : all){
            long size = path.toFile().length();
            String name = path.getFileName().toString();
            long time = path.toFile().lastModified();
            Calendar date = Calendar.getInstance();
            date.setTimeInMillis(time);
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
           // Date dateForServer = new SimpleDateFormat("yyyy-MM-d h:m:s").parse(date.toString());
            insertQuery.append("'")
                    .append(name).append("'")
                    .append(", ").append(size)
                    .append(",").append("'")
                    .append(formatter.format(date.getTime()))
                    .append("'");
            String query = "INSERT INTO file_information (name, size, time_modify) VALUE" + "(" + insertQuery + ")";
            Connect.getConnection(dbName,dbUser,dbPass).createStatement().execute(query);
            insertQuery.setLength(0);
        }
    }
}


 */