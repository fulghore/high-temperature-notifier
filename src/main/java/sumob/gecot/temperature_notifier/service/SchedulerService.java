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

    // cron = "0 */30 * * * *" (todos os dias a cada 30 minutos)
    @Scheduled(cron = "0 0,30 8-11,14-16 * * MON-FRI") // A cada 30 minutos nos horários especificados, dias úteis
    public void checkTemperature() {
        try {
            double temperature = servicePrevision.getTemperature();
            if (temperature >= 20.0) {
                emailService.sendEmail("Temperatura atual: " + temperature);
            }
        } catch (Exception e) {
            System.err.println("Erro ao verificar temperatura ou enviar e-mail: " + e.getMessage());
        }
    }
}
