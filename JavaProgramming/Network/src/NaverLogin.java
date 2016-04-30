import org.jsoup.Connection.Method;
import org.jsoup.Jsoup;
import org.jsoup.helper.HttpConnection.Response;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.regex.Matcher;
import java.util.regex.Pattern;



/**
 * Created by RockiDong on 2016-05-01.
 * Jsoup 를 이용한 네이버 로그인
 */
public class NaverLogin {
    public static void main(String[] args) throws Exception {
        Document keyDoc = Jsoup.connect("http://nid.naver.com/login/ext/keys.nhn")
                .get();
        String[] loginKey = keyDoc.body().text().split("\\,");

        // 로그인
        Response res = (Response) Jsoup.connect("https://nid.naver.com/nidlogin.login")
                .data("id", "rockidong",
                        "pw", "7306g8881",
                        "encnm", loginKey[1],
                        "enctp", "1",
                        "locale", "ko_KR",
                        "smart_LEVEL", "1",
                        "url", "http://www.naver.com")
                .method(Method.POST)
                .header("Content-Type", "application/x-www-form-urlencoded")
                .referrer("http://static.nid.naver.com/b4/b4login.nhn?svc=d242&url=http%3A%2F%2Fsection.blog.naver.com%2FSectionMain.nhn")
                .execute();
        Document doc1 = res.parse();

        String doc1Html = doc1.html();

        System.out.print("KEY : "+loginKey[1]);
        System.out.println(" - PAGE STATUS CODE : "+res.statusCode());

        Pattern pt = Pattern.compile("https:([^\"]+)");
        Matcher mc = pt.matcher(doc1Html);

        String lastUrl = null;
        String lastUrl2 = null;

        if(mc.find()){
            lastUrl = mc.group();
            System.out.println("URL파싱 : "+lastUrl);
        }

        Response res2 = (Response) Jsoup.connect(lastUrl)
                .cookies(res.cookies())
                .execute();

        Document doc2 = res2.parse();
        String doc2Html = doc2.html();

        Pattern pt1 = Pattern.compile("http:([^\"]+)");
        Matcher mc1 = pt1.matcher(doc2Html);
        if(mc1.find()){
            lastUrl2 = mc1.group();
            System.out.print("URL파싱 : "+mc1.group());
            System.out.println(" - PAGE STATUS CODE : "+res2.statusCode());
        }

        Response res3 = (Response) Jsoup.connect("http://section.blog.naver.com/")
                .cookies(res.cookies())
                .execute();

        Document doc3 = res3.parse();
        Elements doc3E = doc3.select(".user_tit");

        System.out.println("============================");
        System.out.println(doc3E.html());
    }
}
