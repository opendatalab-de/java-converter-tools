package de.opendatalab.regionalstatistik;

public class Realsteuer extends AgsData {

	private long grundsteuerAIstaufkommen;
	private long grundsteuerBIstaufkommen;
	private long gewerbesteuerIstaufkommen;
	private long grundsteuerAGrundbetrag;
	private long grundsteuerBGrundbetrag;
	private long gewerbesteuerGrundbetrag;
	private long grundsteuerAHebesatz;
	private long grundsteuerBHebesatz;
	private long gewerbesteuerHebesatz;
	private long gemeindeanteilAnDerEinkommensteuer;
	private long gemeindeanteilAnDerUmsatzsteuer;
	private long gewerbesteuerumlage;
	private long gewerbesteuereinnahmen;

	public Realsteuer(String ks) {
		super(ks);
	}

	public long getGrundsteuerAIstaufkommen() {
		return grundsteuerAIstaufkommen;
	}

	public void setGrundsteuerAIstaufkommen(long grundsteuerAIstaufkommen) {
		this.grundsteuerAIstaufkommen = grundsteuerAIstaufkommen;
	}

	public long getGrundsteuerBIstaufkommen() {
		return grundsteuerBIstaufkommen;
	}

	public void setGrundsteuerBIstaufkommen(long grundsteuerBIstaufkommen) {
		this.grundsteuerBIstaufkommen = grundsteuerBIstaufkommen;
	}

	public long getGewerbesteuerIstaufkommen() {
		return gewerbesteuerIstaufkommen;
	}

	public void setGewerbesteuerIstaufkommen(long gewerbesteuerIstaufkommen) {
		this.gewerbesteuerIstaufkommen = gewerbesteuerIstaufkommen;
	}

	public long getGrundsteuerAGrundbetrag() {
		return grundsteuerAGrundbetrag;
	}

	public void setGrundsteuerAGrundbetrag(long grundsteuerAGrundbetrag) {
		this.grundsteuerAGrundbetrag = grundsteuerAGrundbetrag;
	}

	public long getGrundsteuerBGrundbetrag() {
		return grundsteuerBGrundbetrag;
	}

	public void setGrundsteuerBGrundbetrag(long grundsteuerBGrundbetrag) {
		this.grundsteuerBGrundbetrag = grundsteuerBGrundbetrag;
	}

	public long getGewerbesteuerGrundbetrag() {
		return gewerbesteuerGrundbetrag;
	}

	public void setGewerbesteuerGrundbetrag(long gewerbesteuerGrundbetrag) {
		this.gewerbesteuerGrundbetrag = gewerbesteuerGrundbetrag;
	}

	public long getGrundsteuerAHebesatz() {
		return grundsteuerAHebesatz;
	}

	public void setGrundsteuerAHebesatz(long grundsteuerAHebesatz) {
		this.grundsteuerAHebesatz = grundsteuerAHebesatz;
	}

	public long getGrundsteuerBHebesatz() {
		return grundsteuerBHebesatz;
	}

	public void setGrundsteuerBHebesatz(long grundsteuerBHebesatz) {
		this.grundsteuerBHebesatz = grundsteuerBHebesatz;
	}

	public long getGewerbesteuerHebesatz() {
		return gewerbesteuerHebesatz;
	}

	public void setGewerbesteuerHebesatz(long gewerbesteuerHebesatz) {
		this.gewerbesteuerHebesatz = gewerbesteuerHebesatz;
	}

	public long getGemeindeanteilAnDerEinkommensteuer() {
		return gemeindeanteilAnDerEinkommensteuer;
	}

	public void setGemeindeanteilAnDerEinkommensteuer(long gemeindeanteilAnDerEinkommensteuer) {
		this.gemeindeanteilAnDerEinkommensteuer = gemeindeanteilAnDerEinkommensteuer;
	}

	public long getGemeindeanteilAnDerUmsatzsteuer() {
		return gemeindeanteilAnDerUmsatzsteuer;
	}

	public void setGemeindeanteilAnDerUmsatzsteuer(long gemeindeanteilAnDerUmsatzsteuer) {
		this.gemeindeanteilAnDerUmsatzsteuer = gemeindeanteilAnDerUmsatzsteuer;
	}

	public long getGewerbesteuerumlage() {
		return gewerbesteuerumlage;
	}

	public void setGewerbesteuerumlage(long gewerbesteuerumlage) {
		this.gewerbesteuerumlage = gewerbesteuerumlage;
	}

	public long getGewerbesteuereinnahmen() {
		return gewerbesteuereinnahmen;
	}

	public void setGewerbesteuereinnahmen(long gewerbesteuereinnahmen) {
		this.gewerbesteuereinnahmen = gewerbesteuereinnahmen;
	}
}
