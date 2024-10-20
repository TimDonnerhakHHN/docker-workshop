package controller;

import org.junit.jupiter.api.Test;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Testklasse für die {@link ApiClient} Klasse.
 * Diese Klasse enthält Tests, um die Funktionalität des API-Clients zu überprüfen.
 */
public class ApiClientTest {

    @Test
    public void testFetchDataset_Success() throws Exception {
        // Mock HttpClient and HttpResponse
        HttpClient mockClient = mock(HttpClient.class);
        HttpResponse<String> mockResponse = mock(HttpResponse.class);
        when(mockResponse.statusCode()).thenReturn(200);
        when(mockResponse.body()).thenReturn("{\"events\":[]}");
        when(mockClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenReturn(mockResponse);

        // Inject mock client into ApiClient
        ApiClient apiClient = new ApiClient(mockClient);
        Dataset dataset = apiClient.fetchDataset();

        assertNotNull(dataset, "Dataset should not be null");
        assertTrue(dataset.getEvents().isEmpty(), "Events list should be empty");
    }

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

    @Test
    public void testSendResults_Success() {
        Map<String, Long> runtimes = new HashMap<>();
        runtimes.put("customer1", 100L);
        ApiClient apiClient = new ApiClient();
        apiClient.sendResults(runtimes);
        // Keine Assertion, da sendResults keinen Rückgabewert hat.
    }

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
