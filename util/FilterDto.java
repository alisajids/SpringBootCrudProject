import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

public class FilterDto {
	
	@Getter @Setter
	private Map<String, List<Map<String, String>>> filter;

}
