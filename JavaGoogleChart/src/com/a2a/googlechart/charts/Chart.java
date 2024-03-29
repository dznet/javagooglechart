package com.a2a.googlechart.charts;

import java.util.ArrayList;

import com.a2a.googlechart.ChartAxis;
import com.a2a.googlechart.FillArea;
import com.a2a.googlechart.LinearGradientFill;
import com.a2a.googlechart.LinearStripesFill;
import com.a2a.googlechart.RangeMarker;
import com.a2a.googlechart.ShapeMarker;
import com.a2a.googlechart.SolidFill;
import com.a2a.googlechart.data.ChartData;
import com.a2a.googlechart.data.VectorFloat;
import com.a2a.googlechart.data.VectorInt;
import com.a2a.googlechart.exception.InvalidFeatureForChartTypeException;

/**
 * Represents a generic chart.
 */
public abstract class Chart {
  private static final String API_BASE = "http://chart.apis.google.com/chart?";
  protected ArrayList<String> urlElements = new ArrayList<String>();

  private int width;
  private int height;
  private String title;
  private String titleColor;
  private String data;
  private String[] datasetColors;

  protected static final String chartLineChart = "LineChart";
  protected static final String chartScatterPlot = "ScatterPlot";
  protected static final String chartBarChart = "BarChart";
  protected static final String chartVennDiagram = "VennDiagram";
  protected static final String chartPieChart = "PieChart";

  protected ArrayList<ChartAxis> axes = new ArrayList<ChartAxis>();
  protected ArrayList<String> legendStrings = new ArrayList<String>();
  protected ArrayList<SolidFill> solidFills = new ArrayList<SolidFill>();
  protected ArrayList<LinearGradientFill> linearGradientFills = 
    new ArrayList<LinearGradientFill>();
  protected ArrayList<LinearStripesFill> linearStripesFills = 
    new ArrayList<LinearStripesFill>();
  protected ArrayList<ShapeMarker> shapeMarkers = new ArrayList<ShapeMarker>();
  protected ArrayList<RangeMarker> rangeMarkers = new ArrayList<RangeMarker>();
  protected ArrayList<FillArea> fillAreas = new ArrayList<FillArea>();

  /**
   * Creates a new generic chart with specified size.
   * @param width in pixels
   * @param height in pixels
   */
  public Chart(int width, int height) {
    this.width = width;
    this.height = height;
  }

  public void setData(int[] data) {
    this.data = ChartData.Encode(data);
  }

  public void setData(VectorInt data) {
    this.data = ChartData.Encode(data);
  }

  /**
   * Sets the data to be used for the chart. 
   * @param data array of float values
   */
  public void setData(float[] data) {
    this.data = ChartData.Encode(data);
  }

  public void setData(VectorFloat data) {
    this.data = ChartData.Encode(data);
  }

  /**
   * Sets the title for the chart with a default color and font size.
   * @param title String
   */
  public void setTitle(String title) {
    String urlTitle = title.replaceAll(" ", "+");
    // TODO : replace le newline
    // urlTitle = urlTitle.replaceAll(Environment.NewLine, "|");
    this.title = urlTitle;
  }

  /**
   * Sets the title for the chart with the specified color and default font size.
   * @param title String
   * @param color title color in RRGGBB hexidecimal format
   */
  public void setTitle(String title, String color) {
    setTitle(title);
    this.titleColor = color;
  }

  /**
   * Sets the title for the chart with the specified color and font size.
   * @param title String 
   * @param color title color in RRGGBB hexidecimal format
   * @param fontSize in pixels
   */
  public void setTitle(String title, String color, int fontSize) {
    setTitle(title);
    this.titleColor = color + "," + fontSize;
  }

  /**
   * Sets the colors for the corresponding data sets.
   * @param datasetColors array of Strings in RRGGBB hexidecimal format
   */
  public void setDatasetColors(String[] datasetColors) {
    this.datasetColors = datasetColors;
  }

