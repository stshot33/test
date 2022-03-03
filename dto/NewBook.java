package dto;

public class NewBook {
	private String img;			// 이미지
	private String name;		// 제목
	private String publisher;	// 저자
	private int value;			// 도서 고유값
	private int Purchase;		// 도서 구매 수

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public int getPurchase() {
		return Purchase;
	}

	public void setPurchase(int purchase) {
		Purchase = purchase;
	}

}