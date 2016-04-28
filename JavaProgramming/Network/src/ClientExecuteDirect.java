import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 * Created by Robin on 2016. 4. 27..
 */
public class ClientExecuteDirect {
    public static void main(String[] args) throws Exception{
        CloseableHttpClient httpclient = HttpClients.createDefault();

        try {
//            HttpHost target = new HttpHost("www.apache.org",80,"http");
            HttpGet req  = new HttpGet("http://www.apache.org/");

            System.out.println("Executing request ot " + req.getURI());

            HttpResponse response = httpclient.execute(req);

            HttpEntity entity = response.getEntity();

            System.out.println("------------------------");
            System.out.println(response.getStatusLine());

            System.out.println("------------------------");

            Header[] header =  response.getAllHeaders();
            for(int i = 0 ; i < header.length ; i++){
    //            System.out.println(header[i].getName() + " : " + header[i].getValue());
                System.out.println(header[i]);
            }
            System.out.println("------------------------");

            if(entity != null)
            {
                System.out.println(EntityUtils.toByteArray(entity));
            }
        } finally {
            httpclient.close();

        }
    }
}
