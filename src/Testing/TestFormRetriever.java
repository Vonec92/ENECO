package Testing;

import Model.FormRetriever;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by clootvo.
 */
public class TestFormRetriever {

    public static void main(String[] args) throws Exception {

        FormRetriever ret = new FormRetriever();
        HashMap<String, HashMap<String, String>> map;
        ArrayList<String> formIds;


        ret.getForms("ENECO\\Vone.Cloots:Mmis19925577.");

        map = ret.getAllFormsData();
        formIds = ret.getFormId();

        System.out.println(map + " " + formIds);



        }
    }







