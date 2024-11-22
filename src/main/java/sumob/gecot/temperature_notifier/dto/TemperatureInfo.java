package sumob.gecot.temperature_notifier.dto;

public class TemperatureInfo {
    private String locationName;
    private Double temperature;

    public TemperatureInfo(String locationName, Double temperature) {
        this.locationName = locationName;
        this.temperature = temperature;
    }

    public String getLocationName() {
        return locationName;
    }

    public Double getTemperature() {
        return temperature;
    }
}