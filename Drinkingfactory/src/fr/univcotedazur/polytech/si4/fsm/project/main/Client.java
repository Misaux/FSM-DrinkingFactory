package fr.univcotedazur.polytech.si4.fsm.project.main;

import drinks.Drink;

public class Client {
    private String cardHash;
    private int commandedDrinks;
    private float totalCash;

    Client (String cardHash){
        this.cardHash = cardHash;
    }

    public float getTotalCash() {
        return totalCash;
    }

    public int getCommandedDrinks() {
        return commandedDrinks;
    }

    public String getCardHash() {
        return cardHash;
    }

    public float getPromo(Drink drink){
        if(commandedDrinks < 10) {
            this.commandedDrinks += 1;
            this.totalCash += drink.getPrice();
            return 0;
        }
        else {
            float promo = Math.round(totalCash * 10f)/100f;
            this.commandedDrinks = 0;
            this.totalCash = 0;
            return promo;
        }
    }
}
