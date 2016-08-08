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
