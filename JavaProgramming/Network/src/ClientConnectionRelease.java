import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by RockiDong on 2016-04-27.
 */
public class ClientConnectionRelease {
    public static void main(String[] args) throws Exception{
        CloseableHttpClient client = HttpClients.createDefault();

        try {
            HttpGet httpget = new HttpGet("http://www.google.com/");
            System.out.println(httpget.getURI());

            HttpResponse response = client.execute(httpget);

            System.out.println("----------------------------");
            System.out.println(response.getStatusLine());
            System.out.println("----------------------------");
            HttpEntity entity = response.getEntity();
            if(entity!= null)
            {
                BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent()));

                try {
                    String inputLine;
                    while( (inputLine = reader.readLine()) != null){
                        System.out.println(inputLine);
                    }
                } catch (IOException e) {
                    throw e;
                }catch (RuntimeException e){
                    httpget.abort();
                    throw e;
                }finally {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (UnsupportedOperationException e) {
            e.printStackTrace();
        }finally {
            client.close();
        }

        System.out.println("----------------------------");

    }
}
