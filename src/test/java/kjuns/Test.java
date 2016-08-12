package kjuns;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import com.kjuns.util.DBUtils;

import ch.qos.logback.core.db.dialect.DBUtil;
import jxl.Cell;
import jxl.CellType;
import jxl.Sheet;
import jxl.Workbook;
import jxl.write.Label;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*
		 * Mail mail = new Mail(); mail.setHost("smtp.qq.com"); //
		 * 设置邮件服务器,如果不用163的,自己找找看相关的 mail.setSender("346327002@qq.com");
		 * mail.setReceiver("346327002@qq.com"); // 接收人
		 * mail.setUsername("346327002@qq.com"); // 登录账号,一般都是和邮箱名一样吧
		 * mail.setPassword("s&j0270"); // 发件人邮箱的登录密码
		 * mail.setSubject("aaaaaaaaa"); mail.setMessage("bbbbbbbbbbbbbbbbb");
		 * new MailUtil().send(mail);
		 */
		System.out.println("000000000000000000000000000000000000".length());
		jxl.Workbook readwb = null;

		try

		{

			// 构建Workbook对象, 只读Workbook对象

			// 直接从本地文件创建Workbook

			InputStream instream = new FileInputStream("C:/Users/jl/Desktop/11.xls");

			readwb = Workbook.getWorkbook(instream);

			// Sheet的下标是从0开始

			// 获取第一张Sheet表

			Sheet readsheet = readwb.getSheet(0);


			// 获取Sheet表中所包含的总行数

			int rsRows = readsheet.getRows();

			// 获取指定单元格的对象引用
		    for (int i = 1; i < rsRows; i++)

			{
				
				Cell cell1 = readsheet.getCell(0, i);
				String name = cell1.getContents();
				Cell cell2 = readsheet.getCell(1, i);
				String nationality = cell2.getContents();
				Cell cell3 = readsheet.getCell(2, i);
				String remark = cell3.getContents();
				new DBUtils().cch(name, nationality, remark);
			}


			

		} catch (Exception e) {

			e.printStackTrace();

		} finally {

			readwb.close();

		}
	}

}
