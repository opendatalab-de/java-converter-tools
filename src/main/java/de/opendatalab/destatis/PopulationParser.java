package de.opendatalab.destatis;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;

import de.opendatalab.utils.ResourceUtils;
import de.opendatalab.xls.AbstractExcelParser;
import de.opendatalab.zensus.Category;
import de.opendatalab.zensus.ZensusData;

public class PopulationParser extends AbstractExcelParser {

	public PopulationParser(InputStream in) {
		super(in, 1, 8);
	}

	public static List<ZensusData> parseKnownData() {
		PopulationParser populationParser = new PopulationParser(
				ResourceUtils.getResourceAsStream("AuszugGV2QAktuell.xls"));
		return populationParser.parse();
	}

	public List<ZensusData> parse() {
		List<ZensusData> result = new ArrayList<>();
		while (rowIterator.hasNext()) {
			Row row = rowIterator.next();
			String satzart = getStringValue(row, 0);
			if ("60".equals(satzart)) {
				String areaKey = parseAreaKey(row);
				String name = getStringValue(row, 7);
				Integer populationMale = getIntegerValue(row, 11);
				Integer populationFemale = getIntegerValue(row, 12);
				ZensusData zensusData = new ZensusData();
				zensusData.setSatzart(Category.byKey(satzart));
				zensusData.setAgs(areaKey);
				zensusData.setName(name);
				zensusData.setEwzM(populationMale);
				zensusData.setEwzW(populationFemale);
				result.add(zensusData);
			}
		}
		return result;
	}

	private String parseAreaKey(Row row) {
		return parseCellsIntoOne(row, new int[] { 2, 3, 4, 5, 6 });
	}
}
