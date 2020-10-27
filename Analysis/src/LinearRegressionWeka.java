import weka.classifiers.functions.LinearRegression;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;

import java.util.List;

//uses weka 3.6
public class LinearRegressionWeka {

    public static Instances loadIntoWeka(List<CovidReport> covidReportList){
        Attribute attributeCases = new Attribute("cases",0);
        Attribute attributeRsvp = new Attribute("rsvp",1);
        Attribute attributeDate = new Attribute("date",2);

        FastVector attributes = new FastVector();
        attributes.addElement(attributeCases);
        attributes.addElement(attributeRsvp);
        attributes.addElement(attributeDate);

        Instances dataset = new Instances("covidVsRsvps", attributes, covidReportList.size());
        for (CovidReport covidReport : covidReportList) {
            Instance instance = new Instance(3);
            instance.setValue(attributeCases, covidReport.cases);
            instance.setValue(attributeRsvp, covidReport.rsvp);
            instance.setValue(attributeRsvp, covidReport.date);
            dataset.add(instance);
        }

        dataset.setClassIndex(0);

        return dataset;
    }

    public static void regression(Instances dataset) {
        LinearRegression linearRegression = new LinearRegression();
        try {
            linearRegression.buildClassifier(dataset);
        } catch (Exception e) {
            System.out.println("ERROR >>>> Failed building Classifier");
            e.printStackTrace();
        }

        double[] coefficientss = linearRegression.coefficients();

        System.out.println(">>Coefficients>>>>>");
        for (int i = 0; i < coefficientss.length - 1; i++) {
            System.out.println(coefficientss[i]);
        }

        double covidCasesCausedByTodaysEvents = (coefficientss[1] * 574) + (coefficientss[2] * 20201021);

        System.out.println();
        System.out.println(">>>>Covid cases = " + covidCasesCausedByTodaysEvents);
    }
}
