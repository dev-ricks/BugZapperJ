package testing.data;

import com.github.devricks.bugzapperj.data.InputData;
import com.github.devricks.bugzapperj.service.BugHandlerService;

public class OutputDataForBugEntities {

    public static void createExistingBug(BugHandlerService service) {
        InputData givenInputData = InputDataForBugEntities.createValidInputData();
        service.createBug(givenInputData);
    }
}
