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

@Service
public class SchedulerService {

    private static final Logger logger = LoggerFactory.getLogger(SchedulerService.class);

    @Autowired
    private ServicePrevision servicePrevision;

    @Autowired
    private EmailService emailService;

    // Definindo o limite de temperatura como uma constante
    @Value("${temperature.threshold:34.0}")
    private double temperatureThreshold;

    @Scheduled(fixedRate = 1800000) // A cada 30 minutos
    public void checkTemperature() {
        // Verifica se hoje é um dia útil e se a hora está dentro do intervalo permitido
        if (isWeekday() && isWithinTimeRange()) {
            try {
                List<TemperatureInfo> currentTemperatures = servicePrevision.getTemperatures(); // Chama o método correto
                logger.info("Temperaturas atuais: {}", currentTemperatures);

                // Verifica se alguma temperatura está acima do limite
                boolean isAboveThreshold = currentTemperatures.stream()
                        .anyMatch(tempInfo -> tempInfo.getTemperature() > temperatureThreshold);

                if (isAboveThreshold) {
                    // Determina a saudação com base na hora atual
                    String greeting = getGreeting();
                    StringBuilder messageBuilder = new StringBuilder(greeting + "\nPrezados supervisores,\n Segue abaixo alerta sobre as temperaturas atuais registradas nas localidades monitoradas.\n");

                    messageBuilder.append("\nAtenção: Uma ou mais temperaturas estão acima do limite de ")
                            .append(temperatureThreshold).append("°C.\n\n");

                    messageBuilder.append("Temperaturas atuais:\n\n");

                    for (TemperatureInfo temperatureInfo : currentTemperatures) {
                        messageBuilder.append(temperatureInfo.getLocationName())
                                .append(": ").append(temperatureInfo.getTemperature()).append("°C\n");
                    }

                    // Envia um único e-mail com todas as temperaturas
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
        DayOfWeek dayOfWeek = LocalDate.now().getDayOfWeek();
        return dayOfWeek != DayOfWeek.SATURDAY && dayOfWeek != DayOfWeek.SUNDAY; // Verifica se não é sábado ou domingo
    }

    private boolean isWithinTimeRange() {
        LocalTime now = LocalTime.now();
        return now.isAfter(LocalTime.of(11, 0)) && now.isBefore(LocalTime.of(17, 0)); // Verifica se está entre 11:00 e 17:00
    }

    private String getGreeting() {
        LocalTime now = LocalTime.now();
        if (now.isBefore(LocalTime.NOON)) {
            return "Bom dia.\n";
        } else {
            return "Boa tarde.\n";
        }
    }
}