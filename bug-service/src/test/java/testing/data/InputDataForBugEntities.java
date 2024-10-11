package testing.data;

import com.github.devricks.bugzapperj.data.InputData;
import com.github.devricks.bugzapperj.entity.Project;

public class InputDataForBugEntities {

    private InputDataForBugEntities() {
    }

    public static InputData createValidInputData() {
        InputData givenInputData = new InputData();
        givenInputData.name = "Bug Name";
        givenInputData.description = "Bug Description";
        givenInputData.project = new Project.Builder().withName("Project Name").build();
        return givenInputData;
    }

    public static InputData createInputDataWithNullName() {
        InputData givenInputData = createValidInputData();
        givenInputData.name = null;
        return givenInputData;
    }

    public static InputData createInputDataWithEmptyName() {
        InputData givenInputData = createValidInputData();
        givenInputData.name = "";
        return givenInputData;
    }

    public static InputData createInputDataWithAllNullValues() {
        InputData givenInputData = new InputData();
        givenInputData.name = null;
        givenInputData.description = null;
        givenInputData.project = null;
        return givenInputData;
    }

    public static InputData createInputDataWithNullDescription() {
        InputData givenInputData = createValidInputData();
        givenInputData.description = null;
        return givenInputData;
    }

    public static InputData createInputDataWithNullProject() {
        InputData givenInputData = createValidInputData();
        givenInputData.project = null;
        return givenInputData;
    }

    public static InputData createInputDataWithNameAndDescriptionNull() {
        InputData givenInputData = createValidInputData();
        givenInputData.name = null;
        givenInputData.description = null;
        return givenInputData;
    }
}
