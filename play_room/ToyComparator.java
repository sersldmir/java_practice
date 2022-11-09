import java.util.Comparator;

public class ToyComparator implements Comparator<Toy> {
    @Override
    public int compare(Toy a, Toy b){
        int a_price = a.get_price();
        int b_price = b.get_price();
        return Integer.compare(a_price, b_price);
        }
}
