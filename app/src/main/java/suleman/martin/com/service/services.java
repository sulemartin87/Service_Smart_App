package suleman.martin.com.service;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import android.widget.Spinner;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class services extends AppCompatActivity
{
    List<service_description_class> service_description = null;
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

    public void show_services(String carrier)
    {

       ListView service_list= (ListView) findViewById(R.id.service_list);
        try
        {
            XMLPullParserHandler_services parser = new XMLPullParserHandler_services();
            service_description = parser.parse(getAssets().open(carrier + ".xml"));
            ArrayAdapter<service_description_class> adapter =
                    new ArrayAdapter<service_description_class>(this, android.R.layout.simple_list_item_1, service_description);
            service_list.setAdapter(adapter);
            service_list.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                {


                }
            });

        } catch (java.io.IOException e)
        {

            if (e.toString().equals( "java.io.FileNotFoundException:"))
            {
                Toast.makeText(getApplicationContext(), "Error XML file not found", Toast.LENGTH_LONG).show();
            }

        }

    }
    public void addItemsOnSpinner()
    {
        final Spinner spin = (Spinner) findViewById(R.id.spinner_service);
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



                            actionBar.setTitle("Airtel MW");
                            actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#DB030C")));
                            show_services("Airtel MW");
                            if (android.os.Build.VERSION.SDK_INT >= 21)
                            {
                                Window statusBar = getWindow();
                                statusBar.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                                statusBar.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                                statusBar.setStatusBarColor(Color.parseColor("#DB030C"));
                            }

                    }
                    else if(item.equals("TNM"))
                    {
                        show_services("TNM");
                        actionBar.setTitle("TNM");
                        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#70C045")));
                        if (android.os.Build.VERSION.SDK_INT >= 21)
                        {
                            Window statusBar = getWindow();
                            statusBar.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                            statusBar.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                            statusBar.setStatusBarColor(Color.parseColor("#70C045"));
                        }

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
                    show_services(mi);
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
                else if(mi.equals("Airtel MW"))
                {
                    actionBar.setTitle(mi);
                    actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#DB030C")));
                    show_services(mi);
                    if (android.os.Build.VERSION.SDK_INT >= 21)
                    {
                        Window statusBar = getWindow();
                        statusBar.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                        statusBar.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                        statusBar.setStatusBarColor(Color.parseColor("#DB030C"));
                    }
                }else {
                    actionBar.setTitle("carrier not supported Yet!");
                    addItemsOnSpinner();
                }
                break;

            case TelephonyManager.SIM_STATE_UNKNOWN:
                break;
        }
    }

    class XMLPullParserHandler_services
    {

        List<service_description_class> service_vals;
        List<carrier_colors_class> color_value;
        private service_description_class service_class;
        private carrier_colors_class carrier_color_class;
        private String text;

        public XMLPullParserHandler_services()
        {
            service_vals = new ArrayList<service_description_class>();
        }

        public List<service_description_class> parse(InputStream is)
        {

            XmlPullParserFactory factory = null;
            XmlPullParser parser = null;

            try
            {
                factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(true);

                parser = factory.newPullParser();
                parser.setInput(is, null);

                int eventType = parser.getEventType();
                while(eventType != XmlPullParser.END_DOCUMENT)
                {
                    String tagname = parser.getName();
                    switch (eventType)
                    {
                        case XmlPullParser.START_TAG:

                            if(tagname.equalsIgnoreCase("service"))
                            {
                                service_class = new service_description_class();
                            }
                            break;

                        case XmlPullParser.TEXT:

                            text = parser.getText();
                            break;

                        case XmlPullParser.END_TAG:

                            if(tagname.equalsIgnoreCase("service"))
                            {
                                service_vals.add(service_class);
                            }else if(tagname.equalsIgnoreCase("service_name"))
                            {
                                service_class.set_service_description(text);
                            }else if(tagname.equalsIgnoreCase("service_info"))
                            {
                                service_class.set_service_value(text);
                            }
                            break;
                        default:
                            break;
                    }
                    eventType = parser.next();
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
            return service_vals;
        }



    }

    public class service_description_class {

        private String service_description;
        private String service_value;

        public String getbundle_description()
        {
            return service_description;
        }
        public void set_service_description(String bundle_description)
        {
            this.service_description = bundle_description;
        }
        public String getbundle_value()
        {
            return service_value;
        }
        public void set_service_value(String bundle_value)
        {
            this.service_value = bundle_value;
        }

        @Override
        public String toString()
        {
            return service_description + "" + service_value;
        }

    }
}

/**
 * Created by sulem on 11/24/2015.
 */





