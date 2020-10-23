package drinks;

public enum DrinkTemperature {
	Ambient(20), 
	Gentle(35),
	Hot(60),
	VeryHot(85);
	
	private int temperature;
	
	DrinkTemperature(int temperature) {
		this.temperature = temperature;
	}

	public int getTemperature() {
		return temperature;
	}
	
	
}

