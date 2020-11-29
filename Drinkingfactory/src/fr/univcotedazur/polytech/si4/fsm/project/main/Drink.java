package fr.univcotedazur.polytech.si4.fsm.project.main;

public enum Drink {
	Tea("Tea",0.40f),
	Espresso("Espresso",0.50f),
	Coffee("Coffee",0.35f),
	Soup("Soup",0.75f),
	IcedTea("Iced Tea",0.50f);
	
	private String name;
	private float price;

	Drink(String name, float price){
		this.name = name;
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public float getPrice() {
		return price;
	}
}
