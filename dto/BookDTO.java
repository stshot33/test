package dto;

public class BookDTO {
	private int bookvalue; // 도서 고유번호
	private String bookimg; // 책 이미지
	private String bname; // 제목
	private int authorvalue; // 저자 고유번호
	private String author; // 저자
	private int bgrade; // 평점
	private int review; // 리뷰수
	private String bpublisher; // 출판사
	private int category; // 장르
	private int total; // 회차
	private String binfomation; // 내용
	private int bprice; // 가격

	@Override
	public String toString() {
		return "bookimg:" + bookimg + ", bname:" + bname + ", author:" + author + ", bgrade:" + bgrade + ", review:"
				+ review + ", bpublisher:" + bpublisher + ", category:" + category + ", total:" + total
				+ ", binfomation:" + binfomation + ", bprice:" + bprice + "/";
	}

	public int getBookvalue() {
		return bookvalue;
	}

	public void setBookvalue(int bookvalue) {
		this.bookvalue = bookvalue;
	}

	public String getBookimg() {
		return bookimg;
	}

	public void setBookimg(String bookimg) {
		this.bookimg = bookimg;
	}

	public String getBname() {
		return bname;
	}

	public void setBname(String bname) {
		this.bname = bname;
	}

	public int getAuthorvalue() {
		return authorvalue;
	}

	public void setAuthorvalue(int authorvalue) {
		this.authorvalue = authorvalue;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public int getBgrade() {
		return bgrade;
	}

	public void setBgrade(int bgrade) {
		this.bgrade = bgrade;
	}

	public int getReview() {
		return review;
	}

	public void setReview(int review) {
		this.review = review;
	}

	public String getBpublisher() {
		return bpublisher;
	}

	public void setBpublisher(String bpublisher) {
		this.bpublisher = bpublisher;
	}

	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public String getBinfomation() {
		return binfomation;
	}

	public void setBinfomation(String binfomation) {
		this.binfomation = binfomation;
	}

	public int getBprice() {
		return bprice;
	}

	public void setBprice(int bprice) {
		this.bprice = bprice;
	}

}