  /** 
   * Adds a solid fill to the chart.
   * @param solidFill SolidFill object
   */
  public void addSolidFill(SolidFill solidFill) {
    solidFills.add(solidFill);
  }

  /**
   * Adds a linear gradient fill to the chart.
   * @param linearGradientFill LinearGradientFill object 
   */
  public void addLinearGradientFill(LinearGradientFill linearGradientFill) {
    linearGradientFills.add(linearGradientFill);
  }

  /**
   * Adds a linear stripes fill to the chart.
   * @param linearStripesFill LinearStripesFill objects
   */
  public void addLinearStripesFill(LinearStripesFill linearStripesFill) {
    linearStripesFills.add(linearStripesFill);
  }

  boolean gridSet = false;
  private float gridXAxisStepSize = -1;
  private float gridYAxisStepSize = -1;
  private float gridLengthLineSegment = -1;
  private float gridLengthBlankSegment = -1;

  /**
   * Sets the grid for the chart.
   * @param xAxisStepSize Space between x-axis grid lines in relation to axis 
   *        range
   * @param yAxisStepSize Space between y-axis grid lines in relation to axis 
   *        range.
   * @throws InvalidFeatureForChartTypeException if the chart type does not 
   *         support grid
   */
  public void setGrid(float xAxisStepSize, float yAxisStepSize)
      throws InvalidFeatureForChartTypeException {
    String chartType = getChartType();
    if (!(chartType.equals(Chart.chartLineChart) || 
        chartType.equals(Chart.chartScatterPlot))) {
      throw new InvalidFeatureForChartTypeException();
    }

    this.gridXAxisStepSize = xAxisStepSize;
    this.gridYAxisStepSize = yAxisStepSize;
    this.gridLengthLineSegment = -1;
    this.gridLengthBlankSegment = -1;
    this.gridSet = true;
  }

  /**
   * Sets the grid for the chart.
   * @param xAxisStepSize Space between x-axis grid lines in relation to axis 
   *        range
   * @param yAxisStepSize Space between y-axis grid lines in relation to axis 
   *        range.
   * @param lengthLineSegment Length of each line segment in a grid line
   * @param lengthBlankSegment Length of each blank segment in a grid line
   * @throws InvalidFeatureForChartTypeException if the chart type does not 
   *         support grid
   */
  public void setGrid(float xAxisStepSize, float yAxisStepSize, 
      float lengthLineSegment, float lengthBlankSegment) 
      throws InvalidFeatureForChartTypeException {
    String chartType = getChartType();
    if (!(chartType.equals(Chart.chartLineChart) || 
        chartType.equals(Chart.chartScatterPlot))) {
      throw new InvalidFeatureForChartTypeException();
    }

    this.gridXAxisStepSize = xAxisStepSize;
    this.gridYAxisStepSize = yAxisStepSize;
    this.gridLengthLineSegment = lengthLineSegment;
    this.gridLengthBlankSegment = lengthBlankSegment;
    this.gridSet = true;
  }

  private String getGridUrlElement() {
    if (gridXAxisStepSize != -1 && gridYAxisStepSize != -1) {
      String s = "chg="+String.valueOf(gridXAxisStepSize)+","+String.valueOf(gridYAxisStepSize);
      if (gridLengthLineSegment != -1 && gridLengthBlankSegment != -1) {
        s += "," + String.valueOf(gridLengthLineSegment) + "," + String.valueOf(gridLengthBlankSegment);
      }
      return s;
    }
    return null;
  }

  /**
   * Adds a fill area to the chart. Fill areas are fills between / under lines.
   * @param fillArea FillArea object
   */
  public void addFillArea(FillArea fillArea) {
    this.fillAreas.add(fillArea);
  }

  /**
   * Adds a shape marker to the chart. Shape markers are used to call attention 
   * to a data point on the chart.
   * @param shapeMarker ShapeMarker object
   */
  public void addShapeMarker(ShapeMarker shapeMarker) {
    this.shapeMarkers.add(shapeMarker);
  }

