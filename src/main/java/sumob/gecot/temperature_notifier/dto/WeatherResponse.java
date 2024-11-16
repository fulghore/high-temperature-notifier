package sumob.gecot.temperature_notifier.dto;

public class WeatherResponse {

    private Main main;

    // Getters e Setters
    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    // Classe interna para mapear o objeto "main" do JSON
    public static class Main {
        private double temp;

        public double getTemp() {
            return temp;
        }

        public void setTemp(double temp) {
            this.temp = temp;
        }
    }
}
