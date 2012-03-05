package com.fbraz.mchelper.prism;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import com.fbraz.mchelper.mch.Log;
import com.fbraz.mchelper.mch.Property;
import com.fbraz.mchelper.misc.IOHelper;

public class PrismIOHelper extends IOHelper {
	
	private static final String delimiter = ";";
	
	@Override
	public ArrayList<Property> readResult(String inputFilename) {
		if (inputFilename == null) return null; 
		if (inputFilename.isEmpty()) return null;
		ArrayList<Property> prismProperties = new ArrayList<Property>();		
		try {
			FileReader fileReader = new FileReader(new File(inputFilename));
			BufferedReader buffReader = new BufferedReader(fileReader);
			String line = buffReader.readLine();
			int p = 1;
			do {
				PrismProperty prismProperty = new PrismProperty();			
				prismProperty.setPropertyCommand(line);
				prismProperty.setPropertyNumber(p);
				line = buffReader.readLine();
				prismProperty.setPropertyHeaders(line);
				line = buffReader.readLine();
				while (line != null && !line.equals("")) {
					prismProperty.addPropertyResult(new PrismResult(line.trim()));
					line = buffReader.readLine();
				}
				prismProperties.add(prismProperty);
				line = buffReader.readLine();
			} while (line != null);
			buffReader.close();
			fileReader.close();
		}
		catch (Exception e) {
			System.out.println("Failure to handle input file.");
			e.printStackTrace();
		}
		return prismProperties;
	}

	@Override
	public Log readLog(String inputFilename) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private ArrayList<String> genHeaders(ArrayList<String> csvResults, ArrayList<Property> prismProperties) {
		Property baseProperty = prismProperties.get(0);
		int numResults = baseProperty.getPropertyResults().size();
		int numHeaders = baseProperty.getPropertyHeaders().size();
		if (baseProperty != null && baseProperty.getPropertyHeaders().size() > 0 && 
				baseProperty.getPropertyHeaders().get(0) != null) 
			csvResults.add(baseProperty.getPropertyHeaders().get(0)+delimiter);
		for (int i = 0; i < numResults; i++) {
			String result = delimiter;
			if (numHeaders != 1) {
				result = baseProperty.getPropertyResults().get(i).getAtribs().get(0) + result;
			}
			csvResults.add(result);
		}		
		return csvResults;
	}
	
	private boolean genResultsCheck(ArrayList<String> csvResults, ArrayList<Property> prismProperties) {
		if (prismProperties == null) return false;
		if (prismProperties.size() <= 0) return false;
		Property baseProperty = prismProperties.get(0);
		if (baseProperty == null) return false;
		int numResults = baseProperty.getPropertyResults().size();
		int numHeaders = baseProperty.getPropertyHeaders().size();
		if (numResults <= 0 || numHeaders <= 0) return false;
		if (csvResults == null) return false;
		if (csvResults.size() <= 0) return false;
		return true;
	}
	
	private ArrayList<String> genResults(ArrayList<String> csvResults, ArrayList<Property> prismProperties) {
		if (!genResultsCheck(csvResults,prismProperties)) return null;
		Property baseProperty = prismProperties.get(0);
		int numResults = baseProperty.getPropertyResults().size();
		int numHeaders = baseProperty.getPropertyHeaders().size();		
		for (Property p : prismProperties) {
			if (csvResults.get(0) != null) {
				String header = "";
				header = (numHeaders == 1) ? p.getPropertyCommand() : p.getPropertyName();
				csvResults.set(0,csvResults.get(0)+header+delimiter);
			}
			for (int i = 1; i < numResults+1; i++) {
				if (csvResults.size() > i && csvResults.get(i) != null) {
					String result = p.getPropertyResults().get(i-1).getAtribs().get(numHeaders-1);
					result = result.replace(".", ",");
					csvResults.set(i, csvResults.get(i)+result+delimiter);
				}
			}
		}		
		return csvResults;
	}
	
	public void writeCSV(String outputFilename, ArrayList<Property> prismProperties) {
		System.out.println("Creating CSV file... ");		
		if (outputFilename == null) return;
		if (outputFilename.isEmpty()) return;
		try {
			FileWriter fileWriter = new FileWriter(new File(outputFilename));
			PrintWriter printWriter = new PrintWriter(fileWriter,true);
			ArrayList<String> csvResults = new ArrayList<String>();
			csvResults = genHeaders(csvResults,prismProperties);
			csvResults = genResults(csvResults,prismProperties);
			for (String string : csvResults)
				printWriter.println(string);
			System.out.println("CSV file created: "+outputFilename);
			printWriter.close();
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Error to create CSV file");
		}  
		System.out.println();
	}			

}
