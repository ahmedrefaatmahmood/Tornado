package edu.purdue.cs.tornado.helper;

public class SpatioTextualConstants {
	
	//**********************************************************************************
	//**********************Spatio-textual index constants
	public static String IndexIDExtension= "_index";
	public static String Index_Bolt_STreamIDExtension_Query= "_index_bolt_extension_query";
	public static String Index_Bolt_STreamIDExtension_Data= "_index_bolt_extension_data";
	public static String Bolt_Index_STreamIDExtension_Query= "_bolt_index_extension_query";
	public static String Bolt_Index_STreamIDExtension_Data= "_bolt_index_extension_data";
	public static String Bolt_Bolt_STreamIDExtension_Query= "_bolt_bolt_extension_query";
	public static String Bolt_Bolt_STreamIDExtension_Data= "_bolt_bolt_extension_data";
	public static String Bolt_Output_STreamIDExtension= "_bolt_output_extension";
	public static String Fields_Grouping_ID_Field= "id";
	public static String Persistent= "persistent";
	public static String Volatile= "volatile";
	public static String Data_Source= "torando.data.source";
	public static String Query_Source= "tornado.query.source";
	public static String Default = "default";
	//****************************************************************************
	//***********************Spout constants
	public static final Integer generatorSeed = 1000;
	public static final Integer dataGeneratorDelay = 10; // setting this to zero to achieve the highest data rate possible
	public static final Integer queryGeneratorDelay = 0; // setting this to zero to achieve to send queries to bolts as  soon as possible

	public static final Integer numQueries = 1000;
	public static final Integer numMovingObjects = 100;
	//knn query constants 
	public static final Integer maxK = 10; // for kNN queries.
	public static final Double queryMaxWidth = 100.0;
	public static final Double queryMaxHeight = 100.0;
	
	//Data constants 
	public static final Double xMaxRange = 10000.0;
	public static final Double yMaxRange = 10000.0;
	
	
	//Textual content length
	public static final Integer objectTextualContentLength = 3;
	public static final Integer queryTextualContentLength = 3;
	
	// Object's fields
	public static final String objectIdField = Fields_Grouping_ID_Field+"_Object";
	public static final String objectXCoordField = "xCoord";
	public static final String objectYCoordField = "yCoord";
	public static final String objectTextField = "textContent";
	public static final String incrementalState = "incrementalState";
	public static final String timeStamp = "timeStamp";
	
	public static final String queryIdField = Fields_Grouping_ID_Field+"_Query";

	// Text Query field
	public static final String queryTextField = "queryText";
	// Text Timesatoamp field
	public static final String queryTimeStampField = "queryTimeStampField";
		
	// Range Query fields
	public static final String queryXMinField = "xMin";
	public static final String queryYMinField = "yMin";
	public static final String queryXMaxField = "xMax";
	public static final String queryYMaxField = "yMax";
	

	// kNN Query fields
	public static final String focalXCoordField = "focalXCoord";
	public static final String focalYCoordField = "focalYCoord";
	public static final String kField = "k";
	public static final String updateStatus = "updateStatus";
	
	//Query types 
	public static final String queryTypeField = "type";
	public static final String queryTextualKNN = "TextualKNN";
	public static final String queryTextualRange = "TextualRange";
	public static final String queryTextualSpatialJoin = "TextualSpatialJoin";

}
