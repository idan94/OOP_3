package Solution;

public class ForTestCloneable implements Cloneable {
    public int num;
    public ForTestCloneable() {
        num = 1;
    }
    public ForTestCloneable(ForTestCloneable fTC) {
        num = fTC.num + 100;
    }
    private void setNum(int num){
        this.num = num;
    }
    @Override
    protected Object clone() throws CloneNotSupportedException {
        ForTestCloneable toRet = new ForTestCloneable();
        int numberToFun = 1000+this.num;
        toRet.setNum(numberToFun);
        return toRet;
    }
}
