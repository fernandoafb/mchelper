package com.fbraz.mchelper.misc;

import java.util.ArrayList;

import com.fbraz.mchelper.mch.Log;
import com.fbraz.mchelper.mch.Property;

public abstract class IOHelper {
	
	public IOHelper() {
		
	}
	
	public abstract Log readLog(String inputFilename);
	
	public abstract ArrayList<Property> readResult(String inputFilename);	
	
	public abstract void writeCSV(String outputFilename, ArrayList<Property> prismProperties);	

}
