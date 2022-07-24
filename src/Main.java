import org.w3c.dom.xpath.XPathException;
import org.xml.sax.SAXException;

import javax.management.modelmbean.XMLParseException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.TransformerException;
import java.sql.*;
//Efimov DA
public class Main {
    public static void main(String[] args) throws ClassNotFoundException, SAXException, XMLParseException, XMLStreamException, TransformerException, XPathException, ParserConfigurationException {
        String url = "jdbc:mysql://localhost:3306/mysql";
        String userName = "root";
        String password = "54321";
        Class.forName("com.mysql.cj.jdbc.Driver");
        Settings settings = new Settings();
        try(Connection connection = DriverManager.getConnection(settings.getValue(Settings.URL),settings.getValue(Settings.USER),settings.getValue(Settings.PASSWORD))) {
            Statement statement = connection.createStatement();
            statement.executeUpdate("create  table  if not exists Authors(id mediumint not null auto_increment, name VARCHAR(64), notices VARCHAR(255) ,primary key (id))");
            statement.executeUpdate("insert into authors(name) values ( 'Arnold Grey')");
            statement.executeUpdate("insert into authors( name, notices) values ('Tom Hawkins','new author')");
            statement.executeUpdate("insert into authors(name) values ('Jim Beam')");
            statement.executeUpdate("create table if not exists Test(id mediumint not null auto_increment, name varchar(64), body varchar(255), primary key (id))");
            statement.executeUpdate("insert into test (name) value ('vitaliy')");
            statement.executeUpdate("create table if not exists Documents(id mediumint not null auto_increment, name VARCHAR(100) not null , text VARCHAR(1024), creationDate VARCHAR(50) not null ,authorID mediumint not null , primary key (id))");
            statement.executeUpdate("insert into documents(name,text,creationDate,authorID) values('Project plan', 'First content','2022-12-11','1')");
            statement.executeUpdate("insert into documents(name,text,creationDate,authorID) values('First report', 'Second content','2021-07-28','2')");
            statement.executeUpdate("insert into documents(name,text,creationDate,authorID) values('Test result', 'Third content','2020-09-12','2')");
            statement.executeUpdate("insert into documents(name,text,creationDate,authorID) values('Second report', 'Report content','2021-10-04','3')");
            statement.executeUpdate("UPDATE authors SET notices = 'No data' WHERE notices is null ");
            statement.executeUpdate("UPDATE authors SET notices = 'new author' where name ='Tom Hawkins'");
            System.out.println(statement.execute("SELECT authors.name, documents.name FROM documents RIGHT JOIN authors ON authors.id = documents.authorID WHERE authors.name='Tom Hawkins';"));
            System.out.println(statement.execute("SELECT documents.id, documents.name,authors.id FROM documents RIGHT JOIN authors ON authors.id = documents.authorID WHERE documents.name LIKE ('%Tom Hawkins%');"));
            } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        }
    }

