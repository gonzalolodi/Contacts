package co.mobilemakers.contacts;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class ContactListFragment extends ListFragment {

    private final static int REQUEST_CODE = 0;
    public final static String  LOG_TAG= ContactListFragment.class.getSimpleName();
    public final static String ACTION_ADD = "add";
    public final static String ACTION_EDIT = "edit";
    public final static String ACTION_DELETE = "delete";
    public final static String ACTION = "action";

    ContactAdapter mContactAdapter;
    DatabaseHelper mDBHelper = null;

    public ContactListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_contact_list, container, false);
        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_contact_list_fragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Boolean handled = false;
        switch (id) {
            case R.id.action_add:
                Intent intent = new Intent(getActivity(), AddContactActivity.class);
                intent.putExtra(ACTION, ACTION_ADD);
                startActivityForResult(intent, REQUEST_CODE);
                handled = true;
                break;
        }
        if (!handled) {
            handled = super.onOptionsItemSelected(item);
        }
        return handled;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        prepareListView();
    }

    private void prepareListView() {
        List<Contact> entries;
        entries = getSavedContacts();
        mContactAdapter = new ContactAdapter(getActivity(), entries);
        setListAdapter(mContactAdapter);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                String firstname = data.getStringExtra(Contact.FIRSTNAME);
                String lastname = data.getStringExtra(Contact.LASTNAME);
                String nickname = data.getStringExtra(Contact.NICKNAME);
                byte[] image = data.getByteArrayExtra(Contact.IMAGE);
                Contact contact = setNewContactData(firstname, lastname, nickname, image);
                if (data.getStringExtra(ACTION).equals(ACTION_ADD)){
                    contact = saveContactOnDB(contact);
                    mContactAdapter.add(contact);
                } else {
                    contact.setId(data.getIntExtra(Contact.ID, 1));
                    if (data.getStringExtra(ACTION).equals(ACTION_EDIT)){
                        try {
                            Dao<Contact,Integer> dao = getDBHelper().getContactDao();
                            dao.update(contact);
                        } catch (SQLException e) {
                            Log.e(LOG_TAG, "Failed to create DAO.", e);
                        }
                        mContactAdapter.add(contact);
                    } else {
                        try {
                            Dao<Contact,Integer> dao = getDBHelper().getContactDao();
                            dao.delete(contact);
                        } catch (SQLException e) {
                            Log.e(LOG_TAG, "Failed to create DAO.", e);
                        }
                        mContactAdapter.remove(contact);
                    }
                }

            }
        }
    }

    private Contact setNewContactData(String firstname, String lastname, String nickname, byte[] image) {
        Contact contact = new Contact();
        contact.setFirstName(firstname);
        contact.setLastName(lastname);
        contact.setNickname(nickname);
        contact.setImage(image);
        return contact;
    }

    public DatabaseHelper getDBHelper() {
        if (mDBHelper == null){
            mDBHelper = OpenHelperManager.getHelper(getActivity(), DatabaseHelper.class);
        }
        return mDBHelper;
    }

    private List<Contact> getSavedContacts() {
        List<Contact> contacts = new ArrayList<>();
        try {
            Dao<Contact,Integer> contactDao = getDBHelper().getContactDao();
            contacts = contactDao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contacts;
    }

    private Contact saveContactOnDB(Contact contact) {
        try {
            Dao<Contact,Integer> dao = getDBHelper().getContactDao();
            dao.create(contact);
        } catch (SQLException e) {
            Log.e(LOG_TAG, "Failed to create DAO.", e);
        }

        return contact;
    }

    @Override
    public void onDestroy() {
        if (mDBHelper!=null){
            OpenHelperManager.releaseHelper();
            mDBHelper = null;
        }
        super.onDestroy();
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Contact contact = mContactAdapter.getContactItem(position);
        Intent intent = new Intent(getActivity(), AddContactActivity.class);
        intent.putExtra(Contact.FIRSTNAME, contact.getFirstName());
        intent.putExtra(Contact.LASTNAME, contact.getLastName());
        intent.putExtra(Contact.NICKNAME, contact.getNickname());
        intent.putExtra(Contact.IMAGE, contact.getImage());
        intent.putExtra(Contact.ID, contact.getId());
        intent.putExtra(ACTION, ACTION_EDIT);
        startActivityForResult(intent, REQUEST_CODE);
        mContactAdapter.remove(contact);

    }
}
