package backupTests;

public class Bear {
	private int age;
	private int exhaustion;

	public Bear(int age) {
		this.age = age;
		this.exhaustion = 0;
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
