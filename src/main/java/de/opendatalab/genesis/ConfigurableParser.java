package de.opendatalab.genesis;

import java.util.Collection;

import de.opendatalab.csv.AbstractCsvParser;
import de.opendatalab.csv.ParserConfiguration;
import de.opendatalab.utils.KeyValue;

public class ConfigurableParser extends AbstractCsvParser {

	public ConfigurableParser(Collection<String[]> lines, ParserConfiguration configuration) {
		super(lines, configuration);
	}

	@Override
	protected KeyValue parseItem(String[] strings) {
		try {
			if (strings.length == configuration.getProperties().length + configuration.getDataStart()) {
				String areaKey = strings[configuration.getAreaKey()];
				KeyValue keyValue = new KeyValue(areaKey);
				for (int x = 0; x < configuration.getProperties().length; x++) {
					String property = configuration.getProperties()[x];
					String value = strings[configuration.getDataStart() + x];
					Long convertedValue = convertSafe(value);
					keyValue.setValue(property, convertedValue);
				}
				return keyValue;
			}
			else
				return null;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private Long convertSafe(String value) {
		if (value.contains("-") || value.contains("."))
			return null;
		else if (value.contains(",")) {
			value = value.replace(',', '.');
			return Double.valueOf(value).longValue();
		}
		else
			return Long.valueOf(value);
	}
}
