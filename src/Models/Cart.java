package Models;

import SupportClasses.Subject;

import java.util.ArrayList;

public class Cart extends Subject{

    private ArrayList<Product> _cartList;
    private User _user;
    private float _totalPrice;

    public Cart(User user)
    {
        _totalPrice = 0;
        _user = user;
        _cartList = new ArrayList<>();
    }

    public ArrayList<Product> get_cartList() {
        return _cartList;
    }

    public void addToCart(Product p){
        _cartList.add(p);
        if(!_user.get_isPremium()) {
            _totalPrice += p.get_price();
            round(_totalPrice, 2);
        }
        else {
            _totalPrice += p.get_price();
            _totalPrice *= 0.9;
            _totalPrice = round(_totalPrice, 2);
            System.out.println("Prezzo totale: "+ _totalPrice);
        }
        notifyAllObservers();
    }

    public void removeFromCart(int position)
    {
        _totalPrice -= _cartList.get(position).get_price();
        _cartList.remove(position);
        System.out.println(_cartList);
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

    public User get_user() {
        return _user;
    }

    public float get_totalPrice() {
        return _totalPrice;
    }

    public float round( float number, int decimals ){
        return (float)(Math.round( number * Math.pow( 10, decimals ) )/Math.pow( 10, decimals ));
    }
}
