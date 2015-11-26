package suleman.martin.com.service;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
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

    String[] main_list = new String[]
            {
                    "services","internet"
            };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        simStuff();
        show_main_list();
    }

    public void show_main_list()
    {
        first_list_view = (ListView) findViewById(R.id.main_listView);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1, android.R.id.text1, main_list);
        first_list_view.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                int itemPosition = position;
                String itemValue = (String) first_list_view.getItemAtPosition(position);
                // Show Alert
                if (position == 0)
                {
                    launch_service();
                }
                else
                {
                    launch_internet();
                }
            }
        });
        first_list_view.setAdapter(adapter);
    }
    public void simStuff()
    {
        ActionBar actionBar = getSupportActionBar();
        TelephonyManager tManager = (TelephonyManager) getBaseContext().getSystemService(Context.TELEPHONY_SERVICE);
        int simState = tManager.getSimState();
        switch (simState) {
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
                }
                break;
            case TelephonyManager.SIM_STATE_UNKNOWN:
                break;
        }

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
}