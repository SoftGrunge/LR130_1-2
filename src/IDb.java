import java.util.List;

public interface IDb extends AutoCloseable {
    boolean addAuthor(Authors authors) throws DocumentException;

    boolean addDocument(Documents documents, Authors authors) throws DocumentException;
    List<Documents> findAuthorDocument (Authors authors) throws DocumentException;
    List <Documents> findDocumentByContent(String content) throws DocumentException;
    boolean deleteAuthor(Authors author) throws DocumentException;
    boolean deleteAuthor(int id) throws DocumentException;
}
