package com.example.user.fidyahapp.Model;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties
public class AsnafDetails {
     public String AsnafName;
    public double Latitude;
    public double Longitude;
    public String keyValue;

    public AsnafDetails() {
    }

    public AsnafDetails(String AsnafName, double latitude, double longitude) {
        this.AsnafName = AsnafName;
        this.Latitude = latitude;
        this.Longitude = longitude;
    }

    public void setKeyValue(String keyValue) {
        this.keyValue = keyValue;
    }

    public void setAsnafName(String asnafName) {
        AsnafName = asnafName;
    }

    public void setLatitude(double latitude) {
        Latitude = latitude;
    }

    public void setLongitude(double longitude) {
        Longitude = longitude;
    }

    public String getAsnafName() {
        return AsnafName;
    }

    public double getLatitude() {
        return Latitude;
    }

    public double getLongitude() {
        return Longitude;
    }

    public String getKeyValue() {
        return keyValue;
    }
}
