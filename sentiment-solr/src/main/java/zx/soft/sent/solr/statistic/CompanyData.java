package zx.soft.sent.solr.statistic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;

/**
 * 公司信息
 * 
 * @author wanggang
 *
 */
public class CompanyData {

	private final HashMap<String, String> company;

	public CompanyData() {
		company = new HashMap<>();
		int index;
		String str;
		try (BufferedReader br = new BufferedReader(new FileReader(new File("company/data")));) {
			while ((str = br.readLine()) != null) {
				index = str.indexOf("	");
				company.put(str.substring(0, index), str.substring(index + 1));
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static void main(String[] args) {

		CompanyData companyData = new CompanyData();
		HashMap<String, String> company = companyData.getCompany();
		for (Entry<String, String> temp : company.entrySet()) {
			System.out.println(temp.getKey() + " " + temp.getValue());
		}

	}

	public HashMap<String, String> getCompany() {
		return company;
	}

}
