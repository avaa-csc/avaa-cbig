package fi.csc.avaa.cbig.common.csv;

import java.util.ArrayList;
import java.util.List;

import fi.csc.avaa.tools.StringTools;





/**
 * Names for the columns (or CSV row items) of CSV files
 * 
 * @author jmlehtin
 *
 */
public class CSVHeaders {
	
	private static final String STR_PR_REM = "pr_rem";
	private static final String STR_VARIANT = "variant";
	private static final String STR_GROUP = "group";
	private static final String STR_VALUE = "value";
	private static final String STR_PROP_LOST = "Prop_lost";
	private static final String STR_CURR = "curr";
	private static final String STR_FUT = "fut";
	private static final String STR_LARGE = "median1";
	private static final String STR_SMALL = "median2";
	private static final String STR_LC = "median1";
	private static final String STR_NT = "median2";
	private static final String STR_VU = "median3";
	private static final String STR_EN = "median4";
	private static final String STR_CE = "median5";
	private static final String STR_DD = "median6";
	private static final String STR_FELIDAE = "Felidae";
	private static final String STR_URSIDAE = "Ursidae";
	private static final String STR_AILURIDAE = "Ailuridae";
	private static final String STR_CANIDAE = "Canidae";
	private static final String STR_MUSTELIDAE = "Mustelidae";
	private static final String STR_VIVERRIDAE = "Viverridae";
	private static final String STR_OTARIDAEE = "Otaridaee";
	private static final String STR_HERPESTIDAE = "Herpestidae";
	private static final String STR_PROCYONIDAE = "Procyonidae";
	private static final String STR_MEMPHITIDAE = "Mephitidae";
	private static final String STR_HYAENIDAE = "Hyaenidae";
	private static final String STR_EUPLERIDAE = "Eupleridae";
	private static final String STR_PHOCIDAE = "Phocidae";
	private static final String STR_NANDINIIDAE = "Nandiniidae";
	private static final String STR_OBOBENIDAE = "Obobenidae";
	private static final String STR_PRIONODONTIDAE = "Prionodontidae";
	private static final String STR_DASYURIDAE = "Dasyuridae";
	private static final String STR_MYRMECOBIDAE = "Myrmecobidae";
	
	
	public enum GPANCurves2Headers {
		F_REM(STR_PR_REM),
		GROUP(STR_GROUP),
		VARIANT(STR_VARIANT),
		VALUE(STR_VALUE);
		
		String value;
		
		private GPANCurves2Headers(String value) {
			this.value = value;
		}
		
		public String getValue() {
			return value;
		}
		
		public static String[] getValues() {
			GPANCurves2Headers[] headers = values();
		    String[] values = new String[headers.length];

		    for (int i = 0; i < headers.length; i++) {
		    	values[i] = headers[i].getValue();
		    }
		    return values;
		}
	}
	
	public enum GPANMeanHeaders {
		F_REM(STR_PR_REM),
		VARIANT(STR_VARIANT),
		VALUE(STR_VALUE);
		
		String value;
		
		private GPANMeanHeaders(String value) {
			this.value = value;
		}
		
		public String getValue() {
			return value;
		}	
		
		public static String[] getValues() {
			GPANMeanHeaders[] headers = values();
		    String[] values = new String[headers.length];

		    for (int i = 0; i < headers.length; i++) {
		    	values[i] = headers[i].getValue();
		    }
		    return values;
		}
	}
	
