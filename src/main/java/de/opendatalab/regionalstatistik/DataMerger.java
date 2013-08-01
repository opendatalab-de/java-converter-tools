package de.opendatalab.regionalstatistik;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.geojson.Feature;
import org.geojson.FeatureCollection;

import de.opendatalab.csv.ParserConfiguration;
import de.opendatalab.genesis.ConfigurableParser;
import de.opendatalab.utils.ResourceUtils;
import de.opendatalab.utils.Utils;

public class DataMerger {

	private static final Map<String, ParserConfiguration> CONFIGS = new HashMap<String, ParserConfiguration>();
	static {
		CONFIGS.put("356-11-5.csv", new ParserConfiguration(7, 0, 2, new String[] { "grundsteuerAIstaufkommen",
				"grundsteuerBIstaufkommen", "gewerbesteuerIstaufkommen", "grundsteuerAGrundbetrag",
				"grundsteuerBGrundbetrag", "gewerbesteuerGrundbetrag", "grundsteuerAHebesatz", "grundsteuerBHebesatz",
				"gewerbesteuerHebesatz", "gemeindeanteilAnDerEinkommensteuer", "gemeindeanteilAnDerUmsatzsteuer",
				"gewerbesteuerumlage", "gewerbesteuereinnahmen" }));
		CONFIGS.put("001-03-5.csv", new ParserConfiguration(7, 1, 3, new String[] { "HandwerkBetriebe",
				"HandwerkBeschäftigte", "HandwerkBruttoentgelte" }));
		CONFIGS.put("659-21-5.csv", new ParserConfiguration(9, 0, 2, new String[] { "ArbeitsmarktAnzahl",
				"ArbeitsmarktAusländer", "Arbeitsmarktschwerbehindert", "Arbeitsmarkt15 bis unter 20 Jahre",
				"Arbeitsmarkt15 bis unter 25 Jahre", "Arbeitsmarkt55 bis unter 65 Jahre",
				"Arbeitsmarktlangzeitarbeitslos" }));
		CONFIGS.put("517-01-5.csv", new ParserConfiguration(8, 1, 3, new String[] {
				"TrinkwasserVerbrauchsabhängiges Entgelt pro cbm",
				"TrinkwasserHaushaltsäbliches verbrauchsunabh. Entgelt p. Jahr" }));
		CONFIGS.put("469-11-5.csv", new ParserConfiguration(8, 0, 2, new String[] { "Geöffnete Beherbergungsbetriebe",
				"Angebotene Gästebetten", "Gästeübernachtungen", "Gästeankünfte" }));
		CONFIGS.put("035-21-5.csv", new ParserConfiguration(8, 1, 3, new String[] { "WohngebäudeInsgesamt",
				"Wohngebäude1Wohnung", "Wohngebäude2Wohnungen", "WohnflächeinWohngebäuden",
				"WohnungeninWohnundNichtwohngebäuden_Größe_Insgesamt",
				"WohnungeninWohnundNichtwohngebäuden_Größe_Wohnungenmit1Raum",
				"WohnungeninWohnundNichtwohngebäuden_Größe_Wohnungenmit2Räumen",
				"WohnungeninWohnundNichtwohngebäuden_Größe_Wohnungenmit3Räumen",
				"WohnungeninWohnundNichtwohngebäuden_Größe_Wohnungenmit4Räumen",
				"WohnungeninWohnundNichtwohngebäuden_Größe_Wohnungenmit5Räumen",
				"WohnungeninWohnundNichtwohngebäuden_Größe_Wohnungenmit6Räumen",
				"WohnungeninWohnundNichtwohngebäuden_Größe_Wohnungenmit7Räumenodermehr",
				"RäumeinWohnungenmit7odermehrRäumen" }));
		CONFIGS.put("302-11-5.csv", new ParserConfiguration(7, 0, 2, new String[] { "Unfälle (insgesamt)",
				"Unfälle mit Personenschaden", "Schwerwiegende Unfälle mit Sachschaden i. e. S.",
				"Sons.Sachschadensunf.unter d.Einfl.berausch.Mittel", "Getötete Personen", "Verletzte Personen" }));
		CONFIGS.put("449-01-5.csv", new ParserConfiguration(9, 0, 2, new String[] { "Bodenfläche",
				"Siedlungs- und Verkehrsfläche", "Gebäude- und Freifläche Insgesamt", "Gebäude- und Freifläche Wohnen",
				"Gebäude- und Freifläche Gewerbe, Industrie", "Betriebsfläche (ohne Abbauland)",
				"Erholungsfläche Insgesamt", "Erholungsfläche Grünanlage", "Friedhofsfläche",
				"Verkehrsfläche Insgesamt", "Verkehrsfläche Straße, Weg, Platz", "Landwirtschaftsfläche Insgesamt",
				"Landwirtschaftsfläche Moor", "Landwirtschaftsfläche Heide", "Waldfläche", "Wasserfläche", "Abbauland",
				"Flächen anderer Nutzung Insgesamt", "Flächen anderer Nutzung Unland" }));
	}

	public static void main(String[] args) {
		try {
			FeatureCollection json = Utils.readGeoJsonFeatureCollection(
					ResourceUtils.getResourceAsStream("heilbronn.geojson"), "utf8");
			for (Entry<String, ParserConfiguration> entry : CONFIGS.entrySet()) {
				System.out.println(entry.getKey());
				ConfigurableParser parser = new ConfigurableParser(Utils.readCsvFile(
						ResourceUtils.getResourceAsStream(entry.getKey()), "ISO-8859-1"), entry.getValue());
				Map<String, Map<String, Object>> rsMap = parser.parse();
				System.out.println("Parsed data: " + rsMap.size());
				enrichGeoJsonByAgsKey(json, rsMap);
				System.out.println("Objects: " + json.getFeatures().size());
			}
			String outputFile = "../viewer/src/data/heilbronn-rs.geojson";
			Utils.writeData(json, outputFile);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void enrichGeoJsonByAgsKey(FeatureCollection geoJson, Map<String, Map<String, Object>> rsMap) {
		for (Feature feature : geoJson) {
			String ags = feature.getProperty("AGS");
			Map<String, Object> data = rsMap.get(ags);
			if (data != null) {
				feature.getProperties().putAll(data);
			}
		}
	}
}
