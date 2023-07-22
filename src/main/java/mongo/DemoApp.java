package mongo;
import com.mongodb.client.MongoClient;
import org.bson.Document;
import org.bson.conversions.Bson;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
// https://github.com/mongodb-university/atlas_starter_java/blob/master/src/main/java/mongodb/Main.java
public class DemoApp {
    public static void main(final String[] args) {
        Logger.getLogger( "org.mongodb.driver" ).setLevel(Level.WARNING);
       try {
           // Create conection
           System.out.println("Start Process Mongo DB");
           MongoClient mongoClient = MongoDBSingleton.getInstance();
           //ExecuteQuery(mongoClient);
           QueryAgregate(mongoClient);
           //CRUD QUERY-- FIND ONE
           //FindOneRecord(mongoClient);
           //CRUD QUERY-- FIND MANY
           //FindManyRecord(mongoClient);
           //CRUD INSERT - Many record
           //InsertManyRecord(mongoClient);
           //CRUD INSERT - One record
           //InsertOneRecord(mongoClient);
           //Cerra conexion
           MongoDBSingleton.closeConnection();
           System.out.println("End Process Mongo DB");

       }catch (Exception e){
           System.out.println("Error -->"+ e);
       }

    }
    public static void InsertManyRecord(MongoClient client){
        Document mt1 = new Document().append("mt", "103-1").append("sender", "UNIANCOBB").append("receiver", "CLASSCOBB").append("Beneficiary", "Uniandes").append("Ordenante", "Sistemas Transaccionales").append("monto", 120);
        Document mt2 = new Document().append("mt", "103-2").append("sender", "UNIANCOBB").append("receiver", "FLARCOBB").append("Beneficiary", "Uniandes").append("Ordenante", "Flar").append("monto", 100);
        Document mt3 = new Document().append("mt", "103-3").append("sender", "UNIANCOBB").append("receiver", "CASOCOBB").append("Beneficiary", "Uniandes").append("Ordenante", "Fundacion Scial").append("monto", 200);
        List<Document> sampleDocuments = new ArrayList<>();
        sampleDocuments.add(mt1);
        sampleDocuments.add(mt2);
        sampleDocuments.add(mt3);
        //CRUD
        Crud crud = new Crud(client);
        //INSERT MANY
        crud.insertManyDocuments(sampleDocuments);
    }
    public static void InsertOneRecord(MongoClient client){
        Document mt = new Document().append("mt", "202-1").append("sender", "UNIANCOBB").append("receiver", "CASOCOBM").append("Beneficiary", "Uniandes").append("Ordenante", "Colmena").append("monto", 300);;
        //CRUD
        Crud crud = new Crud(client);
        //INSERT ONE
        crud.insertOneDocument(mt);
    }
    public static void FindOneRecord(MongoClient client){
        Crud crud = new Crud(client);
        //FIND ONE
        Bson documentToFind = and(eq("Beneficiary", "Uniandes"), eq("mt", "202"));
        crud.findOneDocument(documentToFind);
    }
    public static void FindManyRecord(MongoClient client){
        Crud crud = new Crud(client);
        //FIND ONE
        Bson documentToFind = (eq("Beneficiary", "Uniandes"));
        crud.findManyDocument(documentToFind);
    }
    public static void ExecuteQuery(MongoClient client){
        // Realizar la consulta nativa
        String nativeQuery = "{Beneficiary:{$in:[\"Uniandes\", \"Rector\"]}}"; // Ejemplo: obtener documentos donde el campo "edad" es mayor o igual a 18
        Crud crud = new Crud(client);
        crud.executeQuery(nativeQuery);


    }
    public static void QueryAgregate(MongoClient client){
        // Realizar la consulta nativa
        Crud crud = new Crud(client);
        crud.AggegateQuery();


    }

}
