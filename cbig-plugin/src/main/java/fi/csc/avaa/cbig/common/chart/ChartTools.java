/**
 * 
 */
package fi.csc.avaa.cbig.common.chart;

import java.util.ArrayList;
import java.util.List;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.vaadin.addon.charts.model.MarkerSymbolEnum;
import com.vaadin.addon.charts.model.style.Color;
import com.vaadin.addon.charts.model.style.SolidColor;

import fi.csc.avaa.cbig.common.chart.GraphNames.CarnivoresAllSpeciesDisplayName;
import fi.csc.avaa.cbig.common.chart.GraphNames.CarnivoresFamilyDisplayName;
import fi.csc.avaa.cbig.common.chart.GraphNames.CarnivoresIUCNDisplayName;
import fi.csc.avaa.cbig.common.chart.GraphNames.CarnivoresSizeDisplayName;
import fi.csc.avaa.cbig.common.csv.CSVDataValueTypes.GPANGroupType;
import fi.csc.avaa.cbig.common.csv.CSVDataValueTypes.GPANVariantType;
import fi.csc.avaa.cbig.common.csv.CSVHeaders.CarnivoresAllSpeciesHeaders;
import fi.csc.avaa.cbig.common.csv.CSVHeaders.CarnivoresFutureFamilyHeaders;
import fi.csc.avaa.cbig.common.csv.CSVHeaders.CarnivoresFutureIUCNHeaders;
import fi.csc.avaa.cbig.common.csv.CSVHeaders.CarnivoresFutureSizeHeaders;
import fi.csc.avaa.cbig.common.csv.CSVHeaders.CarnivoresPresentFamilyHeaders;
import fi.csc.avaa.cbig.common.csv.CSVHeaders.CarnivoresPresentIUCNHeaders;
import fi.csc.avaa.cbig.common.csv.CSVHeaders.CarnivoresPresentSizeHeaders;

/**
 * @author jmlehtin
 *
 */
public final class ChartTools {
	
	private static final Log LOG = LogFactoryUtil.getLog(ChartTools.class);
	private static final String STR_ERROR = "Unknown chart type";
	private static final String GPAN_DEFAULT_X_AXIS = "Protected area (% of terrestrial world)";
	private static final String GPAN_DEFAULT_Y_AXIS = "Species range protected on average (%)";
	private static final String CV_DEFAULT_X_AXIS = "Percentage of terrestrial land protected";
	private static final String CV_DEFAULT_Y_AXIS = "Proportion of species range protected";

	public static final String CXT_PATH_TO_TOOLTIP_IMGS = "/cbig-portlet/ext_images/";
	
	private ChartTools() {
	}

	/**
	 * Get the title for a specific type of chart
	 * 
	 * @param chartType
	 * @return
	 */
	public static String getChartTitle(ChartTypes chartType) {
		String title = null;
		switch (chartType) {
		case GPAN_SPECIES:
			title = "Performance by species groups";
			break;
		case GPAN_IUCN:
			title = "Performance by IUCN Red List Categories";
			break;
		case GPAN_MEAN:
			title = "Overall performance";
			break;
		case CV_FUTURE_FAMILY:
			title = "Family (future)";
			break;
		case CV_FUTURE_IUCN:
			title = "IUCN (future)";
			break;
		case CV_FUTURE_SIZE:
			title = "Body size (future)";
			break;
		case CV_PRESENT_FAMILY:
			title = "Family (present)";
			break;
		case CV_PRESENT_IUCN:
			title = "IUCN (present)";
			break;
		case CV_PRESENT_SIZE:
			title = "Body size (present)";
			break;
		case CV_ALL_SPECIES:
			title = "All species";
			break;
		default:
			LOG.error(STR_ERROR);
		}
		return title;
	}
	
	/**
	 * Get the subtitle for a specific type of chart
	 * 
	 * @param chartType
	 * @return
	 */
	public static String getChartSubtitle(ChartTypes chartType) {
		String subtitle = null;
		switch (chartType) {
		case GPAN_SPECIES:
//			subtitle = "More info";
			break;
		case GPAN_IUCN:
//			subtitle = "More info";
			break;
		case GPAN_MEAN:
//			subtitle = "Choose variant type from the legend on the left to update the charts";
			break;
		case CV_ALL_SPECIES:
		case CV_FUTURE_FAMILY:
		case CV_FUTURE_IUCN:
		case CV_FUTURE_SIZE:
		case CV_PRESENT_FAMILY:
		case CV_PRESENT_IUCN:
		case CV_PRESENT_SIZE:
			break;
		default:
			LOG.error(STR_ERROR);
		}
		return subtitle;
	}
	
