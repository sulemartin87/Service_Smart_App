package suleman.martin.com.service;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class XMLPullParserHandler1
{

  List<bundle_values> bundle_vals;
  private bundle_values bundle_class;
  private String text;

  public XMLPullParserHandler1()
  {
      bundle_vals = new ArrayList<bundle_values>();
  }

  public List<bundle_values> parse(InputStream is)
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
                          bundle_class = new bundle_values();
                      }
                      break;

                  case XmlPullParser.TEXT:

                      text = parser.getText();
                      break;

                  case XmlPullParser.END_TAG:

                      if(tagname.equalsIgnoreCase("bundle"))
                      {
                          bundle_vals.add(bundle_class);
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
