import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.io.FileUtils;

public class StringUtil {

	private static final String DELIMITER_PERCENT = "%";
	private static final DateFormat DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy");
	
	public static String parseURI(String uri) {
		if (uri.contains("//"))
			uri = uri.replaceAll("//+", "/");
		return uri;
	}

	public static boolean isInteger(String str) {
		try {
			Integer.parseInt(str);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public static String getFileName(ScanSummary scanSummary) {

		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(scanSummary.getSiteId()).append("_");
		stringBuilder.append(scanSummary.getSiteName()).append("_");
		stringBuilder.append(scanSummary.getScanId());

		return stringBuilder.toString();
	}

	public static String getConcatenatedString(String input) {
		return DELIMITER_PERCENT.concat(input).concat(DELIMITER_PERCENT);
	}
	
	public static String getFilenamewithoutExtension(String Filenamewithoutextension) {
 		Filenamewithoutextension = Filenamewithoutextension.replaceAll("\\(.*?\\) ?", "");
 		return Filenamewithoutextension;
 	}
 	
 	public static String getFilewithnewcount(String mostRecentFileName)
 	{
 		final Pattern PATTERN = Pattern.compile("(.*?)(?:\\((\\d+)\\))?(\\.[^.]*)?");
 		Matcher m = PATTERN.matcher(mostRecentFileName);
 		if (m.matches()) {
 			String prefix = m.group(1);
 			String last = m.group(2);
 			String suffix = m.group(3);
 			if (suffix == null)
 				suffix = "";
 			int countLocal = last != null ? Integer.parseInt(last) : 0;
 			countLocal++;
 			mostRecentFileName = prefix + "(" + countLocal + ")" + suffix;			
 	}
 		return mostRecentFileName;
 	}
 	
 	public static String getFileToString(File inFile,String charFormat) throws IOException{
 		if(!charFormat.isEmpty())
 			return FileUtils.readFileToString(inFile, Charset.forName(charFormat));
 		else
 			return FileUtils.readFileToString(inFile);
 	}
 	
 	public static String removeWhiteSpace(String input){
 		return input.replaceAll("\\s+","");
 	}
 	
 	public static String toCamelCase(String input) {
 	    if (input==null)
 	        return null;

 	     StringBuilder output = new StringBuilder(input.length());

 	        if (!input.isEmpty()) {
 	            output.append(input.substring(0, 1).toLowerCase());
 	            output.append(input.substring(1));
 	        }

 	    return output.toString();
 	}
 
	public static List<String> split(String input){
 	return Stream.of(input.split(",")).map (elem -> new String(elem))
 										.collect(Collectors.toList());
	}
	
	public static Date convertStringToDate(String timestamp){
		
		if(timestamp == null)  return null;
		try{
			return DATE_FORMAT.parse(timestamp);
		}catch(Exception e){
		}
		return null;
	}
	
	public static boolean isValidIPv4(String ipAddress) {
		if (ipAddress.isEmpty()) return false;
		Pattern pattern = Pattern.compile(FusionConstants.IPv4_REGEX_PATTERN);
		Matcher matcher = pattern.matcher(ipAddress);
		return matcher.matches();
	}
}
