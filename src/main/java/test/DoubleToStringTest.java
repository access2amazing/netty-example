package test;

/**
 * @author xueli.wang
 * @since 2021/01/19 16:10
 */

public class DoubleToStringTest {
    public static void main(String[] args) {
        Double lat = 44.699095D;
        Double lon = 84.83399600000001D;

        String lats = lat.toString();
        String lons = lon.toString();

        System.out.println(lat);
        System.out.println(lats);
        System.out.println(lon);
        System.out.println(lons);
    }
}