	/**
	 * Get the x-axis description for a specific type of chart
	 * 
	 * @param chartType
	 * @return
	 */
	public static String getChartXAxisName(ChartTypes chartType) {
		String name = null;
		switch (chartType) {
		case GPAN_SPECIES:
			name = GPAN_DEFAULT_X_AXIS;
			break;
		case GPAN_IUCN:
			name = GPAN_DEFAULT_X_AXIS;
			break;
		case GPAN_MEAN:
			name = GPAN_DEFAULT_X_AXIS;
			break;
		case CV_FUTURE_FAMILY:
			name = CV_DEFAULT_X_AXIS;
			break;
		case CV_FUTURE_IUCN:
			name = CV_DEFAULT_X_AXIS;
			break;
		case CV_FUTURE_SIZE:
			name = CV_DEFAULT_X_AXIS;
			break;
		case CV_PRESENT_FAMILY:
			name = CV_DEFAULT_X_AXIS;
			break;
		case CV_PRESENT_IUCN:
			name = CV_DEFAULT_X_AXIS;
			break;
		case CV_PRESENT_SIZE:
			name = CV_DEFAULT_X_AXIS;
			break;
		case CV_ALL_SPECIES:
			name = CV_DEFAULT_X_AXIS;
			break;
		default:
			LOG.error(STR_ERROR);
		}
		return name;
	}
	
	/**
	 * Get the y-axis description for a specific type of chart
	 * 
	 * @param chartType
	 * @return
	 */
	public static String getChartYAxisName(ChartTypes chartType) {
		String name = null;
		switch (chartType) {
		case GPAN_SPECIES:
			name = GPAN_DEFAULT_Y_AXIS;
			break;
		case GPAN_IUCN:
			name = GPAN_DEFAULT_Y_AXIS;
			break;
		case GPAN_MEAN:
			name = GPAN_DEFAULT_Y_AXIS;
			break;
		case CV_FUTURE_FAMILY:
			name = CV_DEFAULT_Y_AXIS;
			break;
		case CV_FUTURE_IUCN:
			name = CV_DEFAULT_Y_AXIS;
			break;
		case CV_FUTURE_SIZE:
			name = CV_DEFAULT_Y_AXIS;
			break;
		case CV_PRESENT_FAMILY:
			name = CV_DEFAULT_Y_AXIS;
			break;
		case CV_PRESENT_IUCN:
			name = CV_DEFAULT_Y_AXIS;
			break;
		case CV_PRESENT_SIZE:
			name = CV_DEFAULT_Y_AXIS;
			break;
		case CV_ALL_SPECIES:
			name = CV_DEFAULT_Y_AXIS;
			break;
		default:
			LOG.error(STR_ERROR);
		}
		return name;
	}
	
	/**
	 * Get the color for a specific graph
	 * 
	 * @param variant
	 * @param group
	 * @return
	 */
	public static Color getGPANGraphColor(GPANVariantType variant) {
		switch (variant) {
		case GLOBAL_2000:
			return new SolidColor("#291393");
		case GLOBAL_2040:
			return new SolidColor("#018573");
		case NATIONAL_2000:
			return new SolidColor("#D47D02");
		case NATIONAL_2040:
			return new SolidColor("#B6024B");
		default:
			LOG.error("Unknown variant type");
			return new SolidColor("#000000");
		}
	}
	
