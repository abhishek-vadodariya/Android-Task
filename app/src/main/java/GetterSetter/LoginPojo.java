package GetterSetter;

public class LoginPojo
{
    private int LoginId = 0 ;
    private String Name = "";
    private String Email = "";
    private String Password ="";


    public int getLoginId() {
        return LoginId;
    }

    public void setLoginId(int loginId) {
        LoginId = loginId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

}
