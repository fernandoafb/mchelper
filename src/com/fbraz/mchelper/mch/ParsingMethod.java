package com.fbraz.mchelper.mch;

import com.fbraz.mchelper.misc.IOHelper;

public abstract class ParsingMethod {
	
	private MCHProperties mchProperties;
	protected IOHelper ioHelper;
	
	public ParsingMethod() {
	}
	
	public ParsingMethod(MCHProperties mchProperties) {
		this.mchProperties = mchProperties;
	}

	public MCHProperties getMCHProperties() {
		return mchProperties;
	}

	public void setMCHProperties(MCHProperties mchProperties) {
		this.mchProperties = mchProperties;
	}
	
	public IOHelper getIoHelper() {
		return ioHelper;
	}

	public void setIoHelper(IOHelper ioHelper) {
		this.ioHelper = ioHelper;
	}
	
	public abstract void execute();
	
}
