package fi.csc.avaa.cbig.common.chart;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import fi.csc.avaa.tools.StringTools;

/**
 * @author jmlehtin
 *
 */
public class GraphNames {

	public enum GPANVariantDisplayName {
		GLOBAL_PRESENT("Global present"),
		GLOBAL_FUTURE("Global future (2040)"),
		NATIONAL_PRESENT("National present"),
		NATIONAL_FUTURE("National future (2040)");
		
		
		String value;
		
		private GPANVariantDisplayName(String value) {
			this.value = value;
		}
		
		public String getValue() {
			return value;
		}	
		
		public static String[] getValues() {
			GPANVariantDisplayName[] headers = values();
		    String[] values = new String[headers.length];
	
		    for (int i = 0; i < headers.length; i++) {
		    	values[i] = headers[i].getValue();
		    }
		    return values;
		}
		
		public static GPANVariantDisplayName fromString(String str) {
			if(StringTools.isEmptyOrNull(str)) {
				return null;
			}
			for(GPANVariantDisplayName variantDispNameType : GPANVariantDisplayName.values()) {
				String variantDispNameVal = variantDispNameType.getValue();
				if(variantDispNameVal.equals(str)) {
					return variantDispNameType;
				}
			}
			return null;
		}
		
		public static List<String> getVariantsAsStringList() {
			return Arrays.asList(getValues());
		}
	}
	
	public enum GPANGroupDisplayName {
		AMPHIBIANS("Amphibians"),
		BIRDS("Birds"),
		MAMMALS("Mammals"),
		REPTILES("Reptiles"),
		CR("CR"),
		EN("EN"),
		VU("VU"),
		NT("NT"),
		LC("LC"),
		ENDANGERED("Endangered");
		
		String value;
		
		private GPANGroupDisplayName(String value) {
			this.value = value;
		}
		
		public String getValue() {
			return value;
		}	
		
		public static String[] getValues() {
			GPANGroupDisplayName[] headers = values();
		    String[] values = new String[headers.length];
	
		    for (int i = 0; i < headers.length; i++) {
		    	values[i] = headers[i].getValue();
		    }
		    return values;
		}
		
		public static GPANGroupDisplayName fromString(String str) {
			if(StringTools.isEmptyOrNull(str)) {
				return null;
			}
			for(GPANGroupDisplayName groupDispNameType : GPANGroupDisplayName.values()) {
				String groupDispNameVal = groupDispNameType.getValue();
				if(groupDispNameVal.equals(str)) {
					return groupDispNameType;
				}
			}
			return null;
		}
		
		public static Set<String> getSpeciesGroupsAsStringSet() {
			Set<String> retVal = new TreeSet<String>();
			retVal.addAll(Arrays.asList(GPANGroupDisplayName.AMPHIBIANS.getValue(),GPANGroupDisplayName.BIRDS.getValue(), GPANGroupDisplayName.MAMMALS.getValue(), GPANGroupDisplayName.REPTILES.getValue()));
			return retVal;
		}
		
		public static List<String> getSpeciesGroupsAsStringList() {
			return Arrays.asList(GPANGroupDisplayName.AMPHIBIANS.getValue(),GPANGroupDisplayName.BIRDS.getValue(), GPANGroupDisplayName.MAMMALS.getValue(), GPANGroupDisplayName.REPTILES.getValue());
		}
		
		public static Set<String> getIUCNGroupsAsStringSet() {
			Set<String> retVal = new TreeSet<String>();
			retVal.addAll(Arrays.asList(GPANGroupDisplayName.CR.getValue(),GPANGroupDisplayName.EN.getValue(), GPANGroupDisplayName.VU.getValue(), GPANGroupDisplayName.NT.getValue(),GPANGroupDisplayName.LC.getValue(),GPANGroupDisplayName.ENDANGERED.getValue()));
			return retVal;
		}
		
		public static List<String> getIUCNGroupsAsStringList() {
			return Arrays.asList(GPANGroupDisplayName.CR.getValue(),GPANGroupDisplayName.EN.getValue(), GPANGroupDisplayName.VU.getValue(), GPANGroupDisplayName.NT.getValue(),GPANGroupDisplayName.LC.getValue(),GPANGroupDisplayName.ENDANGERED.getValue());
		}
	}
	
	public enum CarnivoresFamilyDisplayName {
		FELIDAE("Felidae"),
		URSIDAE("Ursidae"),
		AILURIDAE("Ailuridae"),
		CANIDAE("Canidae"),
		MUSTELIDAE("Mustelidae"),
		VIVERRIDAE("Viverridae"),
		OTARIDAEE("Otaridaee"),
		HERPESTIDAE("Herpestidae"),
		PROCYONIDAE("Procyonidae"),
		MEMPHITIDAE("Memphitidae"),
		HYAENIDAE("Hyaendiae"),
		EUPLERIDAE("Eupleridae"),
		PHOCIDAE("Phocidae"),
		NANDINIIDAE("Nandiinidae"),
		OBOBENIDAE("Obobenidae"),
		PRIONODONTIDAE("Prionodontidae"),
		DASYURIDAE("Dasyuridae"),
		MYRMECOBIDAE("Myrmecobidae");
		
		String value;
		
		private CarnivoresFamilyDisplayName(String value) {
			this.value = value;
		}
		
		public String getValue() {
			return value;
		}	
		
