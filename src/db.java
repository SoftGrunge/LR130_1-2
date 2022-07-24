import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
public class db implements IDb {

    @Override
    public boolean addAuthor(Authors author) throws DocumentException {
        Settings setting = new Settings();
        try (Connection connection = DriverManager.getConnection(
                setting.getValue(Settings.URL),
                setting.getValue(Settings.USER),
                setting.getValue(Settings.PASSWORD))) {
            if (author.getAuthor_id() != 0
                    && author.getAuthor() != null
                    && !author.getAuthor().trim().equals("")
                    && author.getNotes() != null) {
                Statement stm = connection.createStatement();
                if (stm == null) stm = connection.createStatement();
                stm.executeUpdate("INSERT INTO Authors (id, name, notices) VALUES (" + author.getAuthor_id() + ",'" + author.getAuthor() + "','" + author.getNotes() + "')");
                return true;
            } else if (author.getAuthor_id() != 0 && author.getAuthor() == null) {
                Statement stm = connection.createStatement();
                if (stm == null) stm = connection.createStatement();
                stm.executeUpdate("UPDATE Authors SET notices = '" + author.getNotes() + "' WHERE id = " + author.getAuthor_id());
                return false;
            } else if (author.getAuthor_id() != 0 && author.getNotes() == null) {
                Statement stm = connection.createStatement();
                if (stm == null) stm = connection.createStatement();
                stm.executeUpdate("UPDATE Authors SET notices = '" + author.getNotes() + "' WHERE id = " + author.getAuthor_id());
                return false;
            } else
                throw new DocumentException("Update failed");
        } catch (SQLException ex) {
            throw new DocumentException("Connection not establish" + ex.getMessage());
        }

    }

    @Override
    public boolean addDocument(Documents doc, Authors author) throws DocumentException {
        Settings setting = new Settings();
        try (Connection connection = DriverManager.getConnection(
                setting.getValue(Settings.URL),
                setting.getValue(Settings.USER),
                setting.getValue(Settings.PASSWORD))) {
            if (doc.getDocument_id() != 0
                    && author.getAuthor_id() != 0
                    && doc.getAuthor_id() != 0
                    && doc.getTitle() != null
                    && !doc.getTitle().trim().equals("")
                    && doc.getText() != null
                    && !doc.getText().trim().equals("")
                    && doc.getDate() != null) {
                Statement stm = connection.createStatement();
                ;
                if (stm == null) stm = connection.createStatement();
                stm.executeUpdate("INSERT INTO Documents (id, name, text, creationDate, authorID) " +
                        "VALUES (" + doc.getDocument_id() + ",'" + doc.getTitle() + "', '" + doc.getText() + "', '" + doc.getDate() + "', " + doc.getAuthor_id() + ")");
                return true;
            } else if (doc.getDocument_id() != 0
                    && doc.getAuthor_id() != 0
                    && doc.getText() == null) {
                Statement stm = connection.createStatement();
                if (stm == null) stm = connection.createStatement();
                stm.executeUpdate("UPDATE Documents SET " + "name='" + doc.getTitle() + "', " +
                        "creationDate='" + doc.getDate() + "', " + "authorID=" + doc.getAuthor_id() + " WHERE id=" + doc.getDocument_id());
                return false;
            }else if(doc.getDocument_id()!=0 && doc.getAuthor_id()!=0 &&doc.getTitle()==null){
                Statement stm = connection.createStatement();
                if(stm ==null) stm = connection.createStatement();
                stm.executeUpdate(("UPDATE  documents SET " + "text='"+doc.getText()+"', "+ "creationDate='"+ doc.getDate()+"',"+"authorID="+
                        doc.getAuthor_id()+" WHERE id="+doc.getDocument_id()));
                return false;
            }
             else throw new DocumentException("Update failed");
        } catch (SQLException ex) {
            throw new DocumentException("Connection not establish" + ex.getMessage());
        }
    }


