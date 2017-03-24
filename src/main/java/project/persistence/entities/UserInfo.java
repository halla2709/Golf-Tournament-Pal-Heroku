package project.persistence.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table( name="UserInfo" )
public class UserInfo {

	@Id
	private long social;
	
	private String password;
	
	public UserInfo() {
		super();
	}
	
	public UserInfo(long social, String password) {
		super();
		this.social = social;
		this.password = password;
	}
	
	public long getSocial() {
		return social;
	}
	public void setSocial(long social) {
		this.social = social;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