	public static Color getCarnivoresGraphColor(ChartTypes chartType, String graphId) {
		switch (chartType) {
		case CV_ALL_SPECIES:
			CarnivoresAllSpeciesDisplayName a = CarnivoresAllSpeciesDisplayName.fromName(CarnivoresAllSpeciesHeaders.fromString(graphId).name());
			switch (a) {
			case CURR:
				return new SolidColor("#291393");
			case FUT:
				return new SolidColor("#018573");
			default:
				return new SolidColor("#000000");
			}
		case CV_FUTURE_FAMILY:
			CarnivoresFamilyDisplayName b = CarnivoresFamilyDisplayName.fromName(CarnivoresFutureFamilyHeaders.fromString(graphId).name());
			switch (b) {
			case FELIDAE:
				return new SolidColor("#291393");
			case URSIDAE:
				return new SolidColor("#018573");
			case AILURIDAE:
				return new SolidColor("#D47D02");
			case CANIDAE:
				return new SolidColor("#B6024B");
			case MUSTELIDAE:
				return new SolidColor("#291393");
			case VIVERRIDAE:
				return new SolidColor("#018573");
			case OTARIDAEE:
				return new SolidColor("#D47D02");
			case HERPESTIDAE:
				return new SolidColor("#B6024B");
			case PROCYONIDAE:
				return new SolidColor("#291393");
			case MEMPHITIDAE:
				return new SolidColor("#018573");
			case HYAENIDAE:
				return new SolidColor("#D47D02");
			case EUPLERIDAE:
				return new SolidColor("#B6024B");
			case PHOCIDAE:
				return new SolidColor("#291393");
			case NANDINIIDAE:
				return new SolidColor("#018573");
			case OBOBENIDAE:
				return new SolidColor("#D47D02");
			case PRIONODONTIDAE:
				return new SolidColor("#B6024B");
			case DASYURIDAE:
				return new SolidColor("#291393");
			case MYRMECOBIDAE:
				return new SolidColor("#018573");
			default:
				return new SolidColor("#000000");
			}
		case CV_FUTURE_IUCN:
			CarnivoresIUCNDisplayName c = CarnivoresIUCNDisplayName.fromName(CarnivoresFutureIUCNHeaders.fromString(graphId).name());
			switch (c) {
			case LC:
				return new SolidColor("#291393");
			case NT:
				return new SolidColor("#018573");
			case VU:
				return new SolidColor("#D47D02");
			case EN:
				return new SolidColor("#B6024B");
			case CE:
				return new SolidColor("#291393");
			case DD:
				return new SolidColor("#018573");
			default:
				return new SolidColor("#000000");
			}
		case CV_FUTURE_SIZE:
			CarnivoresSizeDisplayName d = CarnivoresSizeDisplayName.fromName(CarnivoresFutureSizeHeaders.fromString(graphId).name());
			switch (d) {
			case LARGE:
				return new SolidColor("#291393");
			case SMALL:
				return new SolidColor("#018573");
			default:
				return new SolidColor("#000000");
			}
		case CV_PRESENT_FAMILY:
			CarnivoresFamilyDisplayName e = CarnivoresFamilyDisplayName.fromName(CarnivoresPresentFamilyHeaders.fromString(graphId).name());
			switch (e) {
			case FELIDAE:
				return new SolidColor("#291393");
			case URSIDAE:
				return new SolidColor("#018573");
			case AILURIDAE:
				return new SolidColor("#D47D02");
			case CANIDAE:
				return new SolidColor("#B6024B");
			case MUSTELIDAE:
				return new SolidColor("#291393");
			case VIVERRIDAE:
				return new SolidColor("#018573");
			case OTARIDAEE:
				return new SolidColor("#D47D02");
			case HERPESTIDAE:
				return new SolidColor("#B6024B");
			case PROCYONIDAE:
				return new SolidColor("#291393");
			case MEMPHITIDAE:
				return new SolidColor("#018573");
			case HYAENIDAE:
				return new SolidColor("#D47D02");
			case EUPLERIDAE:
				return new SolidColor("#B6024B");
			case PHOCIDAE:
				return new SolidColor("#291393");
			case NANDINIIDAE:
				return new SolidColor("#018573");
			case OBOBENIDAE:
				return new SolidColor("#D47D02");
			case PRIONODONTIDAE:
				return new SolidColor("#B6024B");
			case DASYURIDAE:
				return new SolidColor("#291393");
			case MYRMECOBIDAE:
				return new SolidColor("#018573");
			default:
				return new SolidColor("#000000");
			}
		case CV_PRESENT_IUCN:
			CarnivoresIUCNDisplayName f = CarnivoresIUCNDisplayName.fromName(CarnivoresPresentIUCNHeaders.fromString(graphId).name());
			switch (f) {
			case LC:
				return new SolidColor("#291393");
			case NT:
				return new SolidColor("#018573");
			case VU:
				return new SolidColor("#D47D02");
			case EN:
				return new SolidColor("#B6024B");
			case CE:
				return new SolidColor("#291393");
			case DD:
				return new SolidColor("#018573");
			default:
				return new SolidColor("#000000");
			}
		case CV_PRESENT_SIZE:
			CarnivoresSizeDisplayName g = CarnivoresSizeDisplayName.fromName(CarnivoresPresentSizeHeaders.fromString(graphId).name());
			switch (g) {
			case LARGE:
				return new SolidColor("#291393");
			case SMALL:
				return new SolidColor("#018573");
			default:
				return new SolidColor("#000000");
			}
		default:
			LOG.error("Invalid Carnivores chart type given: " + chartType);
			return new SolidColor("#000000");
		}
	}
	
	
	public static String getCarnivoresGraphDisplayName(ChartTypes chartType, String graphId) {
		switch (chartType) {
		case CV_ALL_SPECIES:
			return CarnivoresAllSpeciesDisplayName.fromName(CarnivoresAllSpeciesHeaders.fromString(graphId).name()).getValue();
		case CV_FUTURE_FAMILY:
			return CarnivoresFamilyDisplayName.fromName(CarnivoresFutureFamilyHeaders.fromString(graphId).name()).getValue();
		case CV_FUTURE_IUCN:
			return CarnivoresIUCNDisplayName.fromName(CarnivoresFutureIUCNHeaders.fromString(graphId).name()).getValue();
		case CV_FUTURE_SIZE:
			return CarnivoresSizeDisplayName.fromName(CarnivoresFutureSizeHeaders.fromString(graphId).name()).getValue();
		case CV_PRESENT_FAMILY:
			return CarnivoresFamilyDisplayName.fromName(CarnivoresPresentFamilyHeaders.fromString(graphId).name()).getValue();
		case CV_PRESENT_IUCN:
			return CarnivoresIUCNDisplayName.fromName(CarnivoresPresentIUCNHeaders.fromString(graphId).name()).getValue();
		case CV_PRESENT_SIZE:
			return CarnivoresSizeDisplayName.fromName(CarnivoresPresentSizeHeaders.fromString(graphId).name()).getValue();
		default:
			LOG.error("Invalid Carnivores chart type given: " + chartType);
			return null;
		}
	}

