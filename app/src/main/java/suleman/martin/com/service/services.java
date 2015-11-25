package suleman.martin.com.service;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.view.Window;
import android.view.WindowManager;

public class services extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);
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

}
