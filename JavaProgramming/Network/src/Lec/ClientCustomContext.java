package Lec;

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
 * Created by Robin on 2016. 4. 30..
 */
public class ClientCustomContext {
    public static void main(String[] args) throws Exception{

        CloseableHttpClient client = HttpClients.createDefault();

        //쿠키 및 캐시 정보를 담기위한 컨택스트 객체 생성
        HttpClientContext context = HttpClientContext.create();

        //쿠키 저장소 생성
        CookieStore store = new BasicCookieStore();

        context.setCookieStore(store);

        HttpGet request = new HttpGet("http://www.google.com/");
        System.out.println("Executing request : " + request.getURI() );

        CloseableHttpResponse response = client.execute(request,context);

        HttpEntity entity = response.getEntity();

        System.out.println("----------------------");
        System.out.println(response.getStatusLine());

        if(entity != null){
            if(entity.isChunked())
                System.out.println("Transfer-Encoding : chunked ");
            else{
                long l = entity.getContentLength();
                System.out.println("Response content length : " + l );
            }
        }

        List<Cookie> cookies = store.getCookies();

        for(int i = 0; i < cookies.size() ; i++)
        {
            System.out.println("Local Cookies : " + cookies.get(i));
        }

        Header[] headers = response.getHeaders("Set-Cookie");
        for(int i = 0 ; i < headers.length ; i++)
        {
            System.out.println(headers[i]);
        }

        System.out.println();
        System.out.println();
        System.out.println();

        EntityUtils.consume(entity);


    }
}
