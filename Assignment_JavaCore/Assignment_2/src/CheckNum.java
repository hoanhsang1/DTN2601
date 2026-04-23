public class CheckNum {
    public boolean CheckNumRange(int a,int b, int Num) {
        if (Num < a || Num > b) {
            return false;
        }
        return true;
    }
}
