package it_school.sumdu.edu.odz;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class MoneyString {
    double amount;

    public MoneyString(double value) {
        amount = (double) Math.round(value * 100) / 100;;
    }

    String convertToString() {
        DecimalFormat decimalFormat = new DecimalFormat("0.00");

        decimalFormat.setRoundingMode(RoundingMode.HALF_UP);

        return decimalFormat.format(amount);
    }
}
