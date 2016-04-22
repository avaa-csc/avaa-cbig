package fi.csc.avaa.cbig.common.csv;

import static fi.csc.avaa.cbig.common.Const.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import fi.csc.avaa.cbig.common.csv.CSVHeaders.CarnivoresAllSpeciesHeaders;
import fi.csc.avaa.cbig.common.csv.CSVHeaders.CarnivoresFutureFamilyHeaders;
import fi.csc.avaa.cbig.common.csv.CSVHeaders.CarnivoresFutureIUCNHeaders;
import fi.csc.avaa.cbig.common.csv.CSVHeaders.CarnivoresFutureSizeHeaders;
import fi.csc.avaa.cbig.common.csv.CSVHeaders.CarnivoresPresentFamilyHeaders;
import fi.csc.avaa.cbig.common.csv.CSVHeaders.CarnivoresPresentIUCNHeaders;
import fi.csc.avaa.cbig.common.csv.CSVHeaders.CarnivoresPresentSizeHeaders;
import fi.csc.avaa.cbig.common.csv.CSVHeaders.GPANCurves2Headers;
import fi.csc.avaa.cbig.common.csv.CSVHeaders.GPANMeanHeaders;
import fi.csc.avaa.tools.csv.OpenCSVDataReader;

/**
 * @author jmlehtin
 *
 */
public final class CSVTools {

	private static final Log LOG = LogFactoryUtil.getLog(CSVTools.class);
	private static final String STR_WRONG_CSV_TYPE = "Wrong CSV type received in VaadinRequest";
	
	private CSVTools() {
	}
	
	/**
	 * Get URL from where to fetch a specific csvType
	 * 
	 * @param csvType
	 * @return
	 */
	public static String getCSVUrl(CSVType csvType) {
		String csvUrl = null;
		switch (csvType) {
		case CURVES2:
			csvUrl = GPAN_CSV_CURVES2_URL;
			break;
		case MEAN_CURVES:
			csvUrl = GPAN_CSV_MEAN_CURVES_URL;
			break;
		case ALL_SPECIES:
			csvUrl = CV_ALL_SPECIES_URL;
			break;
		case FUTURE_FAMILY:
			csvUrl = CV_FUTURE_FAMILY_URL;
			break;
		case FUTURE_IUCN:
			csvUrl = CV_FUTURE_IUCN_URL;
			break;
		case FUTURE_SIZE:
			csvUrl = CV_FUTURE_SIZE_URL;
			break;
		case PRESENT_FAMILY:
			csvUrl = CV_PRESENT_FAMILY_URL;
			break;
		case PRESENT_IUCN:
			csvUrl = CV_PRESENT_IUCN_URL;
			break;
		case PRESENT_SIZE:
			csvUrl = CV_PRESENT_SIZE_URL;
			break;
		default:
			LOG.error(STR_WRONG_CSV_TYPE);
		}
		return csvUrl;
	}

	/**
	 * The line from which to start reading the CSV data
	 * 
	 * @param csvType
	 * @return
	 */
	public static int getCSVBeginLine(CSVType csvType) {
		int beginLine = 0;
		switch (csvType) {
		case CURVES2:
		case MEAN_CURVES:
		case ALL_SPECIES:
		case FUTURE_FAMILY:
		case FUTURE_IUCN:
		case FUTURE_SIZE:
		case PRESENT_FAMILY:
		case PRESENT_IUCN:
		case PRESENT_SIZE:
			beginLine = 1;
			break;
		default:
			LOG.error(STR_WRONG_CSV_TYPE);
		}
		return beginLine;
	}

	/**
	 * The amount of entries per CSV row
	 * 
	 * @param csvType
	 * @return
	 */
	public static int getCSVHeaderAmt(CSVType csvType) {
		int headerAmt = 0;
		switch (csvType) {
		case CURVES2:
			headerAmt = GPANCurves2Headers.values().length;
			break;
		case MEAN_CURVES:
			headerAmt = GPANMeanHeaders.values().length;
			break;
		case ALL_SPECIES:
			headerAmt = CarnivoresAllSpeciesHeaders.values().length;
			break;
		case FUTURE_FAMILY:
			headerAmt = CarnivoresFutureFamilyHeaders.values().length;
			break;
		case FUTURE_IUCN:
			headerAmt = CarnivoresFutureIUCNHeaders.values().length;
			break;
		case FUTURE_SIZE:
			headerAmt = CarnivoresFutureSizeHeaders.values().length;
			break;
		case PRESENT_FAMILY:
			headerAmt = CarnivoresPresentFamilyHeaders.values().length;
			break;
		case PRESENT_IUCN:
			headerAmt = CarnivoresPresentIUCNHeaders.values().length;
			break;
		case PRESENT_SIZE:
			headerAmt = CarnivoresPresentSizeHeaders.values().length;
			break;
		default:
			LOG.error(STR_WRONG_CSV_TYPE);
		}
		return headerAmt;
	}
	
