import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

@Slf4j
public class PasswordEncryptionUtil  {
	
	private static boolean supportDES = true; // to support DES for now.

	public static String decrypt(String cryptedText) {
		try {
			if (supportDES && StringUtils.contains(cryptedText, ":"))
				return PasswordAESEncryptionUtil.decrypt(cryptedText);
			else
				return PasswordDESEncryptionUtil.decrypt(cryptedText);
		} catch (Exception e) {
			log.error("Error to decrypt text: ");
			log.error(e.getMessage());
			return null;
		}
	}

	public static String encrypt(String plainText) {
		try {
			return PasswordAESEncryptionUtil.encrypt(plainText);
		} catch (Exception e) {
			log.error("Error to encrypt text");
			log.error(e.getMessage());
			return null;
		}
	}

	public static String getKeySeedStr() {
		return PasswordAESEncryptionUtil.getKeySeedStr();
	}

	public static void setKeySeedStr(String keySeedStr) {
		PasswordAESEncryptionUtil.setKeySeedStr(keySeedStr);
	}
}
