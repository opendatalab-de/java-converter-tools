package de.opendatalab.csv;

public class ParserConfiguration {

	private int skipLines;
	private int areaKey;
	private int dataStart;
	private String[] properties;

	public ParserConfiguration(int skipLines, int areaKey, int dataStart, String[] properties) {
		this.skipLines = skipLines;
		this.areaKey = areaKey;
		this.dataStart = dataStart;
		this.properties = properties;
	}

	public int getAreaKey() {
		return areaKey;
	}

	public int getDataStart() {
		return dataStart;
	}

	public int getSkipLines() {
		return skipLines;
	}

	public String[] getProperties() {
		return properties;
	}
}
