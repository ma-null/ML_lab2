package apart;

import java.util.ArrayList;
import java.util.Random;
import java.util.List;


public class Weight {
    public double[] w = new double[3];

    public Weight(double w0, double w1, double w2) {
        w[0] = w0;
        w[1] = w1;
        w[2] = w2;

    }
     public double coustFunc(List<Apart> data, Weight x ) {
         double sum_der = 0;

         for (Apart item : data) {
             double res = x.w[0] + x.w[1] * item.area + x.w[2] * item.room;

             sum_der += (res - item.price) * (res - item.price);

         }
         return (sum_der / 2 / data.size());
     }

     //genetic
    /* public List<Weight> selection(List<Apart> data, List<Weight> thetas, int n) {
         thetas.sort((o1, o2) -> (int) (Math.abs(coustFunc(data, o1)) - Math.abs(coustFunc(data, o2))));
         System.out.println("Best cost-func :" + coustFunc(data, thetas.get(0)));
         thetas.subList(n, thetas.size()).clear();
         return thetas;
     }

     public List<Weight> crossover(List<Weight> thetas) {
         List<Weight> theta = new ArrayList<>();
         for (int i = 0; i < thetas.size() - 1; i++) {
             Weight test = thetas.get(i);
             Weight nextTest = thetas.get(i+1);
             theta.add(new Weight(test.w[0], nextTest.w[1], nextTest.w[2]));
         }
         return theta;
     }

     public Weight mutation() {
         final Random random = new Random();
         return new Weight(random.nextInt( 310000),random.nextInt(10000), -random.nextInt(2000));
     }

     public Weight genetic(List<Apart> data) {
         int lastGeneration = 400;
         int mutationNamber = 100;

         List<Weight> thetas = new ArrayList<>();
         for (int i = 0; i < mutationNamber; i++) {
             thetas.add(mutation());
         }

         for (int gen = 0; gen < lastGeneration; gen++) {
             int n;
             if(thetas.size() / (3 + gen/100) < 1) n = 1;
             else n = thetas.size() / (3 + gen/100);
             thetas = selection(data, thetas, n);

             for (int i = 0; i < thetas.size() / 4; i++) {
                 List<Weight> newThetas = new ArrayList<>();
                 newThetas.add(thetas.get(i));
                 newThetas.add(thetas.get(i + 1));
                 thetas = crossover(newThetas);
             }

             int m = thetas.size() / (3 + gen / 100);
             for (int i = 0; i < m; i++) {
                 thetas.add(mutation());
             }
             System.out.println("Generation " + gen + " alive " + thetas.size() + " best thetas: w0 " + thetas.get(0).w[0]+
                     " w1 " + thetas.get(0).w[1] + " w2 " + thetas.get(0).w[2] );
         }

         thetas = selection(data, thetas, 1);
         return thetas.get(0);

     }

*/
     public void  linearRegression(List<Apart> data) {
         double alf_step = 1; //с каким шагом перебираем параметры.Его нужно задать

         int iter = 0;
         double lastSqSum = 1000_000_000_000d;

         while (true) {
             System.out.println("Iter: " + iter++);
             for (int i = 0; i < 3; i++) {
                 double delta;
                 List<Double> sum_der = new ArrayList<>(data.size());
                 for (Apart item : data) {
                     double res = w[0] + w[1] * item.area + w[2] * item.room;

                     double xi = 0;
                     switch (i) {
                         case 0:
                             xi = 1;
                             break;
                         case 1:
                             xi = item.area;
                             break;
                         case 2:
                             xi = item.room;
                             break;
                     }

                     sum_der.add((res - item.price) * xi);

                 }
                 delta = alf_step * sum_der.stream().mapToDouble(x->x).sum() / data.size();
                 w[i] -= delta;
                 //System.out.println("res" + sum_der.get(2));
             }

             double sum_square = 0;
             for (Apart item : data) {
                 double res = w[0] + w[1] * item.area + w[2] * item.room;
                 sum_square += (res - item.price) * (res - item.price);
             }
             sum_square /= 2 * data.size();
             //System.out.println("SqSum: " + sqSum + " delta " + (lastSqSum - sqSum));
             if (lastSqSum - sum_square < 100) {
                 System.out.println("Found coeffs: " + w[0] + " " + w[1] + " " + w[2]);
                 break;
             }
             lastSqSum = sum_square;
         }
     }


    public double error (List<Apart> data) {
            double err = 0;
            for(Apart item: data) {
                double res = w[0] + w[1] * item.area + w[2] * item.room;
                err += (res - item.price) * (res - item.price);
            }
            err /= data.size();
            return Math.sqrt(err) ;
    }
    //????weight

    /*public List<Double> makeHypot(Apart[] data) {
        List<Double> hypot = new ArrayList<>();
        int n = 0;
        for(Apart item: data) {
            hypot.add( w[0] + w[1] * item.area + w[2] * item.room);
        }
        return hypot;
    }*/


}