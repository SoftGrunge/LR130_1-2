import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Documents {


    private final int document_id;
    private String title;
    private String text;
    private Date date;
    private int author_id;

    public Documents(int document_id, String title, String text, int author_id) {
        this(document_id, title, text,
                new Date(System.currentTimeMillis()), author_id);
    }

    public Documents(int document_id, String title, String text, Date date, int author_id) {
        this.document_id = document_id;
        this.title = title;
        this.text = text;
        this.date = date;
        this.author_id = author_id;
    }

    public static List<Documents> getDocuments(){

        List<Documents> list = new ArrayList<>();
        Settings settings = new Settings();
        try (Connection connection = DriverManager.getConnection(settings.getValue(Settings.URL),settings.getValue(Settings.USER),settings.getValue(Settings.PASSWORD))) {

            Statement stm = connection.createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM DOCUMENTS");
            Documents document = null;
            while (rs.next()) {
                document = new Documents(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getDate(4),
                        rs.getInt(5));
                list.add(document);
            }

        } catch (SQLException ex) {
            Logger.getLogger(Authors.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public int getDocument_id() {
        return document_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(int author_id) {
        this.author_id = author_id;
    }



    @Override
    public String toString() {
        return "Documents{" + "document_id=" + document_id + ", title=" + title + ", text=" + text + ", date=" + date + ", author_id=" + author_id + '}';
    }

}