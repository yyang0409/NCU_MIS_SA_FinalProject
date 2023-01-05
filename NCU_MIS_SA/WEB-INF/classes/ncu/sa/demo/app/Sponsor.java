package ncu.sa.demo.app;

public class Sponsor extends Member {
	
	/** role，會員身份 */
	private int role;
	
	/** payment_type，付款方式 */
	private int payment_type;
	
	
	/**
     * 實例化（Instantiates）一個新的（new）Sponsor物件<br>
     * 採用多載（overload）方法進行，此建構子用於建立贊助者資料時，產生一名新的贊助者
     * @param id 會員編號
     * @param name 會員姓名
     * @param phone 會員電話
     * @param email 會員電子信箱
     * @param salt 會員加鹽值
     * @param address 會員住址
     * @param role 會員身份
     * @param password 會員密碼
     * @param payment_type 付款方式
     *  
     */
	public Sponsor(String name, String phone, String email, String salt, 
			String address, int role,String password,int id,int payment_type) {
		super(name,phone, email, salt, address, role, password,id);
		setRole();
		setPayment_type(payment_type);
	}
    
	 
    /**
     * 設立會員資料之會員身份
     *
     * 
     */
    
    public void setRole(){
        this.role=2;
    }
    
    /**
     * 設定會員資料之會員身份
     *
     * 
     */
    
    public void setRole(int role) {
        this.role=role;
    }
    
    /**
     * 取得會員資料之會員身份
     *
     * @return the role 回傳會員身份
     */
    
    public int getRole() {
        return this.role;
    }
	
    
    
    /**
     * 設立會員資料之付款方式
     *
     * 
     */
    
    public void setPayment_type(){
        this.payment_type=1;
    }
    
    /**
     * 設定會員資料之付款方式
     *
     * 
     */
    
    public void setPayment_type(int payment_type) {
        this.payment_type=payment_type;
    }
    
    
    /**
     * 取得會員資料之付款方式
     *
     * @return the role 回傳會員之付款方式
     */
    
    public int getPayment_type() {
        return this.payment_type;
    }
	
	
}
