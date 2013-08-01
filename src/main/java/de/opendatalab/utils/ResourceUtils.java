package de.opendatalab.utils;

import java.io.File;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;

public class ResourceUtils {

	public static File getResource(String filename) {
		URL url = ResourceUtils.class.getClassLoader().getResource(filename);
		try {
			return new File(url.toURI());
		}
		catch (URISyntaxException e) {
			throw new IllegalStateException(e);
		}
	}

	public static InputStream getResourceAsStream(String filename) {
		return ResourceUtils.class.getClassLoader().getResourceAsStream(filename);
	}
}
