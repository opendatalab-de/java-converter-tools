package de.opendatalab.zensus;

public enum Category {
	DEUTSCHLAND("00"), BUNDESLAND("10"), REGIERUNGSBEZIRK("20"), KREIS("40"), VERWALTUNGSGEMEINDE("50"), GEMEINDE("60");

	String key;

	private Category(String key) {
		this.key = key;
	}

	public static Category byKey(String key) {
		for (Category category : Category.values()) {
			if (category.key.equals(key))
				return category;
		}
		throw new RuntimeException("Unknown key (" + key + ")");
	}
}
