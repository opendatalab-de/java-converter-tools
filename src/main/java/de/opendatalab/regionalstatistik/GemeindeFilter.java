package de.opendatalab.regionalstatistik;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.geojson.FeatureCollection;

import de.opendatalab.utils.Utils;

public class GemeindeFilter {

	private static final Map<String, String> filter = new HashMap<>();
	static {
		filter.put("gemeinden", "08125");
		filter.put("landkreise", "08121");
	}

	public static void main(String[] args) {
		try {
			FeatureCollection result = new FeatureCollection();
			for (int x = 0; x < args.length - 1; x++) {
				String arg = args[x];
				System.out.println("Reading: " + arg);
				FeatureCollection json = Utils.readGeoJsonFeatureCollection(new FileInputStream(arg), "utf8");
				String filter = identifyFilterByFileName(arg);
				result = Utils.mergeGeoJson(result, Utils.filterGeoJsonByKey(json, filter));
			}
			System.out.println("Objects: " + result.getFeatures().size() + " => " + args[args.length - 1]);
			Utils.writeData(result, args[args.length - 1]);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static String identifyFilterByFileName(String arg) {
		for (Entry<String, String> entry : filter.entrySet()) {
			if (arg.contains(entry.getKey()))
				return entry.getValue();
		}
		throw new RuntimeException("no filter for file " + arg);
	}
}
