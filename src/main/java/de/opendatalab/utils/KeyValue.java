package de.opendatalab.utils;

import java.util.HashMap;
import java.util.Map;

public class KeyValue {

	private String key;
	private Map<String, Object> value = new HashMap<>();

	public KeyValue(String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}

	public void setValue(String key, Object value) {
		this.value.put(key, value);
	}

	public Map<String, Object> getValue() {
		return value;
	}
}
