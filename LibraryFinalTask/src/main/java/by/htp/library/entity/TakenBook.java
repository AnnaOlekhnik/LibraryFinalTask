package by.htp.library.entity;

public class TakenBook {

	private int cardId;
	private int bookId;
	private int daysOfuse;
	
	public TakenBook() {}

	public TakenBook(int cardId, int bookId, int daysOfuse) {
		super();
		this.cardId = cardId;
		this.bookId = bookId;
		this.daysOfuse = daysOfuse;
	}

	public int getCardId() {
		return cardId;
	}

	public void setCardId(int cardId) {
		this.cardId = cardId;
	}

	public int getBookId() {
		return bookId;
	}

	public void setBookId(int bookId) {
		this.bookId = bookId;
	}

	public int getDaysOfuse() {
		return daysOfuse;
	}

	public void setDaysOfuse(int daysOfuse) {
		this.daysOfuse = daysOfuse;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + bookId;
		result = prime * result + cardId;
		result = prime * result + daysOfuse;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TakenBook other = (TakenBook) obj;
		if (bookId != other.bookId)
			return false;
		if (cardId != other.cardId)
			return false;
		if (daysOfuse != other.daysOfuse)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TakenBook [cardId=" + cardId + ", bookId=" + bookId + ", daysOfuse=" + daysOfuse + "]";
	}
	
	
}
