package DBProject;

public class Item {
	protected int itemID;
	protected double price;
	protected String name;
	protected String description;
	protected String category;
	protected String imageURL; //for storing images of the items
	protected int seller;
	
	public Item(int itemID, double price, String name, String description, String category, int seller) {
		super();
		
		this.itemID = itemID;
		this.price = price;
		this.name = name;
		this.description = description;
		this.category = category;
		/* this.imageURL = URL; */
		this.seller = seller;
	}
	
	public int getSeller() {
		return seller;
	}

	public void setSeller(int seller) {
		this.seller = seller;
	}

	public String getImageURL() {
		return imageURL;
	}

	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}

	public Item(String name, String description, String category, double price) {
		this.price = price;
		this.name = name;
		this.description = description;
		this.category = category;
		/* this.imageURL = URL; */
	}
	
	
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public int getItemID() {
		return itemID;
	}

	public void setItemID(int itemID) {
		this.itemID = itemID;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
