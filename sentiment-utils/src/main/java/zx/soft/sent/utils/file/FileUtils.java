package zx.soft.sent.utils.file;

import java.io.File;
import java.io.IOException;

/**
 * 文件和目录工具类
 * 
 * @author wanggang
 *
 */
public class FileUtils {

	/**
	 * 判断目录是否存在
	 */
	public static boolean isExisted(String path) {
		return new File(path).exists();
	}

	/**
	 * 创建目录
	 */
	public static void createPath(String path) {
		try {
			new File(path).createNewFile();
		} catch (IOException e) {
			//
		}
	}

	/**
	 * 删除目录
	 */
	public static void deletePath(String path) {
		File file = new File(path);
		if (file.isDirectory()) {
			String[] files = file.list();
			for (String f : files) {
				deletePath(file.getAbsolutePath() + "/" + f);
			}
		}
		file.delete();
	}

}
