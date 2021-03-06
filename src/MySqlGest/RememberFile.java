/*
    RememberFile is the class who controls the login data, saving them
 */
package MySqlGest;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class RememberFile {
    /**
     * @param user     The name user
     * @param pass     The password of the user
     * @param database The name of the database
     * @param address  The address (ip:port) from the database
     */
    public RememberFile(String user, String pass, String database, String address) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("remember.txt"));
            writer.write(user + "\n" + pass + "\n" + database + "\n" + address + "\n");
            writer.close();


        } catch (IOException e) {
            PrincipalPanel.appendToConsole("Cannot Remember\n");
        }
    }


}
