package enums;

public enum NeighborEnum {
	NEIGHBOR_X, 
	NEIGHBOR_CROSS, 
	NEIGHBOR_3X3;
	
	public static NeighborEnum getById(int id) {

		if (NeighborEnum.values().length > id) {
			return NeighborEnum.values()[id];
		}
		return null;
	}

	public int getId() {
		return ordinal();
	}

	public String getName() {
		return name();
	}
}
