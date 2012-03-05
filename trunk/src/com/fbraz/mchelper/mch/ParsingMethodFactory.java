package com.fbraz.mchelper.mch;

import com.fbraz.mchelper.misc.ModelChecker;
import com.fbraz.mchelper.prism.PrismParser;

public class ParsingMethodFactory {
	
	public ParsingMethodFactory() {
		
	}
	
	public static ParsingMethod buildMethod(ModelChecker modelChecker, MCHProperties mchProperties) {
		switch (modelChecker) {
			case BIOLAB: return null;
			case NUSMV: return null;
			case PRISM: return new PrismParser(mchProperties);
			case SMV: return null;
			case YMER: return null;
			default: return null; 
		}
	}
	
}
