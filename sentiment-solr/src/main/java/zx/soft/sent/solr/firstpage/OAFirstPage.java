package zx.soft.sent.solr.firstpage;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.apache.solr.common.SolrDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import zx.soft.sent.solr.domain.QueryParams;
import zx.soft.sent.solr.domain.QueryResult;
import zx.soft.sent.solr.domain.SimpleFacetInfo;
import zx.soft.sent.solr.query.QueryCore;
import zx.soft.utils.json.JsonUtils;
import zx.soft.utils.time.TimeUtils;

/**
 * OA首页信息类
 *
 * @author wanggang
 *
 */
public class OAFirstPage {

	private static Logger logger = LoggerFactory.getLogger(OAFirstPage.class);

	private final QueryCore queryCore;

	public static final String[] NEGATIVES = { //
	"造谣窒息事故暴行毁容致死诬陷猥亵砍人诈骗案反动被害逃逸违法罪犯刺死爆炸物施暴开枪迷幻药毒手炸通缉令砍伤毒打砍杀辱骂偷窃窒息而死违法行为",
			"灭口杀灭非法滋事下毒手交警刑事案件贪污腐败恐怖行动伸冤害人强奸淫秽强暴炸药事件起火毒死跳楼尸体恶毒暴打变态新疆人毒害受害人草菅人命威吓",
			"通缉侵占霸占打架斗殴砸死报案恶名拆迁房死了假释暴力冤枉灾区嫌疑人暴动腐败分子禁毒仇恨死亡淫笑恐怖主义撞死死人侵害犯罪猥亵作乱审判打劫得罪",
			"奸商强拆淫荡申冤虐杀放火勒死追捕刑拘焚烧报复骚扰杀人狂流血暴卒刑事拘留逃犯毒品虐待反腐败冤假冤案歹徒惨杀纵火案纵火瘟疫诈骗罪恩仇世仇奸淫",
			"死打死大爆炸骗子行窃禽流感心狠手辣投毒暴民毒行骗出事妖言惑众草芥人命死者流感伤亡分赃杀人犯厌世凶杀判决戒毒陷害包庇潜逃举报杀死作案工具尸",
			"抢劫行政拘留肇事者自焚性侵作案贪污杀害残暴谋杀扣押监禁粗暴砍死兽性大发获刑糟蹋身亡医疗事故拘留所报警交通事故内乱出血拘留炸弹肢解赃物毒杀",
			"残忍拆迁恐吓侮辱网警举报人监狱行贿吸毒毒性病毒越狱杀人罪堕落车祸噩耗打人扫毒逼供洗脑色狼威胁寻死肇事藏独警备区逃亡流氓炸裂冲突骗人下毒色魔",
			"镇压暴徒非法拘禁恐怖分子禽兽惨死烧毁乱砍突发事件轮奸骂我诈骗贿赂受贿恐怖袭击假币假钱假钞闹事制服冰毒擒获抢劫罪偷盗被盗失窃纠纷女尸失踪线索",
			"涉嫌色情持刀抓获归案同案赃款被害人击毙击伤搜查围捕血案圣战爆炸声爆炸枪走私涉枪涉赌绑架寻衅枪支子弹枪弹举报线索上当骗走被骗谎称受骗骗取被打", //
			"因涉嫌故意伤害案发后" };

	public static String LOCATION_ANHUI = "安徽";

	public OAFirstPage() {
		this.queryCore = new QueryCore();
	}

	/**
	 * 测试函数
	 */
	public static void main(String[] args) {

		OAFirstPage firstPage = new OAFirstPage();
		// 1、统计当前时间各类数据的总量
		//		HashMap<String, Long> currentPSum = firstPage.getCurrentPlatformSum();
		//		System.out.println(JsonUtils.toJson(currentPSum));
		// 2、统计当天各类数据的进入量，其中day=0表示当天的数据
		HashMap<String, Long> todayPlatformInputSum = firstPage.getTodayPlatformInputSum(0);
		System.out.println(JsonUtils.toJson(todayPlatformInputSum));
		// 4、根据当天的微博数据，分别统计0、3、6、9、12、15、18、21时刻的四大微博数据进入总量；
		//		HashMap<String, Long> todayWeibosSum = firstPage.getTodayWeibosSum(0, 6);
		//		System.out.println(JsonUtils.toJson(todayWeibosSum));
		//		List<SolrDocument> todayWeibos = firstPage.getTodayNegativeRecords(7, 50, "合肥");
		//		System.out.println(JsonUtils.toJson(todayWeibos));
		//		List<SolrDocument> negative = firstPage.getNegativeRecords(2, 0, 10);
		//		List<SolrDocument> negative = firstPage.getNegativeRecords(3, 0, 10);
		//		System.out.println(JsonUtils.toJson(negative));
		firstPage.close();

	}

