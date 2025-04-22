package vn.rawuy.laptopshop.domain.dto;

public class PriceRange {
    private Double min;
    private Double max;

    public PriceRange(double min, double max) {
        this.min = min;
        this.max = max;
    }

    public Double getMin() {
        return min;
    }

    public Double getMax() {
        return max;
    }

    public boolean hasMin() {
        return min != null;
    }

    public boolean hasMax() {
        return max != null;
    }

    public boolean isValid() {
        return hasMin() || hasMax();
    }
}
