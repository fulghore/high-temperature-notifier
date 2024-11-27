package sumob.gecot.temperature_notifier.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import sumob.gecot.temperature_notifier.dto.TemperatureInfo;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.time.*;

@Service
public class SchedulerService {

    private static final Logger logger = LoggerFactory.getLogger(SchedulerService.class);

    private static final ZoneId SAO_PAULO_ZONE = ZoneId.of("America/Sao_Paulo");

    @Autowired
    private ServicePrevision servicePrevision;

    @Autowired
    private EmailService emailService;

    @Value("${temperature.threshold:34.0}")
    private double temperatureThreshold;

    @Scheduled(fixedRate = 1800000) // A cada 30 minutos
    public void checkTemperature() {
        if (isWeekday() && isWithinTimeRange()) {
            try {
                List<TemperatureInfo> currentTemperatures = servicePrevision.getTemperatures();
                logger.info("Temperaturas atuais: {}", currentTemperatures);

                boolean isAboveThreshold = currentTemperatures.stream()
                        .anyMatch(tempInfo -> tempInfo.getTemperature() > temperatureThreshold);

                if (isAboveThreshold) {
                    String greeting = getGreeting();
                    StringBuilder messageBuilder = new StringBuilder(greeting)
                            .append("Prezados supervisores,\nSegue abaixo alerta sobre as temperaturas atuais registradas nas localidades monitoradas.\n")
                            .append("\nAtenção: Uma ou mais temperaturas estão acima do limite de ")
                            .append(temperatureThreshold).append("°C.\n\n");

                    messageBuilder.append("Temperaturas atuais:\n\n");

                    for (TemperatureInfo temperatureInfo : currentTemperatures) {
                        messageBuilder.append(temperatureInfo.getLocationName())
                                .append(": ").append(temperatureInfo.getTemperature()).append("°C\n");
                    }

                    emailService.sendEmail(messageBuilder.toString());
                    logger.info("E-mail enviado com as temperaturas atuais.");
                } else {
                    logger.info("Nenhuma temperatura acima do limite de {}°C. E-mail não enviado.", temperatureThreshold);
                }

            } catch (Exception e) {
                logger.error("Erro ao verificar a temperatura: {}", e.getMessage());
            }
        } else {
            logger.info("Método checkTemperature não executado: fora do horário permitido ou não é dia útil.");
        }
    }

    private boolean isWeekday() {
        DayOfWeek dayOfWeek = LocalDate.now(SAO_PAULO_ZONE).getDayOfWeek();
        return dayOfWeek != DayOfWeek.SATURDAY && dayOfWeek != DayOfWeek.SUNDAY;
    }

    private boolean isWithinTimeRange() {
        LocalTime now = LocalTime.now(SAO_PAULO_ZONE);
        return now.isAfter(LocalTime.of(11, 0)) && now.isBefore(LocalTime.of(17, 0));
    }

    private String getGreeting() {
        LocalTime now = LocalTime.now(SAO_PAULO_ZONE);
        return now.isBefore(LocalTime.NOON) ? "Bom dia.\n" : "Boa tarde.\n";
    }
}