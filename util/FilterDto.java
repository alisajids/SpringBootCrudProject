import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

public class FilterDto {
	
	@Getter @Setter
	private Map<String, List<Map<String, String>>> filter;

}

/*
@RequestMapping(value = "",
			method = RequestMethod.POST)
	public ResponseEntity<?> findTop5ComplianceByFailedControls(@RequestBody FilterDto request) throws Exception {

			service.findeByfilter(
					FilterUtil.getValuesAsList(request.getFilter().get(FilterConstants.CLIENT)),
					FilterUtil.getValuesAsList(request.getFilter().get(FilterConstants.COURSES)));

		return new ResponseEntity<>(aList, HttpStatus.OK);
	}
*/
