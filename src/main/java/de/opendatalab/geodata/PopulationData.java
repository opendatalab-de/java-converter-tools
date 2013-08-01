package de.opendatalab.geodata;

public class PopulationData {

	private Integer male;
	private Integer female;

	public void addMale(int value) {
		if (male == null)
			male = value;
		else
			male += value;
	}

	public void addFemale(int value) {
		if (female == null)
			female = value;
		else
			female += value;
	}

	public Integer getMale() {
		return male;
	}

	public Integer getFemale() {
		return female;
	}

	public boolean hasData() {
		return male != null || female != null;
	}
}