	public enum CarnivoresPresentFamilyHeaders {
		PROP_LOST(STR_PROP_LOST),
		FELIDAE(STR_FELIDAE),
		URSIDAE(STR_URSIDAE),
		AILURIDAE(STR_AILURIDAE),
		CANIDAE(STR_CANIDAE),
		MUSTELIDAE(STR_MUSTELIDAE),
		VIVERRIDAE(STR_VIVERRIDAE),
		OTARIDAEE(STR_OTARIDAEE),
		HERPESTIDAE(STR_HERPESTIDAE),
		PROCYONIDAE(STR_PROCYONIDAE),
		MEMPHITIDAE(STR_MEMPHITIDAE),
		HYAENIDAE(STR_HYAENIDAE),
		EUPLERIDAE(STR_EUPLERIDAE),
		PHOCIDAE(STR_PHOCIDAE),
		NANDINIIDAE(STR_NANDINIIDAE),
		OBOBENIDAE(STR_OBOBENIDAE),
		PRIONODONTIDAE(STR_PRIONODONTIDAE),
		DASYURIDAE(STR_DASYURIDAE),
		MYRMECOBIDAE(STR_MYRMECOBIDAE);
		
		String value;
		
		private CarnivoresPresentFamilyHeaders(String value) {
			this.value = value;
		}
		
		public String getValue() {
			return value;
		}
		
		public static String[] getValues() {
			CarnivoresPresentFamilyHeaders[] headers = values();
		    String[] values = new String[headers.length];

		    for (int i = 0; i < headers.length; i++) {
		    	values[i] = headers[i].getValue();
		    }
		    return values;
		}
		
		public static CarnivoresPresentFamilyHeaders fromString(String str) {
			if(StringTools.isEmptyOrNull(str)) {
				return null;
			}
			for(CarnivoresPresentFamilyHeaders type : CarnivoresPresentFamilyHeaders.values()) {
				String groupVal = type.getValue();
				if(groupVal.equals(str)) {
					return type;
				}
			}
			return null;
		}
		
		public static List<String> getGraphIds() {
			List<String> ids = new ArrayList<>();
			for(String val : getValues()) {
				if(!PROP_LOST.getValue().equals(val)) {
					ids.add(val);
				}
			}
			return ids;
		}
		
	}
	
	public enum CarnivoresFutureFamilyHeaders {
		PROP_LOST(STR_PROP_LOST),
		FELIDAE(STR_FELIDAE),
		URSIDAE(STR_URSIDAE),
		AILURIDAE(STR_AILURIDAE),
		CANIDAE(STR_CANIDAE),
		MUSTELIDAE(STR_MUSTELIDAE),
		VIVERRIDAE(STR_VIVERRIDAE),
		OTARIDAEE(STR_OTARIDAEE),
		HERPESTIDAE(STR_HERPESTIDAE),
		PROCYONIDAE(STR_PROCYONIDAE),
		MEMPHITIDAE(STR_MEMPHITIDAE),
		HYAENIDAE(STR_HYAENIDAE),
		EUPLERIDAE(STR_EUPLERIDAE),
		PHOCIDAE(STR_PHOCIDAE),
		NANDINIIDAE(STR_NANDINIIDAE),
		OBOBENIDAE(STR_OBOBENIDAE),
		PRIONODONTIDAE(STR_PRIONODONTIDAE),
		DASYURIDAE(STR_DASYURIDAE),
		MYRMECOBIDAE(STR_MYRMECOBIDAE);
		
		String value;
		
		private CarnivoresFutureFamilyHeaders(String value) {
			this.value = value;
		}
		
		public String getValue() {
			return value;
		}
		
		public static String[] getValues() {
			CarnivoresFutureFamilyHeaders[] headers = values();
		    String[] values = new String[headers.length];

		    for (int i = 0; i < headers.length; i++) {
		    	values[i] = headers[i].getValue();
		    }
		    return values;
		}
		
		public static CarnivoresFutureFamilyHeaders fromString(String str) {
			if(StringTools.isEmptyOrNull(str)) {
				return null;
			}
			for(CarnivoresFutureFamilyHeaders type : CarnivoresFutureFamilyHeaders.values()) {
				String groupVal = type.getValue();
				if(groupVal.equals(str)) {
					return type;
				}
			}
			return null;
		}
		
