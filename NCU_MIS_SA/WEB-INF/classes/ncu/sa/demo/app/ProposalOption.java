package ncu.sa.demo.app;

import org.json.JSONObject;

public class ProposalOption {
	
	private int proposal_id;
	private int proposalOption_id;
	private String proposal_option_title;
	private String image;
	private String description;
	private int price;
	private ProposalOptionHelper poh =  ProposalOptionHelper.getHelper();
	
	public ProposalOption(int proposal_id ,String proposalOption_title,String image,String description,int price) {
		setProposalId(proposal_id);
		setProposalOptionTitle(proposalOption_title);
		setImage(image);
		setDescription(description);
		setPrice(price);
	}
	
	public ProposalOption(int proposal_id ,int proposalOption_id,String proposal_option_title,String image,String description,int price) {
		setProposalId(proposal_id);
		setProposalOptionId(proposalOption_id);
		setProposalOptionTitle(proposal_option_title);
		setImage(image);
		setDescription(description);
		setPrice(price);
	}
	
	public void setProposalId(int proposal_id){
    	this.proposal_id=proposal_id;
    }
    public int getProposalId(){
    	return this.proposal_id;
    }
    
	public void setProposalOptionId(int proposalOption_id) {
		this.proposalOption_id=proposalOption_id;
	}
	public int getProposalOptionId() {
		return this.proposalOption_id;
	}
	
	public void setProposalOptionTitle(String proposal_option_title) {
		this.proposal_option_title=proposal_option_title;
	}
	public String getProposalOptionTitle() {
		return this.proposal_option_title;
	}
	
	public void setImage(String image) {
		this.image=image;
	}
	public String getImage() {
		return this.image;
	}
	
	public void setDescription(String description) {
		this.description=description;
	}
	public String getDescription() {
		return this.description;
	}
	
	public void setPrice(int price) {
		this.price=price;
	}	
	public int getPrice() {
		return this.price;
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
        if(this.proposalOption_id != 0) {
            /** 透過MemberHelper物件，更新目前之提案資料置資料庫中 */
            data = poh.update(this);
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
        jso.put("proposalOption_id",getProposalOptionId());
        jso.put("proposal_option_title", getProposalOptionTitle());
        jso.put("image", getImage());
        jso.put("description", getDescription());
        jso.put("price", getPrice());
        

        return jso;
    }

}
