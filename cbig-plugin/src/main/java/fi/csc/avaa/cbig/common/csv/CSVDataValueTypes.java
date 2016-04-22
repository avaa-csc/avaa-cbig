package fi.csc.avaa.cbig.common.csv;

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

import fi.csc.avaa.tools.StringTools;

/**
 * @author jmlehtin
 *
 */
public class CSVDataValueTypes {

	public enum GPANVariantType {
	
		GLOBAL_2000("global2000"),
		GLOBAL_2040("global2040"),
		NATIONAL_2000("national2000"),
		NATIONAL_2040("national2040");
		
		String value;
		
		private GPANVariantType(String value) {
			this.value = value;
		}
		
		public String getValue() {
			return value;
		}	
		
		public static String[] getValues() {
			GPANVariantType[] headers = values();
		    String[] values = new String[headers.length];
	
		    for (int i = 0; i < headers.length; i++) {
		    	values[i] = headers[i].getValue();
		    }
		    return values;
		}
		
		public static GPANVariantType fromString(String str) {
			if(StringTools.isEmptyOrNull(str)) {
				return null;
			}
			for(GPANVariantType variant : GPANVariantType.values()) {
				String variantVal = variant.getValue();
				if(variantVal.equals(str)) {
					return variant;
				}
			}
			return null;
		}
	}
	
	public enum GPANGroupType {
		
		AMPHIBIANS("amphibians"),
		BIRDS("birds"),
		MAMMALS("mammals"),
		REPTILES("reptiles"),
		CR("CR"),
		EN("EN"),
		VU("VU"),
		NT("NT"),
		LC("LC"),
		ENDANGERED("endangered");
		
		String value;
		
		private GPANGroupType(String value) {
			this.value = value;
		}
		
		public String getValue() {
			return value;
		}	
		
		public static String[] getValues() {
			GPANGroupType[] headers = values();
		    String[] values = new String[headers.length];
	
		    for (int i = 0; i < headers.length; i++) {
		    	values[i] = headers[i].getValue();
		    }
		    return values;
		}
		
		public static GPANGroupType fromString(String str) {
			if(StringTools.isEmptyOrNull(str)) {
				return null;
			}
			for(GPANGroupType groupType : GPANGroupType.values()) {
				String groupVal = groupType.getValue();
				if(groupVal.equals(str)) {
					return groupType;
				}
			}
			return null;
		}
		
		/**
		 * Get a list of group items related to species chart
		 * 
		 * @return
		 */
		public static Set<String> getSpeciesCSVGroupsAsString() {
			Set<String> retVal = new TreeSet<String>();
			retVal.addAll(Arrays.asList(GPANGroupType.AMPHIBIANS.getValue(),GPANGroupType.BIRDS.getValue(), GPANGroupType.MAMMALS.getValue(), GPANGroupType.REPTILES.getValue()));
			return retVal;
		}
		
		/**
		 * Get a list of group items related to IUCN chart
		 * @return
		 */
		public static Set<String> getIUCNCSVGroupsAsString() {
			Set<String> retVal = new TreeSet<String>();
			retVal.addAll(Arrays.asList(GPANGroupType.CR.getValue(),GPANGroupType.EN.getValue(), GPANGroupType.VU.getValue(), GPANGroupType.NT.getValue(),GPANGroupType.LC.getValue(),GPANGroupType.ENDANGERED.getValue()));
			return retVal;
		}
		
		public static Set<GPANGroupType> getSpeciesCSVGroups() {
			Set<GPANGroupType> retVal = new TreeSet<GPANGroupType>();
			retVal.addAll(Arrays.asList(GPANGroupType.AMPHIBIANS,GPANGroupType.BIRDS, GPANGroupType.MAMMALS, GPANGroupType.REPTILES));
			return retVal;
		}
		
		/**
		 * Get a list of group items related to IUCN chart
		 * @return
		 */
		public static Set<GPANGroupType> getIUCNCSVGroups() {
			Set<GPANGroupType> retVal = new TreeSet<GPANGroupType>();
			retVal.addAll(Arrays.asList(GPANGroupType.CR, GPANGroupType.EN, GPANGroupType.VU, GPANGroupType.NT,GPANGroupType.LC,GPANGroupType.ENDANGERED));
			return retVal;
		}
		
	}
/*	
	public enum CarnivoresFamilyType {
		
		AMPHIBIANS("amphibians"),
		BIRDS("birds"),
		MAMMALS("mammals"),
		REPTILES("reptiles"),
		CR("CR"),
		EN("EN"),
		VU("VU"),
		NT("NT"),
		LC("LC"),
		ENDANGERED("endangered");
		
		String value;
		
		private CarnivoresFamilyType(String value) {
			this.value = value;
		}
		
		public String getValue() {
			return value;
		}	
		
		public static String[] getValues() {
			CarnivoresFamilyType[] headers = values();
		    String[] values = new String[headers.length];
	
		    for (int i = 0; i < headers.length; i++) {
		    	values[i] = headers[i].getValue();
		    }
		    return values;
		}
		
		public static CarnivoresFamilyType fromString(String str) {
			if(StringTools.isEmpty(str)) {
				return null;
			}
			for(CarnivoresFamilyType groupType : CarnivoresFamilyType.values()) {
				String groupVal = groupType.getValue();
				if(groupVal.equals(str)) {
					return groupType;
				}
			}
			return null;
		}
	}
	
	public enum CarnivoresIUCNType {
		
		AMPHIBIANS("amphibians"),
		BIRDS("birds"),
		MAMMALS("mammals"),
		REPTILES("reptiles"),
		CR("CR"),
		EN("EN"),
		VU("VU"),
		NT("NT"),
		LC("LC"),
		ENDANGERED("endangered");
		
		String value;
		
		private CarnivoresIUCNType(String value) {
			this.value = value;
		}
		
		public String getValue() {
			return value;
		}	
		
		public static String[] getValues() {
			CarnivoresIUCNType[] headers = values();
		    String[] values = new String[headers.length];
	
		    for (int i = 0; i < headers.length; i++) {
		    	values[i] = headers[i].getValue();
		    }
		    return values;
		}
		
		public static CarnivoresIUCNType fromString(String str) {
			if(StringTools.isEmpty(str)) {
				return null;
			}
			for(CarnivoresIUCNType groupType : CarnivoresIUCNType.values()) {
				String groupVal = groupType.getValue();
				if(groupVal.equals(str)) {
					return groupType;
				}
			}
			return null;
		}
	}
	
	
	public enum CarnivoresBodySizeType {
		
		AMPHIBIANS("amphibians"),
		BIRDS("birds"),
		MAMMALS("mammals"),
		REPTILES("reptiles"),
		CR("CR"),
		EN("EN"),
		VU("VU"),
		NT("NT"),
		LC("LC"),
		ENDANGERED("endangered");
		
		String value;
		
		private CarnivoresBodySizeType(String value) {
			this.value = value;
		}
		
		public String getValue() {
			return value;
		}	
		
		public static String[] getValues() {
			CarnivoresBodySizeType[] headers = values();
		    String[] values = new String[headers.length];
	
		    for (int i = 0; i < headers.length; i++) {
		    	values[i] = headers[i].getValue();
		    }
		    return values;
		}
		
		public static CarnivoresBodySizeType fromString(String str) {
			if(StringTools.isEmpty(str)) {
				return null;
			}
			for(CarnivoresBodySizeType groupType : CarnivoresBodySizeType.values()) {
				String groupVal = groupType.getValue();
				if(groupVal.equals(str)) {
					return groupType;
				}
			}
			return null;
		}
	}
	*/
}
