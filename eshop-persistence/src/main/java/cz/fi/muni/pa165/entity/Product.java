package cz.fi.muni.pa165.entity;

import cz.fi.muni.pa165.enums.Color;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "ESHOP_PRODUCTS")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(unique = true, nullable = false)
    private String name;

    @Enumerated
    private Color color;

    @Temporal(TemporalType.DATE)
    private Date addedDate;

    public Product() {}

    public Product(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || !(obj instanceof Product)) return false;

        Product product = (Product) obj;
        return Objects.equals(getName(), product.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getName());
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

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Date getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(Date addedDate) {
        this.addedDate = addedDate;
    }
	
}
