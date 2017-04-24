package Model;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import sun.misc.BASE64Encoder;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

/**
 * Author: Vone Cloots
 **/

@SuppressWarnings("unchecked")


//GET ALL FORM ID'S.
public class FormRetriever {


    private HashMap<String, HashMap<String, String>> allFormsData = new HashMap<>();
    private HashMap<String, String> formNames = new HashMap<>();

    private ArrayList<String> formId = new ArrayList<>();

    private String auth;


    public void getForms(String auth) throws Exception {

        //URL TO GET ALL FORM DATA.
        String url = "https://secure.p06.eloqua.com/api/REST/2.0/assets/forms";

        //URL CREATION
        URL obj = new URL(url);

        //OPEN CONNECTION
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        //GET REQUEST
        con.setRequestMethod("GET");

        //ELOQUA Authentication for company: ENECO

        //AUTHORIZATION FOR FIRST CALL
        BASE64Encoder enc = new BASE64Encoder();
         //auth = "ENECO\\Vone.Cloots:Mmis19925577.";
        String encodedAuthorization = enc.encode(auth.getBytes());
        con.setRequestProperty("Authorization", "Basic " +
                encodedAuthorization);

        //RESPONSE CODE  200-> Success
        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);


        //GET INPUT STREAM FROM GET REQUEST
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //GET FORM FIELD NAME, GIVEN A SPECIFIC ID.
        JSONObject formIdJson = new JSONObject(response.toString());
        JSONArray recs = formIdJson.getJSONArray("elements");
        JSONArray fieldNameId;

        for (int i = 0; i < 5; ++i) {
            JSONObject rec = recs.getJSONObject(i);

            String id = rec.getString("id");
            String formName = (String) rec.get("name");
            formId.add(id);
            formNames.put(id,formName);

            //URL TO GET FORM FR GIVEN A SPECIFIC ID.
            String url2 = "https://secure.p06.eloqua.com/api/REST/2.0/assets/form/" + id;

            URL obj2 = new URL(url2);

            HttpURLConnection con2 = (HttpURLConnection) obj2.openConnection();

            //GET REQUEST
            con2.setRequestMethod("GET");

            //AUTHORIZATION FOR SECOND CALL
            BASE64Encoder enc2 = new BASE64Encoder();
            //String authstring = "ENECO\\Vone.Cloots:Mmis19925577.";
            String encodedAuthorization2 = enc2.encode(auth.getBytes());
            con2.setRequestProperty("Authorization", "Basic " +
                    encodedAuthorization2);

            //GET INPUT STREAM FROM GET REQUEST
            BufferedReader in2 = new BufferedReader(
                    new InputStreamReader(con2.getInputStream()));
            String inputLine2;
            StringBuilder response2 = new StringBuilder();
            while ((inputLine2 = in2.readLine()) != null) {
                response2.append(inputLine2);
            }
            in2.close();

            //CREATE A JSON OBJECT AND GET ARRAY WITH ALL ELEMENTS.
            JSONObject fieldNameJson = new JSONObject(response2.toString());
            fieldNameId = fieldNameJson.getJSONArray("elements");


            HashMap<String, String> formData = new HashMap<>();

            for (int j = 0; j < fieldNameId.length(); j++) {

                //GET ALL HEADER NAMES PER FORM

                JSONObject jsonFields = JSONObject.fromBean(fieldNameId.get(j));

                String fieldName = (String) jsonFields.get("name");
                String fieldId = (String) jsonFields.get("id");
                formData.put(fieldId, fieldName);

            }

            allFormsData.put(id, formData);

        }


    }

    public String setAuth(String auth){ this.auth = auth; return auth; }

    public String getAuth(){ return this.auth; }

    public HashMap<String, HashMap<String, String>> getAllFormsData() {
        return allFormsData;
    }

    public ArrayList<String> getFormId() {
        return formId;
    }

    public HashMap<String, String> getFormNames() {
        return formNames;
    }

}













