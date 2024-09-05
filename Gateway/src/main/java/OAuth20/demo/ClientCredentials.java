package OAuth20.demo;

public class ClientCredentials {
    private final String clientId;
    private final String clientSecret;

    public ClientCredentials(String clientId, String clientSecret) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }

    public String getClientId() {
        return clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }
}