import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Created by Robin on 2016. 4. 28..
 */

public class RealTimeRanking {
    public static void main(String[] args) {


        try {

            CloseableHttpClient httpclient = HttpClients.createDefault();


            Document doc = Jsoup.connect("http://www.naver.com").get();
            if(doc != null)
            {
                Elements elements = doc.select("ol#realrank > li:not(#lastrank) > a");

                System.out.println("====================");

                for(int i = 0 ; i < elements.size() ; i++ )
                {
                    System.out.println("링캥 : " + (i+1) );
                    System.out.println("검색어 : " + elements.get(i).attr("title"));
                    System.out.println("링크 : " + elements.get(i).attr("href"));
                    System.out.println("상승 : " + elements.get(i).select("span.rk").text());
                    System.out.println();

                }
                System.out.println("====================");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
