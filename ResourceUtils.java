import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@SuppressWarnings("deprecation")
public class ResourceUtils {
	

	public static String loadFileFromClasspath(String pathAndName) throws IOException {
		InputStream input = new ClassPathResource(pathAndName).getInputStream();
        return IOUtils.toString(input);
	}

	public static String loadFileFromAboslutePath(String name) throws IOException {
		InputStream input = new FileInputStream (new File(name));
		 return IOUtils.toString(input);
	}
	
}
