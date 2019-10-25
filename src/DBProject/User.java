package DBProject;

public class User {
	private int userID;
	private String password;
	private String firstName;
	private String lastName;
	private String email;
	private String gender;
	private int age;
	
	//For root user 
	private boolean root;
	
	public User(int userID, String fName, String lName, String email, String gender, int age, boolean root, String password) {
		super();
		this.userID = userID;
		this.password = password;
		this.firstName = fName;
		this.lastName = lName;
		this.email = email;
		this.gender = gender;
		this.age  = age;
		
		this.root = root;
	}


	public int getAge() {
		return age;
	}


	public void setAge(int age) {
		this.age = age;
	}


	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public boolean isRoot() {
		return root;
	}

	public void setRoot(boolean root) {
		this.root = root;
	}

}
