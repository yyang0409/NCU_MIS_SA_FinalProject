package ncu.sa.demo.app;

import org.json.JSONObject;

public class SponsorRecord{
	private int id;
	private int member_id;
	private int proposalOption_id;
	private int sponsor_payment_type;
	private int sponsor_amount;
	private int proposalOption_unitPrice;
	private int totalPrice;
	
	
/*	新增*/	
	public SponsorRecord(int member_id,int proposalOption_id,
			int sponsor_payment_type,int sponsor_amount,int proposalOption_unitPrice,int totalPrice){
		setMember_id(member_id);
		setProposalOption_id(proposalOption_id);
		setSponsor_payment_type(sponsor_payment_type);
		setSponsor_amount(sponsor_amount);
		setProposalOption_unitPrice(proposalOption_unitPrice);
		setTotalPrice(totalPrice);
	}	
	
/*	檢視*/
	
	public SponsorRecord(int id,int member_id,int proposalOption_id,
		int sponsor_payment_type,int sponsor_amount,int proposalOption_unitPrice,int totalPrice){
		
		setMember_id(member_id);
		setProposalOption_id(proposalOption_id);
		setSponsor_payment_type(sponsor_payment_type);
		setSponsor_amount(sponsor_amount);
		setProposalOption_unitPrice(proposalOption_unitPrice);
		setTotalPrice(totalPrice);
	}
	
	public JSONObject getData() {
        /** 透過JSONObject將該名會員所需之資料全部進行封裝*/ 
        JSONObject jso = new JSONObject();
        jso.put("sponsor_record_id", getId());
        jso.put("member_id", getMember_id());
        jso.put("proposal_option_id", getProposalOption_id());
        jso.put("sponsor_payment_type", getSponsor_payment_type());
        jso.put("sponsor_amount", getSponsor_amount());
        jso.put("proposal_option_unit_price", getProposalOption_unitPrice());
        jso.put("total_price", getTotalPrice());
        
        ProposalOptionHelper poh =ProposalOptionHelper.getHelper();
        jso.put("proposal_option_title", poh.getProposalOptionById(Integer.toString(getProposalOption_id())).getProposalOptionTitle());
        ProposalHelper ph =ProposalHelper.getHelper();
        jso.put("proposal_title",ph.getById(Integer.toString(poh.getProposalOptionById(Integer.toString(getProposalOption_id())).getProposalId())).getTitle());
        
        return jso;
    }
	
	
	
	public void setId(int id) {
		this.id=id;
	}
	public int getId() {
		return id;
	}
	
	public void setMember_id(int member_id) {
		this.member_id=member_id;
	}
	public int getMember_id() {
		return member_id;
	}
	
	public void setProposalOption_id(int proposalOption_id) {
		this.proposalOption_id=proposalOption_id;
	}
	public int getProposalOption_id() {
		return this.proposalOption_id;
	}
	
	public void setSponsor_payment_type(int sponsor_payment_type) {
		this.sponsor_payment_type=sponsor_payment_type;
	}
	public int getSponsor_payment_type() {
		return this.sponsor_payment_type;
	}
	
	public void setSponsor_amount(int sponsor_amount) {
		this.sponsor_amount=sponsor_amount;
	}
	public int getSponsor_amount() {
		return this.sponsor_amount;
	}
	
	public void setProposalOption_unitPrice(int proposalOption_unitPrice) {
		this. proposalOption_unitPrice= proposalOption_unitPrice;
	}
	public int getProposalOption_unitPrice() {
		return this. proposalOption_unitPrice;
	}
	
	public void setTotalPrice(int totalPrice) {
		this. totalPrice= totalPrice;
	}
	public int getTotalPrice() {
		return this.totalPrice;
	}
	
	
	
	
}