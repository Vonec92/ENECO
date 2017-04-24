package Testing;

import Model.DataRetriever;

import java.util.HashMap;
import java.util.zip.DataFormatException;

/**
 * Created by clootvo.
 */
public class TestDataRetriever {

    public static void main(String[] args) throws Exception {

        DataRetriever rt = new DataRetriever();
        HashMap<String,HashMap<String,String>> map = new HashMap<>();


        rt.getData(18,"ENECO\\Vone.Cloots:Mmis19925577.");


        map = rt.getMap();

        System.out.println(map);


    }
}
