package MySqlGest;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.FileWriter;
import java.util.Scanner;

public class RememberFile {
    File remember;

    public RememberFile(String user, String pass, String database, String address) {
        try {
            remember = new File("remember.txt");
            remember.delete();
            remember.createNewFile();
            BufferedWriter writer = new BufferedWriter(new FileWriter("remember.txt"));
            writer.write(user + "\n" + pass + "\n" + database + "\n" + address + "\n");
            writer.close();

        } catch (IOException e) {
            PrincipalPanel.con.append("Cannot Remember\n");
        }
    }


}
