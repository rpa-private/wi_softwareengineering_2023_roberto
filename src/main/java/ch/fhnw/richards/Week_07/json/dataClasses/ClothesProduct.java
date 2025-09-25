package ch.fhnw.richards.Week_07.json.dataClasses;

public class ClothesProduct extends Product {
    public static enum Size { S, M, L, XL }
    private Size size;

    public ClothesProduct() { super(); }

    public ClothesProduct(int id, String name, Size size) {
        super(id);
        this.name = name;
        this.size = size;
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }
    @Override
    public boolean equals(Object o) {
        if (o != null && o.getClass() == this.getClass()) {
            ClothesProduct p = (ClothesProduct) o;
            return this.id == p.id && this.name.equals(p.name) && this.size.equals(p.size);
        }
        return false;
    }
}
