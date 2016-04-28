import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

/**
 * Created by RockiDong on 2016-04-29.
 */
public class ExRequestConfig {
    public static void main(String[] args) throws Exception{

        CloseableHttpClient client = HttpClients.createDefault();
        RequestConfig config = RequestConfig.custom().setSocketTimeout(1000)
                .setConnectTimeout(1000)
                .build();

        HttpGet get = new HttpGet("http://www.google.com/");
        get.setConfig(config);
        CloseableHttpResponse response = client.execute(get);
        try{
            HttpEntity entity = response.getEntity();
        }finally {
            client.close();
        }

    }
}
