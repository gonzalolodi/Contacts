package co.mobilemakers.contacts;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import java.io.ByteArrayOutputStream;

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
    byte[] mImageByteArray;

    final static int CAMERA_REQUEST_CODE = 1;

    public AddContactFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add_contact, container, false);
        prepareEditTexts(rootView);
        prepareButtonDone(rootView);
        prepareImageButton(rootView);
        return rootView;
    }

    private void prepareEditTexts(View rootView) {
        mEditTextFirsName = (EditText) rootView.findViewById(R.id.edit_text_first_name);
        mEditTextLastName = (EditText) rootView.findViewById(R.id.edit_text_last_name);
        mEditTextNickname = (EditText) rootView.findViewById(R.id.edit_text_nickname);
        TextWatcher obligatoryFields = (new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mButtonDone.setEnabled(!TextUtils.isEmpty(mEditTextFirsName.getText()) && !TextUtils.isEmpty(mEditTextLastName.getText()));
            }
        });
        mEditTextFirsName.addTextChangedListener(obligatoryFields);
        mEditTextLastName.addTextChangedListener(obligatoryFields);
    }

    private void prepareButtonDone(View rootView) {
        mButtonDone = (Button) rootView.findViewById(R.id.button_done);
        mButtonDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getResultIntent();
                Activity activity = getActivity();
                activity.setResult(Activity.RESULT_OK, intent);
                activity.finish();
            }
        });
    }

    private Intent getResultIntent() {
        Intent intent = new Intent();
        intent.putExtra(Contact.FIRSTNAME, mEditTextFirsName.getText().toString());
        intent.putExtra(Contact.LASTNAME, mEditTextLastName.getText().toString());
        if (!TextUtils.isEmpty(mEditTextNickname.getText())) {
            intent.putExtra(Contact.NICKNAME, mEditTextNickname.getText().toString());
        } else {
            intent.putExtra(Contact.NICKNAME, "");
        }
        convertImageToByteArray();
        intent.putExtra(Contact.IMAGE, mImageByteArray);
        return intent;
    }

    private void convertImageToByteArray() {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        mImageBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        mImageByteArray = stream.toByteArray();
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
        mImageBitmap = BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.placeholder_contact);

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
