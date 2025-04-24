package vn.rawuy.laptopshop.domain.dto;

import java.util.Optional;

public class ProductCriteriaDTO {
    private Optional<String> page;
    private Optional<String> target;
    private Optional<String> factory;
    private Optional<String> price;
    private Optional<String> sort;

    public Optional<String> getPage() {
        return page;
    }

    public void setPage(Optional<String> page) {
        this.page = page;
    }

    public Optional<String> getTarget() {
        return target;
    }

    public void setTarget(Optional<String> target) {
        this.target = target;
    }

    public Optional<String> getFactory() {
        return factory;
    }

    public void setFactory(Optional<String> factory) {
        this.factory = factory;
    }

    public Optional<String> getPrice() {
        return price;
    }

    public void setPrice(Optional<String> price) {
        this.price = price;
    }

    public Optional<String> getSort() {
        return sort;
    }

    public void setSort(Optional<String> sort) {
        this.sort = sort;
    }

}
