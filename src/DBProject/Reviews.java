package DBProject;

public class Reviews {
	private int reviewID;
	private int userID;
	private int itemID;
	private String rating;
	private String description;
	private String dateOfReview;
	
	public Reviews(int reviewID, int userID, int itemID, String rating, String description, String dateOfReview) {
		super();
		
		this.reviewID = reviewID;
		this.userID = userID;
		this.itemID = itemID;
		this.rating = rating;
		this.description = description;
		this.dateOfReview = dateOfReview;
	}
	public Reviews(int userID, int itemID, String rating, String description) {
		super();
		
		this.userID = userID;
		this.itemID = itemID;
		this.rating = rating;
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getReviewID() {
		return reviewID;
	}

	public void setReviewID(int reviewID) {
		this.reviewID = reviewID;
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public int getItemID() {
		return itemID;
	}

	public void setItemID(int itemID) {
		this.itemID = itemID;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public String getDateOfReview() {
		return dateOfReview;
	}

	public void setDateOfReview(String dateOfReview) {
		this.dateOfReview = dateOfReview;
	}

	
}
