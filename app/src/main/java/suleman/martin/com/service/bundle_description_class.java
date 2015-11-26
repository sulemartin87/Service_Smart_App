package suleman.martin.com.service;

/**
 * Created by sulem on 11/24/2015.
 */
public class bundle_description_class {

    private String bundle_description;
    private String bundle_value;

    public String getbundle_description()
    {
        return bundle_description;
    }
    public void setBundle_description(String bundle_description)
    {
        this.bundle_description = bundle_description;
    }
    public String getbundle_value()
    {
        return bundle_value;
    }
    public void setbundle_value(String bundle_value)
    {
        this.bundle_value = bundle_value;
    }

    @Override
    public String toString()
    {
        return bundle_description;
    }

}



