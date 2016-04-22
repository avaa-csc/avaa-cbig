package fi.csc.avaa.cbig.common;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import fi.csc.avaa.cbig.common.chart.ChartTypes;
import fi.csc.avaa.cbig.common.chart.model.DataPoint;
import fi.csc.avaa.cbig.common.chart.model.GraphData;
import fi.csc.avaa.cbig.common.csv.CSVDataValueTypes.GPANGroupType;
import fi.csc.avaa.cbig.common.csv.CSVHeaders.CarnivoresAllSpeciesHeaders;
import fi.csc.avaa.cbig.common.csv.CSVHeaders.CarnivoresFutureFamilyHeaders;
import fi.csc.avaa.cbig.common.csv.CSVHeaders.CarnivoresFutureIUCNHeaders;
import fi.csc.avaa.cbig.common.csv.CSVHeaders.CarnivoresFutureSizeHeaders;
import fi.csc.avaa.cbig.common.csv.CSVHeaders.CarnivoresPresentFamilyHeaders;
import fi.csc.avaa.cbig.common.csv.CSVHeaders.CarnivoresPresentIUCNHeaders;
import fi.csc.avaa.cbig.common.csv.CSVHeaders.CarnivoresPresentSizeHeaders;
import fi.csc.avaa.cbig.common.csv.CSVHeaders.GPANCurves2Headers;
import fi.csc.avaa.cbig.common.csv.CSVHeaders.GPANMeanHeaders;
import fi.csc.avaa.cbig.common.csv.CSVType;
import fi.csc.avaa.tools.NumberTools;

/**
 * @author jmlehtin
 *
 */
public final class CSVToChartTools {
	
	private static final Log LOG = LogFactoryUtil.getLog(CSVToChartTools.class);
	
	/**
	 * Returns a Map whose entries are all the data points for a single graph in
	 * the chart. The Map as a whole should have data for all graphs to be
	 * presented in the chart.
	 * 
	 * @param csvType
	 *            Type of the CSV from where the csvData was read from
	 * @param csvData
	 *            The actual CSV data from where the graph data is extracted
	 * @param chartType
	 *            Type of chart that expects the graph data
	 * @return Return graph data for all graphs comprising for chartType. Graph
	 *         data data point are sorted from x=0 to x=1. Key for graph data is
	 *         graphId, which is composed  "variantType_groupType"
	 */
	public static Map<String, GraphData> getChartDataPoints(AppType appType, CSVType csvType,
			List<Map<String, String>> csvData, ChartTypes chartType) {
		if (csvData == null || (csvData != null && csvData.isEmpty())) {
			return null;
		}
		Map<String, GraphData> allGraphData = new TreeMap<String, GraphData>();
		if(appType == AppType.GPAN) {
			int i = 0;
			for (Map<String, String> csvRow : csvData) {
				String variantOfRow = getGPANVariantFromRow(csvType, csvRow);
				String groupOfRow = getGPANGroupFromRow(csvType, csvRow);
	
				if (rowIsRelevantForGPANChartType(chartType, groupOfRow)) {
					String graphId = getGPANGraphIdFromVariantAndGroupStrings(
							variantOfRow, groupOfRow);
					GraphData cData = allGraphData.get(graphId);
					if (cData == null) {
						cData = new GraphData();
						allGraphData.put(graphId, cData);
					}
					DataPoint dataPoint = getGPANDataPointFromRow(csvType, csvRow);
					if(i % 2 == 0) {
						cData.getDataPoints().add(dataPoint);
					}
					i++;
				}
			}
			sortGraphData(allGraphData);
		} else if(appType == AppType.CARNIVORES) {
			String xHeader = getCarnivoresCSVXValueHeader(csvType);
			String xValue = null;
			for (Map<String, String> csvRow : csvData) {
				if(csvRow == null || csvRow.get(xHeader) == null) {continue;}
				xValue = csvRow.get(xHeader);
				for(Map.Entry<String, String> csvHeaderValueEntry : csvRow.entrySet()) {
					String header = csvHeaderValueEntry.getKey();
					String yValue = csvHeaderValueEntry.getValue();
					if(!xHeader.equals(header)) {
						GraphData cData = allGraphData.get(header);
						if (cData == null) {
							cData = new GraphData();
							allGraphData.put(header, cData);
						}
						DataPoint dataPoint = getCVDataPoint(xValue, yValue);
						cData.getDataPoints().add(dataPoint);
						
					}
				}
			}
			sortGraphDataAndRemoveDuplicateX(allGraphData);
		}
		return allGraphData;
	}

	private static DataPoint getCVDataPoint(String xValue, String yValue) {
		DataPoint cPoint = new DataPoint();

		double x = 0.0d;
		double y = 0.0d;

		if (NumberTools.isDouble(xValue)) {
			x = Double.parseDouble(xValue);
		}

		if (NumberTools.isDouble(yValue)) {
			y = Double.parseDouble(yValue);
		}

		cPoint.setX(100*(1-x));
		cPoint.setY(y);
		return cPoint;
	}