	/**
	 * Get the name of graph that is to be displayed e.g. in the legend of the chart
	 * 
	 * @param variant
	 * @param group
	 * @return
	 */
	public static String getGPANGraphDisplayName(GPANVariantType variant, GPANGroupType group) {
		if(group == null) {
			switch (variant) {
			case GLOBAL_2000:
				return GraphNames.GPANVariantDisplayName.GLOBAL_PRESENT.getValue();
			case GLOBAL_2040:
				return GraphNames.GPANVariantDisplayName.GLOBAL_FUTURE.getValue();
			case NATIONAL_2000:
				return GraphNames.GPANVariantDisplayName.NATIONAL_PRESENT.getValue();
			case NATIONAL_2040:
				return GraphNames.GPANVariantDisplayName.NATIONAL_FUTURE.getValue();
			default:
				LOG.error("Unknown variant type");
				return "unknown";
			}
		} else {
			switch (group) {
			case AMPHIBIANS:
				return GraphNames.GPANGroupDisplayName.AMPHIBIANS.getValue();
			case BIRDS:
				return GraphNames.GPANGroupDisplayName.BIRDS.getValue();
			case CR:
				return GraphNames.GPANGroupDisplayName.CR.getValue();
			case EN:
				return GraphNames.GPANGroupDisplayName.EN.getValue();
			case ENDANGERED:
				return GraphNames.GPANGroupDisplayName.ENDANGERED.getValue();
			case LC:
				return GraphNames.GPANGroupDisplayName.LC.getValue();
			case MAMMALS:
				return GraphNames.GPANGroupDisplayName.MAMMALS.getValue();
			case NT:
				return GraphNames.GPANGroupDisplayName.NT.getValue();
			case REPTILES:
				return GraphNames.GPANGroupDisplayName.REPTILES.getValue();
			case VU:
				return GraphNames.GPANGroupDisplayName.VU.getValue();
			default:
				return "unknown";
			}
		}
	}
	
