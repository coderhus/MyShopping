package com.example.myshopping.Model;

public class Orders {
    private String id_Orders;
    private String id_seller;
    private String id_buyer;
    private int status; // quá trình vận chuyển
    // ví dụ như : 0 -> bên bán chưa giao hàng
    // 1-> bên bán đang giao hàng
    // 2-> bên mua nhận được hàng , bên mua có thể trả lại hàng
    // 3-> bên mua ko đổi được hàng nữa -> end, + lượt bán cho đơn hàng và shop
    // 2+3 -> bên mua có thể tạo feedback
    private String created_at; //time khởi tạo orders này
    public Orders(){

    }

    public Orders(String id_Orders, String id_seller, String id_buyer, int status, String created_at) {
        this.id_Orders = id_Orders;
        this.id_seller = id_seller;
        this.id_buyer = id_buyer;
        this.status = status;
        this.created_at = created_at;
    }

}
