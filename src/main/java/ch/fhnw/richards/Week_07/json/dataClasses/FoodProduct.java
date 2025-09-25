package ch.fhnw.richards.Week_07.json.dataClasses;

import java.time.LocalDateTime;

public class FoodProduct extends Product {
    private LocalDateTime expiry;

    public FoodProduct() { super(); }

    public FoodProduct(int id, String name, LocalDateTime expiry) {
        super(id);
        this.name = name;
        this.expiry = expiry;
    }

    public LocalDateTime getExpiry() {
        return expiry;
    }

    public void setExpiry(LocalDateTime expiry) {
        this.expiry = expiry;
    }
    @Override
    public boolean equals(Object o) {
        if (o != null && o.getClass() == this.getClass()) {
            FoodProduct p = (FoodProduct) o;
            return this.id == p.id && this.name.equals(p.name) && this.expiry.equals(p.expiry);
        }
        return false;
    }
}
