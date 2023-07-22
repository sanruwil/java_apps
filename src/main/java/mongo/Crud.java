package mongo;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;

import com.mongodb.client.result.InsertManyResult;
import com.mongodb.client.result.InsertOneResult;
import org.bson.BsonValue;
import org.bson.Document;
import org.bson.conversions.Bson;


import java.util.Arrays;
import java.util.List;

import static com.mongodb.client.model.Accumulators.avg;
import static com.mongodb.client.model.Accumulators.sum;
import static com.mongodb.client.model.Aggregates.group;

public class Crud {
    private final MongoCollection<Document> collection;
    private final MongoCollection<Document>  collectionCustomer;
    public Crud(MongoClient client) {

        this.collection = client.getDatabase("demoswift").getCollection("mt");
        this.collectionCustomer = client.getDatabase("demoswift").getCollection("customers1");
    }

    public void findOneDocument(Bson query) {
        //TODO: implement the findOne query
        System.out.println("Recovery One Docuemnt MT");
        Document doc = collection.find(query).first();
        System.out.println(doc != null ? doc.toJson() : null);

    }
    public void findManyDocument(Bson query) {
        //TODO: implement the findOne query
        System.out.println("Recovery Many Docuemnt MT");
        try(MongoCursor<Document> cursor = collection.find(query).iterator())
         { while(cursor.hasNext()) {
              System.out.println(cursor.next().toJson());
            }
        }
    }
    public void insertManyDocuments(List<Document> documents) {
        //TODO Add code to insert many documents
        InsertManyResult result = collection.insertMany(documents);
        System.out.println("\tTotal # of documents: " + result.getInsertedIds().size());

    }
    public void insertOneDocument(Document doc) {
        //TODO implement insertOne code here
        System.out.println("Inserting one account document");
        InsertOneResult result = collection.insertOne(doc);
        BsonValue id = result.getInsertedId();
        System.out.println("Inserted document Id: " + id);

    }
    public void findDocuments(Bson query) {
        //TODO: implement the find query
         try (MongoCursor<Document> cursor = collection.find(query).iterator()) {
           while (cursor.hasNext()) {
          System.out.println(cursor.next().toJson());
    }
   }
    }
    // Execute query natives
    public void executeQuery(String nativequery){
        Document queryDocument = Document.parse(nativequery);
        // Ejecutar la consulta y obtener un cursor de resultados
        try (MongoCursor<Document> cursor = collection.find(queryDocument).iterator()) {
            while (cursor.hasNext()) {
                Document document = cursor.next();
                System.out.println(document.toJson());
            }
    } catch (Exception e) {
        e.printStackTrace();
    }
   }

   public void AggegateQuery(){
       System.out.println("Execute Aggreagte  Query");
       Document groupFields = new Document("_id", "$mt");
       groupFields.put("total", new Document("$sum", "$monto"));
       Document group = new Document("$group", groupFields);
       AggregateIterable<Document> result = collection.aggregate(Arrays.asList(group));
       for (Document document : result) {
           System.out.println(document.toJson());
       }
       // db.mt.aggregate( [
       // {$group:
       // {_id:{mt:"$mt",receiver:"$receiver",sender:"$sender"},
       // total: { $sum: "$monto" }}}] )
       System.out.println("Execute Aggreagte  Query- Opcion 1");
       AggregateIterable<Document> result2 = collection.aggregate(
               Arrays.asList(
                       new Document("$group",
                               new Document("_id", new Document("mt", "$mt").append("receiver", "$receiver").append("sender", "$sender")).
                                       append("total", new Document("$sum", "$monto")))
               )
       );
       for (Document document : result2) {
           System.out.println(document);
       }



       System.out.println("Execute Aggreagte  Query- Opcion 2");
       // Definir la consulta de agregación
       Document groupFields2 = new Document("_id", new Document("mt", "$mt").append("receiver", "$receiver").append("sender", "$sender"));
       groupFields2.put("total", new Document("$sum", "$monto"));
       Document groupStage = new Document("$group", groupFields2);
       AggregateIterable<Document> aggregationResult = collection.aggregate(Arrays.asList(groupStage));

       // Ejecutar la consulta de agregación y procesar los resultados
       for (Document document : aggregationResult) {
           System.out.println(document.toJson());
       }

       System.out.println("Execute Aggreagte  Query- Opcion 3");
       collection.aggregate(
               List.of(
                       group(new Document("mt", "$mt")
                                       .append("receiver", "$receiver"),
                                        sum("total", "$monto")
                       )
               )
       ).forEach(document -> System.out.println(document.toJson()));

      // ejemplo de join
       //db.customers1.aggregate([
       // { $lookup: { from: "request1",
       // localField: "id_cli", /* field in the request collection RAIZ*/
       // foreignField: "customer_id", /* field in the items collection INTERNA*/
       // as: "ListOrders" } },
       // { $unwind: "$ListOrders" },
       // { $replaceRoot: { newRoot: { $mergeObjects: ["$ListOrders", "$$ROOT"] } } },
       // { $project: { ListOrders: 0 } }] ).pretty()
       System.out.println("Execute JOIN TABLE");
       AggregateIterable<Document> result3 = collectionCustomer.aggregate(
               Arrays.asList(
                       new Document("$lookup",
                               new Document("from", "request1")
                                       .append("localField", "id_cli")
                                       .append("foreignField", "customer_id")
                                       .append("as", "ListOrders")),
                       new Document("$unwind", "$ListOrders"),
                       new Document("$replaceRoot",
                               new Document("newRoot",
                                       new Document("$mergeObjects",
                                               Arrays.asList("$ListOrders", "$$ROOT")))),
                       new Document("$project", new Document("ListOrders", 0))
               )
       );

       // Procesar los resultados
       for (Document document : result3) {
           System.out.println(document);
       }


     ///
    }
}



