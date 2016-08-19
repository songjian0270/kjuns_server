package kjuns;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

public class Test {

	public static void main(String[] args) throws EmailException {
		// TODO Auto-generated method stub
		
//		 Mail mail = new Mail(); mail.setHost("smtp.qq.com"); //
//		 mail.setSender("houtai1@kanjunshi.net");
//		 mail.setReceiver("houtai2@kanjunshi.net"); // 接收人
//		 mail.setUsername("houtai2@kanjunshi.net"); // 登录账号,一般都是和邮箱名一样吧
//		 mail.setPassword("Tanglang2016"); // 发件人邮箱的登录密码
//		 mail.setSubject("举报信息ID:1123123"); mail.setMessage("举报ID:12312312313");
//		 
//		 
//		 new MailUtil().send(mail);
		 
		 
		  HtmlEmail email = new HtmlEmail();

		   email.setAuthenticator(new DefaultAuthenticator("houtai1@kanjunshi.net", "Tanglang2016"));
		   email.setHostName("smtp.qq.com");

		   //设置收件人
		   email.addTo("houtai2@kanjunshi.net", "收件人昵称（可以为空）");

		   //设置发件人
		   email.setFrom("houtai1@kanjunshi.net", "Tanglang2016");

		   //主题
		   email.setSubject("举报信息ID:1123123");

		   // 设置邮件正文和字符编码
		   StringBuffer bodyBf = new StringBuffer();
		   bodyBf.append("举报ID:12312312313");
		   email.addPart(bodyBf.toString(), "text/html;charset=utf-8");

	
		   email.send();

//		System.out.println("000000000000000000000000000000000000".length());
	/*	jxl.Workbook readwb = null;
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

		}*/
	}

}
