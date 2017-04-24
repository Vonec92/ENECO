package Controller;

import Model.DataRetriever;
import Model.FormRetriever;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Vone.
 */

public class CSVCreator {

    //GLOBAL VARIABLES
    private HashMap<String, HashMap<String, String>> allFormData;
    private ArrayList<String> formIds;
    private FormRetriever fRetriever;


    private void initialize() {

        //INITIALIZING GLOBAL VARIABLES
        this.allFormData = new HashMap<>();
        this.formIds = new ArrayList<>();
        this.fRetriever = new FormRetriever();

    }

    public void createCsv(String path, String auth) throws Exception {

        initialize();

        //SET & GET AUTHORIZATION FOR API CALLS
        fRetriever.setAuth(auth);
        String dataAuth = fRetriever.getAuth();


        //GET ALL FORMS
        fRetriever.getForms(dataAuth);

        allFormData = fRetriever.getAllFormsData();
        HashMap<String, String> formNames = fRetriever.getFormNames();

        formIds = fRetriever.getFormId();


        for (String ids : formIds) {

            //CREATE A CSV FILE FOR RETRIEVED .
            String formName = formNames.get(ids);
            File file = new File(path + formName + ".csv");
            Writer writer = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_16LE);

            StringBuilder sb = new StringBuilder();
            ArrayList<String> formFieldIds = new ArrayList<>();

            for (String fieldid : allFormData.get(ids).keySet()) {

                    //GET FORM HEADERS AND APPEND TO THE STRING BUILDER.
                    sb.append(allFormData.get(ids).get(fieldid)); // --> GET HEADER
                    sb.append(',');
                    formFieldIds.add(fieldid);
            }

            sb.append('\n');

            DataRetriever dRetriever = new DataRetriever();
            dRetriever.getData(Integer.parseInt(ids),dataAuth); // -> GET FORM DATA

            HashMap<String, HashMap<String, String>> dataMap = dRetriever.getMap();

            for(String key : dataMap.keySet()){

             //MAP HEADER TO RETRIEVED FORM DATA
              HashMap<String,String>  fieldMap =  dataMap.get(key);
              String[] fields  = new String [formFieldIds.size()];


              for(String keyTwo : fieldMap.keySet()) {

                  if (formFieldIds.indexOf(keyTwo) != -1) {
                      int index = formFieldIds.indexOf(keyTwo);

                      fields[index] = fieldMap.get(keyTwo);
                  }

              }

              //APPEND FORM DATA TO STRING BUILDER.
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