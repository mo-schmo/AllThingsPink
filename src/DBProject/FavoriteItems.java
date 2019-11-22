package DBProject;

public class FavoriteItems {
	protected int itemID;
	protected int userID;
	protected String itemName;
	
	public FavoriteItems(int itemID, int userID, String itemName) {
		this.itemID = itemID;
		this.userID = userID;
		this.itemName = itemName;
	}

	public int getItemID() {
		return itemID;
	}

	public void setItemID(int itemID) {
		this.itemID = itemID;
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
}
