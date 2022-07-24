import java.sql.Date;
import java.util.List;

//EfimovDA
public class LR130_2 {


    public static void main(String[] args) {
        Settings settings = new Settings();
        System.out.println("url="+settings.getValue(Settings.URL));
        System.out.println("user="+settings.getValue(Settings.USER));
        System.out.println("psw="+settings.getValue(Settings.PASSWORD));

        for (Authors a: Authors.getAuthors()){
            System.out.println(a);
        }

        for (Documents d: Documents.getDocuments()){
            System.out.println(d);
        }

        db db = new db();


        try {
                 Authors author1 = new Authors(0, null, null);
                Authors author2 = new Authors(6, "Pelevin", "PostModern");
                 Authors author3 = new Authors(5, "Lem", "Nobody");
                Authors author4 = new Authors(5, null, "Author");
            System.out.println(db.addAuthor(author4));
//            Documents document1 = new Documents(8, null, "Book", new Date(107, 3 ,26), 6);
            Documents document2 = new Documents(6, null, "Science fiction novel", new Date(111, 7 ,29), 6);
//            System.out.println(db.addDocument(document1, author4)+"success!");
            System.out.println(db.addDocument(document2,author4));
         System.out.println("--------------");
            List<Documents> docs;
            docs =  db.findAuthorDocument(new Authors(4, "Arnold Grey", null));
            System.out.println("list of documents by: \"Arnold Grey\":");
            if (docs!=null){
                for (Documents d: docs){
                    System.out.println(d.toString());
                }
            }
            else System.out.println("list is empty");
            System.out.println("--------------");
            docs =  db.findAuthorDocument(new Authors(4, null, null));
            System.out.println("list of documents by Author id 4:");
            if (docs!=null){
                for (Documents d: docs){
                    System.out.println(d.toString());
                }
            }
            else System.out.println("list if empty");

            System.out.println("--------------");
            docs =  db.findAuthorDocument(new Authors(0, "Arnold Grey", null));
            System.out.println("list of documents by authorship: \"Arnold Grey\":");
            if (docs!=null){
                System.out.println("--------------");
                for (Documents d: docs){
                    System.out.println(d.toString());
                }
            }
            else System.out.println("list is empty");



            System.out.println("--------------");
            System.out.println("list documents which containts: \"First\":");
            docs = db.findDocumentByContent("First");
            if (docs!=null){
                System.out.println("--------------");
                for (Documents d: docs){
                    System.out.println(d.toString());
                }
            }
            else System.out.println("list is empty");

            System.out.println("--------------");
            docs = db.findDocumentByContent("gjfjgdjgfj34");
            System.out.println("list of documents which contains: \"gjfjgdjgfj34\"\":");
            if (docs!=null){
                System.out.println("--------------");
                for (Documents d: docs){
                    System.out.println(d.toString());
                }
            }
            else System.out.println("list is empty");

            System.out.println("--------------");



//            System.out.println("Author and his documents has been successfully deleted: " + db.deleteAuthor(new Authors(6, null, null)));



        } catch (DocumentException ex) {
            System.out.println(ex.getMessage()+" something goes wrong");
        }

    }

}