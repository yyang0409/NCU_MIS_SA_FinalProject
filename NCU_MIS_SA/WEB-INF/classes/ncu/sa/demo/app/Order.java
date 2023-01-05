package ncu.sa.demo.app;

import java.lang.ref.PhantomReference;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

import javax.script.ScriptEngineManager;

import org.json.*;

public class Order {

    /** id，訂單編號 */
    private int id;

    /** member_name，會員姓名 */
    private String member_name;

    /** payment_type 付款方式 */
    private int payment_type;

    /** email，會員電子郵件信箱 */
    private String email;

    /** address，會員地址 */
    private String address;

    /** phone，會員手機 */
    private String phone;

    /** list，訂單列表 */
    private ArrayList<OrderItem> list = new ArrayList<OrderItem>();

    /** oph，OrderItemHelper 之物件與 Order 相關之資料庫方法（Sigleton） */
    private OrderItemHelper oph = OrderItemHelper.getHelper();

    /**
     * 實例化（Instantiates）一個新的（new）Order 物件<br>
     * 採用多載（overload）方法進行，此建構子用於建立訂單資料時，產生一個新的訂單
     *
     * @param member_name 會員姓名
     * @param payment_type 付款方式
     * @param email 會員電子信箱
     * @param address 會員地址
     * @param phone 會員電話
     */
    public Order(String member_name, int payment_type, String email, String address, String phone) {
        setMemberName(member_name);
        setPaymentType(payment_type);
        setEmail(email);
        setAddress(address);
        setPhone(phone);
    }

    /**
     * 實例化（Instantiates）一個新的（new）Order 物件<br>
     * 採用多載（overload）方法進行，此建構子用於修改訂單資料時，新改資料庫已存在的訂單
     *
     * @param member_name 會員姓名
     * @param payment_type 付款方式
     * @param email 會員電子信箱
     * @param address 會員地址
     * @param phone 會員電話
     */
    public Order(int id, String member_name, int payment_type, String email, String address, String phone) {
        setId(id);
        setMemberName(member_name);
        setEmail(email);
        setAddress(address);
        setPhone(phone);
        getOrderProposalOptionFromDB();
    }

    /**
     * 新增一個訂單提案選項
     */
    public void addOrderProposalOption(ProposalOption po) {
        this.list.add(new OrderItem(po));
    }

    /**
     * 新增一個訂單提案選項
     */
    public void addOrderProposalOption(OrderItem op) {
        this.list.add(op);
    }

    /**
     * 設定訂單編號
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * 取得訂單編號
     *
     * @return int 回傳訂單編號
     */
    public int getId() {
        return this.id;
    }

    /**
     * 設定會員姓名
     */
    public void setMemberName(String member_name){
        this.member_name = member_name;
    }

    /**
     * 取得訂單會員的姓名
     *
     * @return String 回傳訂單會員的姓名
     */
    public String getMemberName() {
        return member_name;
    }


    /**
     * 設定付款方式
     */
    public void setPaymentType(int payment_type){
        this.payment_type = payment_type;
    }


    /**
     * 取得付款方式
     * @return 回傳付款方式
     */
    public int getPaymentType(){
        return payment_type;
    }

    /**
     * 設定訂單信箱
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 取得訂單信箱
     *
     * @return String 回傳訂單信箱
     */
    public String getEmail() {
        return email;
    }

    /**
     * 設定訂單地址
     *
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * 取得訂單地址
     *
     * @return String 回傳訂單地址
     */
    public String getAddress() {
        return address;
    }

    /**
     * 設定訂單電話
     *
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * 取得訂單電話
     *
     * @return String 回傳訂單電話
     */
    public String getPhone() {
        return phone;
    }
    
    /**
     * 取得該名會員所有資料
     *
     * @return the data 取得該名會員之所有資料並封裝於JSONObject物件內
     */
    public ArrayList<OrderItem> getOrderProposalOption() {
        return list;
    }

    /**
     * 從 DB 中取得訂單產品
     */
    private void getOrderProposalOptionFromDB() {
        ArrayList<OrderItem> data = oph.getOrderProposalOptionByOrderId(this.id);
        this.list = data;
    }

    /**
     * 取得訂單基本資料
     *
     * @return JSONObject 取得訂單基本資料
     */
    public JSONObject getOrderData() {
        JSONObject jso = new JSONObject();
        jso.put("id", getId());
        jso.put("member_name", getMemberName());
        jso.put("payment_type", getPaymentType());
        jso.put("email", getEmail());
        jso.put("address", getAddress());
        jso.put("phone", getPhone());



        return jso;
    }

    /**
     * 取得訂單產品資料
     *
     * @return JSONArray 取得訂單產品資料
     */
    public JSONArray getOrderProposalOptionData() {
        JSONArray result = new JSONArray();

        for(int i=0 ; i < this.list.size() ; i++) {
            result.put(this.list.get(i).getData());
        }

        return result;
    }

    /**
     * 取得訂單所有資訊
     *
     * @return JSONObject 取得訂單所有資訊
     */
    public JSONObject getOrderAllInfo() {
        JSONObject jso = new JSONObject();
        jso.put("order_info", getOrderData());
        jso.put("proposaloption_info", getOrderProposalOptionData());

        return jso;
    }

    /**
     * 設定訂單產品編號
     */
    public void setOrderProposalOptionId(JSONArray data) {
        for(int i=0 ; i < this.list.size() ; i++) {
            this.list.get(i).setId((int) data.getLong(i));
        }
    }

}