	/**
	 * Resolve GroupCSVType from given GroupDisplayName. GroupCSVType is part of graphId.
	 * 
	 * @param groupDispName
	 * @return
	 */
	public static GPANGroupType getGPANGroupCSVTypeFromDisplayName(GraphNames.GPANGroupDisplayName groupDispName) {
		switch (groupDispName) {
		case AMPHIBIANS:
			return GPANGroupType.AMPHIBIANS;
		case BIRDS:
			return GPANGroupType.BIRDS;
		case CR:
			return GPANGroupType.CR;
		case EN:
			return GPANGroupType.EN;
		case ENDANGERED:
			return GPANGroupType.ENDANGERED;
		case LC:
			return GPANGroupType.LC;
		case MAMMALS:
			return GPANGroupType.MAMMALS;
		case NT:
			return GPANGroupType.NT;
		case REPTILES:
			return GPANGroupType.REPTILES;
		case VU:
			return GPANGroupType.VU;
		default:
			return null;
		}
	}
	
	/**
	 * Get variant type (as in CSV) from variant display name
	 * 
	 * @param variantDispName
	 * @return
	 */
	public static GPANVariantType getGPANVariantCSVTypeFromDisplayName(GraphNames.GPANVariantDisplayName variantDispName) {
		switch (variantDispName) {
		case GLOBAL_FUTURE:
			return GPANVariantType.GLOBAL_2040;
		case GLOBAL_PRESENT:
			return GPANVariantType.GLOBAL_2000;
		case NATIONAL_FUTURE:
			return GPANVariantType.NATIONAL_2040;
		case NATIONAL_PRESENT:
			return GPANVariantType.NATIONAL_2000;
		default:
			return null;
		}
	}

	/**
	 * 
	 * Get the symbol to be used for graphs of specific variant
	 * 
	 * @param variant
	 * @return
	 */
	public static MarkerSymbolEnum getGPANLineSymbol(GPANVariantType variant) {
		switch (variant) {
		case GLOBAL_2000:
			return MarkerSymbolEnum.CIRCLE;
		case GLOBAL_2040:
			return MarkerSymbolEnum.DIAMOND;
		case NATIONAL_2000:
			return MarkerSymbolEnum.SQUARE;
		case NATIONAL_2040:
			return MarkerSymbolEnum.TRIANGLE;
		default:
			LOG.error("Unknown variant type");
			return MarkerSymbolEnum.CIRCLE;
		}
	}


	/**
	 * Extract the variant type (as in CSV) from graph ID.
	 * 
	 * @param graphId
	 *            String indicating the classification of the row data. E.g.
	 *            global2000_mammal.
	 * @return
	 * 		VariantCSVType
	 */
	public static GPANVariantType getGPANVariantCSVTypeFromGraphId(String graphId) {
		String variantStr = null;
		if (graphId.contains("_")) {
			variantStr = graphId.substring(0, graphId.indexOf("_"));
		} else {
			variantStr = graphId;
		}
		return GPANVariantType.fromString(variantStr);
	}


	/**
	 * Extract the group type (as in CSV) from graph ID.
	 * 
	 * @param graphId
	 *            String indicating the classification of the row data. E.g.
	 *            global2000_mammal.
	 * @return
	 * 		GroupCSVType
	 */
	/**
	 * @param graphId
	 * @return
	 */
	public static GPANGroupType getGPANGroupCSVTypeFromGraphId(String graphId) {
		GPANGroupType group = null;
		if (graphId.contains("_")) {
			group = GPANGroupType.fromString(graphId.substring(graphId
					.indexOf("_") + 1));
		}
		return group;
	}
	
