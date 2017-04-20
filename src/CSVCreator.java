

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by clootvo on 12/04/2017.
 */
public class CSVCreator {

    private HashMap<String, HashMap<String, String>> formMap;
    private HashMap<String,HashMap<String,String>> dataMap;
    private HashMap<String, String> formNames = new HashMap<>();

    private ArrayList<String> formIds;
    private ArrayList<String> fieldIds;

    private FormRetriever fRetriever;
    private DataRetriever dRetriever;

    private void initialize() {

        this.formMap = new HashMap<>();
        this.formIds = new ArrayList<>();
        this.fRetriever = new FormRetriever();
        this.dRetriever = new DataRetriever();
    }

    public void createCsv(String path, String auth) throws Exception {

        initialize();

        fRetriever.setAuth(auth);

        String dataAuth = fRetriever.getAuth();
        fRetriever.getForms(fRetriever.getAuth());

        formMap = fRetriever.getAllFormsData();
        dataMap = dRetriever.getMap();
        formNames = fRetriever.getFormNames();

        formIds = fRetriever.getFormId();
        fieldIds = fRetriever.getFieldIds();

        for (String ids : formIds) {

            String formName = formNames.get(ids);
            File file = new File(path + formName + ".csv");
            Writer writer = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_16LE);
            StringBuilder sb = new StringBuilder();
            ArrayList<String> formFieldIds = new ArrayList<>();

            for (String fieldid : formMap.get(ids).keySet()) {


                    sb.append(formMap.get(ids).get(fieldid));
                    sb.append(',');
                    formFieldIds.add(fieldid);
            }

            sb.append('\n');

            dRetriever.getData(Integer.parseInt(ids),dataAuth);
            dataMap = dRetriever.getMap();

            for(String key : dataMap.keySet()){

              HashMap<String,String>  fieldMap =  dataMap.get(key);
              String[] fields  = new String [formFieldIds.size()];

              for(String keyTwo : fieldMap.keySet()) {

                  if (formFieldIds.indexOf(keyTwo) != -1) {
                      int index = formFieldIds.indexOf(keyTwo);

                      fields[index] = fieldMap.get(keyTwo);
                  }

              }

              for(String field : fields){

                  if (field == null){
                      sb.append("");
                  }

                  else{sb.append(field);}
                  sb.append(",");
              }

              sb.append('\n');

            }

            writer.write(sb.toString());
            writer.close();
        }

        }
}