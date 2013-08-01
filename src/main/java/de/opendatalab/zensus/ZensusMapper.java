package de.opendatalab.zensus;

import java.util.HashMap;
import java.util.Map;

public class ZensusMapper {

	public static Map<String, ZensusData> extractDataByCategoryMappedByAgs(Category category, Iterable<ZensusData> data) {
		Map<String, ZensusData> result = new HashMap<>();
		for (ZensusData zensusData : data) {
			if (zensusData.getSatzart().equals(category)) {
				result.put(zensusData.getAgs(), zensusData);
			}
		}
		return result;
	}
}
