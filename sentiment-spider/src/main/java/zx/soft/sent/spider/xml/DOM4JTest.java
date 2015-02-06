package zx.soft.sent.spider.xml;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class DOM4JTest {

	/**
	 * DOM4J读写XML示例
	 */
	public static void main(String[] args) throws IOException, DocumentException {

		// 声明写XML的对象
		XMLWriter writer = null;
		SAXReader reader = new SAXReader();

		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding("UTF-8");// 设置XML文件的编码格式

		String filePath = "output/student.xml";
		File file = new File(filePath);
		if (file.exists()) {
			Document document = reader.read(file); // 读取XML文件
			Element root = document.getRootElement();// 得到根节点
			boolean bl = false;
			for (Iterator<?> i = root.elementIterator("学生"); i.hasNext();) {
				Element student = (Element) i.next();
				if (student.attributeValue("sid").equals("001")) {
					// 修改学生sid=001的学生信息
					student.selectSingleNode("姓名").setText("王五");
					student.selectSingleNode("年龄").setText("25");

					writer = new XMLWriter(new FileWriter(filePath), format);
					writer.write(document);
					writer.close();
					bl = true;
					break;
				}
			}
			if (bl) {
				// 添加一个学生信息
				Element student = root.addElement("学生");
				student.addAttribute("sid", "100");
				Element sid = student.addElement("编号");
				sid.setText("100");
				Element name = student.addElement("姓名");
				name.setText("嘎嘎");
				Element sex = student.addElement("性别");
				sex.setText("男");
				Element age = student.addElement("年龄");
				age.setText("21");

				writer = new XMLWriter(new FileWriter(filePath), format);
				writer.write(document);
				writer.close();
			}
		} else {
			// 新建student.xml文件并新增内容
			Document _document = DocumentHelper.createDocument();
			Element _root = _document.addElement("学生信息");
			Element _student = _root.addElement("学生");
			_student.addAttribute("sid", "001");
			Element _id = _student.addElement("编号");
			_id.setText("001");
			Element _name = _student.addElement("姓名");
			_name.setText("灰机");
			Element _age = _student.addElement("年龄");
			_age.setText("18");

			writer = new XMLWriter(new FileWriter(file), format);
			writer.write(_document);
			writer.close();
		}
		System.out.println("操作结束! ");

	}

}
