package com.example.app1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    RecyclerView mRcvMenu;
    Adapter mAdapter;
    ArrayList<VnExpress> mArraylistVnExpress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRcvMenu = findViewById(R.id.rcvVnexpress);
        mArraylistVnExpress = new ArrayList<VnExpress>();


        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new ReadData().execute("https://vnexpress.net/rss/khoa-hoc.rss");
            }
        });
    }
    class ReadData extends AsyncTask<String,Integer,String> {

        @Override
        protected String doInBackground(String... params) {
            return docNoiDung_Tu_URL(params[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            XMLDOMParser xmldomParser = new XMLDOMParser();
            // lay string ve truyen vao doc
            Document document = xmldomParser.getDocument(s);
            NodeList nodeList = document.getElementsByTagName("item");
            NodeList nodeList1 = document.getElementsByTagName("description");
            //duyet item
            String title = "";
            String link = "";
            String img = "";
            for (int i = 0; i < nodeList.getLength() ; i++) {
                String cData = nodeList1.item(i +1).getTextContent();
                Pattern p = Pattern.compile("<img[^>]+src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>"); // Đúng log nó ra hết img
                Matcher matcher = p.matcher(cData);
                if (matcher.find()) {
                    img = matcher.group(1);
                    //                    Log.d("BBB",img+ "............." + i);
                }


                Element element = (Element) nodeList.item(i);
                title =xmldomParser.getValue(element,"title");
                link = xmldomParser.getValue(element,"link");


                mArraylistVnExpress.add(new VnExpress(title,link,img));
//                Log.d("BBB",link);
//                Toast.makeText(MainActivity.this, title, Toast.LENGTH_LONG).show();
            }
            mAdapter = new Adapter(mArraylistVnExpress);
            mRcvMenu.setAdapter(mAdapter);


            super.onPostExecute(s);
//            Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();

        }
        
    }

    private String docNoiDung_Tu_URL(String theUrl)
    {
        StringBuilder content = new StringBuilder();
        try    {

            URL url = new URL(theUrl);
            URLConnection urlConnection = url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line;

            while ((line = bufferedReader.readLine()) != null){
                content.append(line + "\n");
            }
            bufferedReader.close();
        }
        catch(Exception e)    {
            e.printStackTrace();
        }
        return content.toString();
    }

}
