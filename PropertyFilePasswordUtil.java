public class PropertyFilePasswordUtil {
		
	private static String prefix = "ENC(";
    private static String suffix = ")";
    /**
     * see if the password is encrypted or not
     * @param property
     * @return
     */
    public static boolean isEncrypted(String property) {
        if (property == null)
            return false;
        
        final String trimmedValue = property.trim();
        return (trimmedValue.startsWith(prefix) &&
                trimmedValue.endsWith(suffix));
    }
    /**
     * to unwrap the encrypted password
     * @param property
     * @return
     */
    
    public static String unwrapEncryptedValue(String property) {
        return property.substring(
                prefix.length(),
                (property.length() - suffix.length()));

    }
}
