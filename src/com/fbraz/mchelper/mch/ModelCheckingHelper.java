package com.fbraz.mchelper.mch;

import com.fbraz.mchelper.misc.InputFormat;
import com.fbraz.mchelper.misc.ModelChecker;

public class ModelCheckingHelper {
	
	private static final int NO_ERROR = 0;
	private static final ModelCheckingHelper instance = new ModelCheckingHelper();
	private ParsingMethod parsingMethod;
	private MCHProperties mchProperties;
	
	private ModelCheckingHelper() {
		mchProperties = MCHProperties.getInstance(); 
	}
	
	public static ModelCheckingHelper getInstance() {
		return instance;
	}
	
	public MCHProperties getMCHProperties() {
		return mchProperties;
	}

	public void setMCHProperties(MCHProperties mchProperties) {
		this.mchProperties = mchProperties;
	}

	private static void programInfo() {
		System.out.println("Model Checking Helper Tool");
		System.out.println("Author: Fernando Augusto Fernandes Braz");
		System.out.println("Contact: fbraz@dcc.ufmg.br");
		System.out.println();		
	}
	
	private static void usageInfo() {
		System.out.println("Usage: java -jar mchelper.jar [OPTION]... [FILE]");
		System.out.println("Helper for parsing model checking results and logs, " + 
		                   "as well as running tests");	
		System.out.println();
		System.out.println("  -d, --dir \t \t directory (batch mode)");
		System.out.println("  -l, --log \t \t input log file");
		System.out.println("  -p, --prism \t \t PRISM model checker");
		System.out.println("  -r, --result \t \t input result file");		
	}
	
	private int parseOptions(String[] args) {
		int error = 0;
		int inputIndex = -1;
		for (int i = 0; i < args.length; i++) {

			if (args[i] == null) continue;
			
			if (!args[i].startsWith("-")) {
				inputIndex = i;
				continue;
			}
			
			if (args[i].equals("-d") || args[i].equals("--dir")) {
				mchProperties.setDirectoryMode(Boolean.TRUE);
				continue;
			}			
			
			if (args[i].equals("-l") || args[i].equals("--log")) {
				mchProperties.setInputFormat(InputFormat.LOG);
				continue;
			}
			
			// default
			if (args[i].equals("-p") || args[i].equals("--prism")) {
				mchProperties.setModelChecker(ModelChecker.PRISM);
				continue;
			}			
			
			// default
			if (args[i].equals("-r") || args[i].equals("--result")) {
				mchProperties.setInputFormat(InputFormat.RESULT);
				continue;
			}							
			
		}	

		if (inputIndex != -1)
			mchProperties.generateFilenames(args[inputIndex]);
		else
			error = 1;
		
		return error;
	}	
	
	private void loadProperties() {
		mchProperties.loadProperties();
	}		
	
	private void execute() {
		ModelChecker modelChecker = getMCHProperties().getModelChecker();
		parsingMethod = ParsingMethodFactory.buildMethod(modelChecker,getMCHProperties());
		parsingMethod.execute();
	}
	
	public static void main(String[] args) {
		programInfo();
		
		if (args.length <= 0) {
			usageInfo();
			return;
		}
		
		ModelCheckingHelper mchelper = ModelCheckingHelper.getInstance();
		mchelper.loadProperties();
		int errorCode = mchelper.parseOptions(args);
		
		if (mchelper.getMCHProperties().getInputFilename() != null && errorCode == NO_ERROR) {
			System.out.println("Input filename given: "+mchelper.getMCHProperties().getInputFilename());
		}
		else {
			usageInfo();
			return;
		}
		
		mchelper.execute();		
	}

}
