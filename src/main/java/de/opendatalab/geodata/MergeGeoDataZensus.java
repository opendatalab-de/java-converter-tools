package de.opendatalab.geodata;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.geojson.Feature;
import org.geojson.FeatureCollection;

import de.opendatalab.destatis.PopulationParser;
import de.opendatalab.utils.Utils;
import de.opendatalab.zensus.Category;
import de.opendatalab.zensus.ZensusData;
import de.opendatalab.zensus.ZensusMapper;

public class MergeGeoDataZensus {

	private static Map<String, String> mapping = new HashMap<>();
	static {
		mapping.put("010595974011", "010595974187");
		mapping.put("010595990165", "010595990186");
		mapping.put("031565401001", "031560017017");
		mapping.put("120615114017", "120615112017");
		mapping.put("120615114097", "120615112097");
		mapping.put("120615114164", "120615112164");
		mapping.put("120615114244", "120615112244");
		mapping.put("120615114265", "120615112265");
		mapping.put("120615114405", "120615112405");
		mapping.put("120615114428", "120615112428");
		mapping.put("120615114435", "120615112435");
		mapping.put("120615114471", "120615112471");
		mapping.put("120615114510", "120615112510");
	}
	private static int noZensusData = 0;

	private FeatureCollection merge(String geoDataSource, Category category) throws FileNotFoundException {
		List<ZensusData> parseKnownData = PopulationParser.parseKnownData();
		System.out.println("Zensus Data: " + parseKnownData.size());
		Map<String, ZensusData> mappedZensus = ZensusMapper.extractDataByCategoryMappedByAgs(category, parseKnownData);
		FeatureCollection json = Utils.readGeoJsonFeatureCollection(new FileInputStream(geoDataSource), "utf8");
		System.out.println("Features: " + json.getFeatures().size());
		FeatureCollection result = filterGeoJsonByKey(json, mappedZensus);
		for (ZensusData zensusData : mappedZensus.values()) {
			System.out.println(zensusData.getAgs() + " => " + zensusData.getName() + " EWZ: " + zensusData.getEwzM()
					+ " | " + zensusData.getEwzW());
		}
		System.out.println("Remaining in ZensusData: " + mappedZensus.size() + " / No Zensus Data: " + noZensusData);
		return result;
	}

	public static FeatureCollection filterGeoJsonByKey(FeatureCollection geoJson, Map<String, ZensusData> mappedZensus) {
		FeatureCollection result = new FeatureCollection();
		result.setCrs(geoJson.getCrs());
		result.setBbox(geoJson.getBbox());
		for (Feature feature : geoJson) {
			Double gf = feature.getProperty("GF");
			if (gf == 4) {
				Feature newFeature = new Feature();
				newFeature.setProperty("RS", feature.getProperty("RS"));
				newFeature.setProperty("GEN", feature.getProperty("GEN"));
				newFeature.setProperty("DES", feature.getProperty("DES"));
				newFeature.setProperty("SHAPE_AREA", feature.getProperty("SHAPE_AREA"));
				newFeature.setGeometry(feature.getGeometry());
				String rs = feature.getProperty("RS");
				if (mapping.containsKey(rs))
					rs = mapping.get(rs);
				PopulationData populationData = findAndAggregateZensus(mappedZensus, rs);
				if (populationData.hasData()) {
					newFeature.setProperty("EWZ_M", populationData.getMale());
					newFeature.setProperty("EWZ_W", populationData.getFemale());
				}
				else {
					System.out.println("No Zensus Data for RS: " + rs + " / " + feature.getProperty("GEN"));
					noZensusData++;
				}
				result.add(newFeature);
			}
		}
		return result;
	}

	private static PopulationData findAndAggregateZensus(Map<String, ZensusData> mappedZensus, String rs) {
		PopulationData data = new PopulationData();
		Iterator<String> it = mappedZensus.keySet().iterator();
		while (it.hasNext()) {
			String zensusRs = it.next();
			if (zensusRs.startsWith(rs)) {
				ZensusData zensusData = mappedZensus.get(zensusRs);
				it.remove();
				data.addMale(zensusData.getEwzM());
				data.addFemale(zensusData.getEwzW());
			}
		}
		return data;
	}

	public static void main(String[] args) {
		try {
			MergeGeoDataZensus merger = new MergeGeoDataZensus();
			FeatureCollection featureCollection = merger.merge(args[0], Category.GEMEINDE);
			File file = new File(args[0]);
			String outfileName = args[1] + File.separator + file.getName();
			System.out.println("Output file: " + outfileName);
			Utils.writeData(featureCollection, outfileName);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
