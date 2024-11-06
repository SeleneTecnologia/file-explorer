package cl.coordinador.consumers;

import cl.coordinador.services.FileExplorerService;
import io.smallrye.reactive.messaging.kafka.KafkaRecord;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.jboss.logging.Logger;

import java.util.concurrent.*;

@ApplicationScoped
public class KafkaFileConsumer {


    private static final Logger LOG = Logger.getLogger(KafkaFileConsumer.class);

    @Inject
    FileExplorerService fileExplorerService;

    private final BlockingQueue<KafkaRecord<String, String>> queue = new LinkedBlockingQueue<>();
    private final ExecutorService executor = Executors.newSingleThreadExecutor(); // Procesa secuencialmente

    private static final int MAX_RETRIES = 3; // Número máximo de reintentos
    private static final long RETRY_DELAY_MS = 1000; // Retraso entre reintentos

    public KafkaFileConsumer() {
        // Procesa los mensajes en un hilo separado, uno a la vez
        executor.submit(() -> {
            while (true) {
                try {
                    KafkaRecord<String, String> record = queue.take(); // Espera hasta que haya un mensaje en la cola
                    processRecordWithRetries(record); // Procesa el mensaje con reintentos
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    LOG.error("Consumer thread interrupted", e);
                    break;
                }
            }
        });
    }

    @Incoming("kafka-channel")
    public CompletionStage<Void> consume(KafkaRecord<String, String> record) {
        queue.offer(record); // Coloca el mensaje en la cola para procesarlo secuencialmente
        return CompletableFuture.completedFuture(null); // Indica que la recepción ha sido exitosa
    }

    private void processRecordWithRetries(KafkaRecord<String, String> record) {
        int attempt = 0;
        boolean success = false;

        while (attempt < MAX_RETRIES && !success) {
            try {
                attempt++;
                processRecord(record); // Procesa el mensaje
                success = true; // Marca como exitoso si no hubo excepciones
            } catch (Exception e) {
                LOG.error("Error procesando mensaje ", e);
                if (attempt >= MAX_RETRIES) {
                    LOG.error("Error después de los siguientes reintentos: " + MAX_RETRIES);
                    // Aquí puedes agregar lógica adicional, como mover el mensaje a una cola de "dead letter" o registro en base de datos
                } else {
                    try {
                        Thread.sleep(RETRY_DELAY_MS); // Espera antes de reintentar
                    } catch (InterruptedException interruptedException) {
                        Thread.currentThread().interrupt();
                        break;
                    }
                }
            }
        }
    }

    private void processRecord(KafkaRecord<String, String> record) {
        String key = record.getKey().replaceAll("^\"|\"$", ""); // Elimina comillas dobles
        String payload = record.getPayload().replaceAll("^\"|\"$", "");

        LOG.info("[[[[[[ KAFKA CONSUMER ]]]]]] Mensaje recibido desde Kafka:");
        LOG.info("Clave: " + key);
        LOG.info("Valor: " + payload);

        if ("ATTACHMENT".equals(key)) {
            fileExplorerService.processFiles(payload);
        } else {
            LOG.warn("Caso no soportado para key: " + key);
        }
    }
}
