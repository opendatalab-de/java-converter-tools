package de.opendatalab.xls;

import java.io.InputStream;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public abstract class AbstractExcelParser {

	protected Sheet sheet;
	protected Iterator<Row> rowIterator;

	protected AbstractExcelParser(InputStream in, int sheetToUse, int headerRowsToSkip) {
		try {
			Workbook wb = new HSSFWorkbook(in);
			sheet = wb.getSheetAt(sheetToUse);
			rowIterator = sheet.rowIterator();
			for (int x = 0; x < headerRowsToSkip; x++) {
				rowIterator.next();
			}
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String parseCellsIntoOne(Row row, int[] cellIds) {
		StringBuilder sb = new StringBuilder();
		for (int x : cellIds) {
			String value = getStringValue(row, x);
			if (value == null)
				return null;
			sb.append(value);
		}
		return sb.toString();
	}

	protected String getStringValue(Row row, int cellId) {
		Cell cell = row.getCell(cellId);
		if (cell == null)
			return null;
		String value = cell.getStringCellValue();
		if (value == null || value.isEmpty())
			return null;
		return value;
	}

	protected Integer getIntegerValue(Row row, int cellId) {
		Cell cell = row.getCell(cellId);
		if (cell == null)
			return null;
		Integer value = Double.valueOf(cell.getNumericCellValue()).intValue();
		if (value == null)
			return null;
		return value;
	}
}
