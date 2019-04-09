package model;

/**
 * Created by wolfsoft4 on 22/1/18.
 */

public class Property {

    private String address,bed,shower,price,sqrft;
    private int image;

    public Property(String address, String price, String bed, String shower, String sqrft, int image) {
        this.address = address;
        this.bed = bed;
        this.shower = shower;
        this.price = price;
        this.sqrft = sqrft;
        this.image = image;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBed() {
        return bed;
    }

    public void setBed(String bed) {
        this.bed = bed;
    }

    public String getShower() {
        return shower;
    }

    public void setShower(String shower) {
        this.shower = shower;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSqrft() {
        return sqrft;
    }

    public void setSqrft(String sqrft) {
        this.sqrft = sqrft;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
