package de.opendatalab.regionalstatistik;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class AgsData {

	@JsonIgnore
	private String ks;

	public AgsData(String ks) {
		this.ks = ks;
	}

	public String getKs() {
		return ks;
	}
}
