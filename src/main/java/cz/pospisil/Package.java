package cz.pospisil;

public class Package {

    private float weight;
    private String postalCode;

    public Package(float weight, String postalCode) {
        this.weight = weight;
        this.postalCode = postalCode;
    }

    public float getWeight() {
        return weight;
    }

    public String getPostalCode() {
        return postalCode;
    }
}
