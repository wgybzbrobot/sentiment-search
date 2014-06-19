package demo;

import java.util.List;

import zx.soft.sent.lucene.domain.DayCountModel;
import zx.soft.sent.lucene.domain.NameKeySort;

public class TestZhuanTiReturnObj {
	List<objectret> moret;

	public class objectret {
		int key;
		List<DayCountModel> Modelct;
		List<NameKeySort> GtoupModelct;
	}

}
