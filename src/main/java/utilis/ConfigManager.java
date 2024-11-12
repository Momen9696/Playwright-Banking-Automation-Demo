package utilis;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigManager {
    private static ConfigManager manager;
    public static String env = "/PreProdEnv";
    public static String DefaultProjectId = "parabank";
    private static final Properties properties = new Properties();

    static String projectId = "parabank";

    public static String getProjectId() {
        return projectId;
    }

    public static void setProjectID(String projectId) {
        ConfigManager.projectId = projectId;
    }

    public ConfigManager() throws IOException {
        properties.load(new FileInputStream("resources/" + env + "/config-" + projectId + ".properties"));
    }


    public static ConfigManager getInstance() {
        if (manager == null) {
            synchronized (ConfigManager.class) {
                try {
                    manager = new ConfigManager();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }

        return manager;
    }

    public String getString(String key) {
        return System.getProperty(key, properties.getProperty(key));
    }


    public static String getDefaultProjectID() {
        return DefaultProjectId;
    }

}
