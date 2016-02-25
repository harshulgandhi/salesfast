package com.stm.salesfast.constant;

public enum PhysicianStatus {
	PRESCRIBING("prescribing"),
	PROSPECTING("prospecting"),
	LOST("lost");
	
	private final String stringValue;
	
	private PhysicianStatus(final String s){ this.stringValue = s;}
	
	@Override
	public String toString(){
		return stringValue;
	}
}
