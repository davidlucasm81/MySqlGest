package MySqlGest;

import java.util.Scanner;


public class controller {


    public static void main(String[] args) {

        Scanner sc = new Scanner (System.in);
        MySqlGest gest = new MySqlGest();
        boolean disc= false;
        String user;
        String pass;
        String db;
        do{
            System.out.println("Write an user: ");
            user = sc.next();
            System.out.println("Write a pass: ");
            pass = sc.next();
            System.out.println("Which DB do you want to access (REMBEMBER: DB must be created before in MySQL): ");
            db = sc.next();
            System.out.println("Trying connection...");
        }
        while(!gest.getConnection(user,pass,db));
        System.out.println("Connection succesfull!");
        while(!disc){
            System.out.println("What do you want to do? Press a number :\n1- A query\n2- An update\n3-Disconect");
            int cont=0;
            switch(sc.nextInt()){
                case 1:
                    String query;
                    do{
                        System.out.println("Do a query:");
                        query = sc.nextLine();
                        System.out.println("Doing query...");
                        cont++;
                    }
                    while(!gest.doQuery(query) && cont<3);
                    break;
                case 2:
                    String update;
                    do{
                        update = sc.nextLine();
                        System.out.println("Updating...");
                        cont++;
                    }
                    while(!gest.doUpdate(update) && cont<3);
                    System.out.println("Done!");
                    break;
                case 3:
                    do{
                       System.out.println("Disconecting...");
                       cont++;
                    }
                    while(!(disc=gest.desconnect()) && cont<3);
                    System.out.println("Disconect succesfull! Bye bye");
                    break;
                default:
                    System.out.println("I do not understand you!! Try again ^^");
                    break;
            }






        }








    }




}
