/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ms3codechallenge;

/**
 *
 * @author abdul363
 */
public class Ms3Person {
    
//define Person data members
	private String firstName;
	private String lastName;
	private String website;
	private String gender;
	private String img;
	private String company;
        
	private float charges;
	private boolean h_column;
	private boolean i_column;
	private String city;

	//initialise the data members of Person class
	public Ms3Person(String firstName, String lastName, String email, String gender, String urlLink, String card,
			float charges, boolean h_column, boolean i_column, String city) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.website = email;
		this.gender = gender;
		this.img = urlLink;
		this.company = card;
		this.charges = charges;
		this.h_column = h_column;
		this.i_column = i_column;
		this.city = city;
	}

	//get the firstName of Person
	public String getFirstName() {
		return firstName;
	}
	
	//set the lastName of Person
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	//get the lastName of Person
	public String getLastName() {
		return lastName;
	}
	
	//set the lastName of Person
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	//get the email of Person
	public String getEmail() {
		return website;
	}

	//set the email of Person
	public void setEmail(String email) {
		this.website = email;
	}

	//get gender of Person
	public String getGender() {
		return gender;
	}

	//set Gender of PErson
	public void setGender(String gender) {
		this.gender = gender;
	}

	//get URLLINK 
	public String getUrlLink() {
		return img;
	}

	//setURLLINK
	public void setUrlLink(String urlLink) {
		this.img = urlLink;
	}

	//get card details of PErson
	public String getCard() {
		return company;
	}

	//set card details of PErson
	public void setCard(String card) {
		this.company = card;
	}

	//get Charges paid by PErson
	public float getCharges() {
		return charges;
	}

	//setcharges paid by PErson
	public void setCharges(float charges) {
		this.charges = charges;
	}

	//get details of H_column
	public boolean getH_column() {
		return h_column;
	}

	//set details of H_column
	public void setH_column(boolean h_column) {
		this.h_column = h_column;
	}

	//get details of I_column
	public boolean getI_column() {
		return i_column;
	}

	//set details of I_column
	public void setI_column(boolean i_column) {
		this.i_column = i_column;
	}

	//get  location ie. city of person
	public String getCity() {
		return city;
	}

	//set location ie. city of person
	public void setCity(String city) {
		this.city = city;
	}

	//print the details of Person class when printed 
	@Override
	public String toString() {
		return "Person [firstName=" + firstName + ", lastName=" + lastName + ", website=" + website + ", gender=" + gender
				+ ", img=" + img + ", company=" + company + ",charges=" +  charges + ", h_column=" + h_column
				+ ", i_column=" + i_column + ", city=" + city + "]";
	}
	
	
}
