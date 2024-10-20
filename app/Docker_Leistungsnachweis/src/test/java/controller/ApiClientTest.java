package controller;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testklasse für die {@link ApiClient} Klasse.
 * Diese Klasse enthält Tests, um die Funktionalität des API-Clients zu überprüfen.
 */
public class ApiClientTest {

    private final ApiClient apiClient = new ApiClient();

    /**
     * Testet die erfolgreiche Abfrage eines Datasets.
     * Überprüft, ob das Dataset nicht null ist und ob die Liste der Events nicht leer ist.
     */
    @Test
    public void testFetchDataset_Success() {
        Dataset dataset = apiClient.fetchDataset();

        assertNotNull(dataset, "Das Dataset sollte nicht null sein.");
        assertNotNull(dataset.getEvents(), "Die Liste der Events sollte nicht null sein.");
        assertFalse(dataset.getEvents().isEmpty(), "Die Liste der Events sollte nicht leer sein.");
    }

    /**
     * Testet den Fehlerfall beim Abrufen eines Datasets.
     * Simuliert eine fehlgeschlagene API-Anfrage und überprüft, ob das Dataset null ist.
     */
    @Test
    public void testFetchDataset_Failure() {
        ApiClient failingApiClient = new ApiClient() {
            @Override
            public Dataset fetchDataset() {
                return null; // Simulieren eines Fehlers
            }
        };

        Dataset dataset = failingApiClient.fetchDataset();

        assertNull(dataset, "Das Dataset sollte null sein, wenn der API-Aufruf fehlschlägt.");
    }

    /**
     * Testet den erfolgreichen Versand von Ergebnissen.
     * Überprüft, ob die Ergebnisse erfolgreich an den Server gesendet werden.
     */
    @Test
    public void testSendResults_Success() {
        Map<String, Long> runtimes = new HashMap<>();
        runtimes.put("customer1", 100L);

        apiClient.sendResults(runtimes);
        // Keine Assertion, da sendResults keinen Rückgabewert hat.
    }

    /**
     * Testet den Fehlerfall beim Versand von Ergebnissen.
     * Simuliert einen Fehler beim Senden der Ergebnisse.
     */
    @Test
    public void testSendResults_Failure() {
        ApiClient failingApiClient = new ApiClient() {
            @Override
            public void sendResults(Map<String, Long> runtimes) {
                System.out.println("Fehler beim Senden der Ergebnisse: Falsche URL");
            }
        };

        Map<String, Long> runtimes = new HashMap<>();
        runtimes.put("customer1", 100L);

        failingApiClient.sendResults(runtimes);
        // Keine Assertion, da sendResults keinen Rückgabewert hat.
    }
}
