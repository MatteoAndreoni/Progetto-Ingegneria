package Models;

import SupportClasses.Subject;

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
        _totalPrice += p.get_price();
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
}
