import java.util.Iterator;
import java.util.List;

public class Filter {

    public static List<CovidReport> runFilters(List<CovidReport> covidReportList) {
        covidReportList = filterRsvpValues(covidReportList);
        covidReportList = filterCasesValues(covidReportList);
        return covidReportList;
    }

    public static List<CovidReport> filterRsvpValues(List<CovidReport> covidReportList) {
        Iterator<CovidReport> covidReportIterator = covidReportList.iterator();
        while (covidReportIterator.hasNext()) {
            CovidReport covidReport = covidReportIterator.next();
            if (covidReport.rsvp == null) {
                covidReportIterator.remove();
            }
            if (covidReport.rsvp < 10) {
                covidReportIterator.remove();
            }
        }
        return covidReportList;
    }

    public static List<CovidReport> filterCasesValues(List<CovidReport> covidReportList) {
        Iterator<CovidReport> covidReportIterator = covidReportList.iterator();
        while (covidReportIterator.hasNext()) {
            CovidReport covidReport = covidReportIterator.next();
            if (covidReport.cases == null) {
                covidReportIterator.remove();
            }
            if (covidReport.cases < 10 || covidReport.cases > 40000) {
                covidReportIterator.remove();
            }
        }
        return covidReportList;
    }

}
