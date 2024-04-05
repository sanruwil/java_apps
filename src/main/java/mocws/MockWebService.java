package mocws;
import static spark.Spark.*;

public class MockWebService {
  public static void main(String[] args) {
    port(8181); // Establece el puerto en el que se ejecutará el servidor
    post("/pac008", (req, res) ->
            {
              String requestBody = req.body();
              if (requestBody != null && !requestBody.isEmpty()) {
                // Puedes realizar acciones con el JSON recibido aquí
                return "[{\"id\":\"pa08ok-tes43A\",\"estatus\":\"EXITO\",\"comentario\":\"Succesfull messages\"}]";
              } else {
                return "Error solitud post";
              }
            }
    );
    // Servicio que retorna un mensaje en formato JSON
    get("/pac008", (req, res) -> {
      res.type("application/json");
      //return "{\"mensaje\": \"Este es un mensaje de prueba en formato JSON\"}";
      return "[\n" +
              "    {\n" +
              "        \"bicContraparte\": \"CHASUS33XXX\",\n" +
              "        \"dnContraparte\": \"ou=XXX,o=CHASUS33,o=swift\",\n" +
              "        \"grpHdr\": {\n" +
              "            \"msgId\": \"00941530237050100\",\n" +
              "            \"creDtTm\": 1665118800000,\n" +
              "            \"nbOfTxs\": \"1\",\n" +
              "            \"sttlmInf\": {\n" +
              "                \"sttlmMtd\": \"INDA\"\n" +
              "        }\n" +
              "        },\n" +
              "        \"cdtTrfTxInf\": [\n" +
              "            {\n" +
              "                \"intrmyAgt1\": {\n" +
              "                    \"finInstnId\": {\n" +
              "                        \"bicfi\": \"BKTRUS33XXX\"\n" +
              "                    }\n" +
              "                },\n" +
              "                \"cdtrAgt\": {\n" +
              "                    \"finInstnId\": {\n" +
              "                        \"nm\": \"CITIZENS BUSINESS BANK  California\",\n" +
              "                        \"pstlAdr\": {\n" +
              "                            \"adrLine\": [\n" +
              "                                \"CITIZENS BUSINESS BANK  California\"\n" +
              "                            ]\n" +
              "                        }\n" +
              "                    }\n" +
              "                },\n" +
              "                \"cdtrAgtAcct\": {\n" +
              "                    \"id\": {\n" +
              "                        \"othr\": {\n" +
              "                            \"id\": \"654321\"\n" +
              "                        }\n" +
              "                    }\n" +
              "                },\n" +
              "                \"dbtrAcct\": {\n" +
              "                    \"id\": {\n" +
              "                        \"othr\": {\n" +
              "                            \"id\": \"00146271\"\n" +
              "                        }\n" +
              "                    }\n" +
              "                },\n" +
              "                \"cdtrAcct\": {\n" +
              "                    \"id\": {\n" +
              "                        \"othr\": {\n" +
              "                            \"id\": \"0901001735\"\n" +
              "                        }\n" +
              "                    }\n" +
              "                },\n" +
              "                \"pmtId\": {\n" +
              "                    \"instrId\": \"41530237050100\",\n" +
              "                    \"endToEndId\": \"/RFB/PRUEBA CAPTURA 0710 4\",\n" +
              "                    \"uetr\": \"f3e424ee-daf9-41d4-a695-a0bd22725e47\"\n" +
              "                },\n" +
              "                \"intrBkSttlmAmt\": {\n" +
              "                    \"value\": 25.65,\n" +
              "                    \"ccy\": 2\n" +
              "                },\n" +
              "                \"intrBkSttlmDt\": 1665118800000,\n" +
              "                \"sttlmPrty\": \"NORM\",\n" +
              "                \"chrgBr\": \"DEBT\",\n" +
              "                \"instgAgt\": {\n" +
              "                    \"finInstnId\": {\n" +
              "                        \"bicfi\": \"RGIOMXMTXXX\"\n" +
              "                    }\n" +
              "                },\n" +
              "                \"instdAgt\": {\n" +
              "                    \"finInstnId\": {\n" +
              "                        \"bicfi\": \"CHASUS33XXX\"\n" +
              "                    }\n" +
              "                },\n" +
              "                \"dbtr\": {\n" +
              "                    \"nm\": \"AA, XXXXXX No. 611 Col. XXXXXX,  C.P. 123354, GRAL. ESCOBEDO, NUEVO LE\",\n" +
              "                    \"pstlAdr\": {\n" +
              "                        \"adrLine\": [\n" +
              "                            \"AA, XXXXXX No. 611 Col. XXXXXX,  C.\",\n" +
              "                            \"P. 123354, GRAL. ESCOBEDO, NUEVO LE\",\n" +
              "                            \"ON, MEXICO\"\n" +
              "                        ]\n" +
              "                    }\n" +
              "                },\n" +
              "                \"dbtrAgt\": {\n" +
              "                    \"finInstnId\": {\n" +
              "                        \"bicfi\": \"RGIOMXMTXXX\"\n" +
              "                    }\n" +
              "                },\n" +
              "                \"cdtr\": {\n" +
              "                    \"nm\": \"DIVERSIFIED MINERALS INC, ESTADOS UNIDOS,1100 Mount,CALIFORNIA\",\n" +
              "                    \"pstlAdr\": {\n" +
              "                        \"adrLine\": [\n" +
              "                            \"DIVERSIFIED MINERALS INC, ESTADOS U\",\n" +
              "                            \"NIDOS,1100 Mount,CALIFORNIA\"\n" +
              "                        ]\n" +
              "                    }\n" +
              "                },\n" +
              "                \"rltdRmtInf\": [\n" +
              "                    {\n" +
              "                        \"rmtId\": \"/ACC/INVOICE 1\"\n" +
              "                    }\n" +
              "                ],\n" +
              "                \"rmtInf\": {\n" +
              "                    \"ustrd\": [\n" +
              "                        \"/RFB/PRUEBA CAPTURA 0710 4\"\n" +
              "                    ]\n" +
              "                }\n" +
              "            }\n" +
              "        ]\n" +
              "    }]";
    });
    // Servicio que recibe un JSON como callback
    post("/callback", (req, res) -> {
      String requestBody = req.body();
      if (requestBody != null && !requestBody.isEmpty()) {
        // Puedes realizar acciones con el JSON recibido aquí
        return "[{\"id\":\"pa08ok-tes43A\",\"estatus\":\"EXITO\",\"comentario\":\"Succesfull messages\"}]";
      } else {
        return "Error en el callback";
      }
    });
  }
}