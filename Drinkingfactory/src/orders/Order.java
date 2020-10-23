package orders;

import drinks.Drink;

public class Order {
	private Drink drink;
	private double moneyInserted;
	private double moneyDifference;
	
	public Order() {
		
	}
	public Order(Drink drink){
		this.drink = drink;
	}
	public Drink getDrink() {
		return drink;
	}
	public void setDrink(Drink drink) {
		this.drink = drink;
	}
	public double getMoneyInserted() {
		return moneyInserted;
	}
	public void setMoneyInserted(double moneyInserted) {
		this.moneyInserted = moneyInserted;
	}
	public double getMoneyDifference() {
		return moneyDifference;
	}
	public void setMoneyDifference(double moneyDifference) {
		this.moneyDifference = moneyDifference;
	}
	
	
}
