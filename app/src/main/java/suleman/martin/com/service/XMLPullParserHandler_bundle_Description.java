package suleman.martin.com.service;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sulem on 11/24/2015.
 */
public class XMLPullParserHandler_bundle_Description
{

    List<bundle_description_class> bundle_vals;
    List<carrier_colors_class> color_value;
    private bundle_description_class bundle_class;
    private carrier_colors_class carrier_color_class;
    private String text;

    public XMLPullParserHandler_bundle_Description()
    {
        bundle_vals = new ArrayList<bundle_description_class>();
    }

    public List<bundle_description_class> parse(InputStream is)
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

                        if(tagname.equalsIgnoreCase("bundle"))
                        {
                            bundle_class = new bundle_description_class();
                        }
                        break;

                    case XmlPullParser.TEXT:

                        text = parser.getText();
                        break;

                    case XmlPullParser.END_TAG:

                        if(tagname.equalsIgnoreCase("bundle"))
                        {
                            bundle_vals.add(bundle_class);
                        }else if(tagname.equalsIgnoreCase("bundle_description"))
                        {
                            bundle_class.setBundle_description(text);
                        }else if(tagname.equalsIgnoreCase("bundle_value"))
                        {
                            bundle_class.setbundle_value(text);
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
        return bundle_vals;
    }



}


