import java.util.*;

public class KnnRegression {

    public static Map<String, List> regression(List<CovidReport> dataset, List<CovidReport> testData) {
        System.out.println();
        List<CovidReport> resultData = new ArrayList<>();
        for (int i = 0; i < testData.size(); i++) {
            int cases = regression(dataset, testData.get(i));
            System.out.println(testData.get(i).rsvp + " Actual >> " + testData.get(i).cases + ",  Guessed> " + cases);
            resultData.add(new CovidReport(testData.get(i).id, testData.get(i).date, testData.get(i).state, cases, testData.get(i).rsvp));
        }
        HashMap<String, List> map = new HashMap<>();
        map.put("actual", testData);
        map.put("predicted", resultData);

        return map;
    }

    public static int regression(List<CovidReport> dataset, CovidReport test) {

        ArrayList<Distance> distances = new ArrayList<Distance>();
        // find distances
        for (int i = 0; i < dataset.size(); i++) {
            distances.add(new Distance(i, dataset.get(i).cases, dataset.get(i).rsvp, Math.sqrt(Math.pow(test.rsvp - dataset.get(i).rsvp, 2))));
        }

        //pick the nearest neighbours
        Collections.sort(distances);
        int k = 3;//generateK(dataset);
        int sum = 0;
        for (int i = 0; i < k; i++) {
            sum += distances.get(i).cases;
        }
        Integer cases = sum/k;
        return cases;
    }

    public static int generateK(List<CovidReport> dataset) {
        return (int) Math.sqrt(dataset.size());
    }
}
