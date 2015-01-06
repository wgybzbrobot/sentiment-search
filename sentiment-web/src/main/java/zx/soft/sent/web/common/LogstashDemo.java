package zx.soft.sent.web.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import redis.clients.jedis.Jedis;
import zx.soft.utils.json.JsonUtils;

public class LogstashDemo {

	public static void main(String[] args) {
		Jedis jedis = new Jedis("192.168.31.160", 7000);
		String key = "nginx-logs";
		List<String> logs = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader(new File("access.log")));) {
			String line;
			String[] strs;
			while ((line = br.readLine()) != null) {
				strs = line.split("-");
				NginxLog log = new NginxLog();
				log.setRemote_addr(strs[0]);
				log.setTime_local(strs[1]);
				log.setRequest_length(strs[2]);
				log.setStatus(strs[3]);
				log.setRequest_time(strs[4]);
				log.setBody_bytes_sent(strs[5]);
				logs.add(JsonUtils.toJsonWithoutPretty(log));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		jedis.lpush(key, logs.toArray(new String[logs.size()]));
		jedis.close();
	}

}
