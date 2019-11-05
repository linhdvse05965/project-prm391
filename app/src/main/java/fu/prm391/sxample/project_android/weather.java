package fu.prm391.sxample.project_android;

import java.io.Serializable;

public class weather implements Serializable {
    private String Day;
    private String Status;
    private String Image;
    private String MaxTemp;
    private String MinTemp;
    private String Wind;
    private String Could;
    private String Temp;
    private String Sunrise;
    private String SunSet;
    private String Pressure;
    private String Humidity;
    private String Vitri;
    private String ThuNgay;

    public weather(){

    }

    public weather(String day, String status, String image, String maxTemp, String minTemp, String wind, String could, String temp, String sunrise, String sunSet, String pressure, String humidity, String vitri, String thuNgay) {
        Day = day;
        Status = status;
        Image = image;
        MaxTemp = maxTemp;
        MinTemp = minTemp;
        Wind = wind;
        Could = could;
        Temp = temp;
        Sunrise = sunrise;
        SunSet = sunSet;
        Pressure = pressure;
        Humidity = humidity;
        Vitri = vitri;
        ThuNgay = thuNgay;
    }

    public String getDay() {
        return Day;
    }

    public void setDay(String day) {
        Day = day;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getMaxTemp() {
        return MaxTemp;
    }

    public void setMaxTemp(String maxTemp) {
        MaxTemp = maxTemp;
    }

    public String getMinTemp() {
        return MinTemp;
    }

    public void setMinTemp(String minTemp) {
        MinTemp = minTemp;
    }

    public String getWind() {
        return Wind;
    }

    public void setWind(String wind) {
        Wind = wind;
    }

    public String getCould() {
        return Could;
    }

    public void setCould(String could) {
        Could = could;
    }

    public String getTemp() {
        return Temp;
    }

    public void setTemp(String temp) {
        Temp = temp;
    }

    public String getSunrise() {
        return Sunrise;
    }

    public void setSunrise(String sunrise) {
        Sunrise = sunrise;
    }

    public String getSunSet() {
        return SunSet;
    }

    public void setSunSet(String sunSet) {
        SunSet = sunSet;
    }

    public String getPressure() {
        return Pressure;
    }

    public void setPressure(String pressure) {
        Pressure = pressure;
    }

    public String getHumidity() {
        return Humidity;
    }

    public void setHumidity(String humidity) {
        Humidity = humidity;
    }

    public String getVitri() {
        return Vitri;
    }

    public void setVitri(String vitri) {
        Vitri = vitri;
    }

    public String getThuNgay() {
        return ThuNgay;
    }

    public void setThuNgay(String thuNgay) {
        ThuNgay = thuNgay;
    }
}