	/**
	 * 统计当前时间各类数据的总量
	 */
	public HashMap<String, Long> getCurrentPlatformSum() {
		logger.info("Getting current platform's sum...");
		HashMap<String, Long> result = null;
		QueryParams queryParams = new QueryParams();
		queryParams.setRows(0);
		queryParams.setFacetField("platform");
		QueryResult queryResult = queryCore.queryData(queryParams, false);
		for (SimpleFacetInfo facetField : queryResult.getFacetFields()) {
			if ("platform".equalsIgnoreCase(facetField.getName())) {
				result = facetField.getValues();
			}
		}
		return result;
	}

	/**
	 * 统计当天各类数据的进入量，其中day=0表示当天的数据
	 */
	@SuppressWarnings("deprecation")
	public HashMap<String, Long> getTodayPlatformInputSum(int day) {
		logger.info("Getting today platform's sum ...");
		HashMap<String, Long> result = null;
		// 注意：86400_000L必能换成86400_000，否则会超出int型的范围，从而导致计算错误，应当为long型。
		long currentTime = System.currentTimeMillis() - day * 86400_000L;
		long startTime = currentTime - currentTime % 86400_000L - 8 * 3600_000L;
		if (new Date(currentTime).getHours() < 8) {
			startTime += 1 * 86400_000L;
		}
		QueryParams queryParams = new QueryParams();
		queryParams.setRows(0);
		queryParams.setFacetField("platform");
		// lasttime代表入solr时间，update_time更新时间
		logger.info("lasttime:[" + TimeUtils.transToSolrDateStr(startTime) + " TO "
				+ TimeUtils.transToSolrDateStr(currentTime) + "]");
		queryParams.setFq("lasttime:[" + TimeUtils.transToSolrDateStr(startTime) + " TO "
				+ TimeUtils.transToSolrDateStr(currentTime) + "]");
		QueryResult queryResult = queryCore.queryData(queryParams, false);
		for (SimpleFacetInfo facetField : queryResult.getFacetFields()) {
			if ("platform".equalsIgnoreCase(facetField.getName())) {
				result = facetField.getValues();
			}
		}
		return result;
	}

	/**
	 * 根据发布人username获取他最新的N条信息
	 * 测试:N=20,username:452962
	 */
	public List<SolrDocument> getTopNRecordsByUsername(int N, String username) {
		List<SolrDocument> result = null;
		QueryParams queryParams = new QueryParams();
		queryParams.setRows(N);
		queryParams.setFq("username:" + username);
		queryParams.setSort("timestamp:desc");
		QueryResult queryResult = queryCore.queryData(queryParams, false);
		result = queryResult.getResults();
		return result;
	}

	/**
	 * 根据当天的微博数据，分别统计0、3、6、9、12、15、18、21时刻的四大微博数据进入总量；
	 * 即从0点开始，每隔3个小时统计以下。
	 */
	@SuppressWarnings("deprecation")
	public HashMap<String, Long> getTodayWeibosSum(int day, int hour) {
		logger.info("Getting today weibos' sum ...");
		HashMap<String, Long> result = initWeibosResult();
		long currentTime = System.currentTimeMillis() - day * 86400_000L;
		long startTime = currentTime - currentTime % 86400_000L - 8 * 3600_000L;
		if (new Date(currentTime).getHours() < 8) {
			startTime += 1 * 86400_000L;
		}
		long endTime = startTime + hour * 3600_000; // 该天的第hour时刻，时间间隔为三小时
		startTime = endTime - 3 * 3600_000; // 该天的第hour-3时刻

		QueryParams queryParams = new QueryParams();
		queryParams.setRows(0);
		queryParams.setFacetField("source_name");
		logger.info("lasttime:[" + TimeUtils.transToSolrDateStr(startTime) + " TO "
				+ TimeUtils.transToSolrDateStr(endTime) + "];platform:3");
		queryParams.setFq("lasttime:[" + TimeUtils.transToSolrDateStr(startTime) + " TO "
				+ TimeUtils.transToSolrDateStr(endTime) + "];platform:3");
		QueryResult queryResult = queryCore.queryData(queryParams, false);
		for (SimpleFacetInfo facetField : queryResult.getFacetFields()) {
			if ("source_name".equalsIgnoreCase(facetField.getName())) {
				for (Entry<String, Long> t : facetField.getValues().entrySet()) {
					if (result.containsKey(t.getKey())) {
						result.put(t.getKey(), t.getValue());
					}
				}
			}
		}

		return result;
	}

