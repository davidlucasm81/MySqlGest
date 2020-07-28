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
            System.out.println("Which DB do you want to acces (REMBEMBER: DB must be created before in MySQL): ");
            db = sc.next();
            System.out.println("Trying connection...");
        }
        while(!gest.getConnection(user,pass,db));
        System.out.println("Connection succesfull!");
        while(!disc){
            System.out.println("What do you want to do? Press a number :\n1- A query\n2- An update\n3-Disconect");
            switch(sc.nextInt()){
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    do{
                       System.out.println("Disconecting...");
                    }
                    while(!(disc=gest.desconnect()));
                    System.out.println("Disconect succesfull! Bye bye");
                    break;
                default:
                    System.out.println("I do not understand you!! Try again ^^");
            }






        }








    }




}
