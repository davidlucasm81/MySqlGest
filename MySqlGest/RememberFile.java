package MySqlGest;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.FileWriter;

public class RememberFile {
    File remember;

    public RememberFile(String user, String pass, String database, String address) {
        try {
            remember = new File("src/internalFiles/remember.txt");
            BufferedWriter writer = new BufferedWriter(new FileWriter("src/internalFiles/remember.txt"));
            writer.write(user + "\n" + pass + "\n" + database + "\n" + address + "\n");
            writer.close();

        } catch (IOException e) {
            PrincipalPanel.appendToConsole("Cannot Remember\n");
        }
    }


}