	private static String getCarnivoresCSVXValueHeader(CSVType csvType) {
		switch (csvType) {
		case ALL_SPECIES:
			return CarnivoresAllSpeciesHeaders.PROP_LOST.getValue();
		case FUTURE_FAMILY:
			return CarnivoresFutureFamilyHeaders.PROP_LOST.getValue();
		case FUTURE_IUCN:
			return CarnivoresFutureIUCNHeaders.PROP_LOST.getValue();
		case FUTURE_SIZE:
			return CarnivoresFutureSizeHeaders.PROP_LOST.getValue();
		case PRESENT_FAMILY:
			return CarnivoresPresentFamilyHeaders.PROP_LOST.getValue();
		case PRESENT_IUCN:
			return CarnivoresPresentIUCNHeaders.PROP_LOST.getValue();
		case PRESENT_SIZE:
			return CarnivoresPresentSizeHeaders.PROP_LOST.getValue();
		default:
			return null;
		}
	}

	/**
	 * Sort graph data
	 * 
	 * @param allGraphData
	 */
	private static void sortGraphData(Map<String, GraphData> allGraphData) {
		for (Map.Entry<String, GraphData> cData : allGraphData.entrySet()) {
			cData.getValue().sortDataPoints();
		}
	}
	
	private static void sortGraphDataAndRemoveDuplicateX(Map<String, GraphData> allGraphData) {
		for (Map.Entry<String, GraphData> cData : allGraphData.entrySet()) {
			GraphData gd = cData.getValue();
			gd.sortDataPoints();
			gd.removeDuplicateXPoints();
		}
	}

	private static String getGPANGraphIdFromVariantAndGroupStrings(String variant,
			String group) {
		return variant + (group == null ? "" : "_" + group);
	}

	/**
	 * Extract the variant type from the data row.
	 * 
	 * @param csvType
	 * @param csvRow
	 * @return
	 */
	private static String getGPANVariantFromRow(CSVType csvType,
			Map<String, String> csvRow) {
		switch (csvType) {
		case CURVES2:
			return csvRow.get(GPANCurves2Headers.VARIANT.getValue());
		case MEAN_CURVES:
			return csvRow.get(GPANMeanHeaders.VARIANT.getValue());
		default:
			LOG.error("Wrong CSV type received in VaadinRequest");
			return null;
		}
	}

	/**
	 * Extract the group type from the data row.
	 * 
	 * @param csvType
	 * @param csvRow
	 * @return
	 */
	private static String getGPANGroupFromRow(CSVType csvType,
			Map<String, String> csvRow) {
		switch (csvType) {
		case CURVES2:
			return csvRow.get(GPANCurves2Headers.GROUP.getValue());
		case MEAN_CURVES:
			return null;
		default:
			LOG.error("Wrong CSV type received in VaadinRequest");
			return null;
		}
	}

	/**
	 * Extract data point values (x and y) from the data row.
	 * 
	 * @param csvType
	 * @param csvRow
	 * @return
	 */
	private static DataPoint getGPANDataPointFromRow(CSVType csvType,
			Map<String, String> csvRow) {
		DataPoint cPoint = new DataPoint();
		String xStr = null;
		String yStr = null;

		switch (csvType) {
		case CURVES2:
			xStr = csvRow.get(GPANCurves2Headers.F_REM.getValue());
			yStr = csvRow.get(GPANCurves2Headers.VALUE.getValue());
			break;
		case MEAN_CURVES:
			xStr = csvRow.get(GPANMeanHeaders.F_REM.getValue());
			yStr = csvRow.get(GPANMeanHeaders.VALUE.getValue());
			break;
		default:
			LOG.error("Wrong CSV type received in VaadinRequest");
		}

		double x = 0.0d;
		double y = 0.0d;

		if (NumberTools.isDouble(xStr)) {
			x = Double.parseDouble(xStr);
		}

		if (NumberTools.isDouble(yStr)) {
			y = Double.parseDouble(yStr);
		}

		cPoint.setX(x);
		cPoint.setY(y);
		return cPoint;
	}

	/**
	 * Determine whether the group of the data is relevant for a particular
	 * chart type. This method is needed if the input CSV data contains rows
	 * that are irrelevant to the chart being drawn.
	 * 
	 * @param chartType
	 *            Type of the chart to be drawn
	 * @param groupOfRow
	 *            Group to which a (CSV) data row belongs to.
	 * @return Whether groupOfRow string is one of the groups relevant for this
	 *         chartType
	 */
	private static boolean rowIsRelevantForGPANChartType(ChartTypes chartType,
			String groupOfRow) {
		switch (chartType) {
		case GPAN_IUCN:
			Set<String> iucnValues = GPANGroupType.getIUCNCSVGroupsAsString();
			if (iucnValues.contains(groupOfRow)) {
				return true;
			}
			return false;
		case GPAN_MEAN:
			return true;
		case GPAN_SPECIES:
			Set<String> speciesValues = GPANGroupType.getSpeciesCSVGroupsAsString();
			if (speciesValues.contains(groupOfRow)) {
				return true;
			}
			return false;
		default:
			return false;
		}
	}
}
