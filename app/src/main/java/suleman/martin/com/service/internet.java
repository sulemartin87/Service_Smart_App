package suleman.martin.com.service;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class internet extends AppCompatActivity
{

    ListView internet_list;
    Intent intent = new Intent(Intent.ACTION_CALL);
    List<bundle_description_class> bundle_description_internet = null;
    List<bundle_values_class> bundle_values_internet = null;
    List<carrier_colors_class> colors = null;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_internet);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        simStuff();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


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

    public void show_bundles(final String carrier)
    {
        internet_list= (ListView) findViewById(R.id.main_bundle_list);
        try
        {
            XMLPullParserHandler_bundle_Description parser = new XMLPullParserHandler_bundle_Description();
            bundle_description_internet = parser.parse(getAssets().open(carrier + ".xml"));
            ArrayAdapter<bundle_description_class> adapter =
                    new ArrayAdapter<bundle_description_class>(this, android.R.layout.simple_list_item_1, bundle_description_internet);
            internet_list.setAdapter(adapter);
            internet_list.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                {
                    System.out.println(position);
                    System.out.println(bruh(position, carrier));
                    String bun_val = bruh(position,carrier);
                    intent.setData(Uri.parse("tel:" + Uri.encode(bun_val)));
                    AlertDialog.Builder builder = new AlertDialog.Builder(internet.this);
                    builder.setTitle("Buy Bundle");
                    builder.setMessage("do you want to buy this bundle?");
                    builder.setPositiveButton("YES", new DialogInterface.OnClickListener()
                    {

                        public void onClick(DialogInterface dialog, int which)
                        {
                            startActivity(intent);
                            dialog.dismiss();
                        }

                    });

                    builder.setNegativeButton("NO", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            dialog.dismiss();
                        }
                    });

                    AlertDialog alert = builder.create();
                    alert.show();
                }
            });

        } catch (java.io.IOException e)
        {
            e.printStackTrace();
            if (e.toString().equals( "java.io.FileNotFoundException:"))
            {
                Toast.makeText(getApplicationContext(), "Error XML file not found", Toast.LENGTH_LONG).show();
            }

        }

    }


    public void simStuff()
    {
        ActionBar actionBar = getSupportActionBar();
        TelephonyManager tManager = (TelephonyManager) getBaseContext().getSystemService(Context.TELEPHONY_SERVICE);
        int simState = tManager.getSimState();
        switch (simState)
        {
            case TelephonyManager.SIM_STATE_ABSENT:
                actionBar.setTitle("no sim card");
                Toolbar tool = (Toolbar) findViewById(R.id.toolbar);
                addItemsOnSpinner();
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
                setColor(mi,actionBar);
                break;

            case TelephonyManager.SIM_STATE_UNKNOWN:
                break;
        }
    }

    public String bruh(int posititon,String carrier)
    {
        try
        {
            XMLPullParserHandler_bundle_value parser1 = new XMLPullParserHandler_bundle_value();
            bundle_values_internet = parser1.parse(getAssets().open(carrier+".xml"));
        }catch(Exception e)
        {
            Toast.makeText(getApplicationContext(), "Error XML file not found" ,Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

        return String.valueOf(bundle_values_internet.get(posititon));
    }

    public void addItemsOnSpinner()
    {
        final Spinner spin = (Spinner) findViewById(R.id.spinner2);
        final ActionBar actionBar = getSupportActionBar();
        spin.setVisibility(View.VISIBLE);
        List<String> list = new ArrayList<String>();
        list.add("Select Carrier");
        list.add("Airtel MW");
        list.add("TNM");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>
                (this,android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(dataAdapter);
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3)
            {
                // TODO Auto-generated method stub
                Object item = arg0.getItemAtPosition(arg2);
                int I = arg0.getSelectedItemPosition();
                if (item!=null)
                {
                    if (item .equals("Airtel MW"))
                    {
                     getColor("Airtel MW", actionBar);
                    }
                    else if(item.equals("TNM"))
                    {
                        getColor("TNM", actionBar);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0)
            {
                // TODO Auto-generated method stub

            }
        });
    }

    public void getColor(String carrier,ActionBar actionBar)
    {
        try
        {

            String b = new color_task().execute(carrier).get();

            show_bundles(carrier);
            actionBar.setTitle(carrier);
            actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor(b)));
            if (android.os.Build.VERSION.SDK_INT >= 21)
            {
                Window statusBar = getWindow();
                statusBar.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                statusBar.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                statusBar.setStatusBarColor(Color.parseColor(b));
            }


        }catch(Exception  e)
        {
            e.printStackTrace();
            if (e.toString().equals( "java.io.FileNotFoundException:"))
            {
                Toast.makeText(getApplicationContext(), "Error XML file not found", Toast.LENGTH_LONG).show();
            }

        }
    }

    public void setColor (String carrier,ActionBar actionBar)
    {
        try
        {
            final XMLPullParserHandler_carrier_color parser = new XMLPullParserHandler_carrier_color();
            colors = parser.parse_color(getAssets().open((carrier + ".xml")));
            System.out.println(colors);
            String b = String.valueOf(colors.get(0));
            System.out.println("we here mate" + b);
            show_bundles(carrier);
            actionBar.setTitle(carrier);
            actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor(b)));
            if (android.os.Build.VERSION.SDK_INT >= 21) {
                Window statusBar = getWindow();
                statusBar.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                statusBar.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                statusBar.setStatusBarColor(Color.parseColor(b));
            }

        } catch (java.io.IOException e)
        {
            actionBar.setTitle("carrier not supported Yet!");
            System.out.println("XML not found");
            addItemsOnSpinner();
        }
    }
    private class color_task extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String s = params[0];
            try
            {
                final XMLPullParserHandler_carrier_color parser = new XMLPullParserHandler_carrier_color();
                colors = parser.parse_color(getAssets().open(( s + ".xml")));
            }catch (IOException e) {
                System.out.println("XML not found bruuhhh");
            }
            String b = String.valueOf(colors.get(0));
            System.out.println(b);
            return b;
        }

        @Override
        protected void onPostExecute(String result) {
            // might want to change "executed" for the returned string passed
            // into onPostExecute() but that is upto you
        }

        @Override
        protected void onPreExecute() {}

        @Override
        protected void onProgressUpdate(Void... values) {}
    }

    public void info() {
        AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
        dlgAlert.setMessage("Application Created By Martin Suleman");
        dlgAlert.setTitle("Information");
        dlgAlert.setPositiveButton("OK", null);
        dlgAlert.setCancelable(true);
        dlgAlert.create().show();
    }

}
