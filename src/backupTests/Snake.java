package backupTests;

public class Snake implements Cloneable {
	private int age;
	private int exhaustion;

	public Snake(int age) {
		this.age = age;
		this.exhaustion = 0;
	}

	public Snake(Snake s) {
		this.age = s.age;
		this.exhaustion = 50;
	}

	public Object clone() throws
			CloneNotSupportedException
	{
		return super.clone();
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
