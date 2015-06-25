package zx.soft.sent.solr.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import zx.soft.utils.chars.JavaPattern;
import zx.soft.utils.json.JsonUtils;

public class Site2Json {

	public static void main(String[] args) {

		List<String> data = new ArrayList<>();

		try (BufferedReader br = new BufferedReader(new FileReader(new File("st.csv")));) {
			String str;
			String[] strs;
			while ((str = br.readLine()) != null) {
				str = str.substring(1, str.length() - 1);
				strs = str.split(":");
				String ids = "";
				for (int i = 1; i < strs.length - 1; i++) {
					//					System.out.print(strs[i].split("#")[0] + "    ");
					if (strs[i].split("#")[0].length() != 0) {
						data.add(strs[i].split("#")[0]);
						ids += strs[i].split("#")[0] + ",";
					}
				}
				//				System.out.print(strs[strs.length - 1] + "    ");
				if (strs[strs.length - 1].length() != 0) {
					data.add(strs[strs.length - 1]);
					ids += strs[strs.length - 1] + ",";
				}
				ids = ids.substring(0, ids.length() - 1);
				data.add(ids);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println(JsonUtils.toJson(data));

		for (String d : data) {
			if (!JavaPattern.isAllNum(d.replaceAll(",", ""))) {
				System.out.println(d);
			}
		}

	}

}
