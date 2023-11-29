package Common;

import java.io.File;

public class Utilities {
    public static String getProjectPath() {
    String projectPath = new File("C:\\Users\\User\\IdeaProjects\\Test").getAbsolutePath();
    return projectPath;
    }
}
