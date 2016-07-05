package entity;

@SuppressWarnings("unused")
public abstract class CoreEntity {

    private String title;
    private String size;
    private String unit_price;
    private String description;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getUnitPrice() {
        return unit_price;
    }

    public void setUnitPrice(String unitPrice) {
        this.unit_price = unitPrice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
