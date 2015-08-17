package intracode.org.humancounter;

import java.util.ArrayList;

/**
 * Created by William on 2015-07-15.
 */
public class Clicker {
    private ArrayList<ClickerObserver> observers = new ArrayList<ClickerObserver>();
    private boolean positive;

    public void clickerClicked(boolean positive) {
        this.positive = positive;
        updateObserver();
    }

    public void registerObserver(ClickerObserver obs) {
        observers.add(obs);
    }

    private void updateObserver() {
        for(ClickerObserver observer : observers) {
            observer.stateChanged();
        }
    }

    public boolean getPositive(){
        return positive;
    }
}
