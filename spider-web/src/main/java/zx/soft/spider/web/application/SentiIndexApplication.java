package zx.soft.spider.web.application;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;

import zx.soft.spider.web.resource.SentiIndexResource;
import zx.soft.spider.web.sentiment.IndexingData;

public class SentiIndexApplication extends Application {

	private final IndexingData indexingData;

	Thread commitThread;

	public SentiIndexApplication() {
		indexingData = new IndexingData();
		/**
		 * 每分钟定时提交更新
		 */
		commitThread = new Thread(new Runnable() {
			@Override
			public void run() {
				Timer timer = new Timer();
				timer.schedule(new TimerCommit(), 0, 60 * 1000);
			}
		});
		commitThread.start();
	}

	@Override
	public Restlet createInboundRoot() {
		Router router = new Router(getContext());
		router.attach("/platform/{platform}", SentiIndexResource.class);
		return router;
	}

	/**
	 * 添加索引数据
	 */
	public void addDatas(int platform, List<Object> datas) {
		for (Object data : datas) {
			indexingData.addData(platform, data);
		}
	}

	/**
	 * 定时提交更新索引
	 */
	public class TimerCommit extends TimerTask {

		@Override
		public void run() {
			indexingData.commit();
		}

	}

	public void close() {
		commitThread.interrupt(); // 可能需要修改
		indexingData.close();
	}

}
