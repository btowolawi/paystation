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
    
    private int coins = 0;
    

    @Override
    public void addPayment(int coinValue)
            throws IllegalCoinException {
        switch (coinValue) {
            case 5:
            case 10:
            case 25: 
                coins++;
                coinMap.put("coins", coins);        
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

    @Override
    public Receipt buy() {
        Receipt r = new ReceiptImpl(timeBought);
        reset();
        return r;
    }

    @Override
    public HashMap cancel() {
        reset();
        return coinMap;
    }
    
    private void reset() {
        timeBought = insertedSoFar = 0;
    }
    
    @Override
    public int empty(){
        //reset();
        return insertedSoFar;
    }
    
    public void emptyReset(){
        reset();
    }
}
