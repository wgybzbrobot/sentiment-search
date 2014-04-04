package zx.soft.spider.solr.utils;

import zx.soft.spider.solr.domain.AutmSearch;
import zx.soft.spider.solr.domain.Blog;
import zx.soft.spider.solr.domain.Email;
import zx.soft.spider.solr.domain.Forum;
import zx.soft.spider.solr.domain.Information;
import zx.soft.spider.solr.domain.QQGroup;
import zx.soft.spider.solr.domain.Record;
import zx.soft.spider.solr.domain.Reply;
import zx.soft.spider.solr.domain.Weibo;

/**
 * 其他数据类型转换成Record工具类
 * @author wanggang
 *
 */
public class ConvertToRecord {

	/**
	 * 0：other——>Record
	 */

	/**
	 * 1：Information——>Record
	 */
	public static Record informationToRecord(Information info) {
		if (info == null) {
			return null;
		}
		return new Record.Builder(MD5Util.str2MD5(info.getZXDZ()), 1).setTitle(info.getZXBT())
				.setContent(info.getZXLR()).setOriginal_name(info.getWZLY()).setVideo_url(info.getSPURL())
				.setPic_url(info.getTPURL()).setVoice_url(info.getYPURL()).setTimestamp(info.getFBSJ())
				.setNickname(info.getFBYH()).setSource_id(info.getLY()).setLasttime(info.getJCSJ())
				.setServer_id(info.getLZ()).setIdentify_id(info.getBZ()).setIp(info.getIP())
				.setUpdate_time(info.getGXSJ()).setLocation(info.getIPDZ()).setComment_count(info.getPLS())
				.setRepost_count(info.getZBS()).setRead_count(info.getYDS()).build();
	}

	/**
	 * 2：Forum——>Record
	 */
	public static Record forumToRecord(Forum forum) {
		if (forum == null) {
			return null;
		}
		return new Record.Builder(MD5Util.str2MD5(forum.getWYDZ()), 2).setFirst_time(forum.getFXSJ())
				.setUpdate_time(forum.getGXSJ()).setSource_id(forum.getLY()).setLasttime(forum.getJCSJ())
				.setServer_id(forum.getLZ()).setIdentify_id(forum.getBZ()).setIp(forum.getIP())
				.setLocation(forum.getIPDZ()).setType(forum.getBKMC()).setTitle(forum.getWYBT())
				.setContent(forum.getWYLR()).setVideo_url(forum.getSPURL()).setPic_url(forum.getTPURL())
				.setVoice_url(forum.getYPURL()).setTimestamp(forum.getFBSJ()).setRead_count(forum.getLLL())
				.setComment_count(forum.getGTL()).setNickname(forum.getFBYH()).setKeyword(forum.getGJC()).build();
	}

	/**
	 * 3：Weibo——>Record
	 */
	public static Record weiboToRecord(Weibo weibo) {
		if (weibo == null) {
			return null;
		}
		return new Record.Builder(MD5Util.str2MD5(weibo.getWBDZ()), 3).setContent(weibo.getWBLR())
				.setVideo_url(weibo.getSPURL()).setPic_url(weibo.getTPURL()).setVoice_url(weibo.getYPURL())
				.setTimestamp(weibo.getFBSJ()).setNickname(weibo.getFBYH()).setComment_count(weibo.getPLS())
				.setRepost_count(weibo.getZBS()).setRead_count(weibo.getYDS()).setKeyword(weibo.getGJC())
				.setSource_id(weibo.getLY()).setLasttime(weibo.getJCSJ()).setServer_id(weibo.getLZ())
				.setIdentify_id(weibo.getBZ()).setIp(weibo.getIP()).setUpdate_time(weibo.getGXSJ())
				.setLocation(weibo.getIPDZ()).build();
	}

