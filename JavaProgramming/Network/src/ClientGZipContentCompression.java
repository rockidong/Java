import org.apache.http.*;
import org.apache.http.client.entity.GzipDecompressingEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * Created by RockiDong on 2016-04-29.
 */
public class ClientGZipContentCompression {
    public static void main(String[] args) throws Exception {

        HttpClientBuilder hb = HttpClients.custom();

        hb.addInterceptorFirst(new HttpRequestInterceptor() {
            @Override
            public void process(HttpRequest httpRequest, HttpContext httpContext) throws HttpException, IOException {
                if (!httpRequest.containsHeader("Accept-Encoding")) {
                    httpRequest.addHeader("Accept-Encoding", "gzip");
                }
            }
        });

        hb.addInterceptorFirst(new HttpResponseInterceptor() {
            @Override
            public void process(HttpResponse httpResponse, HttpContext httpContext) throws HttpException, IOException {

                HttpEntity entity = httpResponse.getEntity();
                if (entity != null) {
                    Header contentEncoding = httpResponse.getFirstHeader("Content-Encoding");

                    if (contentEncoding.getValue().equalsIgnoreCase("gzip")) {
                        httpResponse.setEntity(new GzipDecompressingEntity(entity));
                    }
                }
            }
        });

        /* 위의 방식이 아니라면 다음과 같이 만들어야 한다.
        * Header ceheader = entity.getContentEncoding();
        * if(ceHeader != null){
        * HeaderElement[] codecs = ceheader.getElements();
        * for(int i = 0 ; i < codecs.length; i++){
        * if(codecs[i].getName().equalsIgnoreCase("gzip")){
        * resonse.setEntity(new GzipDecompressingEntity(entity));
        * return;
        * }
        * }
        * }
         */

        CloseableHttpClient httpclient = hb.build();

        try {
            HttpGet httpget = new HttpGet("http://www.apache.org/");
            System.out.println("Execution request " + httpget.getURI());

            CloseableHttpResponse response = httpclient.execute(httpget);

            try {
                System.out.println("---------------------------------------");
                System.out.println(response.getStatusLine());
                System.out.println(response.getLastHeader("Content-Encoding"));
                System.out.println(response.getLastHeader("Content-Length"));
                System.out.println("---------------------------------------");

                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    String content = EntityUtils.toString(entity);
                    System.out.println(content);
                    System.out.println("---------------------------------------");
                    System.out.println("Uncompressed size : " + content.length());

                }


            } finally {
                response.close();
            }

        } finally {
            httpclient.close();
        }

    }
}
/* ======== 출력결과 =========

---------------------------------------
        HTTP/1.1 200 OK
        Content-Encoding: gzip
        Content-Length: 12498
---------------------------------------
<!DOCTYPE html>
<html lang="en">
<head>
..
..
..
*/
