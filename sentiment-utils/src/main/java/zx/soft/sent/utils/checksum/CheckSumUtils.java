package zx.soft.sent.utils.checksum;

import java.util.zip.CRC32;
import java.util.zip.Checksum;

/**
 * 校验工具类
 * @author wanggang
 *
 */
public class CheckSumUtils {

	/**
	 * CRC32校验
	 */
	public static long getCRC32(String str) {
		// get bytes from string
		byte bytes[] = str.getBytes();
		Checksum checksum = new CRC32();
		// update the current checksum with the specified array of bytes
		checksum.update(bytes, 0, bytes.length);
		// get the current checksum value
		return checksum.getValue();
	}

}
