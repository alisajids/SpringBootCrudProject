import org.jasypt.encryption.StringEncryptor;

/**
 * This class is used by application.properties
 */
public class PasswordEncryptionBean implements StringEncryptor{

	
	@Override
	public String decrypt(String arg0) {
		return PasswordEncryptionUtil.decrypt(arg0);		
	}

	
	@Override
	public String encrypt(String arg0) {
		return PasswordEncryptionUtil.encrypt(arg0);
	}
}
