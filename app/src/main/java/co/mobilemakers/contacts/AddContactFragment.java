package co.mobilemakers.contacts;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

/**
 * A placeholder fragment containing a simple view.
 */
public class AddContactFragment extends Fragment {

    Button mButtonDone;
    ImageButton mImageUserButton;
    Bitmap mImageBitmap;
    EditText mEditTextFirsName;
    EditText mEditTextLastName;
    EditText mEditTextNickname;

    final static int CAMERA_REQUEST_CODE = 1;

    public AddContactFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add_contact, container, false);
        mEditTextFirsName = (EditText) rootView.findViewById(R.id.edit_text_first_name);
        mEditTextLastName = (EditText) rootView.findViewById(R.id.edit_text_last_name);
        mEditTextNickname = (EditText) rootView.findViewById(R.id.edit_text_nickname);
        prepareButtonDone(rootView);
        prepareImageButton(rootView);
        return rootView;
    }

    private void prepareButtonDone(View rootView) {
        mButtonDone = (Button) rootView.findViewById(R.id.button_done);
        mButtonDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void prepareImageButton(View rootView) {
        mImageUserButton = (ImageButton) rootView.findViewById(R.id.image_button_add);
        mImageUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);

            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK && requestCode == CAMERA_REQUEST_CODE) {
            mImageBitmap = (Bitmap) data.getExtras().get("data");
            mImageUserButton.setImageBitmap(mImageBitmap);

        }
    }
}
