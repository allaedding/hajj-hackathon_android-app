package fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.github.lzyzsd.circleprogress.ArcProgress;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.wolfsoft.Ammen.JSONParser;
import com.wolfsoft.Ammen.R;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.google.android.gms.internal.zzagz.runOnUiThread;

public class mlcrow extends Fragment {




    private View view;

    public static String responce,state1,state2;
    private TextView crowd ;
    //private CircleProgress circleProgress;



    private Handler handler = new Handler();
    private int tempi,humi,perso;
    private final int f = 9000;
    private final int r = 1000;
    private ToggleButton Door1,Door2;
    private GoogleApiClient client ;
    String temper = null,humid = null,pers = null;
    private String AdressIp = "localhost";
    private Boolean cancel ;
    private ProgressDialog pDialog;
    private Button about;
    private double jamarat1,jamarat2,jamarat3;

    String pid;
    JSONParser jsonParser = new JSONParser();

    private Activity activity ;

    // private static final String url_get_data = "http://10.7.0.205/esp8266/get_data.php";
    private static final String url_get_data = "http://35.196.112.188:4567/vision";//?place=jeddah&label=crowd";


    private ArcProgress temp,hum,per;
    private String led1Status;
    private String serverAdresses;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_doors, container, false);

        client = new GoogleApiClient.Builder(getContext()).addApi(AppIndex.API).build();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        temp = (ArcProgress) view.findViewById(R.id.etage1);
        hum = (ArcProgress) view.findViewById(R.id.etage2);
        per = (ArcProgress) view.findViewById(R.id.etage3);

        activity = getActivity();

        //Intent i = getActivity().getIntent();


        GetData();

        //crowd = (TextView) view.findViewById(R.id.textView2);


        return view;



    }

    public void GetData() {
        handler.postDelayed(new Runnable() {
            public void run() {

                new GetDetails().execute();
                GetData();


            }
        }, f);

    }


    /**
     * run get all product in background
     * */
    class GetDetails extends AsyncTask<String, String, String> {

        /**
         * if start get activity then run progress dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //pDialog = new ProgressDialog(getContext());
            //pDialog.setMessage("Retrieving Data...");
            // pDialog.setIndeterminate(false);
            //  pDialog.setCancelable(true);
            //pDialog.show();
        }

        /**
         * start run get all list and run in background
         * */
        protected String doInBackground(String... params) {

            // ui updates from threads executed
            runOnUiThread(new Runnable() {
                public void run() {
                    // Check tag success
                    int success;
                    // create paramater
                    List<NameValuePair> params = new ArrayList<NameValuePair>();
                    List<NameValuePair> params1 = new ArrayList<NameValuePair>();
                    List<NameValuePair> params2 = new ArrayList<NameValuePair>();
                    //List<NameValuePair> params3 = new ArrayList<NameValuePair>();
                    params.add(new BasicNameValuePair("place", "jamarat_1"));
                    params1.add(new BasicNameValuePair("place", "jamarat_2"));
                    params2.add(new BasicNameValuePair("place", "jamarat_3"));
                    params.add(new BasicNameValuePair("label", "crowd"));
                    params1.add(new BasicNameValuePair("label", "crowd"));
                    params2.add(new BasicNameValuePair("label", "crowd"));
                    params.add(new BasicNameValuePair("json", "true"));
                    params1.add(new BasicNameValuePair("json", "true"));
                    params2.add(new BasicNameValuePair("json", "true"));
                    // get details from the list using http request

                   // JSONObject json = jsonParser.makeHttpRequest(
                     //       url_get_data, "GET", params);

                    //JSONObject json1 = jsonParser.makeHttpRequest(
                      //      url_get_data, "GET", params1);

                    //JSONObject json2 = jsonParser.makeHttpRequest(
                      //      url_get_data, "GET", params2);

                    //getJSONArray("crowd"); // JSON

                    // Array

                    // check our logs with json response

                        // Log.d("crowd", String.valueOf(json.toString()));
                         //jamarat1 = json.getDouble("crowd");
                        //jamarat2 = json1.getDouble("crowd");
                        //jamarat3 = json2.getDouble("crowd");
                        //jamarat1 *= 100.0;
                        //jamarat2 *= 100.0;
                        //jamarat3 *= 100.0;
                      //  Log.d("Single Product Details", json.toString());



                    Random rand = new Random();
                    int n = rand.nextInt(90);

                    temp.setProgress(n);
                    n = rand.nextInt(90);
                    hum.setProgress(n);
                    n = rand.nextInt(90);
                    per.setProgress(n);
                    // Array

                    // get first list object from json array
                    //JSONObject data = productObj.getJSONObject(0);


                }

            });

            return null;
        }

        /**
         * If the job in the background is complete then stop progress reply
         * running
         * **/
        protected void onPostExecute(String file_url) {
            // stop progress dialog for get
            // pDialog.dismiss();
        }
    }



}
