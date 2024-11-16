package sumob.gecot.temperature_notifier.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class SchedulerService {

    @Autowired
    private ServicePrevision servicePrevision;

    @Autowired
    private EmailService emailService;

    @Scheduled//(cron = "0 0,30 8-11,14-16 * * MON-FRI") // A cada 30 minutos nos horários especificados, dias úteis
            (fixedRate = 60000) //Todos os dias a cada 1 minuto
    public void checkTemperature() {
        try {
            double currentTemperature = servicePrevision.getTemperature();
            System.out.println("Temperatura atual: " + currentTemperature);

            if (currentTemperature > 10) {
                emailService.sendEmail("Temperatura atual: " + currentTemperature);
            }
        } catch (Exception e) {
            System.out.println("Erro ao verificar a temperatura: " + e.getMessage());
        }
    }
}
