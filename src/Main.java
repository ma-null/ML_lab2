import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

import apart.Apart;
import apart.Weight;




public class Main {

    public static void main(String[] args) throws Exception {
        BufferedReader infile = new BufferedReader(new FileReader("prices.txt"));

        List<Apart> data = new ArrayList<>();

        String InString = infile.readLine();

        while (InString != null) {
            String[] InStringArray = InString.split(",");
            data.add(new Apart(Integer.valueOf(InStringArray[0]),
                    Integer.valueOf(InStringArray[1]),
                    Integer.valueOf(InStringArray[2])));

            InString = infile.readLine();
        }

   /* //нормализация датасета
    final double avgAr = data.stream().mapToDouble(x -> x.area).sum() / data.size();
    final double avgRoom = data.stream().mapToDouble(x -> x.room).sum() / data.size();
    final double avgPrice = data.stream().mapToDouble(x -> x.price).sum() / data.size();

    double dispAr = data.stream().mapToDouble(x -> Math.pow((x.area - avgAr), 2)).sum() / (data.size() - 1);
    double dispRoom = data.stream().mapToDouble(x -> Math.pow((x.room - avgRoom), 2)).sum() / (data.size() - 1);
    double dispPrice = data.stream().mapToDouble(x -> Math.pow((x.price - avgPrice), 2)).sum() / (data.size() - 1);
    double avgDispAr = Math.sqrt(dispAr);
    double avgDispRoom = Math.sqrt(dispRoom);
    double avgDispPrice = Math.sqrt(dispPrice);

    for (Apart item: data) {
        item.room = (item.room - avgRoom) / avgDispRoom;
        item.area = (item.area - avgAr) / avgDispAr;
        item.price = (item.price - avgPrice) / avgDispPrice;
       // System.out.println(item.area + " " + item.room + "\n");
    }
*/
        double maxArea = data.get(0).area;
        double maxRoom = data.get(0).room;
        double maxPrice = data.get(0).price;
        for (Apart item: data) {
            if(item.area > maxArea) maxArea = item.area;
            if(item.room > maxRoom) maxRoom = item.room;
            if(item.price > maxPrice) maxPrice = item.price;
        }

        List<Apart> normData = new ArrayList<>();
        for (Apart item: data) {

            normData.add(new Apart(item.area / maxArea, item.room / maxRoom, item.price));
            //item.price /= maxPrice;
        }
        //System.out.println("norm[0] = " + normData.get(0).area + "  " + normData.get(0).room + " " + normData.get(0).price);
       // System.out.println("st = " + data.get(0).area + "  " + data.get(0).room + " " + data.get(0).price);
        Weight weight = new Weight(88000,62000,-41000);
        weight.linearRegression(normData);
        //weight = weight.genetic(data);
        System.out.println( weight.error(normData));

//        System.out.println(weight.w0 + " " + weight.w1 + " " + weight.w2);

        //Apart[] aps = new Apart[data.size()];
       // data.toArray(aps);
       // MyChart mc = new MyChart(aps);
       // mc.chart();
    }
}
