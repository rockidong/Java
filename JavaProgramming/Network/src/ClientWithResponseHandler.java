import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

/**
 * Created by RockiDong on 2016-04-29.
 */
public class ClientWithResponseHandler {
    public static void main(String[] args) throws Exception{
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try{
            HttpGet httpget = new HttpGet("http://www.google.com/");
            System.out.println("Executing request : " + httpget.getURI());

            // BasicResponseHandler 클래스는 응답 메시지로부터 엔티티를 추출하여
            // 문자열로 만들어 반환한다.

            ResponseHandler<String> responseHandler = new BasicResponseHandler();

            //execute() 메소드는 HttpResponse 객체가 아닌 String으로 반환한다.
            String responseBody = httpclient.execute(httpget,responseHandler);
            System.out.println("========================================");
            System.out.println(responseBody);
            System.out.println("========================================");
        }finally {
            httpclient.close();
        }
    }
}
