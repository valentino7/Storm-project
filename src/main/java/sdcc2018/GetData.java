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
        String ROOT_URL1 ="http://a9723e15ee5c711e8b34b023124c2344-257485846.us-west-2.elb.amazonaws.com:8080/api/v1/topology/topQuery1-1-1541950926";
        String ROOT_URL2="http://a9723e15ee5c711e8b34b023124c2344-257485846.us-west-2.elb.amazonaws.com:8080/api/v1/topology/topQuery2-2-1541950949";//stringa top1
        String ROOT_URL3="http://a9723e15ee5c711e8b34b023124c2344-257485846.us-west-2.elb.amazonaws.com:8080/api/v1/topology/topTrafficControl-3-1541950963";//stringa top3
        PrintWriter pw1 = null;
        PrintWriter pw2 = null;
        PrintWriter pw3 = null;
        try {
            pw1 = new PrintWriter(new File("statistics2_top1.csv"));
            pw2 = new PrintWriter(new File("statistics2_top2.csv"));
            pw3 = new PrintWriter(new File("statistics2_control.csv"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        StringBuilder sb1 = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        StringBuilder sb3 = new StringBuilder();

        sb1.append("latency");
        sb1.append(',');
        sb1.append("emitted");
        sb1.append('\n');

        sb2.append("latency");
        sb2.append(',');
        sb2.append("emitted");
        sb2.append('\n');

        sb3.append("latency");
        sb3.append(',');
        sb3.append("emitted");
        sb3.append('\n');

        int increment = 0;
        int numData=100;

        while(increment < numData){

            try {

                JSONObject j1 = readJsonFromUrl(ROOT_URL1);
                JSONObject j2 = readJsonFromUrl(ROOT_URL2);
                JSONObject j3 = readJsonFromUrl(ROOT_URL3);

                JSONArray array1 = (JSONArray) j1.get("topologyStats");
                JSONArray array2 = (JSONArray) j2.get("topologyStats");
                JSONArray array3 = (JSONArray) j3.get("topologyStats");

                JSONObject temp1 = (JSONObject) array1.get(3);
                JSONObject temp2 = (JSONObject) array2.get(3);
                JSONObject temp3 = (JSONObject) array3.get(3);

                String latency1 = (String) temp1.get("completeLatency");
                String latency2 = (String) temp2.get("completeLatency");
                String latency3 = (String) temp3.get("completeLatency");

                int emitted1 = (int) temp1.get("emitted");
                int emitted2 = (int) temp2.get("emitted");
                int emitted3 = (int) temp3.get("emitted");

                latency1=latency1.replaceAll(",",".");
                latency2=latency2.replaceAll(",",".");
                latency3=latency3.replaceAll(",",".");

                System.out.println("latency1="+latency1+",emitted1="+emitted1+",latency2="+latency2+",emitted2="+emitted2+",latency3="+latency3+",emitted3="+emitted3);
                
                sb1.append(latency1);
                sb1.append(',');
                sb1.append(emitted1);
                sb1.append('\n');

                sb2.append(latency2);
                sb2.append(',');
                sb2.append(emitted2);
                sb2.append('\n');

                sb3.append(latency3);
                sb3.append(',');
                sb3.append(emitted3);
                sb3.append('\n');

            } catch(MalformedURLException ex) {
                ex.printStackTrace();
            } catch(IOException ioex) {
                ioex.printStackTrace();
            }
            try {
                Thread.sleep(8000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(increment);
            increment++;
        }

        pw1.write(sb1.toString());
        pw1.close();

        pw2.write(sb2.toString());
        pw2.close();

        pw3.write(sb3.toString());
        pw3.close();

        System.out.println("done!");

    }

}