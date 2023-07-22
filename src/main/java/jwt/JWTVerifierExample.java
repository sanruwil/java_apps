package jwt;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
public class JWTVerifierExample {
    public static void main(String[] args) {
        try {
            String claveSecreta = "12345678901234567890123456789012"; // la clave tiene que ser de 32 caracteres
            //clave encriptada con el utlitario cipher
            String kecipher = "Ze/HlvLBA7EtsfS0i+kXLQCw6HnmCU4WUxX9IAjxGG9qW1mZybXBFFQKgpkLJLQQi4rOfblDAXYb/BdwYK9GmT2I23wP7XNUOOCUoQ==";
            byte[] mykey = kecipher.getBytes();
            System.out.println("Lenght Key " + mykey.length);
            // Create JWT Token
            long epoch = System.currentTimeMillis();
            String jwt1 = Jwts.builder()
                    .setSubject("WebServices") // define parameter body
                    .setAudience("Banco Base2")
                    .setIssuer("EXCELTIA")
                    .setIssuedAt(new Date(epoch))
                    .signWith(SignatureAlgorithm.HS256, mykey) // Sign el JWT con HS256 y la clave secreta
                    .compact();
            System.out.println("JWT Ceado localmente: " + jwt1);
            // Verifica la firma del JWT
            String jwt2 = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyLCJleHAiOjE2ODY4Nzg5Mjd9.BNvQZH1agbJ-TCMXLwTbkAIFP2vknNxTwHZGoJSpk4k";
            // Claims val1 = Jwts.parser().setSigningKey(mykey).parseClaimsJws(jwt2).getBody();
            Claims val2 = Jwts.parserBuilder().setSigningKey(mykey).build().parseClaimsJws(jwt1).getBody();
            System.out.println("Token validado localmente: " + val2 );
            Claims val3 = Jwts.parserBuilder().setSigningKey(mykey).build().parseClaimsJws(jwt2).getBody();
            System.out.println("Token validado con archivo generado desde  jwt.io : " + val3);
        }catch (JwtException e){
            System.out.println("Token inválido: " + e.getMessage());
        }
        catch (Exception e) {
            System.out.println("Error general: " + e.getMessage());
        }
    }

}
