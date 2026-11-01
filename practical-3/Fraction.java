import java.util.Objects;

public class Fraction {

    private int num;
    private int den;

    public Fraction(int num, int den) {

        if (den == 0) {
            throw new IllegalArgumentException("Denominator cannot be zero");
        }

        if (den < 0) {
            num = -num;
            den = -den;
        }

        int g = gcd(Math.abs(num), den);

        this.num = num / g;
        this.den = den / g;
    }

    private int gcd(int a, int b) {

        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }

        return a;
    }

    @Override
    public String toString() {
        return num + "/" + den;
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj)
            return true;

        if (obj == null || getClass() != obj.getClass())
            return false;

        Fraction f = (Fraction) obj;

        return num == f.num && den == f.den;
    }

    @Override
    public int hashCode() {
        return Objects.hash(num, den);
    }
}