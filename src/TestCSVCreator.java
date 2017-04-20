
import javafx.stage.FileChooser;
import javax.swing.*;
import java.io.File;
import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.util.regex.Matcher;

/**
 * Created by clootvo on 12/04/2017.
 */
public class TestCSVCreator {

    public static void main(String[] args) throws Exception {


        CSVCreator csv = new CSVCreator();
        File path;
        String newPath = " ";
        String formattedPath;
        String auth = " ";


        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new java.io.File("."));
        chooser.setDialogTitle("Locatie voor alle forms.");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);


        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {

            path =  chooser.getSelectedFile();
            newPath  = path.getAbsolutePath().replace("\\", "\\\\");

        } else {

            JOptionPane.showMessageDialog(null,

                    "U hebt niets geselecteerd!",
                    "Geen selectie",JOptionPane.ERROR_MESSAGE);
        }

        formattedPath = newPath + "\\\\";

        csv.createCsv(formattedPath,auth);

        }

    }





