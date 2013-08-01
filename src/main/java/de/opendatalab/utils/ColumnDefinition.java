package de.opendatalab.utils;

public class ColumnDefinition {

	private int key;
	private String label;

	public ColumnDefinition(int key, String label) {
		this.key = key;
		this.label = label;
	}

	public int getKey() {
		return key;
	}

	public String getLabel() {
		return label;
	}
}
