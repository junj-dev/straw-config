package cn.tedu.straw.search.test;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @Description: 测试过滤掉html标签
 * @Author: ChenHaiBao
 * @CreateDate: 2020/5/27$ 10:57$
 * @Version: 1.0
 */

public class TestRemoveHtmlTag {

    public static void main(String[] args) {
       String html="<p><span style='color: rgb(33, 37, 41); background-color: rgb(248, 249, 250);'>老师,请问客户端和服务器端的通讯是怎么通讯的啊,Socket是什么?</span><br></p>";
        Document doc = Jsoup.parse(html);
        String text = doc.text();
        System.out.println(text);
    }


}
