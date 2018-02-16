package Models;

import SupportClasses.Subject;

import java.math.BigDecimal;
import java.util.ArrayList;

public class Cart extends Subject{

    private ArrayList<Product> _cartList;
    private User _user;
    private float _totalPrice;

    public Cart(User user)
    {

       // Product p = new Product();
       // p.set_code(0);

        _totalPrice = 0;
        _user = user;
        _cartList = new ArrayList<Product>();
        //_cartList.add(p);
    }

    public ArrayList<Product> get_cartList() {
        return _cartList;
    }

    public void addToCart(Product p){
        _cartList.add(p);
        if(!_user.get_isPremium()) {
            _totalPrice += p.get_price();
            arrotonda(_totalPrice, 2);
        }
        else {
            _totalPrice += p.get_price();
            _totalPrice *= 0.9;
            _totalPrice = arrotonda(_totalPrice, 2);
            System.out.println("Prezzo totale: "+ _totalPrice);
        }
        notifyAllObservers();
    }

    public void set_totalPrice(float price)
    {
        _totalPrice = price;
    }

    public void clear()
    {
        _cartList.clear();
        notifyAllObservers();
    }

    public void removeFromCart(int position)
    {
        _totalPrice -= _cartList.get(position).get_price();
        _cartList.remove(position);
        System.out.println(_cartList);
        notifyAllObservers();
    }

    //public void resetCart(){_cartList=null;}

    public User get_user() {
        return _user;
    }

    public float get_totalPrice() {
        return _totalPrice;
    }

    public float arrotonda( float numero, int nCifreDecimali ){
        return (float)(Math.round( numero * Math.pow( 10, nCifreDecimali ) )/Math.pow( 10, nCifreDecimali ));
    }
}
