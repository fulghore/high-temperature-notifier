package sumob.gecot.temperature_notifier.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class SchedulerService {

    private static final Logger logger = LoggerFactory.getLogger(SchedulerService.class);

    @Autowired
    private ServicePrevision servicePrevision;

    @Autowired
    private EmailService emailService;


    // Definindo o limite de temperatura como uma constante
    @Value("${temperature.threshold:20.0}")
    private static final double TEMPERATURE_THRESHOLD = 20.0;

    @Scheduled(fixedRate = 60000) // A cada 1 minuto
    public void checkTemperature() {
        try {
            double currentTemperature = servicePrevision.getTemperature();
            logger.info("Temperatura atual: {}", currentTemperature);

            if (currentTemperature > TEMPERATURE_THRESHOLD) {
                emailService.sendEmail("Temperatura atual acima do limite: " + currentTemperature);
                logger.info("E-mail enviado: Temperatura atual acima do limite: {}", currentTemperature);
            } else {
                logger.info("Temperatura dentro do limite: {}", currentTemperature);
            }
        } catch (Exception e) {
            logger.error("Erro ao verificar a temperatura: {}", e.getMessage());
        }
    }
}