    @Override
    public List<Documents> findAuthorDocument(Authors author) throws DocumentException {
        Settings settings = new Settings();
        String sql;

        try (Connection connection = DriverManager.getConnection(settings.getValue(Settings.URL), settings.getValue(Settings.USER), settings.getValue(Settings.PASSWORD))) {
            Statement stm = connection.createStatement();
            if (author.getAuthor_id() != 1 && author.getAuthor() != null) {
                sql = "SELECT * FROM DOCUMENTS WHERE DOCUMENTS.authorID = " + author.getAuthor_id() + " "
                        + "OR DOCUMENTS.authorID = (SELECT AUTHORS.ID FROM AUTHORS WHERE name = '" + author.getAuthor() + "')";
                ResultSet rs = stm.executeQuery(sql);
                List<Documents> documents = new ArrayList<>();
                Documents document = null;
                while (rs.next()) {
                    document = new Documents(
                            rs.getInt(1),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getDate(4),
                            rs.getInt(5));
                    documents.add(document);
                }
                if (!documents.isEmpty()) return documents;
                else return null;

            } else if (author.getAuthor_id() != 0 && author.getAuthor() == null) {
                sql = "SELECT * FROM DOCUMENTS WHERE DOCUMENTS.authorID = " + author.getAuthor_id() + "";
                ResultSet rs = stm.executeQuery(sql);
                List<Documents> documents = new ArrayList<>();
                Documents document;
                while (rs.next()) {
                    document = new Documents(
                            rs.getInt(1),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getDate(4),
                            rs.getInt(5));
                    documents.add(document);
                }
                if (!documents.isEmpty()) return documents;
                else return null;
            } else if (author.getAuthor_id() == 0 && author.getAuthor() != null) {
                sql = "SELECT * FROM DOCUMENTS WHERE DOCUMENTS.authorID = (SELECT AUTHORS.ID FROM AUTHORS WHERE name = '" + author.getAuthor() + "')";
                ResultSet rs = stm.executeQuery(sql);
                List<Documents> documents = new ArrayList<>();
                Documents document;
                while (rs.next()) {
                    document = new Documents(
                            rs.getInt(1),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getDate(4),
                            rs.getInt(5));
                    documents.add(document);
                }
                if (!documents.isEmpty()) return documents;
                else return null;
            } else throw new DocumentException("Incorrect params, search not completed");
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

    }

    @Override
    public List<Documents> findDocumentByContent(String content) throws DocumentException {
        Settings settings = new Settings();
        String sql;
        try (Connection connection = DriverManager.getConnection(settings.getValue(Settings.URL), settings.getValue(Settings.USER), settings.getValue(Settings.PASSWORD))) {
            Statement stm = connection.createStatement();
            if (content == null || content.trim().isEmpty())
                throw new DocumentException("строка content равна null или является пустой");
            sql = "SELECT * FROM DOCUMENTS WHERE name LIKE '%" + content + "%' OR text LIKE '%" + content + "%'";
            ResultSet rs = stm.executeQuery(sql);
            List<Documents> documents = new ArrayList<>();
            Documents document;
            while (rs.next()) {
                document = new Documents(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getDate(4),
                        rs.getInt(5));
                documents.add(document);
            }
            if (!documents.isEmpty()) return documents;
            else return null;
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            throw new DocumentException("Connection not establish");
        }
    }

    @Override
    public boolean deleteAuthor(Authors author) throws DocumentException {
        Settings settings = new Settings();
        String sql;
        ResultSet rs = null;
        List<Authors> list = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(settings.getValue(Settings.URL), settings.getValue(Settings.USER), settings.getValue(Settings.PASSWORD))) {
            Statement stm = connection.createStatement();
            if (author.getAuthor_id() != 0) {
                if (stm == null) stm = connection.createStatement();
                stm.executeUpdate("DELETE FROM Documents WHERE authorID=" + author.getAuthor_id());
                stm.executeUpdate("DELETE FROM Authors WHERE id=" + author.getAuthor_id());
                return true;
            } else if (Authors.getAuthors() != null && !Authors.getAuthors().equals("")) {
                if (stm == null) stm = connection.createStatement();
                stm.executeQuery("SELECT id FROM Authors WHERE name = '" + Authors.getAuthors() + "'");
                if (!rs.next()) return false;
                int idAuthor = rs.getInt(1);
                stm.executeUpdate("DELETE FROM Documents WHERE authorID=" + idAuthor);
                stm.executeUpdate("DELETE FROM Authors WHERE id=" + idAuthor);
                return true;
            } else throw new DocumentException("Field is incorrect");
        } catch (SQLException e) {
            throw new DocumentException("Connection not establish");
        }
    }



 @Override
    public boolean deleteAuthor(int id) throws DocumentException {
        Settings settings = new Settings();
        ResultSet rs = null;
        try (Connection connection = DriverManager.getConnection(
                settings.getValue(Settings.URL),
                settings.getValue(Settings.USER),
                settings.getValue(Settings.PASSWORD))) {
            Statement stm = connection.createStatement();
            if(id != 0){
                if (stm == null) stm = connection.createStatement();
                stm.executeUpdate("DELETE FROM Documents WHERE authorID=" + id);
                stm.executeUpdate("DELETE FROM Authors WHERE id=" + id);
                return true;
            } else return false;
        }catch (SQLException e) {
            throw new DocumentException("Соединение не установлено");
        }
}


    @Override
    public void close() throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }


}