package suleman.martin.com.service;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class internet extends AppCompatActivity
{
    String hassh = Uri.encode("#");
    ListView internet_list;
    Intent intent = new Intent(Intent.ACTION_DIAL);
    List<bundles> employees = null;
    List<bundle_values> employee1 = null;

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

    public void simStuff()
    {
        ActionBar actionBar = getSupportActionBar();
        TelephonyManager tManager = (TelephonyManager) getBaseContext().getSystemService(Context.TELEPHONY_SERVICE);
        int simState = tManager.getSimState();
        switch (simState)
        {
            case TelephonyManager.SIM_STATE_ABSENT:
                actionBar.setTitle("no sim card");
                break;

            case TelephonyManager.SIM_STATE_NETWORK_LOCKED:
                break;

            case TelephonyManager.SIM_STATE_PIN_REQUIRED:
                break;

            case TelephonyManager.SIM_STATE_PUK_REQUIRED:
                break;

            case TelephonyManager.SIM_STATE_READY:
                String mi = tManager.getSimOperatorName();
                if(mi.equals("TNM"))
                {
                    show_bundles(mi);
                    actionBar.setTitle(mi);
                    actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#70C045")));
                    if (android.os.Build.VERSION.SDK_INT >= 21)
                    {
                        Window statusBar = getWindow();
                        statusBar.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                        statusBar.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                        statusBar.setStatusBarColor(Color.parseColor("#70C045"));
                    }
                }
                else if(mi.equals("airtel"))
                {
                    actionBar.setTitle(mi);
                    show_bundles(mi);
                    actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#DB030C")));
                    if (android.os.Build.VERSION.SDK_INT >= 21)
                    {
                        Window statusBar = getWindow();
                        statusBar.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                        statusBar.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                        statusBar.setStatusBarColor(Color.parseColor("#DB030C"));
                    }
                }
                break;

            case TelephonyManager.SIM_STATE_UNKNOWN:
                break;
        }
    }
    public void show_bundles(final String carrier)
    {
        internet_list= (ListView) findViewById(R.id.main_bundle_list);
        try
        {
            XMLPullParserHandler parser = new XMLPullParserHandler();
            employees = parser.parse(getAssets().open(carrier + ".xml"));
            ArrayAdapter<bundles> adapter =
                    new ArrayAdapter<bundles>(this, android.R.layout.simple_list_item_1,employees);
            internet_list.setAdapter(adapter);
            internet_list.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                {
                    System.out.println(position);
                    System.out.println(bruh(position,carrier));
                    String bun_val = bruh(position,carrier);
                    intent.setData(Uri.parse("tel:" + Uri.encode(bun_val)));
                    startActivity(intent);
                }
            });

        }catch(java.io.IOException  e)
        {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Error XML file not found" ,Toast.LENGTH_LONG).show();
        }

    }
    public String bruh(int posititon,String carrier)
    {
        try
        {
            XMLPullParserHandler1 parser1 = new XMLPullParserHandler1();
            employee1 = parser1.parse(getAssets().open(carrier+".xml"));
        }catch(Exception e)
        {
            Toast.makeText(getApplicationContext(), "Error XML file not found" ,Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

        return String.valueOf(employee1.get(posititon));
    }

}
