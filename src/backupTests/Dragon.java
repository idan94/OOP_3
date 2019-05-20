package backupTests;

public class Dragon implements Cloneable {
    private int age;
    private int exhaustion;

    public Dragon(int age) {
        this.age = age;
        this.exhaustion = 0;
    }

    public Dragon(Dragon s) {
        this.age = s.age;
        this.exhaustion = 5000;
    }

    protected Object clone() throws
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
