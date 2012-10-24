package com.fbraz.mchelper.prism;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import com.fbraz.mchelper.mch.Log;
import com.fbraz.mchelper.mch.Property;
import com.fbraz.mchelper.misc.IOHelper;
import com.fbraz.mchelper.prism.PrismLogData.ModelMatrix;
import com.fbraz.mchelper.prism.PrismLogData.Reachab;
import com.fbraz.mchelper.prism.PrismProperty.IMethod;

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
		if (inputFilename == null) return null;
		if (inputFilename.isEmpty()) return null;
		int count = 0;
		PrismLog log = new PrismLog();
		List <PrismLog> logs = new ArrayList<PrismLog>();
		try {
			FileReader fileReader = new FileReader(new File(inputFilename));
			BufferedReader buffReader = new BufferedReader(fileReader);
			String line = buffReader.readLine();
			String[] tokens = null;
			tokens = line.split(" ");
			do {
				tokens = testRead(buffReader, "Command", tokens);
				for(int i = 0; i < tokens.length; i++)
					if(tokens[i].equals("-cuddmaxmem"))
					{
						log.setMemory(Long.parseLong(tokens[i+1]));
						break;
					}
				log.setCommandLine(concString(tokens));
				//Skip list of properties
				while(tokens[0].indexOf("----")< 0)
				{
					line = buffReader.readLine();
					tokens = line.split(" ");				
				}
				while(!tokens[0].equals("Building") && !tokens[0].equals("Model"))
				{
					line = buffReader.readLine();
					tokens = line.split(" ");
				}
				if(tokens[0].equals("Model")){
				}
				//test mem problem
				log.setMemProblem(testMem(buffReader));
				
				//model log info
				if(!log.isMemProblem())
				{				
					log = modelLog(buffReader, tokens);
					//log.printModelProperty();
					//Read tests until next Prism executian
					List <PrismProperty> properties = new ArrayList<PrismProperty>();

					while(true)
					{
						line = buffReader.readLine();
						tokens = line.split(" ");
						if(tokens[0].equals("Model"))
						{
							properties.add(readMCLog(buffReader, line));
						}else
						{
							if(tokens[0].equals("PRISM")) break;
						}
						if(!buffReader.ready()) break;
					}
					printProperty(properties, inputFilename, count++);				
				}
				logs.add(log);
				
			} while (line != null && buffReader.ready());
			buffReader.close();
			fileReader.close();			
		}
		catch (Exception e) {
			System.out.println("Failure to handle input file.");
			e.printStackTrace();
		}
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
	

	public static String[] testRead(BufferedReader buffReader, String commandRead, String[] tokens){
		String line = null;
		while(!tokens[0].equals(commandRead))
		{
			try {
				line = buffReader.readLine();
				tokens = line.split(" ");
			} catch (Exception e) {
				System.out.println("Failure to handle input file.");
				e.printStackTrace();
			}
		}
		return tokens;
	}
	
	//teste if prism execution stoped cause of mem problem
	public static boolean testMem(BufferedReader buffReader){
		String[] tokens;
		String line;
		
		try {
			line = buffReader.readLine();
			tokens = line.split(" ");
			if(tokens[0].equals("#")) return true;
			
		} catch (Exception e) {
			System.out.println("Failure to handle input file.");
		}
		return false;
	}
	
	
	//read model data
	public static PrismLog modelLog(BufferedReader buffReader, String[] tokens){
		
		PrismLog log = new PrismLog();
		PrismLogData logData = new PrismLogData();
		Reachab reach = logData.new Reachab();
		ModelMatrix mMatrix = logData.new ModelMatrix();
		String line = null;
		
		//Reachability
		tokens = testRead(buffReader, "Reachability:", tokens);
		reach.setReachab(tokens);
		log.setModelReach(reach);		
		//Time
		tokens = testRead(buffReader, "Time", tokens);
		log.setTime(Float.parseFloat(tokens[4]));		
		//Type
		tokens = testRead(buffReader, "Type:", tokens);
		log.setTypeModel(tokens[8]);		
		//States
		tokens = testRead(buffReader, "States:", tokens);
		log.setStates(Integer.parseInt(tokens[6]));
		log.setInitState(Integer.parseInt(tokens[7].substring(1)));		
		//Transitions
		tokens = testRead(buffReader, "Transitions:", tokens);
		log.setTransitions(Integer.parseInt(tokens[1]));		
		//MPD choices
		if(log.getTypeModel().equals("MDP"))
		{
			tokens = testRead(buffReader, "Choices:", tokens);
			log.setChoices(Integer.parseInt(tokens[5]));
		}
		//Matrix
		while(!tokens[0].equals("Rate") && !tokens[0].equals("Transition") )
		{
			try {
				line = buffReader.readLine();
				tokens = line.split(" ");
			} catch (Exception e) {
				System.out.println("Failure to handle input file.");
				e.printStackTrace();
				}
		}
		mMatrix.setMMatrix(tokens);
		log.setMatrix(mMatrix);
		return log;
	}
	
	//Read test log data 
	public static PrismProperty readMCLog(BufferedReader buffReader, String line){
		PrismProperty mCProperty = new PrismProperty();
		IMethod iMethod = mCProperty.new IMethod();
		
		try{			
			String[] tokens = null;
			tokens = line.split(" ");
			
			mCProperty.setProp(concString(tokens));
			//move to print method
			//System.out.println(mCProperty.getProp());
			
			line = buffReader.readLine();
			tokens = line.split(" ");
			
			if(!tokens[0].isEmpty()){
				if(tokens[0].equals("Model"))
				{
					mCProperty.setmConst(tokens[2]);
					line = buffReader.readLine();
					tokens = line.split(" ");
				}
					
				if(tokens[0].equals("Property"))
				{
					mCProperty.setpConst(tokens[2]);
					line = buffReader.readLine();
					tokens = line.split(" ");
				}
			}
			tokens = testRead(buffReader, "TOTAL:", tokens);
			mCProperty.setMemProp(Float.parseFloat(tokens[1].substring(1)));
			//Iterative Starting
			tokens = testRead(buffReader, "Starting", tokens);
			line = buffReader.readLine();
			line = buffReader.readLine();
			tokens = line.split(" ");
			if(tokens[0].equals("Iterative")){
				iMethod.setIMethodIte(tokens);
			}
			else {
				iMethod.setIMethodIja(tokens);
			}
			mCProperty.setiMethod(iMethod);
			tokens = testRead(buffReader, "Value", tokens);
			mCProperty.setValueInit(Double.parseDouble(tokens[5]));
			tokens = testRead(buffReader, "Time", tokens);
			mCProperty.setTimeMC(Float.parseFloat(tokens[4]));
			tokens = testRead(buffReader,"Result:",tokens);
			mCProperty.setResult(Double.parseDouble(tokens[1]));
		}
		catch (Exception e) {
			System.out.println("Failure to handle input file 2.");
			e.printStackTrace();
		}
		return mCProperty;
		
	}
	
	//print log data 
	public static void printProperty(List<PrismProperty> properties, String inFile, int cont){
		int i = 0;
		try{
			File file = new File(inFile+"."+cont+".out");
			if (!file.exists()) file.createNewFile();
			FileWriter fw = new FileWriter(file);
			BufferedWriter bw = new BufferedWriter(fw);
		
			//print output header
			while (properties.size() >i)
			{
				properties.get(i++).printProperty(bw);
			}
			System.out.println("CSV file created: "+ inFile+"."+cont+".out");
			bw.close();
			fw.close();
		}catch(IOException e) {
			e.printStackTrace();
			System.out.println("Error to create CSV file");
		}
	}
	
	public static String concString(String[] tokens)
	{
		int i = 3;
		String string = new String(tokens[2]+" ");
		while(i < tokens.length)
		{
			string += tokens[i]+" ";
			i++;
		}
		return string;
	}
}
