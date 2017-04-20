/**
 * Created by clootvo on 20/04/2017.
 */
public class TestCSVCreator2 {




    public static void main(String[] args) throws Exception {

        String path = args[0];
        String auth = args[1];

        String newPath  = path.replace("\\", "\\\\");
        String formattedPath = newPath + "\\\\";

       // String formattedAuth =  auth.replace("\\", "\\\\");

        CSVCreator csv = new CSVCreator();
        csv.createCsv(formattedPath,auth);
    }

}


