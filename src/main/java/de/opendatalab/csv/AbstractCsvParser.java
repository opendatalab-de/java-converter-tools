package de.opendatalab.csv;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import de.opendatalab.utils.KeyValue;

public abstract class AbstractCsvParser {

	protected Collection<String[]> lines;
	protected ParserConfiguration configuration;

	protected AbstractCsvParser(Collection<String[]> lines, ParserConfiguration configuration) {
		this.lines = lines;
		this.configuration = configuration;
	}

	public Map<String, Map<String, Object>> parse() {
		Iterator<String[]> iterator = lines.iterator();
		skipLines(iterator);
		return parseWithIterator(iterator);
	}

	protected void skipLines(Iterator<String[]> it) {
		for (int x = 0; x < configuration.getSkipLines(); x++) {
			it.next();
		}
	}

	protected Map<String, Map<String, Object>> parseWithIterator(Iterator<String[]> iterator) {
		Map<String, Map<String, Object>> result = new LinkedHashMap<>();
		while (iterator.hasNext()) {
			KeyValue keyValue = parseItem(iterator.next());
			if (keyValue != null)
				result.put(keyValue.getKey(), keyValue.getValue());
		}
		return result;
	}

	protected abstract KeyValue parseItem(String[] strings);
}
