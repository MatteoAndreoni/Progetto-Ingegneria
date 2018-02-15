package Models;

public class User {

    private String _CF;
    private String _username;
    private String _password;
    private String _name;
    private String _surname;
    private String _address;
    private String _telephone;
    private String _cellphone;
    private boolean _isEmployee;

    public User(){

    }

    public User(String _CF, String _username, String _password, String _name, String _surname, String _address, String _telephone, String _cellphone, boolean _isEmployee) {
        this._CF = _CF;
        this._username = _username;
        this._password = _password;
        this._name = _name;
        this._surname = _surname;
        this._address = _address;
        this._telephone = _telephone;
        this._cellphone = _cellphone;
        this._isEmployee = _isEmployee;
    }

    public String get_CF() {
        return _CF;
    }

    public void set_CF(String _CF) {
        this._CF = _CF;
    }

    public String get_username() {
        return _username;
    }

    public void set_username(String _username) {
        this._username = _username;
    }

    public String get_password() {
        return _password;
    }

    public void set_password(String _password) {
        this._password = _password;
    }

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public String get_surname() {
        return _surname;
    }

    public void set_surname(String _surname) {
        this._surname = _surname;
    }

    public String get_address() {
        return _address;
    }

    public void set_address(String _address) {
        this._address = _address;
    }

    public String get_telephone() {
        return _telephone;
    }

    public void set_telephone(String _telephone) {
        this._telephone = _telephone;
    }

    public String get_cellphone() {
        return _cellphone;
    }

    public void set_cellphone(String _cellphone) {
        this._cellphone = _cellphone;
    }

    public boolean get_isEmployee() {
        return _isEmployee;
    }

    public void set_isEmployee(boolean _isEmployee) {
        this._isEmployee = _isEmployee;
    }
}