		public static String[] getValues() {
			CarnivoresFamilyDisplayName[] headers = values();
		    String[] values = new String[headers.length];
	
		    for (int i = 0; i < headers.length; i++) {
		    	values[i] = headers[i].getValue();
		    }
		    return values;
		}
		
		public static CarnivoresFamilyDisplayName fromValue(String str) {
			if(StringTools.isEmptyOrNull(str)) {
				return null;
			}
			for(CarnivoresFamilyDisplayName dispName : CarnivoresFamilyDisplayName.values()) {
				String dispNameVal = dispName.getValue();
				if(dispNameVal.equals(str)) {
					return dispName;
				}
			}
			return null;
		}
		
		public static CarnivoresFamilyDisplayName fromName(String str) {
			if(StringTools.isEmptyOrNull(str)) {
				return null;
			}
			for(CarnivoresFamilyDisplayName dispName : CarnivoresFamilyDisplayName.values()) {
				String name = dispName.name();
				if(name.equals(str)) {
					return dispName;
				}
			}
			return null;
		}
		
		public static List<String> getAsStringList() {
			return Arrays.asList(getValues());
		}
	}
	
	public enum CarnivoresIUCNDisplayName {
		LC("LC"),
		NT("NT"),
		VU("VU"),
		EN("EN"),
		CE("CE"),
		DD("DD");
		
		String value;
		
		private CarnivoresIUCNDisplayName(String value) {
			this.value = value;
		}
		
		public String getValue() {
			return value;
		}	
		
		public static String[] getValues() {
			CarnivoresIUCNDisplayName[] headers = values();
		    String[] values = new String[headers.length];
	
		    for (int i = 0; i < headers.length; i++) {
		    	values[i] = headers[i].getValue();
		    }
		    return values;
		}
		
		public static CarnivoresIUCNDisplayName fromValue(String str) {
			if(StringTools.isEmptyOrNull(str)) {
				return null;
			}
			for(CarnivoresIUCNDisplayName dispName : CarnivoresIUCNDisplayName.values()) {
				String dispNameVal = dispName.getValue();
				if(dispNameVal.equals(str)) {
					return dispName;
				}
			}
			return null;
		}
		
		public static CarnivoresIUCNDisplayName fromName(String str) {
			if(StringTools.isEmptyOrNull(str)) {
				return null;
			}
			for(CarnivoresIUCNDisplayName dispName : CarnivoresIUCNDisplayName.values()) {
				String name = dispName.name();
				if(name.equals(str)) {
					return dispName;
				}
			}
			return null;
		}
		
		public static List<String> getAsStringList() {
			return Arrays.asList(getValues());
		}
	}
	

	public enum CarnivoresSizeDisplayName {
		LARGE("Large"),
		SMALL("Small");
		
		String value;
		
		private CarnivoresSizeDisplayName(String value) {
			this.value = value;
		}
		
		public String getValue() {
			return value;
		}	
		
		public static String[] getValues() {
			CarnivoresSizeDisplayName[] headers = values();
		    String[] values = new String[headers.length];
	
		    for (int i = 0; i < headers.length; i++) {
		    	values[i] = headers[i].getValue();
		    }
		    return values;
		}
		
		public static CarnivoresSizeDisplayName fromValue(String str) {
			if(StringTools.isEmptyOrNull(str)) {
				return null;
			}
			for(CarnivoresSizeDisplayName dispName : CarnivoresSizeDisplayName.values()) {
				String dispNameVal = dispName.getValue();
				if(dispNameVal.equals(str)) {
					return dispName;
				}
			}
			return null;
		}
		
		public static CarnivoresSizeDisplayName fromName(String str) {
			if(StringTools.isEmptyOrNull(str)) {
				return null;
			}
			for(CarnivoresSizeDisplayName dispName : CarnivoresSizeDisplayName.values()) {
				String name = dispName.name();
				if(name.equals(str)) {
					return dispName;
				}
			}
			return null;
		}
		
		public static List<String> getAsStringList() {
			return Arrays.asList(getValues());
		}
	}
	
	public enum CarnivoresAllSpeciesDisplayName {
		CURR("Present"),
		FUT("Future");
		
		String value;
		
		private CarnivoresAllSpeciesDisplayName(String value) {
			this.value = value;
		}
		
		public String getValue() {
			return value;
		}	
		
		public static String[] getValues() {
			CarnivoresAllSpeciesDisplayName[] headers = values();
		    String[] values = new String[headers.length];
	
		    for (int i = 0; i < headers.length; i++) {
		    	values[i] = headers[i].getValue();
		    }
		    return values;
		}
		
		public static CarnivoresAllSpeciesDisplayName fromValue(String str) {
			if(StringTools.isEmptyOrNull(str)) {
				return null;
			}
			for(CarnivoresAllSpeciesDisplayName dispName : CarnivoresAllSpeciesDisplayName.values()) {
				String dispNameVal = dispName.getValue();
				if(dispNameVal.equals(str)) {
					return dispName;
				}
			}
			return null;
		}
		
		public static CarnivoresAllSpeciesDisplayName fromName(String str) {
			if(StringTools.isEmptyOrNull(str)) {
				return null;
			}
			for(CarnivoresAllSpeciesDisplayName dispName : CarnivoresAllSpeciesDisplayName.values()) {
				String name = dispName.name();
				if(name.equals(str)) {
					return dispName;
				}
			}
			return null;
		}
		
		public static List<String> getAsStringList() {
			return Arrays.asList(getValues());
		}
	}
}
