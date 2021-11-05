package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import com.mchange.io.FileUtils;

public class OperUtil {
	
    public static void delete(File directory) {
    	if (!directory.isDirectory()){
            directory.delete();
        } else{
            File [] files = directory.listFiles();

            if (files.length == 0){
                directory.delete();
                return;
            }

            for (File file : files){
                if (file.isDirectory()){
                    delete(file);
                } else {
                    file.delete();
                }
            }

            directory.delete();
            return;
        }
    }
    
    public static boolean create(String path, String dirname) {
    	String cpath = path+System.getProperty("file.separator")+dirname;
		
    	File file = new File(cpath);
		boolean res = false;
    	if (file.exists()){
    		return res;
    	}
    	try{
    		res = file.mkdir();
    	} catch (Exception e){
    		e.printStackTrace();
			return res;
    	}
    	return res;
    }
    

    public static boolean share(String path, String filename, String dest) {
    	String sep = System.getProperty("file.separator");
    	File source = new File(path+sep+filename);
    	File tofile = new File(dest+sep+filename);
    	if(tofile.exists()) {
    		return false;
    	}
    	try {
    		copyFile(source,dest);
    	}catch (Exception e){
    		e.printStackTrace();
    	}
    	return true;
    }
    
	public static void copyFile(File source,String dest )throws IOException{
		//创建目的地文件夹
		File destfile = new File(dest);
		if(!destfile.exists()){
			destfile.mkdir();
		}
		//如果source是文件夹，则在目的地址中创建新的文件夹
		if(source.isDirectory()){
			File file = new File(dest+System.getProperty("file.separator")+source.getName());//用目的地址加上source的文件夹名称，创建新的文件夹
			file.mkdir();
			//得到source文件夹的所有文件及目录
			File[] files = source.listFiles();
			if(files.length==0){
				return;
			}else{
				for(int i = 0 ;i<files.length;i++){
					copyFile(files[i],file.getPath());
				}
			}
			
		}
		//source是文件，则用字节输入输出流复制文件
		else if(source.isFile()){
			FileInputStream fis = new FileInputStream(source);
			//创建新的文件，保存复制内容，文件名称与源文件名称一致
			File dfile = new File(dest+System.getProperty("file.separator")+source.getName());
			if(!dfile.exists()){
				dfile.createNewFile();
			}
			
			FileOutputStream fos = new FileOutputStream(dfile);
				// 读写数据
				// 定义数组
				byte[] b = new byte[1024];
				// 定义长度
				int len;
				// 循环读取
				while ((len = fis.read(b))!=-1) {
					// 写出数据
					fos.write(b, 0 , len);
				}

				//关闭资源
				fos.close();
				fis.close();
			
		}
	}
    
}
