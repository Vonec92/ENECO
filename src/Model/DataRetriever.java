package Model;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import sun.misc.BASE64Encoder;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

/**
 * Author: Vone Cloots
 **/

public class DataRetriever {

    @SuppressWarnings("unchecked")

    //DECLARE GLOBAL HASHMAP TO STORE ALL DATA FROM A FORM.
    private HashMap<String,HashMap<String,String>> map = new HashMap<>();


    public void getData(int id, String auth) throws Exception { // ID AND LOGIN CREDENTIALS ARE NEEDED TO RETRIEVE DATA.

            /*START API CALL**/

            //URL TO GET FORM DATA GIVEN A SPECIFIC ID.
            String url = "https://secure.p06.eloqua.com/api/REST/2.0/data/form/" + id;
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection(); //OPEN CONNECTION

            //GET REQUEST
            con.setRequestMethod("GET");

            //AUTHORIZATION FOR CALL.
            //For HTTP basic authentication, each request must include an Authentication header, with a base-64 encoded value.

            BASE64Encoder enc = new BASE64Encoder(); //
            //String authstring = "ENECO\\Vone.Cloots:Mmis19925577." ;
            String encodedAuthorization = enc.encode(auth.getBytes());
            con.setRequestProperty("Authorization", "Basic " +
                    encodedAuthorization);

            //GET INPUT STREAM FROM GET REQUEST
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {

                //OUTPUT FROM API CALL
                response.append(inputLine);

            }

            in.close();

            /* END API CALL*/

            //CREATE JSON OBJECTS AND GET ARRAY WITH ALL ELEMENTS.
            JSONObject FormDataJson = new JSONObject(response.toString());
            JSONArray elementsArray = FormDataJson.getJSONArray("elements");



            for (int j = 0; j < elementsArray.length(); j++) {

                //LOOP OVER ELEMENT ARRAY AND RETRIEVE SUBMITTER ID AND THEIR FORM FIELDS.
                JSONObject dataFields = JSONObject.fromBean(elementsArray.get(j));
                String submitterID = dataFields.getString("id");
                JSONArray data = dataFields.getJSONArray("fieldValues");

                HashMap<String, String> formData = new HashMap<>();

                for (int v = 0; v < data.length(); v++) {

                    //RETRIEVE FORM FIELD ID + VALUE
                    JSONObject values = JSONObject.fromBean(data.get(v));

                    int fieldValueLength = 3;// MAKE SURE WE RETRIEVE FORMS WITH ACTUAL VALUES
                    if (values.length() == fieldValueLength) {
                        String dataId = (String) values.get("id");
                        String value = (String) values.get("value");
                        formData.put(dataId, value);

                    }
                    map.put(submitterID, formData); //SUBMITTER ID + FORM DATA ARE KEPT IN AN HASH MAP.


                    if(map.get(submitterID).isEmpty()){ //REMOVE EMPTY FIELDS FROM HASH MAP.
                        map.remove(submitterID);
                    }
                }
            }



        }

    public HashMap<String, HashMap<String, String>> getMap() {
                return map;
            }


}





