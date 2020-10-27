import weka.core.Instances;

import java.util.List;
import java.util.Map;

public class main {

    public static void main(String[] args) {
    //loader
        List<CovidReport> covidReports = DataLoader.loadCovidReportsFromDB();
        System.out.println("sizeeeeeee"+covidReports.size());

    //filter
        List<CovidReport> covidReportsFiltered = Filter.runFilters(covidReports);
        System.out.println("sizeeeeeee"+covidReportsFiltered.size());

    //split data into model and test set.
        int model = (covidReportsFiltered.size() * 4)/5;
        List<CovidReport> modelData = covidReportsFiltered.subList(0, model);
        List<CovidReport> testData = covidReportsFiltered.subList(model + 1, covidReportsFiltered.size() - 1);

    //testing using KNN regression
        Map<String, List> resultMap = KnnRegression.regression(modelData, testData);
        Visualizer.display(resultMap.get("actual"), resultMap.get("predicted"));


//        require weka 3.6
        //testing using Linear Regression using Weka
        Instances dataset = LinearRegressionWeka.loadIntoWeka(covidReportsFiltered);
        LinearRegressionWeka.regression(dataset);
    }
}
