/**
 * Implementation of Receipt.
 */
package paystation.domain;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class ReceiptImpl implements Receipt {
    static NumberFormat formatter = NumberFormat.getCurrencyInstance();
    private int value;
    
    
    public ReceiptImpl(int value, int ISF) {
        this.value = value;
        
        if(value == -1){
            CancelReceipt(ISF);
        }
        else{
            PurchaseReceipt(value, ISF);
            
        }
    }
    
    private void PurchaseReceipt(int value, int ISF) {
        
            double dollarAmount = (double) ISF / 100;

            System.out.println("______________________\n");
            System.out.println("RECEIPT");
            GetDateAndTime();
            System.out.println("Amount paid: " + formatter.format(dollarAmount));
            System.out.println("Minutes of parking time: " + value);
            System.out.print("Meter will expire at ");
            GetDateAndTime(value);
            System.out.println("______________________");
    }
    private void CancelReceipt(int insertedSoFar){
        
        double dollarAmount = (double) insertedSoFar / 100;
        
        System.out.println("______________________\n");
        System.out.println("RECEIPT");
        GetDateAndTime();
        System.out.println("Tranaction canceled");
        System.out.println("Amount returned: " + formatter.format(dollarAmount));
        System.out.println("______________________");
    }
    
    private void GetDateAndTime() {
        String year, month, day, hour, min, sec, dow;
        
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	Date date = new Date();
        String dat = dateFormat.format(date); //dat means date and time
        

        Date now = new Date();
        SimpleDateFormat simpleDateformat = new SimpleDateFormat("E"); // the day of the week abbreviated
        dow = simpleDateformat.format(now);

        
        year = dat.substring(0, 4);
        month = dat.substring(5, 7);
        day = dat.substring(8, 10);
        hour = dat.substring(11, 13);
        min = dat.substring(14, 16);

	System.out.println(dow + " " + day + "/" + month + "/" + year + "  " + hour + ":"
                + min);
        }
    
    private void GetDateAndTime(int parkingTime) {
        int year, month, day, hour, min, sec;
        int pt = parkingTime;
        
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	Date date = new Date();
        String dat = dateFormat.format(date); //dat means date and time
        
        year = Integer.parseInt(dat.substring(0, 4));
        month = Integer.parseInt(dat.substring(5, 7));
        day = Integer.parseInt(dat.substring(8, 10));
        hour = Integer.parseInt(dat.substring(11, 13));
        min = Integer.parseInt(dat.substring(14, 16));
        
        int newMin, newHour;
        int newDay = day;
        int newMonth = month;
        int newYear = year;
        
        newMin = pt % 60;
        newHour = (pt - newMin) / 60;
        if ((min + newMin) > 59){
            hour++;
            min = (min + newMin) % 60;
        }
        else{
            min += newMin;
        }
        
        if ((hour + newHour) > 23){
            newDay = day++;
            newHour = (hour + newHour) % 24;
            hour = newHour;
        }
        else{
            hour += newHour;
        }
        
        if ((month == 1) || (month == 3) || (month == 5) || (month == 7) || 
           (month == 8) || (month == 10) || (month == 12)){
            if (newDay > 31){
                newMonth++;
                newDay = 1;
            }
        }
        else if((month == 4) || (month == 6) || (month == 9) || (month == 11)){
            if (newDay > 30){
                newMonth++;
                newDay = 1;
            }
        }
        else if (month == 2){
            if (newDay > 28){
                newMonth++;
                newDay = 1;
            }
        }
        if (newMonth == 13){
            newYear++;
            newMonth = 1;
        }
        
        if(min < 10)
            System.out.println(hour + ":0" + min + " on " + month + "/" + day + "/" + year);
        else
            System.out.println(hour + ":" + min + " on " + month + "/" + day + "/" + year);
    }
    
    
    @Override
    public int value() {
        return value;
    }

}
