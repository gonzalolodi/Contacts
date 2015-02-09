package co.mobilemakers.contacts;

        import android.content.Context;
        import android.graphics.Bitmap;
        import android.graphics.BitmapFactory;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ArrayAdapter;
        import android.widget.ImageView;
        import android.widget.TextView;

        import java.io.ByteArrayOutputStream;
        import java.util.List;

/**
 * Created by gonzalo.lodi on 06/02/2015.
 */
public class ContactAdapter extends ArrayAdapter<Contact> {

    Context mContext;
    List<Contact> mContacts;

    public ContactAdapter(Context context, List<Contact> contacts) {
        super(context, R.layout.contact_list_item, contacts);
        mContext = context;
        mContacts = contacts;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView;
        rowView = reuseOrGenerateRowView(convertView, parent);
        displayContentInView(position, rowView);

        return rowView;
    }

    private View reuseOrGenerateRowView(View convertView, ViewGroup parent) {
        View rowView;
        if (convertView != null) {
            rowView = convertView;
        } else {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.contact_list_item, parent, false);
        }
        return rowView;
    }

    private void displayContentInView(int position, View rowView) {
        if (rowView != null) {
            TextView textViewFirstName = (TextView) rowView.findViewById(R.id.text_view_first_name);
            textViewFirstName.setText(mContacts.get(position).getFirstName());
            TextView textViewLastName = (TextView) rowView.findViewById(R.id.text_view_last_name);
            textViewLastName.setText(mContacts.get(position).getLastName());
            TextView textViewNickname = (TextView) rowView.findViewById(R.id.text_view_nickname);
            textViewNickname.setText(mContacts.get(position).getNickname());
            ImageView imageViewPhoto = (ImageView) rowView.findViewById(R.id.image_view_photo);

            Bitmap bmp = getBitmap(position);
            imageViewPhoto.setImageBitmap(bmp);
        }
    }

    private Bitmap getBitmap(int position) {
        Bitmap bmp;
        byte[] image;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inMutable = true;
        image = mContacts.get(position).getImage();
        bmp = BitmapFactory.decodeByteArray(image, 0, image.length, options);
        return bmp;
    }

    public Contact getContactItem(int position){
        return mContacts.get(position);
    }
}