package com.dlf.lt;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;

public class TomcatUtil {
	
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
			
			//删除命令执行后 cmd就退出了 没办法 只能改成两个命令
			createStopCmdFileAndDeleteDirectory(location);
			executeCmd(location);
			deltetFile();
			createStartCmdFile(location);
			executeCmd(location);
			System.out.println(location);
			  
			
		}
		public static void main(String[] args) {
			String location=ConfigUtil.getRestartLocation();
			
			
			//删除命令执行后 cmd就退出了 没办法 只能改成两个命令
			createStopCmdFileAndDeleteDirectory(location);
			executeCmd(location);
			deltetFile();
			createStartCmdFile(location);
			executeCmd(location);
			System.out.println(location);
			
		}
		


		/**
		 * @param location
		 */
		private static void createStartCmdFile(String location) {

			File f = new File(location + "\\bin\\restart.bat");
			try {
				FileWriter fw = new FileWriter(f);
				BufferedWriter bw = new BufferedWriter(fw);
				 //下面的必须加上
				bw.write("set CATALINA_HOME=" + location);
				bw.newLine();
				bw.write(" ping 127.0.0.1 -n 5  1>nul ");
				bw.newLine();
				bw.write("call " + f.getParent() + "\\startup.bat ");

				bw.close();
				fw.close();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		
			
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

		private static void createStopCmdFileAndDeleteDirectory(String location) {
			File f = new File(location + "\\bin\\restart.bat");
			String projectName=ConfigUtil.getProjectName();
			try {
				FileWriter fw = new FileWriter(f);
				BufferedWriter bw = new BufferedWriter(fw);
				 //下面的必须加上
				bw.write("set CATALINA_HOME=" + location);
				bw.newLine();
				bw.write("call " + f.getParent() + "\\shutdown.bat");
				bw.newLine();
				String location2=location+"\\webapps\\"+projectName;
				location2=location2.replaceAll("/","\\\\"); 
				bw.write("rd /s/q " + location2);

				bw.close();
				fw.close();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}
		
		/**
		 * delete war
		 */
		private static void deltetFile() {
			File f=new File(ConfigUtil.getLocalFileLocationSon());
			f.delete();
		}



}
