package zx.soft.sent.spider.xml;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

public class WriteXML {

	public static void main(String[] args) throws DocumentException, IOException {
		// 声明写XML的对象

		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding("UTF-8");// 设置XML文件的编码格式
		String filePath = "student.xml";
		File file = new File(filePath);

		// 新建xml文件并新增内容
		Document _document = DocumentHelper.createDocument();
		Element _root = _document.addElement("MESSAGE");
		Element _dataset = _root.addElement("DATASET");
		_dataset.addAttribute("name", "WA_COMMON_010017");
		_dataset.addAttribute("rmk", "数据文件索引信息");
		Element _data = _dataset.addElement("DATA");
		Element _ddataset = _data.addElement("DATASET");
		_ddataset.addAttribute("name", "WA_COMMON_010013");
		_ddataset.addAttribute("rmk", "BCP文件格式信息");
		Element _ddata = _ddataset.addElement("DATA");
		addITEM(_ddata, "I010032", "   ", "列分隔符(缺少值时默认为制表符\\t)");
		addITEM(_ddata, "I010033", "   ", "行分隔符(缺少值时默认为换行符\\n)");
		addITEM(_ddata, "A010004", "WACODE_0080", "数据集代码");
		addITEM(_ddata, "B050016", "138", "数据来源");
		addITEM(_ddata, "G020013", "757914656", "网安专用产品厂家组织机构代码");
		addITEM(_ddata, "F010008", "340000", "数据采集地");
		addITEM(_ddata, "I010038", "1", "数据起始行，可选项，不填写默认为第１行");
		Element _dddataset1 = _ddata.addElement("DATASET");
		_dddataset1.addAttribute("name", "WA_COMMON_010014");
		_dddataset1.addAttribute("rmk", "BBCP数据文件信息");
		Element _dddata1 = _dddataset1.addElement("DATA");
		addITEM(_dddata1, "H040003", "./", "文件路径");
		addITEM(_dddata1, "H010020", "138-340000-1423211396-10001-WACODE_0080-0.bcp", "文件名");
		addITEM(_dddata1, "I010034", "574", "记录行数");
		Element _dddataset2 = _ddata.addElement("DATASET");
		_dddataset2.addAttribute("name", "WA_COMMON_010015");
		_dddataset2.addAttribute("rmk", "BCP文件数据结构");
		Element _dddata2 = _dddataset2.addElement("DATA");
		addITEM(_dddata2, "H100001", "MBLOG_ID", "微博ID");
		addITEM(_dddata2, "H100008", "RELEVANT_USERID", "相关人ID");
		addITEM(_dddata2, "H100009", "RELEVANT_USER_NICKNAME", "相关人昵称");
		addITEM(_dddata2, "H100006", "WEIBO_MESSAGE", "微博消息内容");
		addITEM(_dddata2, "F010008", "COLLECT_PLACE", "采集地");
		addITEM(_dddata2, "B050012", "FIRST_TIME", "首次采集时间");
		addITEM(_dddata2, "B050014", "LAST_TIME", "最近采集时间");
		addITEM(_dddata2, "B050016", "DATA_SOURCE", "数据来源名称");
		addITEM(_dddata2, "G010001", "DOMAIN", "域名");
		addITEM(_dddata2, "H010014", "CAPTURE_TIME", "数据截获时间");
		//
		XMLWriter writer = new XMLWriter(new FileWriter(file), format);
		writer.write(_document);
		writer.close();
		System.out.println("操作结束! ");
	}

	public static void addITEM(Element data, String key, String value, String rmk) {
		Element _item = data.addElement("ITEM");
		_item.addAttribute("key", key);
		_item.addAttribute("value", value);
		_item.addAttribute("rmk", rmk);
	}

}
