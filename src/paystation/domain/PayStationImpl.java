package paystation.domain;

import java.util.HashMap;

/**
 * Implementation of the pay station.
 *
 * Responsibilities:
 *
 * 1) Accept payment; 
 * 2) Calculate parking time based on payment; 
 * 3) Know earning, parking time bought; 
 * 4) Issue receipts; 
 * 5) Handle buy and cancel events.
 */
public class PayStationImpl implements PayStation {
    
    private int insertedSoFar;
    private int timeBought;
    
    private HashMap coinMap = new HashMap<>();
    private int nickels = 0;
    private int dimes = 0;
    private int quarters = 0;
    
    @Override
    public void addPayment(int coinValue)
            throws IllegalCoinException {
        switch (coinValue) {
            case 5: 
                nickels++;
                coinMap.put("nickels", nickels);
                break;
            case 10: 
                dimes++;
                coinMap.put("dimes", dimes);
                break;
            case 25: 
                quarters++;
                coinMap.put("quarters", quarters);
                break;
            case 0:
                buy(timeBought);
                break;
            default:
                throw new IllegalCoinException("Invalid coin: " + coinValue);
        }
        insertedSoFar += coinValue;
        timeBought = insertedSoFar / 5 * 2;
    }

    @Override
    public int readDisplay() {
        return timeBought;
    }

    /*@Override
    public int readDisplay() {
        return timeBought;
    }*/
    
    @Override
    public Receipt buy(int TB) {
        Receipt r = new ReceiptImpl(TB, insertedSoFar);
        reset();
        clearMap();
        return r;
    }

    @Override
    public void cancel() {
        Receipt r = new ReceiptImpl(-1, empty());
        reset();
        clearMap();
    }
    
    
    private void reset() {
        timeBought = insertedSoFar = 0;
    }
    
    private HashMap clearMap(){
        coinMap.clear();
        return coinMap;
    }
    
    @Override
    public int empty(){
        return insertedSoFar;
    }
    
    @Override
    public void emptyReset(){
        reset();
    }
    
    @Override
    public HashMap getCoinMap() {
        return coinMap;
    }
    
    @Override
    public int getInsertedSoFar(){
        return insertedSoFar;
    }
    @Override
    public int getTimeBought(){
        return timeBought;
    }


  /*  @Override
    public void setTB(int TB){
        timeBought = TB;
    }*/
    
}
