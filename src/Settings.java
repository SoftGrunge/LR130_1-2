import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;


public class Settings {

    Properties properties;
    public static final String URL = "url";
    public static final String USER = "userName";
    public static final String PASSWORD = "password";
    public Settings() {
        properties = new Properties();
        File file = new File("settings.prop");
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            properties.load(new FileReader(file));
        } catch (IOException ex) {}
    }

    public String getValue(String key){
        String value = properties.getProperty(key);
        return value;
    }

    public String getUrl(){
        String url = properties.getProperty("url");
        return url;
        }

    public String getUser(){
        String user = properties.getProperty("user");
        return user;
        }

    public String getPsw(){
        String psw = properties.getProperty("psw");
        return psw;
        }
}