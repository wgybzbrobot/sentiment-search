package zx.soft.sent.solr.statistic;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CompanyMain {

	private static Logger logger = LoggerFactory.getLogger(CompanyMain.class);

	private CompanyQuery searchData = new CompanyQuery();
	private CompanyData companyData = new CompanyData();

	private static final String GOOD_NEWS = "盈利、景气、受益、回暖、增长、突破、优化、转型、扩张、利好、优势、核心技术、政策倾斜、需求旺盛、前景广阔";

	private static final String BAD_NEWS = "亏损、淡季、低迷、风险、损失、差距、下跌、低谷、过剩、回落、萧条、疲软、复苏乏力、增速放缓、业绩下滑";

	private final String[] FACET_QUERY_BY_YEAR = { "{!key=\"2013\"}timestamp:[NOW/YEAR-1YEAR TO NOW/YEAR]",
			"{!key=\"2012\"}timestamp:[NOW/YEAR-2YEAR TO NOW/YEAR-1YEAR]",
			"{!key=\"2011\"}timestamp:[NOW/YEAR-3YEAR TO NOW/YEAR-2YEAR]",
			"{!key=\"2010\"}timestamp:[NOW/YEAR-4YEAR TO NOW/YEAR-3YEAR]",
			"{!key=\"2009\"}timestamp:[NOW/YEAR-5YEAR TO NOW/YEAR-4YEAR]" };

	private final String[] FACET_QUERY_BY_HALF_YEAR = { "{!key=\"2013down\"}timestamp:[NOW/MONTH-6MONTH TO NOW/MONTH]",
			"{!key=\"2013up\"}timestamp:[NOW/MONTH-12MONTH TO NOW/MONTH-6MONTH]",
			"{!key=\"2012down\"}timestamp:[NOW/MONTH-18MONTH TO NOW/MONTH-12MONTH]",
			"{!key=\"2012up\"}timestamp:[NOW/MONTH-24MONTH TO NOW/MONTH-18MONTH]",
			"{!key=\"2011down\"}timestamp:[NOW/MONTH-30MONTH TO NOW/MONTH-24MONTH]",
			"{!key=\"2011up\"}timestamp:[NOW/MONTH-36MONTH TO NOW/MONTH-30MONTH]",
			"{!key=\"2010down\"}timestamp:[NOW/MONTH-42MONTH TO NOW/MONTH-36MONTH]",
			"{!key=\"2010up\"}timestamp:[NOW/MONTH-48MONTH TO NOW/MONTH-42MONTH]",
			"{!key=\"2009down\"}timestamp:[NOW/MONTH-54MONTH TO NOW/MONTH-48MONTH]",
			"{!key=\"2009up\"}timestamp:[NOW/MONTH-60MONTH TO NOW/MONTH-54MONTH]" };

	private final String[] FACET_QUERY_BY_QUARTER = { "{!key=\"2013q4\"}timestamp:[NOW/MONTH-3MONTH TO NOW/MONTH]",
			"{!key=\"2013q3\"}timestamp:[NOW/MONTH-6MONTH TO NOW/MONTH-3MONTH]",
			"{!key=\"2013q2\"}timestamp:[NOW/MONTH-9MONTH TO NOW/MONTH-6MONTH]",
			"{!key=\"2013q1\"}timestamp:[NOW/MONTH-12MONTH TO NOW/MONTH-9MONTH]",
			"{!key=\"2012q4\"}timestamp:[NOW/MONTH-15MONTH TO NOW/MONTH-12MONTH]",
			"{!key=\"2012q3\"}timestamp:[NOW/MONTH-18MONTH TO NOW/MONTH-15MONTH]",
			"{!key=\"2012q2\"}timestamp:[NOW/MONTH-21MONTH TO NOW/MONTH-18MONTH]",
			"{!key=\"2012q1\"}timestamp:[NOW/MONTH-24MONTH TO NOW/MONTH-21MONTH]",
			"{!key=\"2011q4\"}timestamp:[NOW/MONTH-27MONTH TO NOW/MONTH-24MONTH]",
			"{!key=\"2011q3\"}timestamp:[NOW/MONTH-30MONTH TO NOW/MONTH-27MONTH]",
			"{!key=\"2011q2\"}timestamp:[NOW/MONTH-33MONTH TO NOW/MONTH-30MONTH]",
			"{!key=\"2011q1\"}timestamp:[NOW/MONTH-36MONTH TO NOW/MONTH-33MONTH]",
			"{!key=\"2010q4\"}timestamp:[NOW/MONTH-39MONTH TO NOW/MONTH-36MONTH]",
			"{!key=\"2010q3\"}timestamp:[NOW/MONTH-42MONTH TO NOW/MONTH-39MONTH]",
			"{!key=\"2010q2\"}timestamp:[NOW/MONTH-45MONTH TO NOW/MONTH-42MONTH]",
			"{!key=\"2010q1\"}timestamp:[NOW/MONTH-48MONTH TO NOW/MONTH-45MONTH]",
			"{!key=\"2009q4\"}timestamp:[NOW/MONTH-51MONTH TO NOW/MONTH-48MONTH]",
			"{!key=\"2009q3\"}timestamp:[NOW/MONTH-54MONTH TO NOW/MONTH-51MONTH]",
			"{!key=\"2009q2\"}timestamp:[NOW/MONTH-57MONTH TO NOW/MONTH-54MONTH]",
			"{!key=\"2009q1\"}timestamp:[NOW/MONTH-60MONTH TO NOW/MONTH-57MONTH]" };

	public CompanyMain() {
		searchData = new CompanyQuery();
		companyData = new CompanyData();
	}

	/**
	 * 主函数
	 */
	public static void main(String[] args) {

		CompanyMain company = new CompanyMain();
		company.downLoadComapnyAll();
		company.downLoadComapnyByYear();
		company.downLoadComapnyByHalfYear();
		company.downLoadComapnyByQuarter();

	}

	public void downLoadComapnyByQuarter() {
		HashMap<String, String> company = companyData.getCompany();
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(new File("company_by_quarter.txt")));) {
			bw.append("company_id     company_name     2013q4     2013q3     2013q2     2013q1     "
					+ "2012q4     2012q3     2012q2     2012q1     2011q4     2011q3     2011q2     2011q1     "
					+ "2010q4     2010q3     2010q2     2010q1     2009q4     2009q3     2009q2     2009q1     ");
			bw.newLine();
			int count = 0;
			for (Entry<String, String> temp : company.entrySet()) {
				logger.info("ComapnyByYear count=" + count++);
				// 总数
				QueryResponse all = searchData.queryData(getQuery(3, temp.getValue(), 0));
				// 好消息
				QueryResponse good = searchData.queryData(getQuery(3, temp.getValue(), 1));
				// 坏消息
				QueryResponse bad = searchData.queryData(getQuery(3, temp.getValue(), 2));

				bw.append(temp.getKey() + "     " + temp.getValue() + "     ");
				// 2013
				bw.append(all.getFacetQuery().get("2013q4") + ",").append(good.getFacetQuery().get("2013q4") + ",")
						.append(bad.getFacetQuery().get("2013q4") + "     ");
				bw.append(all.getFacetQuery().get("2013q3") + ",").append(good.getFacetQuery().get("2013q3") + ",")
						.append(bad.getFacetQuery().get("2013q3") + "     ");
				bw.append(all.getFacetQuery().get("2013q2") + ",").append(good.getFacetQuery().get("2013q2") + ",")
						.append(bad.getFacetQuery().get("2013q2") + "     ");
				bw.append(all.getFacetQuery().get("2013q1") + ",").append(good.getFacetQuery().get("2013q1") + ",")
						.append(bad.getFacetQuery().get("2013q1") + "     ");
				// 2012
				bw.append(all.getFacetQuery().get("2012q4") + ",").append(good.getFacetQuery().get("2012q4") + ",")
						.append(bad.getFacetQuery().get("2012q4") + "     ");
				bw.append(all.getFacetQuery().get("2012q3") + ",").append(good.getFacetQuery().get("2012q3") + ",")
						.append(bad.getFacetQuery().get("2012q3") + "     ");
				bw.append(all.getFacetQuery().get("2012q2") + ",").append(good.getFacetQuery().get("2012q2") + ",")
						.append(bad.getFacetQuery().get("2012q2") + "     ");
				bw.append(all.getFacetQuery().get("2012q1") + ",").append(good.getFacetQuery().get("2012q1") + ",")
						.append(bad.getFacetQuery().get("2012q1") + "     ");

				// 2011
				bw.append(all.getFacetQuery().get("2011q4") + ",").append(good.getFacetQuery().get("2011q4") + ",")
						.append(bad.getFacetQuery().get("2011q4") + "     ");
				bw.append(all.getFacetQuery().get("2011q3") + ",").append(good.getFacetQuery().get("2011q3") + ",")
						.append(bad.getFacetQuery().get("2011q3") + "     ");
				bw.append(all.getFacetQuery().get("2011q2") + ",").append(good.getFacetQuery().get("2011q2") + ",")
						.append(bad.getFacetQuery().get("2011q2") + "     ");
				bw.append(all.getFacetQuery().get("2011q1") + ",").append(good.getFacetQuery().get("2011q1") + ",")
						.append(bad.getFacetQuery().get("2011q1") + "     ");

				// 2010
				bw.append(all.getFacetQuery().get("2010q4") + ",").append(good.getFacetQuery().get("2010q4") + ",")
						.append(bad.getFacetQuery().get("2010q4") + "     ");
				bw.append(all.getFacetQuery().get("2010q3") + ",").append(good.getFacetQuery().get("2010q3") + ",")
						.append(bad.getFacetQuery().get("2010q3") + "     ");
				bw.append(all.getFacetQuery().get("2010q2") + ",").append(good.getFacetQuery().get("2010q2") + ",")
						.append(bad.getFacetQuery().get("2010q2") + "     ");
				bw.append(all.getFacetQuery().get("2010q1") + ",").append(good.getFacetQuery().get("2010q1") + ",")
						.append(bad.getFacetQuery().get("2010q1") + "     ");

				// 2009
				bw.append(all.getFacetQuery().get("2009q4") + ",").append(good.getFacetQuery().get("2009q4") + ",")
						.append(bad.getFacetQuery().get("2009q4") + "     ");
				bw.append(all.getFacetQuery().get("2009q3") + ",").append(good.getFacetQuery().get("2009q3") + ",")
						.append(bad.getFacetQuery().get("2009q3") + "     ");
				bw.append(all.getFacetQuery().get("2009q2") + ",").append(good.getFacetQuery().get("2009q2") + ",")
						.append(bad.getFacetQuery().get("2009q2") + "     ");
				bw.append(all.getFacetQuery().get("2009q1") + ",").append(good.getFacetQuery().get("2009q1") + ",")
						.append(bad.getFacetQuery().get("2009q1") + "     ");

				bw.newLine();
			}
		} catch (IOException e) {
			logger.error(e.getMessage());
			//			throw new RuntimeException(e);
		}
	}

	public void downLoadComapnyByHalfYear() {
		HashMap<String, String> company = companyData.getCompany();
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(new File("company_by_half_year.txt")));) {
			bw.append("company_id     company_name     2013down     2013up     2012down     2012up     "
					+ "2011down     2011up     2010down     2010up     2009down     2009up");
			bw.newLine();
			int count = 0;
			for (Entry<String, String> temp : company.entrySet()) {
				logger.info("ComapnyByYear count=" + count++);
				// 总数
				QueryResponse all = searchData.queryData(getQuery(2, temp.getValue(), 0));
				// 好消息
				QueryResponse good = searchData.queryData(getQuery(2, temp.getValue(), 1));
				// 坏消息
				QueryResponse bad = searchData.queryData(getQuery(2, temp.getValue(), 2));

				bw.append(temp.getKey() + "     " + temp.getValue() + "     ");
				// 2013
				bw.append(all.getFacetQuery().get("2013down") + ",").append(good.getFacetQuery().get("2013down") + ",")
						.append(bad.getFacetQuery().get("2013down") + "     ");
				bw.append(all.getFacetQuery().get("2013up") + ",").append(good.getFacetQuery().get("2013up") + ",")
						.append(bad.getFacetQuery().get("2013up") + "     ");
				// 2012
				bw.append(all.getFacetQuery().get("2012down") + ",").append(good.getFacetQuery().get("2012down") + ",")
						.append(bad.getFacetQuery().get("2012down") + "     ");
				bw.append(all.getFacetQuery().get("2012up") + ",").append(good.getFacetQuery().get("2012up") + ",")
						.append(bad.getFacetQuery().get("2012up") + "     ");
				// 2011
				bw.append(all.getFacetQuery().get("2011down") + ",").append(good.getFacetQuery().get("2011down") + ",")
						.append(bad.getFacetQuery().get("2011down") + "     ");
				bw.append(all.getFacetQuery().get("2011up") + ",").append(good.getFacetQuery().get("2011up") + ",")
						.append(bad.getFacetQuery().get("2011up") + "     ");
				// 2010
				bw.append(all.getFacetQuery().get("2010down") + ",").append(good.getFacetQuery().get("2010down") + ",")
						.append(bad.getFacetQuery().get("2010down") + "     ");
				bw.append(all.getFacetQuery().get("2010up") + ",").append(good.getFacetQuery().get("2010up") + ",")
						.append(bad.getFacetQuery().get("2010up") + "     ");
				// 2009
				bw.append(all.getFacetQuery().get("2009down") + ",").append(good.getFacetQuery().get("2009down") + ",")
						.append(bad.getFacetQuery().get("2009down") + "     ");
				bw.append(all.getFacetQuery().get("2009up") + ",").append(good.getFacetQuery().get("2009up") + ",")
						.append(bad.getFacetQuery().get("2009up") + "     ");
				bw.newLine();
			}
		} catch (IOException e) {
			logger.error(e.getMessage());
			//			throw new RuntimeException(e);
		}
	}

	public void downLoadComapnyByYear() {
		HashMap<String, String> company = companyData.getCompany();
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(new File("company_by_year.txt")));) {
			bw.append("company_id     company_name     2013     2012     2011     2010     2009");
			bw.newLine();
			int count = 0;
			for (Entry<String, String> temp : company.entrySet()) {
				logger.info("ComapnyByYear count=" + count++);
				// 总数
				QueryResponse all = searchData.queryData(getQuery(1, temp.getValue(), 0));
				// 好消息
				QueryResponse good = searchData.queryData(getQuery(1, temp.getValue(), 1));
				// 坏消息
				QueryResponse bad = searchData.queryData(getQuery(1, temp.getValue(), 2));

				bw.append(temp.getKey() + "     " + temp.getValue() + "     ");
				// 2013
				bw.append(all.getFacetQuery().get("2013") + ",").append(good.getFacetQuery().get("2013") + ",")
						.append(bad.getFacetQuery().get("2013") + "     ");
				// 2012
				bw.append(all.getFacetQuery().get("2012") + ",").append(good.getFacetQuery().get("2012") + ",")
						.append(bad.getFacetQuery().get("2012") + "     ");
				// 2011
				bw.append(all.getFacetQuery().get("2011") + ",").append(good.getFacetQuery().get("2011") + ",")
						.append(bad.getFacetQuery().get("2011") + "     ");
				// 2010
				bw.append(all.getFacetQuery().get("2010") + ",").append(good.getFacetQuery().get("2010") + ",")
						.append(bad.getFacetQuery().get("2010") + "     ");
				// 2009
				bw.append(all.getFacetQuery().get("2009") + ",").append(good.getFacetQuery().get("2009") + ",")
						.append(bad.getFacetQuery().get("2009") + "     ");
				bw.newLine();
			}
		} catch (IOException e) {
			logger.error(e.getMessage());
			//			throw new RuntimeException(e);
		}
	}

	public void downLoadComapnyAll() {
		HashMap<String, String> company = companyData.getCompany();
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(new File("company_all.txt")));) {
			bw.append("company_id     company_name     all_count     good_news     bad_news");
			bw.newLine();
			int count = 0;
			for (Entry<String, String> temp : company.entrySet()) {
				logger.info("CompanyAll count=" + count++);
				// 总数
				long all = searchData.queryData(getQuery(0, temp.getValue(), 0)).getResults().getNumFound();
				// 好消息
				long good = searchData.queryData(getQuery(0, temp.getValue(), 1)).getResults().getNumFound();
				// 坏消息
				long bad = searchData.queryData(getQuery(0, temp.getValue(), 2)).getResults().getNumFound();
				bw.append(temp.getKey() + "     " + temp.getValue() + "     " + all + "     " + good + "     " + bad);
				bw.newLine();
			}
		} catch (IOException e) {
			logger.error(e.getMessage());
			//			throw new RuntimeException(e);
		}
	}

	public SolrQuery getQuery(int gap, String keywords, int type) {
		SolrQuery query = new SolrQuery();
		query.setQuery("*:*");
		if (type == 1) { // 好消息
			query.addFilterQuery("content:" + keywords + " AND content:" + GOOD_NEWS);
		} else if (type == 2) { // 坏消息
			query.addFilterQuery("content:" + keywords + " AND content:" + BAD_NEWS);
		} else { // 总数
			query.addFilterQuery("content:" + keywords);
		}
		if (gap == 0) {
			//
		} else if (gap == 1) {
			for (String str : FACET_QUERY_BY_YEAR) {
				query.addFacetQuery(str);
			}
		} else if (gap == 2) {
			for (String str : FACET_QUERY_BY_HALF_YEAR) {
				query.addFacetQuery(str);
			}
		} else if (gap == 3) {
			for (String str : FACET_QUERY_BY_QUARTER) {
				query.addFacetQuery(str);
			}
		}
		query.setRows(0);
		return query;
	}

}
