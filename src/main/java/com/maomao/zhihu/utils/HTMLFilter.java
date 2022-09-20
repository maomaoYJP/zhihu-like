package com.maomao.zhihu.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author maomao
 * 2022/9/15 21:29
 */
//用于过滤HTML标签
public class HTMLFilter {
    public static String delHTMLTag(String htmlStr){
        String regEx_script="]*?>[\\s\\S]*?<\\/script>"; //定义script的正则表达式
        String regEx_style="]*?>[\\s\\S]*?<\\/style>"; //定义style的正则表达式
        String regEx_html="<[^>]+>"; //定义HTML标签的正则表达式

        Pattern p_script= Pattern.compile(regEx_script,Pattern.CASE_INSENSITIVE);
        Matcher m_script=p_script.matcher(htmlStr);
        htmlStr=m_script.replaceAll(""); //过滤script标签

        Pattern p_style=Pattern.compile(regEx_style,Pattern.CASE_INSENSITIVE);
        Matcher m_style=p_style.matcher(htmlStr);
        htmlStr=m_style.replaceAll(""); //过滤style标签

        Pattern p_html=Pattern.compile(regEx_html,Pattern.CASE_INSENSITIVE);
        Matcher m_html=p_html.matcher(htmlStr);
        htmlStr=m_html.replaceAll(""); //过滤html标签

        return htmlStr.trim(); //返回文本字符串
    }
}
