package util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {
	
    public static List<String> getFiles(String path) {
        List<String> files = new ArrayList<String>();
        File file = new File(path);
        File[] tempList = file.listFiles();

        for (int i = 0; i < tempList.length; i++) {
            if (tempList[i].isFile()) {
                files.add(tempList[i].toString());
                //文件名，不包含路径
                //String fileName = tempList[i].getName();
            }
        }
        return files;
    }
    
    public static List<String> getDirs(String path) {
        List<String> dirs = new ArrayList<String>();
        File file = new File(path);
        File[] tempList = file.listFiles();

        for (int i = 0; i < tempList.length; i++) {

            if (tempList[i].isDirectory()) {
                //这里就不递归了，
            	dirs.add(tempList[i].toString());
            }
        }
        return dirs;
    }
    

    public static String ext(String filename) {
        int index = filename.lastIndexOf(".");
 
        if (index == -1) {
            return null;
        }
        String result = filename.substring(index + 1);
        return result;
    }
    

    public static long getTotalSizeOfFilesInDir(File file) {
        if (file.isFile())
            return file.length();
        final File[] children = file.listFiles();
        long total = 0;
        if (children != null)
            for (final File child : children)
                total += getTotalSizeOfFilesInDir(child);
        return total;

    }

    public static String addslashes(String temp) {
        return temp.replace("\\", "\\\\");
    }

    public static void main(String[] args) {
		
	}
}
