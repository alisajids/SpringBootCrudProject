import lombok.Getter;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;

public class PasswordDESEncryptionUtil {
	
	@Getter
	//do not modify this keySeedStr. 
	private static final String keySeedStr = "Z8LSq0wWwB5v+6YJzurcP463H3F12iZh74fDj4S74oUH4EONkiKb2FmiWUbtFh97GG/";
	static PooledPBEStringEncryptor encryptor = getEncryptor(keySeedStr);
    
    public static String encrypt(String input) {
        return encryptor.encrypt(input);
    }

    public static String decrypt(String encryptedMessage) {
        return encryptor.decrypt(encryptedMessage);
    }
    /**
     * to get DES encryptor
     * @param seedStr
     * @return
     */
    public static PooledPBEStringEncryptor getEncryptor(String seedStr) {
    	PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
     	SimpleStringPBEConfig config = new SimpleStringPBEConfig();
     	config.setPassword(keySeedStr);
     	config.setAlgorithm("PBEWithMD5AndDES");
     	config.setKeyObtentionIterations("1000");
     	config.setPoolSize("4");
     	config.setProviderName("SunJCE");
     	config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
     	config.setStringOutputType("base64");
     	encryptor.setConfig(config);     	
    	return encryptor;
    }
    

}
