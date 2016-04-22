package fi.csc.avaa.cbig.gpan;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.annotation.WebServlet;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.vaadin.addon.charts.Chart;
import com.vaadin.addon.charts.model.DataSeries;
import com.vaadin.addon.charts.model.Series;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinService;
import com.vaadin.server.VaadinServlet;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.ProgressBar;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import fi.csc.avaa.cbig.common.AppType;
import fi.csc.avaa.cbig.common.chart.GraphNames.GPANGroupDisplayName;
import fi.csc.avaa.cbig.common.chart.GraphNames.GPANVariantDisplayName;
import fi.csc.avaa.cbig.common.chart.ChartTools;
import fi.csc.avaa.cbig.common.chart.ChartTypes;
import fi.csc.avaa.cbig.common.chart.ChartVisualization;
import fi.csc.avaa.cbig.common.csv.CSVDataValueTypes.GPANGroupType;
import fi.csc.avaa.cbig.common.csv.CSVDataValueTypes.GPANVariantType;
import fi.csc.avaa.cbig.common.csv.CSVTools;
import fi.csc.avaa.cbig.common.csv.CSVType;

/**
 * Main class for rendering Vaadin charts using HY CSV data residing in IDA.
 * 
 * @author jmlehtin
 *
 */
@SuppressWarnings("serial")
@Title("GPAN charts")
public class GPAN_UI extends UI {

	// Using these annotations and Servlet class, web.xml can be left almost
	// empty
	@WebServlet(value = "/VAADIN/*", asyncSupported = true)
	@VaadinServletConfiguration(productionMode = true, ui = GPAN_UI.class)
	public static class Servlet extends VaadinServlet {
	}
	
	public static Log LOG = LogFactoryUtil.getLog(GPAN_UI.class);
	// App base path
	String basepath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();
	// Class for creating the actual charts
	private ChartVisualization chartVis = new ChartVisualization();
	// Cache for holding already successfully fetched CSV data
	private static Map<CSVType, List<Map<String, String>>> cbigCsvData = new HashMap<CSVType, List<Map<String, String>>>();
	// Progress bar to show when charts are being loaded
	private final ProgressBar progressBar = new ProgressBar();
	// Outermost layout component within this portlet
	private VerticalLayout portletBaseLayout;
	
	// Maps for holding the visibility state of a graph in a chart
	// Key is graphId, value is whether graph should be visible
	private Map<GPANGroupType,Boolean> speciesGraphVisibilities = new HashMap<GPANGroupType,Boolean>();
	private Map<GPANGroupType,Boolean> iucnGraphVisibilities = new HashMap<GPANGroupType,Boolean>();
	private Map<GPANVariantType,Boolean> variantGraphVisibilities = new HashMap<GPANVariantType,Boolean>();

	public GPAN_UI() {
		// Check for server changes every 1000ms
		// Polling used only when waiting for the charts to be drawn
		setPollInterval(1000);

		// Outermost layout for the portlet
		this.portletBaseLayout = new VerticalLayout();
		
		// Init graph visibility state maps
		for(GPANVariantType variant : GPANVariantType.values()) {
			this.variantGraphVisibilities.put(variant, false);
		}
		for(GPANGroupType group : GPANGroupType.getSpeciesCSVGroups()) {
			this.speciesGraphVisibilities.put(group, false);
		}
		for(GPANGroupType group : GPANGroupType.getIUCNCSVGroups()) {
			this.iucnGraphVisibilities.put(group, false);
		}
	}
	
	private void setVariantGraphVisibility(GPANVariantType type, boolean isVisible) {
    	variantGraphVisibilities.put(type, isVisible);
    }
	
	private void setSpeciesGraphVisibility(GPANGroupType type, boolean isVisible) {
    	speciesGraphVisibilities.put(type, isVisible);
    }

	private void setIUCNGraphVisibility(GPANGroupType type, boolean isVisible) {
    	iucnGraphVisibilities.put(type, isVisible);
    }
	
