package Models;


import java.time.LocalDateTime;

public class Sale {

    private Cart _cart;
    private LocalDateTime _saleDate;
    private String _paymentType;
    private String _deliveryType;
    private float _salePrice;
    public float expressDeliveryCost=5;
    public float postalDeliveryCost =0;


    public Cart get_cart() {
        return _cart;
    }

    public void set_cart(Cart _cart) {
        this._cart = _cart;
    }

    public LocalDateTime get_saleDate() {
        return _saleDate;
    }

    public void set_saleDate(LocalDateTime _saleDate) {
        this._saleDate = _saleDate;
    }

    public String get_paymentType() {
        return _paymentType;
    }

    public void set_paymentType(String _paymentType) {
        this._paymentType = _paymentType;
    }

    public String get_deliveryType() {
        return _deliveryType;
    }

    public void set_deliveryType(String _deliveryType) {
        this._deliveryType = _deliveryType;
    }

    public float get_salePrice() {
        return _salePrice;
    }

    public void set_salePrice(float _salePrice) {
        this._salePrice = _salePrice;
    }

}
