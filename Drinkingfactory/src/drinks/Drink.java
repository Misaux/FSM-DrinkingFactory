package drinks;

public enum Drink {
	Tea("Tea",0.40f),
	Espresso("Espresso",0.50f),
	Coffee("Coffee",0.35f),
	Soup("Soup",0.5f);
	
	private String name;
	private float price;
	private DrinkSize size;
	private DrinkTemperature temperature;
	
	Drink(String name, float price){
		this.name = name;
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public DrinkSize getSize() {
		return size;
	}

	public void setSize(DrinkSize size) {
		this.size = size;
	}

	public void setSize(int size){
		switch(size){
			case 0:
				setSize(DrinkSize.Short);
			case 1:
				setSize(DrinkSize.Normal);
			case 2:
				setSize(DrinkSize.Long);
		}
	}

	public void setSugar(int sugarLevel){

	}
}
