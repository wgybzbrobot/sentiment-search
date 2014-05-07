package zx.soft.sent.solr.statistic;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CompanyMain {

	private static Logger logger = LoggerFactory.getLogger(CompanyMain.class);

	public static void main(String[] args) {

		CompanyQuery searchData = new CompanyQuery();
		CompanyData companyData = new CompanyData();
		HashMap<String, String> company = companyData.getCompany();
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(new File("company_all.txt")));) {
			int count = 0;
			for (Entry<String, String> temp : company.entrySet()) {
				logger.info(count++ + "");
				// 总数
				long all = searchData.queryData(temp.getValue(), 0);
				// 好消息
				long good = searchData.queryData(temp.getValue(), 1);
				// 坏消息
				long bad = searchData.queryData(temp.getValue(), 2);
				bw.newLine();
				bw.append(temp.getKey() + "     " + temp.getValue() + "     " + all + "     " + good + "     " + bad);
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}
}
