import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Created by RockiDong on 2016-04-29.
 */
public class NaverRealSearch {
    public static void main(String[] args) {

        String url = "http://www.naver.com";

        try {
            Document document = Jsoup.connect(url).get();
            Elements elements = document.select("ol#realrank > li:not(#lastrank) > a");

            for(int i = 0 ; i < elements.size() ; i++)
            {
                System.out.println("검색어 : " + elements.get(i).attr("title"));
                System.out.println("링크 : " + elements.get(i).attr("href"));
                System.out.println("순위 : " + (i + 1 ));
                System.out.println("상승폭 : " + elements.get(i).select("span.rk").text() );
                System.out.println("=============================================================");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
