package kjuns;

import java.io.FileInputStream;
import java.io.InputStream;

import com.kjuns.util.DBUtils;
import com.kjuns.util.UUIDUtils;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

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

//		System.out.println("000000000000000000000000000000000000".length());
		jxl.Workbook readwb = null;
		String id = UUIDUtils.getUUID().toString().replace("-", "");
		System.out.println(id);

		try

		{

			// 构建Workbook对象, 只读Workbook对象

			// 直接从本地文件创建Workbook

			InputStream instream = new FileInputStream("C:/Users/jl/Desktop/xxx.xls");

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
				
				new DBUtils().cchname(name);
			}


			

		} catch (Exception e) {

			e.printStackTrace();

		} finally {

			readwb.close();

		}
	}

}
