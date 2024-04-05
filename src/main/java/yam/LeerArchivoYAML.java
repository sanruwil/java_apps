package yam;

import org.yaml.snakeyaml.Yaml;
import java.io.*;
import java.util.Map;
import java.util.Properties;

public class LeerArchivoYAML {

    public static void main(String[] args) throws IOException {
        // Nombre del archivo YAML (ubicado en el mismo directorio que la clase)
        String rutaActual = System.getProperty("user.dir");

        String nombreArchivoYAML = rutaActual+"/ejemplo.yaml";
        String miruta=leerProperties();

        // Determina la ruta del archivo YAML a partir de la clase
        //String rutaArchivoYAML = determinarRutaArchivo(nombreArchivoYAML);

        // Lee el archivo YAML y obtén el contenido como un mapa
        Map<String, Object> datos = cargarYAML(nombreArchivoYAML);

        // Imprime el contenido del mapa
        if (datos != null && datos.containsKey("edad")) {
            Object valorEdad = datos.get("edad");
            System.out.println("La edad es: " + valorEdad);
        } else {
            System.out.println("No se encontró la clave 'edad' en el archivo YAML.");
        }
        System.out.println("Contenido del archivo YAML:");
        for (Map.Entry<String, Object> entry : datos.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    private static String leerProperties() throws IOException {
        Properties prop = new Properties();
        InputStream input = null;
        input = new FileInputStream("./secret_file.properties");
        prop.load(input);
        String ruta = prop.getProperty("connectionString");
        return ruta;

    }

    private static Map<String, Object> cargarYAML(String rutaArchivo) {
        try (InputStream inputStream = new FileInputStream(rutaArchivo)) {
            // Utiliza SnakeYAML para cargar el contenido del archivo YAML en un mapa
            Yaml yaml = new Yaml();
            return yaml.load(inputStream);
        } catch (Exception e) {
            // Manejo de excepciones en caso de problemas al leer el archivo
            e.printStackTrace();
            return null;
        }
    }
}

