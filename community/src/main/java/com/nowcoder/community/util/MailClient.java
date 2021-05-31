package com.nowcoder.community.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Component
//需要spring容器管理，@Component表示通用在哪个层次都可以用
public class MailClient {
    //当前的类来命名
    private static final Logger logger = LoggerFactory.getLogger(MailClient.class);
    //核心组件直接注入，由spring管理
    @Autowired
    private JavaMailSender mailSender;

    //通过key注入到bean里
    //spring.mail.username=chengzhifunice@sina.com
    @Value("${spring.mail.username}")
    private String from;//发件人
    /**
     *
     * @param to 收件人
     * @param subject 标题
     * @param content 内容
     */
    public void sendMail(String to, String subject, String content) {
        try {
            //构建
            MimeMessage message = mailSender.createMimeMessage();
            //帮助类构造详细内容
            MimeMessageHelper helper = new MimeMessageHelper(message);
            helper.setFrom(from);//设置发件人
            helper.setTo(to);//设置收件人
            helper.setSubject(subject);//设置主题
            helper.setText(content, true); //加true支持html
            mailSender.send(helper.getMimeMessage()); //发送出去，从helper里取到构建好的内容
        } catch (MessagingException e) {
            logger.error("发送邮件失败" + e.getMessage());
        }
    }
}