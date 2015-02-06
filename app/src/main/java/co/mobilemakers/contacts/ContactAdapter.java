package co.mobilemakers.contacts;

import android.content.Context;
import android.widget.ArrayAdapter;

/**
 * Created by gonzalo.lodi on 06/02/2015.
 */
public class ContactAdapter extends ArrayAdapter<Contact> {
    public ContactAdapter(Context context, int resource) {
        super(context, resource);
    }
}
