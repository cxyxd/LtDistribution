package com.dlf.lt.mather;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dlf.lt.Address;

public class DistributeFile extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5412958352930488893L;


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
	//	watiUpdate();
		String ip=request.getParameter("ip");
		Address add=new Address();
		add.setIp(ip);
		File file1 = new File(getServletContext().getRealPath(
				"attachment"), "newWar.jpg");
		System.out.println(file1.getPath()+"  "+file1.getName());
		SendFile.sendfile(file1.getPath(), add, "mather");
		 
	}


	

}
