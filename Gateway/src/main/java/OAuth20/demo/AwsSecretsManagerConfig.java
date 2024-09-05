package OAuth20.demo;

import com.amazonaws.services.secretsmanager.AWSSecretsManager;
import com.amazonaws.services.secretsmanager.AWSSecretsManagerClientBuilder;
import com.amazonaws.services.secretsmanager.model.GetSecretValueRequest;
import com.amazonaws.services.secretsmanager.model.GetSecretValueResult;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AwsSecretsManagerConfig {

    @Bean
    public ClientCredentials loadClientCredentials() {
        try {
            AWSSecretsManager client = AWSSecretsManagerClientBuilder.standard().build();
            GetSecretValueRequest getSecretValueRequest = new GetSecretValueRequest()
                    .withSecretId("OAuthManager2.0");
            GetSecretValueResult getSecretValueResult = client.getSecretValue(getSecretValueRequest);
            String secret = getSecretValueResult.getSecretString();

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(secret);
            
            String clientId = jsonNode.get("clientId").asText();
            String clientSecret = jsonNode.get("clientSecret").asText();
            
            return new ClientCredentials(clientId, clientSecret);
        } catch (Exception e) {
            throw new RuntimeException("Could not load AWS secrets", e);
        }
    }
    @Bean
    public String loadSigningKey() {
        try {
            AWSSecretsManager client = AWSSecretsManagerClientBuilder.standard().build();
            GetSecretValueRequest getSecretValueRequest = new GetSecretValueRequest()
                    .withSecretId("JWTSigningKey");
            GetSecretValueResult getSecretValueResult = client.getSecretValue(getSecretValueRequest);
            String secret = getSecretValueResult.getSecretString();

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(secret);

            return jsonNode.get("signingKey").asText();
        } catch (Exception e) {
            throw new RuntimeException("Could not load JWT signing key from AWS Secrets Manager", e);
        }
    } 
}
