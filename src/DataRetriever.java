

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import sun.misc.BASE64Encoder;

import javax.security.auth.Subject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by clootvo on 17/04/2017.
 */
public class DataRetriever {
    /**
     * Author: Vone Cloots
     **/

    @SuppressWarnings("unchecked")
    //GET ALL FORM ID'S.


        private HashMap<String,HashMap<String,String>> map = new HashMap<>();
        private ArrayList<String> fieldIds = new ArrayList<>();


    private final int FIELD_VALUE_LENGTH = 3;

        public void getData(int id, String auth) throws Exception {

            FormRetriever fRetriever  = new FormRetriever();
            //URL TO GET FORM FR GIVEN A SPECIFIC ID.

            String url = "https://secure.p06.eloqua.com/api/REST/2.0/data/form/" + id;
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            //GET REQUEST
            con.setRequestMethod("GET");

            //AUTHORIZATION FOR SECOND CALL
            BASE64Encoder enc = new BASE64Encoder();
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
                response.append(inputLine);
            }

            in.close();

            JSONObject FormDataJson = new JSONObject(response.toString());
            JSONArray elementsArray = FormDataJson.getJSONArray("elements");



            for (int j = 0; j < elementsArray.length(); j++) {

                JSONObject dataFields = JSONObject.fromBean(elementsArray.get(j));
                String submitterID = dataFields.getString("id");
                JSONArray data = dataFields.getJSONArray("fieldValues");

                HashMap<String, String> formData = new HashMap<>();

                for (int v = 0; v < data.length(); v++) {

                    JSONObject values = JSONObject.fromBean(data.get(v));

                    if (values.length() == FIELD_VALUE_LENGTH) {
                        String dataId = (String) values.get("id");
                        String value = (String) values.get("value");
                        formData.put(dataId, value);
                        fieldIds.add(dataId);
                    }
                    map.put(submitterID, formData);


                    if(map.get(submitterID).isEmpty()){
                        map.remove(submitterID);
                    }
                }
            }



        }

    public HashMap<String, HashMap<String, String>> getMap() {
                return map;
            }


}