  /**
   * Adds a range marker to the chart. Range markers are colored bands on the 
   * chart.
   * @param rangeMarker RangeMarker object
   */
  public void addRangeMarker(RangeMarker rangeMarker) {
    this.rangeMarkers.add(rangeMarker);
  }

  private String getFillAreasUrlElement() {
    String s = new String("");
    for(int i = 0; i < fillAreas.size(); i++) {
      FillArea fillArea = (FillArea)fillAreas.get(i);
      s += fillArea.getUrlString() + "|";
    }

    return trimEnd(s,'|');
  }

  private String getShapeMarkersUrlElement() {
    String s = new String("");
    for(int i = 0; i < shapeMarkers.size(); i++) {
      ShapeMarker shapeMarker = (ShapeMarker)shapeMarkers.get(i);
      s += shapeMarker.getUrlString() + "|";
    }

    return trimEnd(s,'|');
  }

  private String getRangeMarkersUrlElement() {
    String s = new String("");
    for(int i = 0; i < rangeMarkers.size(); i++) {
      RangeMarker rangeMarker = (RangeMarker) rangeMarkers.get(i);
      s += rangeMarker.getUrlString();
      if (i < rangeMarkers.size()) {
        s += "|";
      }
    }

    return trimEnd(s,'|');
  }

  /**
   * Sets the legends for the corresponding data sets.
   * @param strs array of Strings
   */
  public void setLegend(String[] strs) {
    for (int i = 0; i < strs.length; i++) {
      String s = (String) strs[i];
      legendStrings.add(s);
    }
  }

  /**
   * Adds a specified axis to the chart.
   * @param axis ChartAxis object
   */
  public void addAxis(ChartAxis axis) { 
    axes.add(axis);
  }

  /**
   * Returns a full url representing the chart.
   * @return String
   */
  public String getUrl() {
    collectUrlElements();
    return generateUrlString();
  }

  /**
   * Returns the api chart identifier for the chart
   * @return String
   */
  protected abstract String urlChartType();

  protected abstract String getChartType();

