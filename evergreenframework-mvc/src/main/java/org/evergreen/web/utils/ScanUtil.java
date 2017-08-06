package org.evergreen.web.utils;

import java.io.File;
import java.net.URL;
import java.util.*;

/**
 * class扫描工具
 * Created by wangl on 2017/7/6.
 */
public class ScanUtil {

	private static final List<String> classNames = new ArrayList<String>();

	/**
	 * 获取指定包下以及子包中所有的类
	 *
	 * @param packageName 包名
	 * @return 所有的完整类名
	 */
	public static List<String> scan(String packageName) {
		packageName = packageName == null ? "" : packageName.trim();
		String packagePath = packageName.replace(".", "/");
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		URL url = loader.getResource(packagePath);
		if (url != null) {
			scanFromDir(url.getPath(), packageName);
		}
		return classNames;
	}

	/**
	 * 从项目文件获取某包下所有类
	 *
	 * @param filePath 文件目录
	 * @param packageName 包名
	 */
	private static void scanFromDir(String filePath, String packageName) {
		File[] files = new File(filePath).listFiles();
		packageName = packageName + ".";
		for (File childFile : files) {
			if (childFile.isDirectory()) {
				scanFromDir(childFile.getPath(), packageName + childFile.getName());
			} else {
				String fileName = childFile.getName();
				if (fileName.endsWith(".class")) {
					if(packageName.charAt(0) == '.'){
						packageName = packageName.substring(1, packageName.length());
					}
					String className = packageName + fileName.replace(".class", "");
					classNames.add(className);
				}
			}
		}
	}


	public static void main(String[] args) throws Exception {
		List<String> classNames = scan("");
		for (String className : classNames) {
			System.out.println(className);
		}
	}

}