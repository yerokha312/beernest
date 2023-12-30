package com.neobis.yerokha.entity.beer;

import java.util.Objects;

public final class Subtype {
    private Long id;
    private Style style;
    private String name;

    public Subtype() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Style getStyle() {
        return style;
    }

    public void setStyle(Style style) {
        this.style = style;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Subtype) obj;
        return Objects.equals(this.id, that.id) &&
                Objects.equals(this.style, that.style) &&
                Objects.equals(this.name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, style, name);
    }

    @Override
    public String toString() {
        return "Subtype[" +
                "id=" + id + ", " +
                "style=" + style + ", " +
                "name=" + name + ']';
    }

}