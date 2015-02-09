package co.mobilemakers.contacts;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;

/**
 * Created by gonzalo.lodi on 06/02/2015.
 */
public class Contact {

    public final static String FIRSTNAME = "firstname";
    public final static String LASTNAME = "lastname";
    public final static String NICKNAME = "nickname";
    public final static String IMAGE = "image";
    public final static String ID = "_id";

    @DatabaseField(generatedId = true, columnName = ID) private int id;
    @DatabaseField (columnName = FIRSTNAME) private String firstName;
    @DatabaseField (columnName = LASTNAME) private String lastName;
    @DatabaseField (columnName = NICKNAME) private String nickname;
    @DatabaseField (columnName = IMAGE, dataType = DataType.BYTE_ARRAY) private byte[] image;


    public Contact() {
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
