package de.opendatalab.zensus;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import de.opendatalab.utils.ResourceUtils;

public class ZensusParserTest {

	@Test
	public void itShouldParse() throws Exception {
		ZensusParser parser = new ZensusParser();
		List<ZensusData> result = parser.parse(
				ResourceUtils.getResourceAsStream("Zensus_Demographie_V1_28Mai2013.csv"), "utf8");
		assertFalse(result.isEmpty());
		assertEquals(16339, result.size());
	}
}
