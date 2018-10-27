package sdcc2018;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;


public class GetData {


    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            return json;
        } finally {
            is.close();
        }
    }

    public synchronized static void main(String[] args)  {
        String ROOT_URL = "http://localhost:8080/api/v1/topology/topQuery2-2-1540470602";
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(new File("test.csv"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        StringBuilder sb = new StringBuilder();
        sb.append("latency");
        sb.append(' ');
        sb.append("emitted");
        sb.append('\n');

        int increment = 0;

        while(increment < 50){

            try {

                JSONObject j = readJsonFromUrl(ROOT_URL);
                JSONArray array = (JSONArray) j.get("topologyStats");
                JSONObject temp = (JSONObject) array.get(3);
                String latency = (String) temp.get("completeLatency");
                int emitted = (int) temp.get("emitted");
                latency=latency.replaceAll(",",".");
                sb.append(latency);
                sb.append(' ');
                sb.append(emitted);
                sb.append('\n');

            } catch(MalformedURLException ex) {
                ex.printStackTrace();
            } catch(IOException ioex) {
                ioex.printStackTrace();
            }
            try {
                Thread.sleep(6000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(increment);
            increment++;
        }

        pw.write(sb.toString());
        pw.close();
        System.out.println("done!");







    }

}