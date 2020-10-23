package orders;

import drinks.Drink;

public class Order {
	private Drink drink;
	private double moneyInserted = 0;
	private double moneyDifference;
	private double paybackMoney = 0;
	
	public Order() {
		
	}
	
	public Order(Drink drink){
		this.drink = drink;
		this.moneyDifference = this.moneyInserted - drink.getPrice();
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
	
	public void addMoney(double moneyAdded) {
		this.moneyInserted += moneyAdded;
		this.moneyDifference = this.moneyInserted - drink.getPrice();
	}
	
	public boolean isEnoughMoney() {
		this.moneyDifference = this.moneyInserted - drink.getPrice();
		if(this.moneyDifference>0.00000001) {
			this.paybackMoney = this.moneyDifference;
			return true;
		}
		else if(this.moneyDifference<0.00000001) {
			return false;
		}
		else {
			return true;
		}
	}
	
	
}
