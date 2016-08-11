package kjuns;

import com.kjuns.util.mail.Mail;
import com.kjuns.util.mail.MailUtil;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Mail mail = new Mail();  
        mail.setHost("smtp.qq.com"); // 设置邮件服务器,如果不用163的,自己找找看相关的  
        mail.setSender("346327002@qq.com");  
        mail.setReceiver("346327002@qq.com"); // 接收人  
        mail.setUsername("346327002@qq.com"); // 登录账号,一般都是和邮箱名一样吧  
        mail.setPassword("s&j0270"); // 发件人邮箱的登录密码  
        mail.setSubject("aaaaaaaaa");  
        mail.setMessage("bbbbbbbbbbbbbbbbb");  
        new MailUtil().send(mail);  
	}

}
