/*
    RememberFile is the class who controls the login data, saving them
 */
package MySqlGest;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.FileWriter;

public class RememberFile {
    /**
     * @param user     The name user
     * @param pass     The password of the user
     * @param database The name of the database
     * @param address  The address (ip:port) from the database
     */
    public RememberFile(String user, String pass, String database, String address) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("src/internalFiles/remember.txt"));
            writer.write(user + "\n" + pass + "\n" + database + "\n" + address + "\n");
            writer.close();

        } catch (IOException e) {
            PrincipalPanel.appendToConsole("Cannot Remember\n");
        }
    }


}
