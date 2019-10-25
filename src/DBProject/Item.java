package DBProject;

public class Item {
	private int itemID;
	private double price;
	private String name;
	private String description;
	
	public Item(int itemID, double price, String name, String description) {
		super();
		
		this.itemID = itemID;
		this.price = price;
		this.name = name;
		this.description = description;
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
