package co.mobilemakers.contacts;

/**
 * Created by gonzalo.lodi on 06/02/2015.
 */
public class Contact {

    String nickname;
    String firstName;
    String lastName;

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
}