	@Override
	protected void init(VaadinRequest request) {
		LOG.debug("Starting to handle VaadinRequest in init method..");
		// Add progress bar to layout
		initProgressBar();

		// Run chart loading in another thread to allow progress bar be visible
		// during this time
		class WorkThread extends Thread {

			@Override
			public void run() {
				// Lock the UI session for protecting data and to avoid
				// concurrent access
				UI.getCurrent().getSession().getLockInstance().lock();
				try {
					boolean isOK = true;
					
					// Create horizontal grid layout for mean chart and
					// control row
					GridLayout variantGrid = new GridLayout(16, 1);
					variantGrid.setWidth("100%");
					
					// Create vertical grid layout for controlling
					// variants
					// To be placed on left side of variantGrid
					GridLayout controlGrid = new GridLayout(1, 8);
					controlGrid.setWidth("100%");
					
					// Create layout to be used within base after
					// variantGrid layout
					// Contains both species and IUCN charts
					GridLayout groupGrid = new GridLayout(16, 1);
					groupGrid.setWidth("100%");

					// Create label for user instructions and add to
					// controlGrid
					Label instructions = new Label(
							"<p class='instructions'>Here you can visualise and compare the performance of our four main prioritizations. Pick the prioritizations for visualisation, and see how they behave overall and in covering the ranges of different species groups and IUCN red list categories. <span style='font-weight: bold;'>Choose variant type below to update the page content:</span></p>");
					instructions.setStyleName("instruction-container");
					instructions.setContentMode(ContentMode.HTML);
					controlGrid.addComponent(instructions, 0, 0, 0, 1);
					
					OptionGroup mainOptionGroup = null;
					controlGrid.setComponentAlignment(instructions, Alignment.MIDDLE_RIGHT);
					// Create mean chart
					Component meanChart = createGPANChart(CSVType.MEAN_CURVES, ChartTypes.GPAN_MEAN);
					if(meanChart == null) {
						isOK = false;
					} else {
						// Create variant checkbox group
						mainOptionGroup = getOptionGroupForChart(ChartTypes.GPAN_MEAN);
					}
					
					if (isOK) {
						// Add variant checkbox group to control grid
						controlGrid.addComponent(mainOptionGroup, 0, 2,
								0, 7);
						controlGrid.setComponentAlignment(mainOptionGroup, Alignment.MIDDLE_CENTER);
						// Add controlGrid and meanChart to variantGrid
						variantGrid.addComponent(controlGrid, 0, 0, 4, 0);
						variantGrid.addComponent(meanChart, 8, 0, 14, 0);
						
						// Create species chart
						Chart speciesChart = createGPANChart(CSVType.CURVES2, ChartTypes.GPAN_SPECIES);
						if(speciesChart == null) {
							isOK = false;
						}
						
						if(isOK) {
							// Create IUCN chart
							Chart iucnChart = createGPANChart(CSVType.CURVES2, ChartTypes.GPAN_IUCN);
							if(iucnChart == null) {
								isOK = false;
							}

							if (isOK) {
								// Create group radio option groups (to be placed to
								// groupGrid)
								OptionGroup radioGroupSpecies = getOptionGroupForChart(ChartTypes.GPAN_SPECIES);
								OptionGroup radioGroupIucn = getOptionGroupForChart(ChartTypes.GPAN_IUCN);
	
								// Hide all curves except one variant's associated
								// curves
								toggleChartSeries();
	
								// Add group charts and radio option groups to
								// groupGrid
								groupGrid.addComponent(speciesChart, 0, 0, 6, 0);
								groupGrid.addComponent(radioGroupSpecies, 7, 0, 7,
										0);
								groupGrid.addComponent(iucnChart, 8, 0, 14, 0);
								groupGrid
										.addComponent(radioGroupIucn, 15, 0, 15, 0);
								groupGrid.setComponentAlignment(radioGroupSpecies,
										Alignment.MIDDLE_CENTER);
								groupGrid.setComponentAlignment(radioGroupIucn,
										Alignment.MIDDLE_CENTER);
	
								// Add grids to base layout
								portletBaseLayout.addComponent(variantGrid);
								portletBaseLayout.addComponent(groupGrid);
							} else {
								LOG.error("IUCN chart null. Do not draw!");
								// Detach UI from this session
								close();
								showErrorNotification();
							}
						} else {
							LOG.error("Species chart null. Do not draw!");
							// Detach UI from this session
							close();
							showErrorNotification();
						}
					} else {
						LOG.error("Mean chart / static image null. Do not draw!");
						// Detach UI from this session
						close();
						showErrorNotification();
					}
				} finally {
					// When operations are done, progress bar is no longer
					// needed
					removeProgressBar();
					// Release lock on this UI session
					UI.getCurrent().getSession().getLockInstance().unlock();
					// Do not poll server for changes any more
					setPollInterval(-1);
				}

			}

			/**
			 * Create option groups (checkboxes or radio buttons) for the
			 * charts.
			 * 
			 * @param chartType
			 * @return
			 */
			private OptionGroup getOptionGroupForChart(ChartTypes chartType) {
				OptionGroup optGroup = new OptionGroup();
				optGroup.setImmediate(true);

				switch (chartType) {
				case GPAN_IUCN:
					configureChartOptionGroup(optGroup, "IUCN", chartType);
					break;
				case GPAN_MEAN:
					optGroup.setMultiSelect(true);
					configureChartOptionGroup(optGroup, null, chartType);
					// Add icons to checkboxes
					setVariantCheckBoxGroupIcons(optGroup,
							GPANVariantDisplayName.values());
					optGroup.addStyleName("variant-control-group");
					break;
				case GPAN_SPECIES:
					configureChartOptionGroup(optGroup, "Species", chartType);
					break;
				default:
					break;
				}
				return optGroup;
			}
			
			/**
			 * Create option groups (checkboxes or radio buttons) for the
			 * charts. Also define the initial states of the buttons. Also
			 * define what happens when button is clicked.
			 * 
			 * @param optGroup
			 * @param optGroupCaption
			 * @param chartType
			 */
			@SuppressWarnings("unchecked")
			private void configureChartOptionGroup(OptionGroup optGroup, String optGroupCaption, ChartTypes chartType) {
				optGroup.setCaption(optGroupCaption);
				optGroup.setStyleName("optiongroup-caption");
				List<String> displayNames = null;
				
				switch (chartType) {
				case GPAN_IUCN:
					displayNames = GPANGroupDisplayName.getIUCNGroupsAsStringList();
					break;
				case GPAN_SPECIES:
					displayNames = GPANGroupDisplayName.getSpeciesGroupsAsStringList();
					optGroup.addItems(displayNames);
					break;
				case GPAN_MEAN:
					displayNames = GPANVariantDisplayName.getVariantsAsStringList();
					optGroup.addItems(displayNames);
					break;
				default:
					break;
				}

				optGroup.addItems(displayNames);
				String initialDispName = displayNames.get(0);
				
				if(chartType == ChartTypes.GPAN_MEAN) {
					// Get variant type as it is in graphId
					GPANVariantType firstVariant = ChartTools
							.getGPANVariantCSVTypeFromDisplayName(GPANVariantDisplayName
									.fromString(initialDispName));
					
					// Set visibility object state to indicate the chosen first variant should be visible
					setVariantGraphVisibility(firstVariant, true);
					// Choose initial selected variant
					optGroup.setValue(Arrays.asList(initialDispName));
					// When checkbox button is clicked, set visibility object state to indicate the chosen variants should be visible
					optGroup.addValueChangeListener(event -> {
						// Which variants currently are checked in the UI
						Set<String> checkedVariants = (Set<String>) event
								.getProperty().getValue();
						
						// Get corresponding VariantCSVType enums
						Set<GPANVariantType> checkedVariantTypes = checkedVariants.stream().map(v -> ChartTools
									.getGPANVariantCSVTypeFromDisplayName(GPANVariantDisplayName.fromString(v))).collect(Collectors.toSet());
						
						// If a variant is among the checked variants, indicate it visible
						Set<GPANVariantType> allVariants = variantGraphVisibilities
								.keySet();
						for (GPANVariantType variant : allVariants) {
							if (checkedVariantTypes.contains(variant)) {
								setVariantGraphVisibility(variant, true);
							} else {
								setVariantGraphVisibility(variant, false);
							}
						}
						// Redraw graphs
						toggleChartSeries();
					});
				} else {
					// Choose initial selected group 
					optGroup.setValue(initialDispName);
					// Get group type as it is in graphId
					GPANGroupType initialGroup = ChartTools
							.getGPANGroupCSVTypeFromDisplayName(GPANGroupDisplayName
									.fromString(initialDispName));
					
					// Set visibility object state to indicate the chosen first group should be visible
					switch (chartType) {
					case GPAN_IUCN:
						setIUCNGraphVisibility(initialGroup, true);
						break;
					case GPAN_SPECIES:
						setSpeciesGraphVisibility(initialGroup, true);
						break;
					default:
						break;
					}
					
					// When radio button is clicked, set visibility object state to indicate the chosen group should be visible
					optGroup.addValueChangeListener(event -> {
						// What button was clicked
						Object chosenRadioBtn = event.getProperty().getValue();
						GPANGroupType chosenGroup = ChartTools
								.getGPANGroupCSVTypeFromDisplayName(GPANGroupDisplayName
										.fromString(chosenRadioBtn.toString()));
						
						Set<GPANGroupType> allGroups = null;
						switch (chartType) {
						case GPAN_IUCN:
							allGroups = iucnGraphVisibilities.keySet();
							break;
						case GPAN_SPECIES:
							allGroups = speciesGraphVisibilities.keySet();
							break;
						default:
							break;
						}

						// Set chosen group as "to be visible"
						for (GPANGroupType group : allGroups) {
							if (group == chosenGroup) {
								switch (chartType) {
								case GPAN_IUCN:
									setIUCNGraphVisibility(group, true);
									break;
								case GPAN_SPECIES:
									setSpeciesGraphVisibility(group, true);
									break;
								default:
									break;
								}
							} else {
								switch (chartType) {
								case GPAN_IUCN:
									setIUCNGraphVisibility(group, false);
									break;
								case GPAN_SPECIES:
									setSpeciesGraphVisibility(group, false);
									break;
								default:
									break;
								}
							}
						}
						// Redraw graphs
						toggleChartSeries();
					});
				}
			}

			/**
			 * Set icons for variant checkboxes. Use same color as variant graph color.
			 * 
			 * @param optGroup
			 * @param variants
			 */
			private void setVariantCheckBoxGroupIcons(OptionGroup optGroup,
					GPANVariantDisplayName[] variants) {
				for (GPANVariantDisplayName variant : variants) {
					FileResource imgRes = null;
					switch (variant) {
					case GLOBAL_PRESENT:
						imgRes = new FileResource(new File(basepath
								+ "/WEB-INF/images/gpan/checkbox_color_291393.png"));
						break;
					case GLOBAL_FUTURE:
						imgRes = new FileResource(new File(basepath
								+ "/WEB-INF/images/gpan/checkbox_color_018573.png"));
						break;
					case NATIONAL_PRESENT:
						imgRes = new FileResource(new File(basepath
								+ "/WEB-INF/images/gpan/checkbox_color_D47D02.png"));
						break;
					case NATIONAL_FUTURE:
						imgRes = new FileResource(new File(basepath
								+ "/WEB-INF/images/gpan/checkbox_color_B6024B.png"));
						break;
					default:
						break;
					}
					optGroup.setItemIcon(variant.getValue(), imgRes);
				}

			}
			
		    /**
		     * This method should be called whenever redraw for the graphs in charts is required.
		     * Drawing is done based on the visibility state objects.
		     * 
		     */
		    private void toggleChartSeries() {
		    	for(Map.Entry<ChartTypes, Chart> chartItem : chartVis.getCbigCharts().entrySet()) {
					Chart chart = chartItem.getValue();
					List<Series> graphs = chart.getConfiguration().getSeries();
					for(Series series : graphs) {
						DataSeries dSeries = (DataSeries) series;
						String graphId = dSeries.getStack();
						GPANVariantType variant = ChartTools.getGPANVariantCSVTypeFromGraphId(graphId);
						GPANGroupType group = ChartTools.getGPANGroupCSVTypeFromGraphId(graphId);
						
						boolean newVisibility = false;
						if(group == null) {
							newVisibility = variantGraphVisibilities.get(variant);
						} else {
							if(speciesGraphVisibilities.containsKey(group)) {
								newVisibility = variantGraphVisibilities.get(variant) && speciesGraphVisibilities.get(group);
							} else if(iucnGraphVisibilities.containsKey(group)){
								newVisibility = variantGraphVisibilities.get(variant) && iucnGraphVisibilities.get(group);
							} else {
								LOG.error("Group " + group + " not found in either species or iucn graph visibility map");
							}
						}
						dSeries.setVisible(newVisibility, true);
					}
				}
		    }
		    
			/**
			 * Show notification for the user to refresh the page in case of error
			 */
			private void showErrorNotification() {
				Notification notif = new Notification(
						"Please refresh the page",
						"Error occurred when drawing the charts..",
						Notification.Type.WARNING_MESSAGE);
				notif.show(UI.getCurrent().getPage());
			}

		}
		UI.getCurrent().setContent(portletBaseLayout);
		final WorkThread th = new WorkThread();
		th.start();
	}

	/**
	 * Create progress bar which does not inform user when the waiting time
	 * should end
	 */
	private void initProgressBar() {
		progressBar.setIndeterminate(true);
		progressBar
				.setCaption("Please wait while the charts are being loaded..");
		portletBaseLayout.addComponent(progressBar);
		portletBaseLayout.setComponentAlignment(progressBar,
				Alignment.TOP_CENTER);
	}

	private void removeProgressBar() {
		portletBaseLayout.removeComponent(progressBar);
	}

	private Chart createGPANChart(CSVType csvType, ChartTypes chartType) {
		List<Map<String, String>> csvData = CSVTools.getDataRowsForCsvType(csvType, cbigCsvData);
		return chartVis.createCBIGChart(450, csvType, chartType, csvData, AppType.GPAN);
	}
	
}
