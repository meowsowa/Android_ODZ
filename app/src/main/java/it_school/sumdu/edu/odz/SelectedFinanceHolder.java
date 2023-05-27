package it_school.sumdu.edu.odz;

public class SelectedFinanceHolder {
    private static Finance selectedFinance;

    public static Finance getSelectedFinance() {
        return selectedFinance;
    }

    public static void setSelectedFinance(Finance finance) {
        selectedFinance = finance;
    }
}