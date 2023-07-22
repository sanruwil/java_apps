package mongo;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class MongoDBSingleton {
    static  String connectionString=null ;
    static MongoClient mongoClient = null;
    MongoDBSingleton() {

    }
    public static MongoClient getInstance() {
        connectionString=getConnectionString();
        if (mongoClient == null) {
            synchronized (MongoDBSingleton.class) {
                if (mongoClient == null) {
                    try {
                        mongoClient = MongoClients.create(connectionString);
                    }catch(Exception e){
                        System.out.println("Error -->"+ e);

                    }
                }
            }
        }
        return mongoClient;
    }
    public static String getConnectionString(){
        try {
            Properties prop = new Properties();
            InputStream input = null;
            input = new FileInputStream("./secret_file.properties");
            prop.load(input);
            // connectionString = mongodb+srv://<user>:<pwd>@<urimomgo>
            connectionString = prop.getProperty("connectionString");

        }catch(Exception e){System.out.println("Error -->"+ e);}
        return connectionString;
    }

    // Método para cerrar la conexión con MongoDB
    public static void closeConnection() {
        if (mongoClient != null) {
            try {
            mongoClient.close();
            mongoClient = null;
            System.out.println("Close conection sucesulfull");
            }catch(Exception e){
                System.out.println("Error Close conection -->"+ e);

            }
        }
    }
}
