package core;

public class Main {
    public static void main(String[] args) {
        String data = "./data/binpack1d_00.txt";
        BinPacking bp = new BinPacking(data);
        bp.firstFitDecreasing();
        System.out.println(bp);
    }
}