	public static String getCarnivoresControlTooltip(ChartTypes chartType, String graphId) {
		String imgName = null;
		String name = null;
		switch (chartType) {
		case CV_FUTURE_FAMILY:
			CarnivoresFamilyDisplayName b = CarnivoresFamilyDisplayName.fromName(CarnivoresFutureFamilyHeaders.fromString(graphId).name());
			switch (b) {
			case FELIDAE:
				name = "Felidae";
				imgName = "Felidae_jaguar.jpeg";
				break;
			case URSIDAE:
				name = "Ursidae";
				imgName = "Ursidae_giant panda.jpeg";
				break;
			case AILURIDAE:
				name = "Ailuridae";
				imgName = "Ailuridae_red panda.jpeg";
				break;
			case CANIDAE:
				name = "Canidae";
				imgName = "Canidae_Ethiopian wolf.jpeg";
				break;
			case MUSTELIDAE:
				name = "Mustelidae";
				imgName = "Mustelidae_Cape clawless otter.jpeg";
				break;
			case VIVERRIDAE:
				name = "Viverridae";
				imgName = "Viverridae_binturong.jpeg";
				break;
			case OTARIDAEE:
				name = "Otaridae";
				imgName = "Otaridae_Stellar sea lion.jpeg";
				break;
			case HERPESTIDAE:
				name = "Herpestidae";
				imgName = "Herpestidae_Indian grey mongoose.jpeg";
				break;
			case PROCYONIDAE:
				name = "Procyonidae";
				imgName = "Procyonidae_crab-eating raccoon.jpeg";
				break;
			case MEMPHITIDAE:
				name = "Mephitidae";
				imgName = "Mephitidae_sunda stink badger.jpeg";
				break;
			case HYAENIDAE:
				name = "Hyaenidae";
				imgName = "Hyaenidae_striped hyeana.jpeg";
				break;
			case EUPLERIDAE:
				name = "Eupleridae";
				imgName = "Eupleridae_fosa.jpeg";
				break;
			case PHOCIDAE:
				name = "Phocidae";
				imgName = "Phocidae_Saimaa seal.jpeg";
				break;
			case NANDINIIDAE:
				name = "Nandiniidae";
				imgName = "Nandiniidae_African palm civet.jpeg";
				break;
			case OBOBENIDAE:
				name = "Obobenidae";
				imgName = "Obobenidae_walrus.jpeg";
				break;
			case PRIONODONTIDAE:
				name = "Prionodontidae";
				imgName = "Prionodontidae_banded linsang.jpeg";
				break;
			case DASYURIDAE:
				name = "Dasyuridae";
				imgName = "Dasyuridae_Tasmanian devil.jpeg";
				break;
			case MYRMECOBIDAE:
				name = "Myrmecobiidae";
				imgName = "Myrmecobiidae_numbat.jpeg";
				break;
			default:
				return null;
			}
			break;
		case CV_PRESENT_FAMILY:
			CarnivoresFamilyDisplayName e = CarnivoresFamilyDisplayName.fromName(CarnivoresPresentFamilyHeaders.fromString(graphId).name());
			switch (e) {
			case FELIDAE:
				name = "Felidae";
				imgName = "Felidae_jaguar.jpeg";
				break;
			case URSIDAE:
				name = "Ursidae";
				imgName = "Ursidae_giant panda.jpeg";
				break;
			case AILURIDAE:
				name = "Ailuridae";
				imgName = "Ailuridae_red panda.jpeg";
				break;
			case CANIDAE:
				name = "Canidae";
				imgName = "Canidae_Ethiopian wolf.jpeg";
				break;
			case MUSTELIDAE:
				name = "Mustelidae";
				imgName = "Mustelidae_Cape clawless otter.jpeg";
				break;
			case VIVERRIDAE:
				name = "Viverridae";
				imgName = "Viverridae_binturong.jpeg";
				break;
			case OTARIDAEE:
				name = "Otaridae";
				imgName = "Otaridae_Stellar sea lion.jpeg";
				break;
			case HERPESTIDAE:
				name = "Herpestidae";
				imgName = "Herpestidae_Indian grey mongoose.jpeg";
				break;
			case PROCYONIDAE:
				name = "Procyonidae";
				imgName = "Procyonidae_crab-eating raccoon.jpeg";
				break;
			case MEMPHITIDAE:
				name = "Mephitidae";
				imgName = "Mephitidae_sunda stink badger.jpeg";
				break;
			case HYAENIDAE:
				name = "Hyaenidae";
				imgName = "Hyaenidae_striped hyeana.jpeg";
				break;
			case EUPLERIDAE:
				name = "Eupleridae";
				imgName = "Eupleridae_fosa.jpeg";
				break;
			case PHOCIDAE:
				name = "Phocidae";
				imgName = "Phocidae_Saimaa seal.jpeg";
				break;
			case NANDINIIDAE:
				name = "Nandiniidae";
				imgName = "Nandiniidae_African palm civet.jpeg";
				break;
			case OBOBENIDAE:
				name = "Obobenidae";
				imgName = "Obobenidae_walrus.jpeg";
				break;
			case PRIONODONTIDAE:
				name = "Prionodontidae";
				imgName = "Prionodontidae_banded linsang.jpeg";
				break;
			case DASYURIDAE:
				name = "Dasyuridae";
				imgName = "Dasyuridae_Tasmanian devil.jpeg";
				break;
			case MYRMECOBIDAE:
				name = "Myrmecobiidae";
				imgName = "Myrmecobiidae_numbat.jpeg";
				break;
			default:
				return null;
			}
			break;
		case CV_ALL_SPECIES:
			return null;
		case CV_PRESENT_IUCN:
			return null;
		case CV_FUTURE_IUCN:
			return null;
		case CV_PRESENT_SIZE:
			return null;
		case CV_FUTURE_SIZE:
			return null;
		default:
			LOG.error("Invalid Carnivores chart type given: " + chartType);
		}
		if(imgName != null) {
			return "<div style=\"margin-bottom:5px;\" class=\"tooltip-name\">" + name + "</div><img class=\"tooltip-img\" src=\"" + CXT_PATH_TO_TOOLTIP_IMGS + imgName + "\"/>";
		}
		return null;
	}
	
