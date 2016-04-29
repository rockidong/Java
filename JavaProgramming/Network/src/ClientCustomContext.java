import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.util.List;

/**
 * Created by Robin on 2016. 4. 25..
 *
 * Http 컴포넌트는 응답 메시지로부터 쿠키를 분리하여 HttpClientContext 객체가 가리키는
 * CookieStore 객체에 쿠키를 보관하고 서버로 요청 메시지를 보낼 때 자동으로 쿠키를 첨부하여 전송한다.
 *
 */
public class ClientCustomContext {
    public static void main(String[] args) throws Exception {

        // 디폴트 client 객체 생성
        CloseableHttpClient httpclient = HttpClients.createDefault();

        try{

            // BasicookieStroe 클래스는 CookieStore 인터페이스를 구현하는 단 하나의 클래스이다.
            // CookieStore 는 인터페이스이다.
            CookieStore cookieStore = new BasicCookieStore();
            HttpClientContext localContext = HttpClientContext.create();

            // 쿠키스토어 등록
            localContext.setCookieStore(cookieStore);

            HttpGet httpget = new HttpGet("http://www.google.com/");
            System.out.println("exeutin requet " + httpget.getURI());

            CloseableHttpResponse response = httpclient.execute(httpget,localContext);

            try{
                HttpEntity entity = response.getEntity();

                System.out.println("---------------------");
                System.out.println(response.getStatusLine());
                if(entity != null){
                    if(entity.isChunked())
                        System.out.println("Transfer_Encoding : chunked");
                    else {
                        long l = entity.getContentLength();
                        System.out.println("Response content length : " + l);

                    }
                }
                List<Cookie> cookies = cookieStore.getCookies();
                for ( int i = 0; i < cookies.size() ; i++){
                    System.out.println("Local cookie : " + cookies.get(i));
                }

                Header[] headers = response.getHeaders("Set-Cookie");
                for(int i = 0; i < headers.length ; i++){
                    System.out.println(headers[i]);
                }

                EntityUtils.consume(response.getEntity());
                System.out.println("=========================");

            }finally {
                response.close();
            }
        }finally {
            httpclient.close();
        }

    }
}
