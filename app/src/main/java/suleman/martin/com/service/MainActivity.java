package suleman.martin.com.service;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity
{
    Activity act;
    ListView first_list_view;
    ArrayAdapter<String> adapter;
    boolean dual_sim_mode;

    String[] main_list = new String[]
            {
                    "services","internet"
            };

    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {



        dual_sim_mode = PreferenceManager.getDefaultSharedPreferences(this).getBoolean("dual_sim_mode", false);
        if (dual_sim_mode == false) {

        }else {

        }
        System.out.println(dual_sim_mode);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (android.os.Build.VERSION.SDK_INT >= 11) {
            new main_activity_task().executeOnExecutor("");
        }else {

            boolean name = PreferenceManager.getDefaultSharedPreferences(this).getBoolean("dual_sim_mode", false);
            System.out.println(name);

            new main_activity_task().execute("");
        }

        show_main_list();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.info, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_info)
        {
            info();
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    public void show_main_list()
    {
        first_list_view = (ListView) findViewById(R.id.main_listView);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1, android.R.id.text1, main_list);
        first_list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int itemPosition = position;
                String itemValue = (String) first_list_view.getItemAtPosition(position);
                // Show Alert
                if (position == 0) {
                    launch_service();
                } else {
                    launch_internet();
                }
            }
        });
        first_list_view.setAdapter(adapter);
    }

    public void launch_service()
    {
        Intent intent = new Intent(this, services.class);
        startActivity(intent);
    }
    public void launch_internet()
    {
        Intent intent = new Intent(this, internet.class);
        startActivity(intent);
    }

    public void info() {

        AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
        dlgAlert.setMessage("Application Created By Martin Suleman");
        dlgAlert.setTitle("Information");
        dlgAlert.setPositiveButton("OK", null);
        dlgAlert.setCancelable(true);
        dlgAlert.create().show();
    }


    private class main_activity_task extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String s = params[0];
            String b = null;



            return b;
        }

        @Override
        protected void onPostExecute(String result) {
            // might want to change "executed" for the returned string passed
            // into onPostExecute() but that is upto you
            simStuff();
        }
        public void simStuff()
        {
            ActionBar actionBar = getSupportActionBar();
            TelephonyManager tManager = (TelephonyManager) getBaseContext().getSystemService(Context.TELEPHONY_SERVICE);
            int simState = tManager.getSimState();
            switch (simState) {
                case TelephonyManager.SIM_STATE_ABSENT:
                    actionBar.setTitle("no sim card");
                    AlertDialog.Builder no_sim = new AlertDialog.Builder(MainActivity.this);
                    no_sim.setTitle("No Sim card Inserted");
                    no_sim.setMessage("please insert sim card to buy bundles \n or click on one of the menu items to manually select an available carrier ");
                    no_sim.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {

                            dialog.dismiss();
                        }

                    });

                    AlertDialog no_sim_alert = no_sim.create();
                    no_sim_alert.show();
                    break;
                case TelephonyManager.SIM_STATE_NETWORK_LOCKED:
                    break;
                case TelephonyManager.SIM_STATE_PIN_REQUIRED:
                    break;
                case TelephonyManager.SIM_STATE_PUK_REQUIRED:
                    break;
                case TelephonyManager.SIM_STATE_READY:
                    String mi = tManager.getSimOperatorName();
                    System.out.println(mi);
                    if(mi.equals("TNM"))
                    {
                        actionBar.setTitle(mi);
                        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#70C045")));

                        if (android.os.Build.VERSION.SDK_INT >= 21) {
                            Window statusBar = getWindow();
                            statusBar.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                            statusBar.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                            statusBar.setStatusBarColor(Color.parseColor("#70C045"));
                        }

                    }
                    else if(mi.equals("Airtel MW"))
                    {
                        actionBar.setTitle(mi);
                        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#DB030C")));
                        if (android.os.Build.VERSION.SDK_INT >= 21) {
                            Window statusBar = getWindow();
                            statusBar.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                            statusBar.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                            statusBar.setStatusBarColor(Color.parseColor("#DB030C"));
                        }
                    }else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        int yy = PreferenceManager.getDefaultSharedPreferences(MainActivity.this).getInt("pref_show_alert", 1);
                        System.out.println(yy);

                            builder.setTitle("Carrier Not Supported Yet");
                            builder.setMessage("Sorry :'/.. we promise we'll get to it \n click on one of the menu items to manuallyh select an available carrier" );
                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener()
                            {

                                @TargetApi(Build.VERSION_CODES.GINGERBREAD)
                                public void onClick(DialogInterface dialog, int which)
                                {
                                     //PreferenceManager.getDefaultSharedPreferences(MainActivity.this).edit().putInt("pref_show_alert", 0).apply();

                                }

                            });

                            AlertDialog alert = builder.create();
                            alert.show();

                    }
                    break;
                case TelephonyManager.SIM_STATE_UNKNOWN:
                    break;
            }

        }
        @Override
        protected void onPreExecute() {}

        @Override
        protected void onProgressUpdate(Void... values) {}

        public void executeOnExecutor(String s) {
            simStuff();
        }
    }
}