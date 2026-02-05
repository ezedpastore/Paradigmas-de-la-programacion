package subscription;

public final class Counter {
	private int value = 0;

	public int getValue() {
		return this.value;
	}

	public void increment() {
		this.value = this.value + 1;
	}

	public void printValue() {
		System.out.println(this.value);
	}

	public static void main(String[] args) {
		Counter c1 = new Counter();
		Counter c2 = new Counter();

		c1.increment();
		c2.printValue();
	}

}
