
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class JsonUtil {
	
	private static ObjectMapper mapper = new ObjectMapper();

	public static String convertToJsonString( Object obj) throws JsonProcessingException {
		return mapper.writeValueAsString(obj);
	}
	
	public static <T> T convertStringToObject(String str, Class<T> t) throws Exception  {
		 return mapper.readValue(str, t);
	}

	public static final String RESULT = "result";
	
	private JsonUtil() {
		
	}
	
	/**
 	 * This is a utility method that will parse a JSON Array String to a list of clazz objects.
	 * Example of jsonString format with "result" as the jsonElementId:
	 * jsonString = { "result" : [{...}, {...}, ..] }
	 * 	 
	 * @param jsonElementId
	 * @param jsonString
	 * @param clazz
	 * @return
	 */
	public static <T> List<T> toList(final String jsonElementId, final String jsonString, final Class<T> clazz) {
		List<T> entries = new ArrayList<>();
		if (jsonString != null) {
			JsonParser parser = new JsonParser();
			JsonElement jsonElement = parser.parse(new StringReader(jsonString));
			if ((jsonElement).isJsonObject() && jsonElement.getAsJsonObject().get(jsonElementId).isJsonArray()) {
				JsonArray jArr = jsonElement.getAsJsonObject().get(jsonElementId).getAsJsonArray();
				for (JsonElement je : jArr) {
					GsonBuilder b = new GsonBuilder();
					Gson gson = b.excludeFieldsWithoutExposeAnnotation().create();
					try {
						T t = gson.fromJson(je, clazz);
						entries.add(t);
					} catch (JsonSyntaxException e) {
						log.warn("Encountered a malformed JSON element | Skipping record: {} | Error: {}", je.toString(), e.getMessage());
					}
				}
			}
		}
		return entries;
	}
	
	/**
	 * This is a utility method that will parse an optional(Which may or may not
	 * contain an object) complex JSON Array String to a list of clazz objects.
	 * Example of jsonString format with "result" as the jsonElementId: jsonString =
	 * { "result" : [{...}, {...}, ..] } or { "result" : [{...}, .., ..] }
	 * 
	 * @param jsonElementId
	 * @param jsonString
	 * @param clazz
	 * @return
	 */
	public static <S, T> List<T> toListWithOptionalNestedObjects(final String jsonElementId, final String jsonString,
			final Class<T> clazz, final Class<S> nestedClazz) {
		List<T> entries = new ArrayList<>();
		if (jsonString != null) {
			JsonParser parser = new JsonParser();
			JsonElement jsonElement = parser.parse(new StringReader(jsonString));
			if ((jsonElement).isJsonObject() && jsonElement.getAsJsonObject().get(jsonElementId).isJsonArray()) {
				JsonArray jArr = jsonElement.getAsJsonObject().get(jsonElementId).getAsJsonArray();
				for (JsonElement je : jArr) {
					GsonBuilder b = new GsonBuilder();
					Gson gson = b.registerTypeAdapter(nestedClazz, new GsonObjectDeserializer<S>())
							.excludeFieldsWithoutExposeAnnotation().create();
					try {
						T t = gson.fromJson(je, clazz);
						entries.add(t);
					} catch (JsonSyntaxException e) {
						log.warn("Encountered a malformed JSON element | Skipping record: {} | Error: {}",
								je.toString(), e.getMessage());
					}
				}
			}
		}
		return entries;
	}
	
}
