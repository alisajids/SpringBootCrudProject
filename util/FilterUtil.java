import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReportUtil {

	/**
	 * Get List of names
	 * @param list
	 * @return
	 */
	public static List<String> getValuesAsList(List<Map<String, String>> list) {
		if (list == null || list.isEmpty())
			return null;
		List<String> retList = new ArrayList<>();
		list.forEach(v -> {
			v.forEach((k1,v1) -> {
				if(k1.equals(FilterConstants.NAME)) {
					retList.add(v1);
				}
			});
		});
		return retList;
	}
  /**
	 * Get List of Ids
	 * @param list
	 * @return
	 */
  	public static List<String> getValuesAsIdList(List<Map<String, String>> list) {
		if (list == null || list.isEmpty())
			return null;
		List<String> retList = new ArrayList<>();
		list.forEach(v -> {
			v.forEach((k1,v1) -> {
				if(k1.equals(FilterConstants.ID)) {
					retList.add(v1);
				}
			});
		});
		return retList;
	}
}