	/**
	 * 4：Blog——>Record
	 */
	public static Record blogToRecord(Blog blog) {
		if (blog == null) {
			return null;
		}
		return new Record.Builder(MD5Util.str2MD5(blog.getBKDZ()), 4).setTitle(blog.getBKBT())
				.setContent(blog.getBKLR()).setMid(blog.getBKID()).setOriginal_name(blog.getYHM())
				.setNickname(blog.getFBYH()).setComment_count(blog.getPLS()).setRead_count(blog.getYDS())
				.setFavorite_count(blog.getSCS()).setAttitude_count(blog.getXHS()).setRepost_count(blog.getZZS())
				.setVideo_url(blog.getSPURL()).setPic_url(blog.getTPURL()).setVoice_url(blog.getYPURL())
				.setTimestamp(blog.getFBSJ()).setSource_id(blog.getLY()).setLasttime(blog.getJCSJ())
				.setServer_id(blog.getLZ()).setIdentify_id(blog.getBZ()).setIp(blog.getIP()).setKeyword(blog.getGJC())
				.setUpdate_time(blog.getGXSJ()).setLocation(blog.getIPDZ()).build();
	}

	/**
	 * 5：QQGroup——>Record
	 */
	public static Record qQGroupToRecord(QQGroup qQGroup) {
		if (qQGroup == null) {
			return null;
		}
		return new Record.Builder(MD5Util.str2MD5(qQGroup.getID()), 5).setOriginal_uid(qQGroup.getQH())
				.setOriginal_name(qQGroup.getMC()).setOriginal_title(qQGroup.getGG()).setNickname(qQGroup.getYHNC())
				.setUsername(qQGroup.getFBYH()).setContent(qQGroup.getFBLR()).setTimestamp(qQGroup.getFBSJ())
				.setSource_id(qQGroup.getLY()).setServer_id(qQGroup.getLZ()).setLasttime(qQGroup.getJCSJ())
				.setIdentify_id(qQGroup.getBZ()).build();
	}

	/**
	 * 6：AutmSearch——>Record
	 */
	public static Record autmSearchToRecord(AutmSearch autm) {
		if (autm == null) {
			return null;
		}
		return new Record.Builder(MD5Util.str2MD5(autm.getSSDZ()), 6).setTitle(autm.getSSBT())
				.setContent(autm.getSSNR()).setVideo_url(autm.getSPURL()).setPic_url(autm.getTPURL())
				.setVoice_url(autm.getYPURL()).setTimestamp(autm.getFBSJ()).setNickname(autm.getFBYH())
				.setKeyword(autm.getGJC()).setSource_id(autm.getLY()).setIp(autm.getIP()).setLasttime(autm.getJCSJ())
				.setServer_id(autm.getLZ()).setIdentify_id(autm.getBZ()).setUpdate_time(autm.getGXSJ())
				.setLocation(autm.getIPDZ()).build();
	}

	/**
	 * 7：Reply——>Record
	 */
	public static Record replyToRecord(Reply reply) {
		if (reply == null) {
			return null;
		}
		return new Record.Builder(MD5Util.str2MD5(reply.getMD5()), 7).setUsername(reply.getHFID())
				.setOriginal_uid(reply.getHFFID()).setOriginal_url(reply.getURL()).setNickname(reply.getYHZH())
				.setHome_url(reply.getHFRZY()).setContent(reply.getHFLR()).setVideo_url(reply.getSPURL())
				.setPic_url(reply.getTPURL()).setVoice_url(reply.getYPURL()).setTitle(reply.getHFZT())
				.setTimestamp(reply.getJCSJ()).setGeo(reply.getGSD()).setSource_id(reply.getLY())
				.setLasttime(reply.getJCSJ()).setServer_id(reply.getLZ()).setIdentify_id(reply.getBZ())
				.setUpdate_time(reply.getGXSJ()).setIdentify_md5(reply.getMD5()).setIp(reply.getIP())
				.setLocation(reply.getIPDZ()).build();
	}

	/**
	 * 8：Email——>Record
	 */
	public static Record emailToRecord(Email email) {
		if (email == null) {
			return null;
		}
		return new Record.Builder(MD5Util.str2MD5(email.getQJID()), 8).setUsername(email.getID())
				.setReceive_addr(email.getSJRDZ()).setAppend_addr(email.getCSRDZ()).setSend_addr(email.getFJRDZ())
				.setTitle(email.getYJBT()).setContent(email.getYJLR()).setTimestamp(email.getFSSJ())
				.setType(email.getLX()).setFirst_time(email.getFXSJ()).setUpdate_time(email.getGXSJ())
				.setSource_id(email.getLY()).setLasttime(email.getJCSJ()).setServer_id(email.getLZ())
				.setIdentify_id(email.getBZ()).build();
	}

}