  /**
   * Collects all the elements that will be used in the chart url.
   */
  protected void collectUrlElements() {
    urlElements.clear();
    urlElements.add("cht=" + this.urlChartType());
    urlElements.add("chs=" + this.width + "x" + this.height);
    urlElements.add(this.data);

    // chart title
    if (title != null) {
      urlElements.add("chtt="+this.title);
    }
    if (titleColor != null) {
      urlElements.add("chts=" + this.titleColor);
    }

    // dataset colors
    if (datasetColors != null) {
      String s = "chco=";
      for(int i = 0; i < datasetColors.length; i++) {
        String color = (String) datasetColors[i];
        s += color + ",";
      }

      urlElements.add(trimEnd(s,','));
    }

    // Fills
    String fillsString = "chf=";
    if (solidFills.size() > 0) {
      for (int i = 0; i < solidFills.size(); i++) {
        SolidFill solidFill = (SolidFill) solidFills.get(i);
        fillsString += solidFill.getUrlString() + "|";
      }
    }
    if (linearGradientFills.size() > 0) {
      for (int i = 0; i < linearGradientFills.size(); i++) {
        LinearGradientFill linearGradient = 
          (LinearGradientFill) linearGradientFills.get(i);
        fillsString += linearGradient.getUrlString() + "|";
      }
    }
    if (linearStripesFills.size() > 0) {
      for (int i = 0; i < linearStripesFills.size(); i++) {
        LinearStripesFill linearStripesFill = 
          (LinearStripesFill) linearStripesFills.get(i);
        fillsString += linearStripesFill.getUrlString() + "|";
      }
    }
    if (solidFills.size() > 0 || linearGradientFills.size() > 0 || 
        linearStripesFills.size() > 0) {
      urlElements.add( trimEnd(fillsString,'|') );
    }

    // Legends
    if (legendStrings.size() > 0) {
      String s = "chdl=";
      for (int i = 0; i < legendStrings.size(); i++) {
        String str = (String) legendStrings.get(i);
        s += str + "|";
      }

      urlElements.add( trimEnd(s,'|') );
    }

    // Axes
    if (axes.size() > 0) {
      String axisTypes = "chxt=";
      String axisLabels = "chxl=";
      String axisLabelPositions = "chxp=";
      String axisRange = "chxr=";
      String axisStyle = "chxs=";
      String axisTickMarks = "chxtc=";

      int axisIndex = 0;
      for(int i = 0; i < axes.size(); i++) {
        ChartAxis axis = (ChartAxis) axes.get(i);

        axisTypes += axis.urlAxisType() + ",";
        axisLabels += String.valueOf(axisIndex) + ":" + axis.urlLabels();
        String labelPositions = axis.urlLabelPositions();

        if (labelPositions != null && labelPositions.trim().equals("")) {
          axisLabelPositions += String.valueOf(axisIndex) + "," + 
          labelPositions + "|";
        }
        String axisRangeStr = axis.urlRange();
        if (axisRangeStr != null && axisRangeStr.trim().equals("")) {
          axisRange += String.valueOf(axisIndex) + "," + axisRangeStr + "|";
        }
        String axisStyleStr = axis.urlAxisStyle();
        if (axisStyleStr != null && axisStyleStr.trim().equals("")) {
          axisStyle += String.valueOf(axisIndex) + "," + axisStyleStr + "|";
        }
        String axisTickMarksStr = axis.urlAxisTickMarks();
        if (axisTickMarksStr != null) {
          axisTickMarks += String.valueOf(axisIndex) + "," + axisTickMarksStr;
        }
        axisIndex++;
      }

      axisTypes =  trimEnd(axisTypes,',');
      axisLabels = trimEnd(axisLabels,'|');
      axisLabelPositions = trimEnd(axisLabelPositions,'|');
      axisRange = trimEnd(axisRange,'|');
      axisStyle = trimEnd(axisStyle,'|');
      axisTickMarks = trimEnd(axisTickMarks, '|');

      urlElements.add(axisTypes);
      urlElements.add(axisLabels);
      urlElements.add(axisLabelPositions);
      urlElements.add(axisRange);
      urlElements.add(axisStyle);
      urlElements.add(axisTickMarks);
    }

    // Grid
    if (gridSet) {
      urlElements.add(getGridUrlElement());
    }

    // Markers
    String markersString = "chm=";
    if (shapeMarkers.size() > 0) {
      markersString += getShapeMarkersUrlElement() + "|";
    }
    if (rangeMarkers.size() > 0) {
      markersString += getRangeMarkersUrlElement() + "|";
    }
    if (fillAreas.size() > 0) {
      markersString += getFillAreasUrlElement() + "|";
    }
    if (shapeMarkers.size() > 0 || rangeMarkers.size() > 0 || 
        fillAreas.size() > 0) {
      urlElements.add( trimEnd(markersString,'|'));
    }
  }

  private String generateUrlString() {
    String url = new String("");

    url += Chart.API_BASE;

    for (int i = 0; i < urlElements.size(); i++) {
      String lurl = (String) urlElements.get(i);
      if (i > 0) {
        url += "&";
      }
      url += lurl;        	
    }

    return url;
  }

  public static String trimEnd(String chaine, char endChar) {
    String retour = chaine;

    if (chaine != null && chaine.trim().length() > 0 && 
        chaine.charAt(chaine.length()-1) == endChar){
      retour = retour.substring(0,chaine.length()-1);
    }

    return retour;
  }

  /**
   * Returns the width of the chart.
   * @return width in pixels
   */
  public int getWidth() {
    return width;
  }

  /**
   * Sets the width of the chart.
   * @param width in pixels
   */
  public void setWidth(int width) {
    this.width = width;
  }

  /**
   * Returns the height of the chart
   * @return height in pixels
   */
  public int getHeight() {
    return height;
  }

  /**
   * Sets the height of the chart.
   * @param height in pixels
   */
  public void setHeight(int height) {
    this.height = height;
  }
}
