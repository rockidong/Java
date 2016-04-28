import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Iterator;

/**
 * Created by Robin on 2016. 4. 28..
 */
public class Main {
    public static void main(String args[]) {
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpget = new HttpGet("http://kbodata.news.naver.com/m_rank/rank_team.asp");
        try {
            httpClient.execute(httpget, new BasicResponseHandler() {
                @Override
                public String handleResponse(HttpResponse response) throws HttpResponseException,
                        IOException {
                    // 웹페이지를 그냥 갖어오면 한글이 깨져요. 인코딩 처리를 해야해요.
                    String res = new String(super.handleResponse(response).getBytes("8859_1"), "euc-kr");
                    Document doc = Jsoup.parse(res);

                    // select 부분을 변경해야 정상 출력.
                    Elements rows = doc.select("table.table_board2 tbody tr");
                    String[] items = new String[] { "순위", "팀", "경기수", "승", "패", "무", "승률", "연속",
                            "최근 10경기" };
                    for (Element row : rows) {
                        Iterator<Element> iterElem = row.getElementsByTag("td").iterator();
                        StringBuilder builder = new StringBuilder();
                        for (String item : items) {
                            builder.append(item + ": " + iterElem.next().text() + "   \t");
                        }
                        System.out.println(builder.toString());
                    }

                    return res;
                }
            });
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
