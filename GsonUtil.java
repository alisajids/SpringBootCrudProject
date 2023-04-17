
import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

/*
 * Utility class to read/write JSON using Google's Gson.
 */
public class GsonUtil {
	
   private static Gson gson;

    static {
        GsonBuilder gsonBuilder = new GsonBuilder().serializeNulls();
        //gsonBuilder.setFieldNamingStrategy(FieldNamingPolicy.UPPER_CAMEL_CASE);
        gson = gsonBuilder.setPrettyPrinting().create();
    }

    private GsonUtil() {

    }

    // ::read JSON::

    public static <T> T fromJson(final String json, final Class<T> clazz) {

        return gson.fromJson(json, clazz);
    }

    public static <T> List<T> fromJson(String json, Type typeOfT) {

        return gson.fromJson(json, typeOfT);
    }

    public static <T> List<T> fromJson(JsonElement json, Type typeOfT) {

        return gson.fromJson(json, typeOfT);
    }

    // ::write JSON::

    public static <T> String toString(T t) {

        return gson.toJson(t);
    }

}
