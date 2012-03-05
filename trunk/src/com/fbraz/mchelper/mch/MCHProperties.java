package com.fbraz.mchelper.mch;

import java.io.File;

import com.fbraz.mchelper.misc.InputFormat;
import com.fbraz.mchelper.misc.ModelChecker;

public class MCHProperties {
	
	private static final MCHProperties instance = new MCHProperties();
	
	private ModelChecker modelChecker = ModelChecker.PRISM;
	private InputFormat inputFormat = InputFormat.RESULT; 
	private String inputFilename;
	private String outputFilename;
	private Boolean directoryMode = Boolean.FALSE;
	
	public MCHProperties() {
		
	}
	
	public static MCHProperties getInstance() {
		return instance;
	}
	
	public static String generateOutputFilename(String inputFilename) {
		int lastBar = inputFilename.lastIndexOf("/");
		int lastDot = inputFilename.lastIndexOf(".");
		if (lastDot-lastBar > 0) {
			inputFilename = inputFilename.substring(0,lastDot);
		}
		return inputFilename+".mch";		
	}
	
	public void generateFilenames(String inputFilename) {
		setInputFilename(inputFilename);
		File file = new File(inputFilename);
		if (file.isDirectory()) { setDirectoryMode(Boolean.TRUE); return; }
		int lastBar = inputFilename.lastIndexOf("/");
		int lastDot = inputFilename.lastIndexOf(".");
		if (lastDot-lastBar > 0) {
			inputFilename = inputFilename.substring(0,lastDot);
		}
		setOutputFilename(inputFilename+".mch");
	}	
	
	public void loadProperties() {
		
	}

	public ModelChecker getModelChecker() {
		return modelChecker;
	}

	public void setModelChecker(ModelChecker modelChecker) {
		this.modelChecker = modelChecker;
	}

	public InputFormat getInputFormat() {
		return inputFormat;
	}

	public void setInputFormat(InputFormat inputFormat) {
		this.inputFormat = inputFormat;
	}

	public String getInputFilename() {
		return inputFilename;
	}

	public void setInputFilename(String inputFilename) {
		this.inputFilename = inputFilename;
	}

	public String getOutputFilename() {
		return outputFilename;
	}

	public void setOutputFilename(String outputFilename) {
		this.outputFilename = outputFilename;
	}

	public Boolean getDirectoryMode() {
		return directoryMode;
	}

	public void setDirectoryMode(Boolean directoryMode) {
		this.directoryMode = directoryMode;
	}
	
}
