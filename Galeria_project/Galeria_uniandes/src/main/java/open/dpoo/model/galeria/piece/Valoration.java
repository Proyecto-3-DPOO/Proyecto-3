package main.java.open.dpoo.model.galeria.piece;

public class Valoration {
    private double fixValue;
    private double minValue;
    private double initialValue;

    public Valoration(double fixValue, double minValue, double initialValue) {
        this.fixValue = fixValue;
        this.minValue = minValue;
        this.initialValue = initialValue;
    }

    @Override public String toString() {
        return "Valoration{" + "fixValue=" + fixValue + ", minValue=" + minValue + ", initialValue=" + initialValue +
                '}';
    }

    public double getFixValue() {
        return fixValue;
    }

    public void setFixValue(double fixValue) {
        this.fixValue = fixValue;
    }

    public double getMinValue() {
        return minValue;
    }

    public void setMinValue(double minValue) {
        this.minValue = minValue;
    }

    public double getInitialValue() {
        return initialValue;
    }

    public void setInitialValue(double initialValue) {
        this.initialValue = initialValue;
    }
}
