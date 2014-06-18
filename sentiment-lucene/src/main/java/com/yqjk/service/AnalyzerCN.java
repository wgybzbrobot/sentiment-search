/**
 * 作者：罗培胤
 * 日期： 2013-11-15
 */
package com.yqjk.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

/**
 * 中文分词
 * 
 * @author Luopy
 * 
 */
public class AnalyzerCN {

	/**
	 * 获取分词结果集
	 * 
	 * @param s:需要分词字符串
	 *           
	 * @return 分词后的结果数组
	 */
	public static List<String> DoAnalyzerCN(String s) {

		List<String> retList = new ArrayList<String>();
		try {
			TokenStream tokenStream = CreateIndex.GetInstance().analyzer
					.tokenStream("txt", s);

			while (tokenStream.incrementToken()) {
				CharTermAttribute charTermAttribute = tokenStream
						.getAttribute(CharTermAttribute.class);
				String sbx = charTermAttribute.toString();
				retList.add(sbx);
			}
		} catch (Exception e) {
			Logger logger = LogManager.getLogger("CreateIndex");
			logger.error(e.getMessage(), e);
			logger.exit();
		}
		return retList;
	}
}
