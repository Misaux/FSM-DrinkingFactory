package drinks;

public enum Drink {
	Tea("Tea",0.40), 
	Espresso("Espresso",0.50),
	Coffee("Coffee",0.35),
	Soup("Soup",0.5);
	
	private String name;
	private int sugarLevel;
	private double price;
	private DrinkSize size;
	private DrinkTemperature temperature;
	
	Drink(String name, double price){
		this.name = name;
		if(name.equals("Soup")) {
			this.sugarLevel = 0;
		}
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSugarLevel() {
		return sugarLevel;
	}

	public void setSugarLevel(int sugarLevel) {
		this.sugarLevel = sugarLevel;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public DrinkSize getSize() {
		return size;
	}

	public void setSize(DrinkSize size) {
		this.size = size;
	}
	
	
	
	
}
