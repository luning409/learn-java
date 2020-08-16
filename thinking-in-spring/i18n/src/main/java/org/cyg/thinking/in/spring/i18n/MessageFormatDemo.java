package org.cyg.thinking.in.spring.i18n;


import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * {@link MessageFormat}
 *
 * @see MessageFormat
 */
public class MessageFormatDemo {

    public static void main(String[] args) {
        int planet = 7;
        String event = "a disturbance in the Force";
        String messageFormatPatter = "At {1,time,long} on {1,date,full}, there was {2} on planet {0,number,integer}.";
        MessageFormat messageFormat = new MessageFormat(messageFormatPatter);

        String result = messageFormat.format(new Object[]{planet, new Date(), event});
        System.out.println(result);
        // 重置 MessageFormatPattern
        // applyPattern
        messageFormatPatter = "This is a text : {0}, {1}, {2}";
        messageFormat.applyPattern(messageFormatPatter);
        // 参数少于模版中的时候, 少的字符串不会被替换
        result = messageFormat.format(new Object[]{"Hello,World", "666"});
        System.out.println(result);
        // 重置 Locale
        messageFormat.setLocale(Locale.ENGLISH);
        messageFormatPatter = "At {1,time,long} on {1,date,full}, there was {2} on planet {0,number,integer}.";
        messageFormat.applyPattern(messageFormatPatter);
        result = messageFormat.format(new Object[]{planet, new Date(), event});
        System.out.println(result);

        // 重置 Format
        // 根据参数索引来设置 Pattern
        messageFormat.setFormat(1, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        result = messageFormat.format(new Object[]{planet, new Date(), event});
        System.out.println(result);
    }
}
