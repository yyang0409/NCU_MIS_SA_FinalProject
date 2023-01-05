package ncu.sa.demo.app;

import org.json.*;

public class Proposal {

    private int proposal_id;
    private int category_id;
	private String title;
    private String content;
    private String image;
    private int raised_funds;
    private int goal;
	private int proposal_status;
	private int viewed_num;
	private String created_date;
	private String due_date;
	
	private ProposalOption po;
	private String proposal_option_title;
	private String description;
	private int price;
	
	
	/** ph，ProposalHelper之物件與Proposal相關之資料庫方法（Sigleton） */
    private ProposalHelper ph =  ProposalHelper.getHelper();
    /*新增提案資訊*/
    public Proposal(int category_id,String title,String content,String image,int raised_funds,int goal,int proposal_status,int viewed_num,String created_date,String due_date) {
		setCategoryId(category_id);
		setTitle(title);
		setContent(content);
		setImage(image);
		setRaisedFunds(raised_funds);
		setGoal(goal);
		setProposalStatus(proposal_status);
		setViewedNum(viewed_num);
		setCreatedDate(created_date);
		setDueDate(due_date);
	}
    /*Get、修改提案資訊*/
	public Proposal(int proposal_id,int category_id,String title,String content,String image,int raised_funds,int goal,int proposal_status,int viewed_num,String created_date,String due_date) {
		setProposalId(proposal_id);
		setCategoryId(category_id);
		setTitle(title);
		setContent(content);
		setImage(image);
		setRaisedFunds(raised_funds);
		setGoal(goal);
		setProposalStatus(proposal_status);
		setViewedNum(viewed_num);
		setCreatedDate(created_date);
		setDueDate(due_date);
	}

    public void setProposalId(int proposal_id){
    	this.proposal_id=proposal_id;
    }
    public int getProposalId(){
    	return this.proposal_id;
    }
    
    public void setCategoryId(int category_id){
    	this.category_id=category_id;
    }
    public int getCategoryId(){
    	return this.category_id;
    }
    
	public void setTitle(String title) {
		this.title=title;
	}
	public String getTitle() {
		return this.title;
	}
	
	public void setContent(String content) {
		this.content=content;
	}
	public String getContent() {
		return this.content;
	}
	
	public void setImage(String image) {
		this.image=image;
	}
	public String getImage() {
		return this.image;
	}
	
	public void setRaisedFunds(int raised_funds) {
		this.raised_funds=raised_funds;
	}
	public int getRaisedFunds() {
		return this.raised_funds;
	}	
	
	public void setGoal(int goal) {
		this.goal=goal;
	}
	public int getGoal() {
		return this.goal;
	}
	
	public void setProposalStatus(int proposal_status) {
		this.proposal_status=proposal_status;
	}
	public int getProposalStatus() {
		return this.proposal_status;
	}
	
	public void setViewedNum(int viewed_num) {
		this.viewed_num=viewed_num;
	}
	public int getViewedNum() {
		return this.viewed_num;
	}
	
	public void setCreatedDate(String created_date) {
		this.created_date=created_date;
	}
	public String getCreatedDate() {
		return this.created_date;
	}
	
	public void setDueDate(String due_date) {
		this.due_date=due_date;
	}
	public String getDueDate() {
		return this.due_date;
	}
	
	public ProposalOption hasOption(String proposal_option_title,String proposal_option_image,String proposal_option_description,int proposal_option_price) {
		po=new ProposalOption (proposal_id ,proposal_option_title,image,description,price);
		return po;
	}
	
   
	/**
     * 更新提案資料
     *
     * @return the JSON object 回傳SQL更新之結果與相關封裝之資料
     */
    public JSONObject update() {
        /** 新建一個JSONObject用以儲存更新後之資料 */
        JSONObject data = new JSONObject();
        
        /** 檢查該名會員是否已經在資料庫 */
        if(this.proposal_id != 0) {
            /** 透過MemberHelper物件，更新目前之提案資料置資料庫中 */
            data = ph.update(this);
        }
        
        return data;
    }
    
    /**
     * 取得產品資訊
     *
     * @return JSONObject 回傳產品資訊
     */
	public JSONObject getData() {
        /** 透過JSONObject將該項產品所需之資料全部進行封裝*/
        JSONObject jso = new JSONObject();
        jso.put("proposal_id",getProposalId());
        jso.put("category_id",getCategoryId());
        jso.put("title", getTitle());
        jso.put("content", getContent());
        jso.put("image", getImage());
        jso.put("raised_funds", getRaisedFunds());
        jso.put("goal",getGoal());
        jso.put("proposal_status", getProposalStatus());
        jso.put("viewed_num", getViewedNum());
        jso.put("created_date", getCreatedDate());
        jso.put("due_date", getDueDate());

        return jso;
    }
}
