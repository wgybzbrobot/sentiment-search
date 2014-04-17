package zx.soft.sentiment.hdfs.common;

public class FileExistsException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public FileExistsException(String file) {
		super("File " + file + " exists");
	}

}
