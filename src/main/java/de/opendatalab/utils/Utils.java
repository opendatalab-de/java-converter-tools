package de.opendatalab.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.geojson.Feature;
import org.geojson.FeatureCollection;
import org.supercsv.io.CsvListReader;
import org.supercsv.prefs.CsvPreference;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.type.MapType;
import com.fasterxml.jackson.databind.type.SimpleType;

public class Utils {

	public static void writeData(Object object, String filename) throws JsonProcessingException, IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		String json = null;
		String prettyJson = System.getProperty("pretty.json", "true");
		if ("true".equals(prettyJson)) {
			ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();
			json = objectWriter.writeValueAsString(object);
		}
		else {
			json = objectMapper.writeValueAsString(object);
		}
		OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(filename), Charset.forName("UTF8"));
		out.write(json);
		out.flush();
		out.close();
	}

	public static Map<String, Object> readGenericJson(InputStream in, String charset) {
		try {
			Map<String, Object> map = new ObjectMapper().readValue(
					new InputStreamReader(in, charset),
					MapType.construct(HashMap.class, SimpleType.construct(String.class),
							SimpleType.construct(Object.class)));
			return map;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static FeatureCollection readGeoJsonFeatureCollection(InputStream in, String charset) {
		try {
			return new ObjectMapper().readValue(new InputStreamReader(in, charset), FeatureCollection.class);
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static List<String[]> readCsvFile(String filename, String charset) {
		try {
			return readCsvFile(new FileInputStream(filename), charset);
		}
		catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	public static List<String[]> readCsvFile(InputStream in, String charset) {
		try {
			Reader input = new InputStreamReader(in, Charset.forName(charset));
			CsvListReader reader = new CsvListReader(input, CsvPreference.EXCEL_NORTH_EUROPE_PREFERENCE);
			List<String[]> result = new ArrayList<>();
			List<String> lineOfStrings = null;
			do {
				lineOfStrings = reader.read();
				if (lineOfStrings != null) {
					String[] line = new String[lineOfStrings.size()];
					for (int x = 0; x < lineOfStrings.size(); x++) {
						String cell = lineOfStrings.get(x);
						if (cell != null) {
							line[x] = cell.intern();
						}
						else
							line[x] = "";
					}
					result.add(line);
				}
			} while (lineOfStrings != null);
			reader.close();
			return result;
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static Map<String, Object> asMap(Object... objects) {
		Map<String, Object> result = new HashMap<>();
		for (int x = 0; x < objects.length; x += 2) {
			result.put((String)objects[x], objects[x + 1]);
		}
		return result;
	}

	public static boolean hasText(String s) {
		return s != null && !s.trim().isEmpty();
	}

	public static FeatureCollection mergeGeoJson(FeatureCollection... geoJson) {
		FeatureCollection result = new FeatureCollection();
		for (FeatureCollection fc : geoJson) {
			result.addAll(fc.getFeatures());
		}
		return result;
	}

	public static FeatureCollection filterGeoJsonByKey(FeatureCollection geoJson, String... rsKeys) {
		FeatureCollection result = new FeatureCollection();
		for (Feature feature : geoJson) {
			String rs = feature.getProperty("RS");
			for (String rsKey : rsKeys) {
				if (rs != null && rs.startsWith(rsKey)) {
					Feature newFeature = new Feature();
					newFeature.setProperty("RS", feature.getProperty("RS"));
					newFeature.setProperty("AGS", rsToAgs((String)feature.getProperty("RS")));
					newFeature.setProperty("Name", feature.getProperty("GEN"));
					newFeature.setProperty("Status", feature.getProperty("DES"));
					newFeature.setProperty("Einwohner", feature.getProperty("EWZ"));
					newFeature.setProperty("qkm", feature.getProperty("SHAPE_AREA"));
					newFeature.setGeometry(feature.getGeometry());
					result.add(newFeature);
					break;
				}
			}
		}
		return result;
	}

	public static String rsToAgs(String rs) {
		if (rs.length() == 12) {
			return rs.substring(0, 5) + rs.substring(9);
		}
		else
			return rs;
	}
}