	public static List<String> getAllTooltipUrls() {
		List<String> urls = new ArrayList<>();
		urls.add(CXT_PATH_TO_TOOLTIP_IMGS + "Ailuridae_red panda.jpeg");
		urls.add(CXT_PATH_TO_TOOLTIP_IMGS + "Canidae_Ethiopian wolf.jpeg");
		urls.add(CXT_PATH_TO_TOOLTIP_IMGS + "Dasyuridae_Tasmanian devil.jpeg");
		urls.add(CXT_PATH_TO_TOOLTIP_IMGS + "Eupleridae_fosa.jpeg");
		urls.add(CXT_PATH_TO_TOOLTIP_IMGS + "Felidae_jaguar.jpeg");
		urls.add(CXT_PATH_TO_TOOLTIP_IMGS + "Herpestidae_Indian grey mongoose.jpeg");
		urls.add(CXT_PATH_TO_TOOLTIP_IMGS + "Hyaenidae_striped hyeana.jpeg");
		urls.add(CXT_PATH_TO_TOOLTIP_IMGS + "Mephitidae_sunda stink badger.jpeg");
		urls.add(CXT_PATH_TO_TOOLTIP_IMGS + "Mustelidae_Cape clawless otter.jpeg");
		urls.add(CXT_PATH_TO_TOOLTIP_IMGS + "Myrmecobiidae_numbat.jpeg");
		urls.add(CXT_PATH_TO_TOOLTIP_IMGS + "Nandiniidae_African palm civet.jpeg");
		urls.add(CXT_PATH_TO_TOOLTIP_IMGS + "Obobenidae_walrus.jpeg");
		urls.add(CXT_PATH_TO_TOOLTIP_IMGS + "Otaridae_Stellar sea lion.jpeg");
		urls.add(CXT_PATH_TO_TOOLTIP_IMGS + "Phocidae_Saimaa seal.jpeg");
		urls.add(CXT_PATH_TO_TOOLTIP_IMGS + "Prionodontidae_banded linsang.jpeg");
		urls.add(CXT_PATH_TO_TOOLTIP_IMGS + "Procyonidae_crab-eating raccoon.jpeg");
		urls.add(CXT_PATH_TO_TOOLTIP_IMGS + "Ursidae_giant panda.jpeg");
		urls.add(CXT_PATH_TO_TOOLTIP_IMGS + "Viverridae_binturong.jpeg");
		return urls;
	}
	
	
	
}
