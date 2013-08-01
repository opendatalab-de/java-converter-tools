package de.opendatalab.zensus;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.supercsv.io.CsvListReader;
import org.supercsv.prefs.CsvPreference;

public class ZensusParser {

	public List<ZensusData> parse(InputStream in, String charset) {
		List<ZensusData> result = new ArrayList<>(17000);
		try {
			Reader input = new InputStreamReader(in, Charset.forName(charset));
			CsvListReader reader = new CsvListReader(input, CsvPreference.EXCEL_NORTH_EUROPE_PREFERENCE);
			reader.getHeader(true);
			List<String> lineOfStrings = null;
			do {
				lineOfStrings = reader.read();
				if (lineOfStrings != null) {
					result.add(createZensusData(lineOfStrings));
				}
			} while (lineOfStrings != null);
			reader.close();
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}
		return result;
	}

	private ZensusData createZensusData(List<String> lineOfStrings) {
		ZensusData data = new ZensusData();
		int c = 0;
		data.setSatzart(lineOfStrings.get(c++));
		data.setAgs(lineOfStrings.get(c++));
		data.setName(lineOfStrings.get(c++));
		data.setEwz(parse(lineOfStrings.get(c++)));
		data.setEwzM(parse(lineOfStrings.get(c++)));
		data.setEwzW(parse(lineOfStrings.get(c++)));
		data.setEwzD(parse(lineOfStrings.get(c++)));
		data.setEwzA(parse(lineOfStrings.get(c++)));
		data.setAlter1(parse(lineOfStrings.get(c++)));
		data.setAlter2(parse(lineOfStrings.get(c++)));
		data.setAlter3(parse(lineOfStrings.get(c++)));
		data.setAlter4(parse(lineOfStrings.get(c++)));
		data.setAlter5(parse(lineOfStrings.get(c++)));
		return data;
	}

	private Integer parse(String value) {
		try {
			if ("/".equals(value))
				return null;
			else
				return Integer.parseInt(value);
		}
		catch (NumberFormatException e) {
			return null;
		}
	}
}
