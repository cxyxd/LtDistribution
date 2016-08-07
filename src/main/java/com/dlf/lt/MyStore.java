package com.dlf.lt;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class MyStore {
	
	 public static List<Address> allSons=(List<Address>) Collections.synchronizedList(ConfigUtil.getAllSon());
	 public static List<Address> notGetFilesSon=(List<Address>) Collections.synchronizedList(ConfigUtil.getAllSon());
	 public static List<Address> getFilesSon=(List<Address>) Collections.synchronizedList(new LinkedList<Address>());
	 
	 
	 public static void main(String[] args) {
		System.out.println(getFilesSon.size());
		System.out.println(getFilesSon.size());
		System.out.println(getFilesSon.size());
	}

}
