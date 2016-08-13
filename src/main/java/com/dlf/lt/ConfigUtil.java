package com.dlf.lt;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ConfigUtil {
	
	public static int tellMatherOK=8000;
	public static int toSonTrunkAddressList=8001;
	public static int toSon=8002;
	

	public static int getVersion() {
		File f=null;
		try {
			//如果不采用tomcat 下面的代码会报错 使用catch里面的file
			//如果使用tomcat 就使用下面的代码
			String path=ConfigUtil.class.getClassLoader().getResource("/version.txt").getFile();
			f = new File(path);
		} catch (Exception e) {
			f = new File("src/main/resources/version.txt");
		}
		BufferedReader br=null;
		try {
			br=new BufferedReader(new FileReader(f));
			String version=br.readLine();
			System.out.println(" i get"+version);
			return Integer.valueOf(version);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return 0;
	}
	
	public static void updateVersion(){
		File f=null;
		try {
			//如果不采用tomcat 下面的代码会报错 使用catch里面的file
			//如果使用tomcat 就使用下面的代码
			String path=ConfigUtil.class.getClassLoader().getResource("/version.txt").getFile();
			f = new File(path);
		} catch (Exception e) {
			f = new File("src/main/resources/version.txt");
		}
		BufferedWriter br=null;
		try {
			int previousVersion=getVersion();
			br=new BufferedWriter(new FileWriter(f));
			br.write(""+(previousVersion+1));
			br.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	
	public static String getLocalFileLocation() {
		 if (bundle==null) 
			 bundle=ResourceBundle.getBundle("localFileLocation");
		
		 String s = bundle.getString("location");  
		 return s;
	}
	


	public static String getLocalFileLocationSon() {
		 if (bundle==null) 
			 bundle=ResourceBundle.getBundle("localFileLocation");
		
		 String s = bundle.getString("locationSon");  
		 return s;
	}
	
	
	public static String getMyIp() {
		if (bundle == null) 
			bundle = ResourceBundle.getBundle("localFileLocation");
		
		String s = bundle.getString("ip");
		return s;
	}
	
	public static int getMyPort() {
		if (bundle == null) 
			bundle = ResourceBundle.getBundle("localFileLocation");
		
		int s = Integer.valueOf(bundle.getString("port"));
		return s;
	}
	
	public static Address getMyAddress(){
		Address address=new Address();
		address.setIp(getMyIp());
		address.setPort(getMyPort());
		address.setTomcat(getLocalFileLocation());
		return address;
	}
	
	public static String getRestartLocation(){
		if (bundle == null) 
			bundle = ResourceBundle.getBundle("localFileLocation");
		
		String s = bundle.getString("tomcatRestart");
		return s;
	}
	
	/**
	 * @return
	 */
	public static String getProjectName() {
		if (bundle == null) 
			bundle = ResourceBundle.getBundle("localFileLocation");
		
		String s = bundle.getString("projectName");
		return s;
	}
	
	private static List<Address> allSons;
	private static  ResourceBundle bundle;  
	 
	public static List<Address> getAllSon() {
		if (allSons != null) {
			return allSons;
		}

		allSons = new LinkedList<Address>();

		// 获得根元素下的子节点
		NodeList childNodes = createNodeList();
		// 遍历这些子节点

		for (int i = 0; i < childNodes.getLength(); i++){
			Address address=getOneSon(childNodes, i);
			if (address!=null) {
				allSons.add(address);
			}
			
		}
		System.out.println("从xml中读到:");
		for (int i = 0; i < allSons.size(); i++)
			System.out.println(allSons.get(i));
		System.out.println("******");
		return allSons;
	}

	private static NodeList createNodeList() {
		Element element = null;
		File f=null;
		try {
			//如果不采用tomcat 下面的代码会报错 使用catch里面的file
			//如果使用tomcat 就使用下面的代码
			String path=ConfigUtil.class.getClassLoader().getResource("/allSons.xml").getFile();
			f = new File(path);
		} catch (Exception e) {
			f = new File("src/main/resources/allSons.xml");
		}
		
		
		// documentBuilder为抽象不能直接实例化(将XML文件转换为DOM文件)
		DocumentBuilder db = null;
		DocumentBuilderFactory dbf = null;
		allSons = new ArrayList<Address>();
		try {
			// 返回documentBuilderFactory对象
			dbf = DocumentBuilderFactory.newInstance();
			// 返回db对象用documentBuilderFatory对象获得返回documentBuildr对象
			db = dbf.newDocumentBuilder();
			// 得到一个DOM并返回给document对象
			Document dt = db.parse(f);
			// 得到一个elment根元素
			element = dt.getDocumentElement();
			// 获得根节点
		//	System.out.println("根元素：" + element.getNodeName());
			return element.getChildNodes();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}
	private static Address getOneSon(NodeList childNodes, int i) {
		
		// 获得每个对应位置i的结点
		Node node1 = childNodes.item(i);
		if ("son".equals(node1.getNodeName())) {
			Address add = new Address();
			// 获得<Accounts>下的节点
			NodeList nodeDetail = node1.getChildNodes();
			// 遍历<Accounts>下的节点
			for (int j = 0; j < nodeDetail.getLength(); j++) {
				// 获得<Accounts>元素每一个节点
				Node detail = nodeDetail.item(j);
				if ("ip".equals(detail.getNodeName()))
					add.setIp(detail.getTextContent());
				if ("port".equals(detail.getNodeName()))
					add.setPort(Integer.valueOf( detail.getTextContent() ));
				if ("tomcat".equals(detail.getNodeName()))
					add.setTomcat(detail.getTextContent());

			}
			return add;
		}
		return null;
		
	}
	
	public static void main(String[] args) {

	}


}
