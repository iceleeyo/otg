package com.springtour.otg.infrastructure.channel.cmbc;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class AssembleRequestParamsForEncryptionUnitTests {
	
	private String EMPTY_STR = "";
	private AssembleRequestParamsForEncryption target = new AssembleRequestParamsForEncryption();
	
	@Test
	public void returnEmptyStrGivenEmptyList(){
		ArrayList<String> params = new ArrayList<String>();
		String actual = target.assemble(params);
		assertEquals(EMPTY_STR ,  actual);
	}
	
	
	@Test
	public void returnSeperatorStrGivenListWithOnlyOneEmptyStr(){
		ArrayList<String> params = new ArrayList<String>();
		params.add(EMPTY_STR);
		String actual = target.assemble(params);
		assertEquals(EMPTY_STR ,  actual);
	}
	
	
	@Test
	public void returnSStrGivenListWithTwoStrs(){
		ArrayList<String> params = new ArrayList<String>();
		params.add("b");
		params.add("a");
		String actual = target.assemble(params);
		assertEquals("b|a" ,  actual);
	}
	

	@Test
	public void returnSStrGivenListWithThreeStrs(){
		ArrayList<String> params = new ArrayList<String>();
		params.add("b");
		params.add(EMPTY_STR);
		params.add(EMPTY_STR);
		params.add("a");
		String actual = target.assemble(params);
		assertEquals("b|||a" ,  actual);
	}
	
	@Test
	public void returnSStrGivenListWithThreeStrs2(){
		ArrayList<String> params = new ArrayList<String>();
		params.add("b");
		params.add("a");
		params.add(EMPTY_STR);
		String actual = target.assemble(params);
		assertEquals("b|a|" ,  actual);
	}

}