	/**
	 * 对当天的论坛和微博进入数据进行负面评分，并按照分值推送最大的签20条内容，每小时推送一次。
	 * @param platform:论坛-2,微博-3
	 * @param day
	 * @return
	 *
	 * TODO : 需要改进，将循环改成多线程
	 */
	@Deprecated
	public List<SolrDocument> getNegativeRecords(int platform, int day, int N) {
		logger.info("Getting today negative records ...");
		List<SolrDocument> result = new ArrayList<>();
		List<SolrDocument> temp = null;
		for (String negative : NEGATIVES) {
			temp = getNegativeShard(platform, day, N, negative);
			if (temp != null) {
				for (SolrDocument t : temp) {
					result.add(t);
				}
			}
		}
		return result;
	}

	/**
	 * 根据关键词查询当天的有害信息
	 *
	 * TODO : 需要改进，将循环改成多线程
	 */
	@Deprecated
	public List<SolrDocument> getTodayNegativeRecords(int interval, int N, String q) {
		List<SolrDocument> result = new ArrayList<>();
		List<SolrDocument> temp = null;
		for (String negative : NEGATIVES) {
			temp = getNegativeShard(interval, N, negative + " AND " + q);
			if (temp != null) {
				for (SolrDocument t : temp) {
					result.add(t);
				}
			}
		}
		return result;
	}

	private List<SolrDocument> getNegativeShard(int interval, int N, String q) {
		long currentTime = System.currentTimeMillis();
		long startTime = currentTime - interval * 86400_000L;
		QueryParams queryParams = new QueryParams();
		queryParams.setQ(q);
		queryParams.setQop("OR");
		queryParams.setRows(N);
		queryParams.setFq("timestamp:[" + TimeUtils.transToSolrDateStr(startTime) + " TO "
				+ TimeUtils.transToSolrDateStr(currentTime) + "]");
		//		queryParams.setSort("timestamp:desc");
		QueryResult queryResult = queryCore.queryData(queryParams, false);
		return queryResult.getResults();
	}

	private List<SolrDocument> getNegativeShard(int platform, int day, int N, String q) {
		long currentTime = System.currentTimeMillis() - day * 86400_000L;
		long startTime = currentTime - currentTime % 86400_000L - 8 * 3600_000L;
		QueryParams queryParams = new QueryParams();
		queryParams.setQ(q);
		queryParams.setQop("OR");
		queryParams.setRows(N);
		queryParams.setFq("timestamp:[" + TimeUtils.transToSolrDateStr(startTime) + " TO "
				+ TimeUtils.transToSolrDateStr(currentTime) + "];platform:" + platform + ";content:" + LOCATION_ANHUI);
		QueryResult queryResult = queryCore.queryData(queryParams, false);
		return queryResult.getResults();
	}

	/**
	 * 初始化四大微博统计结果
	 */
	private HashMap<String, Long> initWeibosResult() {
		HashMap<String, Long> result = new HashMap<>();
		result.put("新浪微博", 0L);
		result.put("腾讯微博", 0L);
		result.put("搜狐微博", 0L);
		result.put("网易微博", 0L);
		return result;
	}

	/**
	 * TODO : 需要改进，将循环改成多线程
	 */
	@Deprecated
	public List<SolrDocument> getHarmfulRecords(String platform, int day, int N) {
		logger.info("Getting today negative records ...");
		List<SolrDocument> result = new ArrayList<>();
		List<SolrDocument> temp = null;
		for (String negative : NEGATIVES) {
			temp = getHarmfulShard(platform, day, N, negative);
			if (temp != null) {
				for (SolrDocument t : temp) {
					result.add(t);
				}
			}
		}
		return result;
	}

	private List<SolrDocument> getHarmfulShard(String platform, int day, int N, String q) {
		long currentTime = System.currentTimeMillis() - day * 86400_000L;
		long startTime = currentTime - currentTime % 86400_000L - 8 * 3600_000L;
		QueryParams queryParams = new QueryParams();
		queryParams.setQ(q);
		queryParams.setQop("OR");
		queryParams.setRows(N);
		queryParams.setFq("timestamp:[" + TimeUtils.transToSolrDateStr(startTime) + " TO "
				+ TimeUtils.transToSolrDateStr(currentTime) + "];platform:" + platform + ";content:" + LOCATION_ANHUI);
		QueryResult queryResult = queryCore.queryData(queryParams, false);
		return queryResult.getResults();
	}

	public void close() {
		queryCore.close();
	}

}
