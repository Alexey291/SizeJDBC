import java.io.IOException;
import java.nio.file.*;
import java.util.Collection;


public class AddTree {
    public static void newTree(Path directory, Collection<Path> all) throws IOException {
        try {
            try (DirectoryStream<Path> ds = Files.newDirectoryStream(directory)) {
                for (Path child : ds) {
                    if (Files.isDirectory(child)) {
                        newTree(child, all);
                    } else {
                        all.add(child);
                    }
                }
            }
        }catch (AccessDeniedException ex){
            System.out.println(ex.getLocalizedMessage() + " недоступна");
        }
    }
}
