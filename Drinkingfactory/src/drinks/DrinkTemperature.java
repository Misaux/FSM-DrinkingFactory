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

	public int getTemperature(int sliderValue){
		switch (sliderValue) {
			case 0: return 20;
			case 1: return 35;
			case 2: return 60;
			case 3: return 85;
			default: return 0;
		}
		/*
		return switch (sliderValue) {
			case 0 -> 20;
			case 1 -> 35;
			case 2 -> 60;
			case 3 -> 85;
			default -> 0;
		};
		 */
	}
	
}

