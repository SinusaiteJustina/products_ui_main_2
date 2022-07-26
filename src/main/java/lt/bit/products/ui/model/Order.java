package lt.bit.products.ui.model;

import lt.bit.products.ui.service.domain.OrderStatus;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Order {

    private String id;
    private String customerName;

    private String customerAddress;
    private String customerCity;
    private String customerCountry;
    private String customerEmail;
    private String customerPhone;
    private Integer userId;
    private double totalCartAmount;

    private OrderStatus status;

    private List<OrderItem> items = new ArrayList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customer_name) {
        this.customerName = customer_name;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customer_address) {
        this.customerAddress = customer_address;
    }

    public String getCustomerCity() {
        return customerCity;
    }

    public void setCustomerCity(String customer_city) {
        this.customerCity = customer_city;
    }

    public String getCustomerCountry() {
        return customerCountry;
    }

    public void setCustomerCountry(String customer_country) {
        this.customerCountry = customer_country;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customer_email) {
        this.customerEmail = customer_email;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customer_phone) {
        this.customerPhone = customer_phone;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer user_id) {
        this.userId = user_id;
    }

    public double getTotalCartAmount() {
        return totalCartAmount;
    }

    public void setTotalCartAmount(double total_cart_amount) {
        this.totalCartAmount = total_cart_amount;
    }
    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }
    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

}