package drinks;

public enum DrinkSize {
	Short(10),
	Normal(25),
	Long(50);

	private int size;

	DrinkSize(int size){this.size = size;}

	public int getSize() {
		return size;
	}

	public int getSize(int sliderValue){
		switch(sliderValue){
			case 0: return 10;
			case 1: return 25;
			case 2: return 50;
			default: return 0;
		}
	}
}
