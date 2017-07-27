package com.coe.wms.common;

import java.util.ArrayList;
import java.util.List;

public class TestList {
	public static void main(String[] args) {
		List<String> testList = new ArrayList<String>();
		testList.add("0");
		testList.add("1");
		testList.add("2");
		for (int i = 0; i < 5; i++) {
			String remove = testList.remove(0);
			System.out.println(remove);
		}
		
	}
}
