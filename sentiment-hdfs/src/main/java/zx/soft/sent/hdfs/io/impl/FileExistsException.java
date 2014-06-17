package zx.soft.sent.hdfs.io.impl;

/**
 * 文件存在异常
 * 
 * @author wanggang
 *
 */
public class FileExistsException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public FileExistsException(String file) {
		super("File " + file + " exists");
	}

}