		public static List<String> getGraphIds() {
			List<String> ids = new ArrayList<>();
			for(String val : getValues()) {
				if(!PROP_LOST.getValue().equals(val)) {
					ids.add(val);
				}
			}
			return ids;
		}
	}
	
	public enum CarnivoresPresentIUCNHeaders {
		PROP_LOST(STR_PROP_LOST),
		LC(STR_LC),
		NT(STR_NT),
		VU(STR_VU),
		EN(STR_EN),
		CE(STR_CE),
		DD(STR_DD);
		
		String value;
		
		private CarnivoresPresentIUCNHeaders(String value) {
			this.value = value;
		}
		
		public String getValue() {
			return value;
		}	
		
		public static String[] getValues() {
			CarnivoresPresentIUCNHeaders[] headers = values();
		    String[] values = new String[headers.length];

		    for (int i = 0; i < headers.length; i++) {
		    	values[i] = headers[i].getValue();
		    }
		    return values;
		}
		
		public static CarnivoresPresentIUCNHeaders fromString(String str) {
			if(StringTools.isEmptyOrNull(str)) {
				return null;
			}
			for(CarnivoresPresentIUCNHeaders type : CarnivoresPresentIUCNHeaders.values()) {
				String groupVal = type.getValue();
				if(groupVal.equals(str)) {
					return type;
				}
			}
			return null;
		}
		
		public static List<String> getGraphIds() {
			List<String> ids = new ArrayList<>();
			for(String val : getValues()) {
				if(!PROP_LOST.getValue().equals(val)) {
					ids.add(val);
				}
			}
			return ids;
		}
	}

	public enum CarnivoresFutureIUCNHeaders {
		PROP_LOST(STR_PROP_LOST),
		LC(STR_LC),
		NT(STR_NT),
		VU(STR_VU),
		EN(STR_EN),
		CE(STR_CE),
		DD(STR_DD);
		
		
		String value;
		
		private CarnivoresFutureIUCNHeaders(String value) {
			this.value = value;
		}
		
		public String getValue() {
			return value;
		}	
		
		public static String[] getValues() {
			CarnivoresFutureIUCNHeaders[] headers = values();
		    String[] values = new String[headers.length];

		    for (int i = 0; i < headers.length; i++) {
		    	values[i] = headers[i].getValue();
		    }
		    return values;
		}
		
		public static CarnivoresFutureIUCNHeaders fromString(String str) {
			if(StringTools.isEmptyOrNull(str)) {
				return null;
			}
			for(CarnivoresFutureIUCNHeaders type : CarnivoresFutureIUCNHeaders.values()) {
				String groupVal = type.getValue();
				if(groupVal.equals(str)) {
					return type;
				}
			}
			return null;
		}
		
		public static List<String> getGraphIds() {
			List<String> ids = new ArrayList<>();
			for(String val : getValues()) {
				if(!PROP_LOST.getValue().equals(val)) {
					ids.add(val);
				}
			}
			return ids;
		}
		
	}
	
	public enum CarnivoresPresentSizeHeaders {
		PROP_LOST(STR_PROP_LOST),
		LARGE(STR_LARGE),
		SMALL(STR_SMALL);
		
		String value;
		
		private CarnivoresPresentSizeHeaders(String value) {
			this.value = value;
		}
		
		public String getValue() {
			return value;
		}	
		
		public static String[] getValues() {
			CarnivoresPresentSizeHeaders[] headers = values();
		    String[] values = new String[headers.length];

		    for (int i = 0; i < headers.length; i++) {
		    	values[i] = headers[i].getValue();
		    }
		    return values;
		}
		
		public static CarnivoresPresentSizeHeaders fromString(String str) {
			if(StringTools.isEmptyOrNull(str)) {
				return null;
			}
			for(CarnivoresPresentSizeHeaders type : CarnivoresPresentSizeHeaders.values()) {
				String groupVal = type.getValue();
				if(groupVal.equals(str)) {
					return type;
				}
			}
			return null;
		}
		
