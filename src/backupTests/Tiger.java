package backupTests;

public class Tiger {
	private int age;
	private int exhaustion;

	public Tiger(int age) {
		this.age = age;
		this.exhaustion = 0;
	}

	public Tiger(Tiger t) {
		this.age = t.age;
		this.exhaustion = t.exhaustion;
	}

	public void hunt(int hours) {
		this.exhaustion += hours;
	}

	public void sleep(int hours) {
		this.exhaustion -= hours;
	}

	public int getAge() {
		return age;
	}

	public String getFeeling() {
		return this.exhaustion > 10 ? "Tired" : "Fine";
	}
}
