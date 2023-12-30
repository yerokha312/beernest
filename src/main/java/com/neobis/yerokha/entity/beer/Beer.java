package com.neobis.yerokha.entity.beer;


import java.math.BigDecimal;
import java.util.Objects;

public class Beer {
    private Long id;
    private String name;
    private Style style;
    private String subtype;
    private Brand brand;
    private Double alcohol;
    private Container container;
    private Integer size;
    private BigDecimal price;
    private String country;
    private String description;
    private Long availableStock;

    public Beer() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Style getStyle() {
        return style;
    }

    public void setStyle(Style style) {
        this.style = style;
    }

    public String getSubtype() {
        return subtype;
    }

    public void setSubtype(String subtype) {
        this.subtype = subtype;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public Double getAlcohol() {
        return alcohol;
    }

    public void setAlcohol(Double alcohol) {
        this.alcohol = alcohol;
    }

    public Container getContainer() {
        return container;
    }

    public void setContainer(Container container) {
        this.container = container;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getAvailableStock() {
        return availableStock;
    }

    public void setAvailableStock(Long availableStock) {
        this.availableStock = availableStock;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Beer beer = (Beer) o;
        return Objects.equals(id, beer.id) && Objects.equals(name, beer.name) && style == beer.style &&
                Objects.equals(subtype, beer.subtype) && Objects.equals(brand, beer.brand) &&
                Objects.equals(alcohol, beer.alcohol) && container == beer.container &&
                Objects.equals(size, beer.size) && Objects.equals(country, beer.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, style, subtype, brand, alcohol, container, size, country);
    }

    @Override
    public String toString() {
        return "Beer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", style=" + style +
                ", subtype='" + subtype + '\'' +
                ", brand=" + brand +
                ", alcohol=" + alcohol +
                ", container=" + container +
                ", size=" + size +
                ", price=" + price +
                ", country='" + country + '\'' +
                ", description='" + description + '\'' +
                ", availableStock=" + availableStock +
                '}';
    }

}
