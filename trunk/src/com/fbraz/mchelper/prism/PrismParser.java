package com.fbraz.mchelper.prism;

import java.io.File;
import java.util.ArrayList;

import com.fbraz.mchelper.mch.Log;
import com.fbraz.mchelper.mch.MCHProperties;
import com.fbraz.mchelper.mch.ParsingMethod;
import com.fbraz.mchelper.mch.Property;
import com.fbraz.mchelper.misc.InputFormat;

public class PrismParser extends ParsingMethod {
	
	private ArrayList<Property> prismProperties;
	private Log prismLog;
	
	public PrismParser(MCHProperties mchProperties) {
		super(mchProperties);
		prismProperties = new ArrayList<Property>();
		setPrismLog(new PrismLog());
		ioHelper = new PrismIOHelper();
	}

	public ArrayList<Property> getPrismProperties() {
		return prismProperties;
	}

	public void setPrismProperties(ArrayList<Property> prismProperties) {
		this.prismProperties = prismProperties;
	}

	public Log getPrismLog() {
		return prismLog;
	}

	public void setPrismLog(Log prismLog) {
		this.prismLog = prismLog;
	}
	
	private void getDirectoryFiles(ArrayList<String> inputList, ArrayList<String> outputList) {
		String basePath = new java.io.File("").getAbsolutePath();
		if (basePath.charAt(basePath.length()-1) != '/') basePath += "/";
		String dir = getMCHProperties().getInputFilename();
		if (dir.charAt(dir.length()-1) != '/') dir += "/";			
	    File folder = new File(dir);
	    File[] listOfFiles = folder.listFiles();
	    for (int i = 0; i < listOfFiles.length; i++) {
	      if (listOfFiles[i].isFile()) {
	    	String inputFilename = basePath+dir+listOfFiles[i].getName();
	    	if (inputFilename.substring(inputFilename.length()-3, inputFilename.length()).equals("mch")) continue;
	        inputList.add(inputFilename);
	        outputList.add(MCHProperties.generateOutputFilename(inputFilename));
	      }
	    }			
	}

	@Override
	public void execute() {
		ArrayList<String> inputList = new ArrayList<String>();
		ArrayList<String> outputList = new ArrayList<String>();
		if (getMCHProperties().getDirectoryMode()) {
			getDirectoryFiles(inputList, outputList);
		}
		else {
			inputList.add(getMCHProperties().getInputFilename());
			outputList.add(getMCHProperties().getOutputFilename());			
		}
		
		int i = 0;
		for (String filename : inputList) {
			if (getMCHProperties().getInputFormat().equals(InputFormat.RESULT)) {
				setPrismProperties(ioHelper.readResult(filename));
				ioHelper.writeCSV(outputList.get(i),getPrismProperties());
			}
			else if (getMCHProperties().getInputFormat().equals(InputFormat.LOG)) {
				setPrismLog(ioHelper.readLog(filename));
			}
			i++;
		}
	}	

}
