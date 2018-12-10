package paystation.domain;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class ChosenRate {

    
    static Scanner key = new Scanner(System.in);
    static NumberFormat formatter = NumberFormat.getCurrencyInstance();
    
    char rate = 'a';
    double parkingTime;
    
    
    public void newTransaction(PayStation ps) throws IllegalCoinException{
        if(rate =='a'){
            linear(ps);
        }
        else if(rate == 'b'){
           progressive(ps);
        }
        else if(rate == 'g'){
            Date now = new Date();
            SimpleDateFormat simpleDateformat = new SimpleDateFormat("E"); // the day of the week abbreviated
            String day = simpleDateformat.format(now);
            if (day.equalsIgnoreCase("sat") || day.equalsIgnoreCase("sun")){
                progressive(ps);
            }
            else{
                linear(ps);
            } 
        }     
    }
    
    private void linear(PayStation ps) throws IllegalCoinException{
        String coin;
        int n;
        double dollarAmount;

        do{
            dollarAmount = ((double)ps.getInsertedSoFar()) / 100;
            System.out.print("Total: " + formatter.format(dollarAmount) + "\t");
            n = userInput(ps);
            if(n == 1){
            }
            else{
                break;
            }
            parkingTime = ps.getTimeBought();
        }while(true);
        if(n == -1){
            ps.cancel();
        }
        else{
            ps.buy(ps.getTimeBought());    
        }
    }
    
    private void progressive(PayStation ps) throws IllegalCoinException{
        String coin;
        int n;
        double dollarAmount;
        
        int ISF; //inserted so far
        
        do{
            dollarAmount = ((double)ps.getInsertedSoFar()) / 100;
            System.out.print("Total: " + formatter.format(dollarAmount) + "\t");
            n = userInput(ps);
            ISF = ps.getInsertedSoFar();
            if(ISF < 150){
                parkingTime = ps.getTimeBought();
                if(n == 1){
                }
                else{
                    break;
                }
            }
            else if((150 <= ISF) && (ISF < 350)){
                parkingTime = (ps.getInsertedSoFar() - 150)*(.3)+60;
                if(n == 1){
                }
                else{
                    break;
                }
            }
            else{ //totalAmount >= 350
                parkingTime = (ISF - 350)/5 + 120;
                if(n == 1){
                }
                else{
                    break;
                }
            }
        }while(true);
        if(n == -1){
            ps.cancel();
        }
        else{
            ps.buy((int)parkingTime);    
        }
    }
    
    private int userInput(PayStation ps) throws IllegalCoinException{
        String coin;
        coin = key.nextLine();
        
        if(coin.equals("5")){
            ps.addPayment(5);
            return 1;
        }
        else if(coin.equals("10")){
            ps.addPayment(10);
            return 1;
        }
        else if(coin.equals("25")){
            ps.addPayment(25);
            return 1;
        }
        else if(coin.equalsIgnoreCase("min")){
            System.out.println(parkingTime + " minutes purchased so far.");
            return 1;
        }    
        else if(coin.equalsIgnoreCase("buy")){
            return 0;
        }
        else if(coin.equalsIgnoreCase("cancel")){
            return -1;
        }
        
        else{
            invalidInput();
            return userInput(ps);
        }  
    }
    
    public void useMaintaince(){
        String password;
        System.out.println("What is the maintainance password?");
        for (int i = 0; i < 3; i++) {
            password = key.nextLine();
            if (password.equals("0000")){
                changeRateStrategy();
                break;
            }
            else{
                invalidInput();
            }
        }
        System.out.println("PayStation will return to main menu. Hit enter to continue.");
        password = key.nextLine();
    }
    
    private void changeRateStrategy(){
        
        String s, t;
        System.out.println("Select rate strategy. Type:"
                + "\n\t\'A\' for Alphatown"
                + "\n\t\'B\' for Betatown"
                + "\n\t\'G\' for Gammatown");
        
        
        int i;
        
        while(true){
            s = key.nextLine();
            if(s.equalsIgnoreCase("a")){
                if(rate == 'a'){
                    changeRateStrategyHelper('a', "Alphatown");
                }    
                else{
                    rate = 'a';
                    System.out.println("Alphatown selected.");
                }
                break;
            }
            else if(s.equalsIgnoreCase("b")){
               if(rate == 'b'){
                    changeRateStrategyHelper('b', "Betatown");
                }    
                else{
                    rate = 'b';
                    System.out.println("Betatown selected.");
                }
               break;
            }
            else if(s.equalsIgnoreCase("g")){
               if(rate == 'g'){
                    changeRateStrategyHelper('g', "Gammatown");
                }    
                else{
                    rate = 'g';
                    System.out.println("Gammatown selected.");
                }
                break;
            }
            else{
                invalidInput();
            }
        }
    
    }
    
    private void changeRateStrategyHelper(char abr, String rateStrategy){
        System.out.println("You are already using the " + rateStrategy
                + " rate strategy.");
        System.out.println("Do you wish continue using " + rateStrategy + ", Y/N");
        String t = key.nextLine();
            while(true){
                if(t.equalsIgnoreCase("y")){
                    rate = abr;
                    break;
                }
                if(t.equalsIgnoreCase("n")){
                    changeRateStrategy();
                    break;
                }
                else{
                    invalidInput();
                }
            }
    }
    
    private void invalidInput(){
        System.out.println("Invalid input");
    }

}
