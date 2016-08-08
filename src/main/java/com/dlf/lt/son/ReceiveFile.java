package com.dlf.lt.son;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

import com.dlf.lt.Address;
import com.dlf.lt.ConfigUtil;
import com.dlf.lt.mather.SendFile;

public class ReceiveFile {
	public static void main(String[] args) {
		
//		new Thread(new Runnable() {
//			
//			@Override
//			public void run() {
//				receiveFile();
//				
//			}
//		}).start();
//		
//		receiveObject();
		restartMyTomcat();
	}
	
	public static void receiveObject(){
		 ServerSocket server;
		try {
			server = new ServerSocket(ConfigUtil.toSonTrunkAddressList);
			while (true) {
	            Socket socket = server.accept();  
	            invoke(socket);  
	        }  
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void invoke(final Socket socket) {
		new Thread(new Runnable() {
			public void run() {
				ObjectInputStream is = null;

				try {
					is = new ObjectInputStream(new BufferedInputStream(
							socket.getInputStream()));
					Object obj = is.readObject();
					Address address = (Address) obj;
					System.out.println("我收到 "+address);
					SendFile.sendfile(ConfigUtil.getLocalFileLocation(), address,socket.getInetAddress().getHostAddress());
				} catch (Exception ex) {
					ex.printStackTrace();
				} finally {
					try {
						is.close();
						socket.close();
					} catch (Exception ex) {
					}

				}
			}
		}).start();
	}  
		
	

	public static void receiveFile(){
		try {
			  //使用本地文件系统接受网络数据并存为新文件
			FileOutputStream outSTr = new FileOutputStream(new File(ConfigUtil.getLocalFileLocationSon()));   

			BufferedOutputStream bos=new BufferedOutputStream(outSTr);   
	       
	        
			// 创建网络服务器接受客户请求
			ServerSocket ss = new ServerSocket(ConfigUtil.toSon);
			Socket client = ss.accept();
			
	        // 通过Socket连接文件服务器
	 //       Socket server=new Socket(InetAddress.getLocalHost(),3108);
	       
	        //创建网络接受流接受服务器文件数据
	        InputStream netIn=client.getInputStream();
	        InputStream in=new DataInputStream(new BufferedInputStream(netIn));
	       
	        //创建缓冲区缓冲网络数据
	        byte[] buf=new byte[2048];
	        int num=in.read(buf);
	       
	        while(num!=(-1)){//是否读完所有数据
	        	bos.write(buf);
	               num=in.read(buf);//继续从网络中读取文件
	        }
	        in.close();
	        bos.close();
	        
	        restartMyTomcat();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	//参考资料 http://java0note.blog.51cto.com/469557/109839
	//http://wenku.baidu.com/link?url=xndX6BSzgVasvy9FX1gf2XLSTq-euP1wwfrH0k2xKSkzLe9QOLlryZUZbq9a8LPuNbAub9lYRNjVlj-xanRJle7ENzBrS-a28biE4mMGqc3
	
	/**
	 * 重启本地的tomcat
	 * 参考资料 
	 * http://java0note.blog.51cto.com/469557/109839
	 *  http://wenku.baidu.com/link?url=xndX6BSzgVasvy9FX1gf2XLSTq-euP1wwfrH0k2xKSkzLe9QOLlryZUZbq9a8LPuNbAub9lYRNjVlj-xanRJle7ENzBrS-a28biE4mMGqc3
	 */
	public static void restartMyTomcat() {
		String location=ConfigUtil.getRestartLocation();
		
		createCmdFile(location);
		executeCmd(location);
		  
		
	}

	private static void executeCmd(String location) {
		System.out.println(location);
		Runtime run = Runtime.getRuntime();
		try {
			Process ps = run.exec("" + location + "\\bin\\restart.bat");
			//我很奇怪  下面的代码去掉的话 tomcat的黑框就不能出现  
			BufferedReader br = new BufferedReader(new InputStreamReader(
					ps.getInputStream(), "GBK"));// 注意中文编码问题
			String line;
			while ((line = br.readLine()) != null) {
				System.out.println("StartedLog==>" + line);
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static void createCmdFile(String location) {
		File f = new File(location + "\\bin\\restart.bat");
		try {
			FileWriter fw = new FileWriter(f);
			BufferedWriter bw = new BufferedWriter(fw);
			 //下面的必须加上
			bw.write("set CATALINA_HOME=" + location);
			bw.newLine();
			bw.write("call " + f.getParent() + "\\bin\\shutdown.bat");
			bw.newLine();
			bw.write(" ping 127.0.0.1 -n 5  1>nul ");
			bw.newLine();
			bw.write("call " + f.getParent() + "\\bin\\startup.bat ");

			bw.close();
			fw.close();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

}
