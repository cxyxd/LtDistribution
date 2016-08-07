package com.dlf.lt.mather;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dlf.lt.Address;
import com.dlf.lt.ConfigUtil;
import com.dlf.lt.MyStore;

public class DistributeAllFiles extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1601294912109842784L;


	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request, response);
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
//		 List<Address> allSons=XmlUtil.getAllSon();
//		 List<Address> notGetFilesSon=XmlUtil.getAllSon();
//		 List<Address> getFilesSon=new LinkedList<Address>();
		watiUpdate();
		 int count=(int) Math.sqrt(MyStore.notGetFilesSon.size()*1.0);
		 for (int i = 0; i < MyStore.notGetFilesSon.size();  i+=count) 
			 SendFile.sendfile(ConfigUtil.getLocalFileLocation(),MyStore.notGetFilesSon.get(i), "mather");
		
		 for (int i = 0; i < MyStore.notGetFilesSon.size(); i+=count) {
			 List<Address> list=MyStore.notGetFilesSon.subList(i, i+count-1);
			 SendFile.sendObject(MyStore.notGetFilesSon.get(i),list);
		 }	 
	}
	public static void main(String[] args) {
		

			watiUpdate();
		
		 int count=(int) Math.sqrt(MyStore.notGetFilesSon.size()*1.0);
		 for (int i = 0; i < MyStore.notGetFilesSon.size();  i+=count) 
			 SendFile.sendfile(ConfigUtil.getLocalFileLocation(),MyStore.notGetFilesSon.get(i), "mather");
		
		 for (int i = 0; i < MyStore.notGetFilesSon.size(); i+=count) {
			 List<Address> list=MyStore.notGetFilesSon.subList(i, i+count-1);
			 list=Collections.synchronizedList(list);
			 SendFile.sendObject(MyStore.notGetFilesSon.get(i),list);
		 }	
	}

	private static void watiUpdate() {
		new  Thread(new Runnable() {
			@Override
			public void run() {
				UpdateMyStore.waiteUpdate();
				
			}
		}).start();
		
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}


}
