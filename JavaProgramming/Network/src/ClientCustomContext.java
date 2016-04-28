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
 */
public class ClientCustomContext {
    public static void main(String[] args) throws Exception {

        CloseableHttpClient httpclient = HttpClients.createDefault();

        try{
            CookieStore cookieStore = new BasicCookieStore();
            HttpClientContext localContext = HttpClientContext.create();

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