		public static List<String> getGraphIds() {
			List<String> ids = new ArrayList<>();
			for(String val : getValues()) {
				if(!PROP_LOST.getValue().equals(val)) {
					ids.add(val);
				}
			}
			return ids;
		}
		
	}
	
	public enum CarnivoresFutureSizeHeaders {
		PROP_LOST(STR_PROP_LOST),
		LARGE(STR_LARGE),
		SMALL(STR_SMALL);
		
		String value;
		
		private CarnivoresFutureSizeHeaders(String value) {
			this.value = value;
		}
		
		public String getValue() {
			return value;
		}	
		
		public static String[] getValues() {
			CarnivoresFutureSizeHeaders[] headers = values();
		    String[] values = new String[headers.length];

		    for (int i = 0; i < headers.length; i++) {
		    	values[i] = headers[i].getValue();
		    }
		    return values;
		}
		
		public static CarnivoresFutureSizeHeaders fromString(String str) {
			if(StringTools.isEmptyOrNull(str)) {
				return null;
			}
			for(CarnivoresFutureSizeHeaders type : CarnivoresFutureSizeHeaders.values()) {
				String groupVal = type.getValue();
				if(groupVal.equals(str)) {
					return type;
				}
			}
			return null;
		}
		
		public static List<String> getGraphIds() {
			List<String> ids = new ArrayList<>();
			for(String val : getValues()) {
				if(!PROP_LOST.getValue().equals(val)) {
					ids.add(val);
				}
			}
			return ids;
		}
	}
	
	public enum CarnivoresAllSpeciesHeaders {
		PROP_LOST(STR_PROP_LOST),
		CURR(STR_CURR),
		FUT(STR_FUT);
		
		String value;
		
		private CarnivoresAllSpeciesHeaders(String value) {
			this.value = value;
		}
		
		public String getValue() {
			return value;
		}	
		public static String[] getValues() {
			CarnivoresAllSpeciesHeaders[] headers = values();
		    String[] values = new String[headers.length];

		    for (int i = 0; i < headers.length; i++) {
		    	values[i] = headers[i].getValue();
		    }
		    return values;
		}
		
		public static CarnivoresAllSpeciesHeaders fromString(String str) {
			if(StringTools.isEmptyOrNull(str)) {
				return null;
			}
			for(CarnivoresAllSpeciesHeaders type : CarnivoresAllSpeciesHeaders.values()) {
				String groupVal = type.getValue();
				if(groupVal.equals(str)) {
					return type;
				}
			}
			return null;
		}
		
		public static List<String> getGraphIds() {
			List<String> ids = new ArrayList<>();
			for(String val : getValues()) {
				if(!PROP_LOST.getValue().equals(val)) {
					ids.add(val);
				}
			}
			return ids;
		}
		
	}

	/**
	 * Get a list of strings containing the names of the CSV entries (header names)
	 * 
	 * @param csvType
	 * @return
	 */
	public static String[] getCSVHeaders(CSVType csvType) {
		switch (csvType) {
		case CURVES2:
			return GPANCurves2Headers.getValues();
		case MEAN_CURVES:
			return GPANMeanHeaders.getValues();
		case ALL_SPECIES:
			return CarnivoresAllSpeciesHeaders.getValues();
		case FUTURE_FAMILY:
			return CarnivoresFutureFamilyHeaders.getValues();
		case FUTURE_IUCN:
			return CarnivoresFutureIUCNHeaders.getValues();
		case FUTURE_SIZE:
			return CarnivoresFutureSizeHeaders.getValues();
		case PRESENT_FAMILY:
			return CarnivoresPresentFamilyHeaders.getValues();
		case PRESENT_IUCN:
			return CarnivoresPresentIUCNHeaders.getValues();
		case PRESENT_SIZE:
			return CarnivoresPresentSizeHeaders.getValues();
		default:
			return null;
		}
	}
	
}
