/**
 * Created by clootvo on 20/04/2017.
 */
public class TestDataRetriever {

    public static void main(String[] args) throws Exception {



        String path = "C:\\Users\\clootvo\\Desktop";
        String auth = "ENECO\\Vone.Cloots:Mmis19925577.";

        String newPath  = path.replace("\\", "\\\\");
        String formattedPath = newPath + "\\\\";

        String formattedAuth =  auth.replace("\\", "\\\\");

        CSVCreator csv = new CSVCreator();
        csv.createCsv(formattedPath,formattedAuth);
    }
}
