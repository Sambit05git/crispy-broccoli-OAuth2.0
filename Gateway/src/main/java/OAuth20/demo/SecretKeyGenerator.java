package OAuth20.demo;
import com.amazonaws.services.secretsmanager.AWSSecretsManager;
import com.amazonaws.services.secretsmanager.AWSSecretsManagerClientBuilder;
import com.amazonaws.services.secretsmanager.model.CreateSecretRequest;
import com.amazonaws.services.secretsmanager.model.CreateSecretResult;
import javax.crypto.KeyGenerator;

import org.bouncycastle.jce.exception.ExtIOException;

import java.util.*;
public class SecretKeyGenerator {
    public static void main ( String[] args) {
        try{
        KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
        keyGen.init(256); 
        String secretKey = Base64.getEncoder().encodeToString(keyGen.generateKey().getEncoded());
        AWSSecretsManager client = AWSSecretsManagerClientBuilder.standard().build();
        CreateSecretRequest createSecretRequest = new CreateSecretRequest().withName("JWTSigningKey").withSecretString("{\"signingKey\":\"" + secretKey + "\"}");
        CreateSecretResult createSecretResult = client.createSecret(createSecretRequest);
        System.out.println("Secret stored with ARN:" + createSecretResult.getARN()); // amazon resource name 
        }catch(Exception e ){
            throw new RuntimeException ("Error genrating or storing secret key" ,e ) ;
        }
    }
}