	/**
	 * How are CSV entries separated
	 * 
	 * @param csvType
	 * @return
	 */
	public static char getCSVSeparator(CSVType csvType) {
		char separator = '\0';
		switch (csvType) {
		case CURVES2:
			separator = ';';
			break;
		case MEAN_CURVES:
			separator = ' ';
			break;
		case ALL_SPECIES:
			separator = ',';
			break;
		case FUTURE_FAMILY:
			separator = ',';
			break;
		case FUTURE_IUCN:
			separator = ',';
			break;
		case FUTURE_SIZE:
			separator = ',';
			break;
		case PRESENT_FAMILY:
			separator = ',';
			break;
		case PRESENT_IUCN:
			separator = ',';
			break;
		case PRESENT_SIZE:
			separator = ',';
			break;
		default:
			LOG.error("Wrong CSV type received in VaadinRequest");
		}
		return separator;
	}

	/**
	 * How are CSV entries quoted
	 * 
	 * @param csvType
	 * @return
	 */
	public static char getCSVQuoteChar(CSVType csvType) {
		char quoteChar = '\0';
		switch (csvType) {
		case CURVES2:
			quoteChar = '"';
			break;
		case MEAN_CURVES:
			quoteChar = '"';
			break;
		case ALL_SPECIES:
		case FUTURE_FAMILY:
		case FUTURE_IUCN:
		case FUTURE_SIZE:
		case PRESENT_FAMILY:
		case PRESENT_IUCN:
		case PRESENT_SIZE:
			quoteChar = '"';
			break;
		default:
			LOG.error(STR_WRONG_CSV_TYPE);
		}
		return quoteChar;
	}

	/**
	 * Get CSV data.
	 * 
	 * @param csvType
	 *            CSV file type to determine the URL, no of items per row etc.
	 * @return List of Map objects representing rows of CSV data
	 */
	public static List<Map<String, String>> getDataRowsForCsvType(CSVType csvType, Map<CSVType, List<Map<String, String>>> csvData) {
		LOG.debug("Trying to read " + csvType.name()
				+ " CSV file and extract data..");
		List<Map<String, String>> retData = null;
		if (csvData.containsKey(csvType)) {
			LOG.debug("Using cached CSV data instead of fetching from IDA..");
			retData = csvData.get(csvType);
		} else {
			// Create CSV data reader
			BufferedReader csvBufferedReader = new BufferedReader(new InputStreamReader(CSVTools.class.getClassLoader()
	                .getResourceAsStream(getCSVUrl(csvType))));
			
			OpenCSVDataReader reader = new OpenCSVDataReader(
					getCSVSeparator(csvType),
					getCSVQuoteChar(csvType),
					getCSVBeginLine(csvType),
					getCSVHeaderAmt(csvType),
					csvBufferedReader);
			// Try three times fetching the CSV data
			for (int i = 0; i < 3; i++) {
				retData = reader.readCSVData(CSVHeaders.getCSVHeaders(csvType));
				if (retData == null || (retData != null && retData.isEmpty())) {
					if (i < 2) {
						LOG.error("Reading CSV file "
								+ csvType.name()
								+ " failed! Waiting for 5 seconds before trying again..");
						try {
							Thread.sleep(5000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				} else {
					break;
				}
			}
	
			// If CSV data was fetched successfully, put it into the cache for
			// later use
			if (retData != null && !retData.isEmpty()) {
				csvData.put(csvType, retData);
				LOG.debug("Done reading!");
			} else {
				LOG.error("Unable to fetch CSV file " + csvType.name() + "!");
			}
		}
		// Use this if csv files are fetched from Internet
		//DataReaderTools.closeHttpURLConnection();
		return retData;
	}
	
}
