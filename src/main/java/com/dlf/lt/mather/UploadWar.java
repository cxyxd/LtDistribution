package com.dlf.lt.mather;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.json.JSONException;
import org.json.JSONObject;

import com.dlf.lt.ConfigUtil;

public class UploadWar extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5749394505803109645L;

	/**
	 * Constructor of the object.
	 */
	public UploadWar() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
		out.println("  <BODY>");
		out.print("    This is ");
		out.print(this.getClass());
		out.println(", using the GET method");
		out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
	}

	/**
	 * The doPost method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to
	 * post.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		File file1 = null;
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");

		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		try {

			List<FileItem> list = upload.parseRequest(request); // 解析request请求
			System.out.println(list.size() + " size");
			for (FileItem fileItem : list) {
				if (fileItem.getFieldName().equals("file_data")) {
					file1 = new File(getServletContext().getRealPath(
							"attachment"), "newWar.jpg");
					file1.getParentFile().mkdirs();
					file1.createNewFile();
					System.out.println(fileItem.getName() + " psd");
					InputStream ins = fileItem.getInputStream();
					OutputStream ous = new FileOutputStream(file1);
					try {
						byte[] buffer = new byte[1024];
						int len = 0;
						while ((len = ins.read(buffer)) > -1)
							ous.write(buffer, 0, len);
					} finally {
						ous.close();
						ins.close();
					}
				}

			}
		} catch (FileUploadException e) {
			e.printStackTrace();
		}

		ConfigUtil.updateVersion();
		request.getSession().setAttribute("version", ConfigUtil.getVersion());
		JSONObject jsonObject = new JSONObject();
		
		try {
			jsonObject.put("result", "ok");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		response.getWriter().write(jsonObject.toString());

	}

	/**
	 * Initialization of the servlet. <br>
	 * 
	 * @throws ServletException
	 *             if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
