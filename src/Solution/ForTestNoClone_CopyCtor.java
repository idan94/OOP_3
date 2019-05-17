package Solution;

public class ForTestNoClone_CopyCtor {
    public int num;
    public ForTestNoClone_CopyCtor() {
        num = 2;
    }
    public ForTestNoClone_CopyCtor(ForTestNoClone_CopyCtor fTNC) {
        num = fTNC.num + 100;
    }
}
