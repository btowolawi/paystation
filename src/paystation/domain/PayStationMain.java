package paystation.domain;

import java.util.Scanner;

public class PayStationMain {
    static Scanner key = new Scanner(System.in);
   
    static PayStation ps = new PayStationImpl();

    public static void main(String[] args) throws IllegalCoinException {

        ChosenRate run = new ChosenRate();
        
        while(true){
            menu(run);
            run.newTransaction(ps);
            System.out.print("Hit any key to continue");
            key.nextLine();
            System.out.println("\n\n");
        }
    }
 
    private static void menu(ChosenRate run) {
        
        String s;
        System.out.println("**********");
        System.out.println("PAYSTATION");
        System.out.println("At any time, type:"
                + "\n'min' to display number of minutes purchased" //Display
                + "\n'buy' to purchase ticket" //Buy Ticket
                + "\n'cancel' to cancel tranaction\n"); //Cancel    

        while(true){
            System.out.println("Type \'GO\' to start transaction"); //Deposit Coins
            s = key.nextLine();
            if(s.equalsIgnoreCase("go")){//Starts PayStation
                 System.out.println("\nMachine only accepts 5¢, 10¢, and 25¢"
                + "\n2 minutes costs 5¢");
                break;
            }
            //This allows maintainance to change rate strategy
            //Requires password to acess
            else if(s.equalsIgnoreCase("maintainance")){
                run.useMaintaince();
                System.out.println("");
                menu(run);
                break;
            }
            else{
                System.out.println("Invalid input\n");
            }
        }
    }  
}
