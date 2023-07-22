// mongo Shell- Consulta de agreagcion
db.orders.aggregate([ { $group: { _id: "$cust_id", total: { $sum: "$price" } } }, { $match: { total: { $gt: 50 } } }] )

//java
AggregateIterable<Document> result = collection.aggregate(
                                     Arrays.asList(
                                     new Document("$group", new Document("_id", "$cust_id").append("total", new Document("$sum", "$price"))),
                                     new Document("$match", new Document("total", new Document("$gt", 50)))
                                    )
                                   );

      // Procesar los resultados de la consulta
      for (Document document : result) {
          System.out.println(document);
      }

// mongo Shell Consulta de agregacion con having 
db.orders.aggregate([ { $group: { _id: {"mt":"$mt","receiver":"$receiver","sender":"$sender"}, total: { $sum: "$monto" } } }] )

//java
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

// mongo shell JOIN de tablas
db.customers1.aggregate([ {
     $lookup: { 
               from: "request1", localField: "id_cli",/* field in the request collection RAIZ*/ 
               foreignField: "customer_id", /* field in the items collection INTERNA*/
               as: "ListOrders" } }, 
    { $unwind: "$ListOrders" }, 
    { $replaceRoot: { newRoot: { $mergeObjects: ["$ListOrders", "$$ROOT"] } } }, { $project: { ListOrders: 0 } }] ).pretty()

 
// java 
AggregateIterable<Document> result = collectionCustomer.aggregate(
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
            for (Document document : result) {
                System.out.println(document);
            }

 


