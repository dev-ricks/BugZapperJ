package testing.data;

import com.github.devricks.bugzapperj.data.InputData;

public class InputDataForBugEntities {

    private InputDataForBugEntities() {
    }

    public static InputData createValidInputData() {
        InputData givenInputData = new InputData();
        givenInputData.name = "Bug Name";
        givenInputData.description = "Bug Description";
        givenInputData.projectId = 5;
        givenInputData.projectName = "Project Name";
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
        givenInputData.projectId = null;
        givenInputData.projectName = null;
        return givenInputData;
    }

    public static InputData createInputDataWithNullDescription() {
        InputData givenInputData = createValidInputData();
        givenInputData.description = null;
        return givenInputData;
    }

    public static InputData createInputDataWithNullProject() {
        InputData givenInputData = createValidInputData();
        givenInputData.projectId = null;
        givenInputData.projectName = null;
        return givenInputData;
    }

    public static InputData createInputDataWithNameAndDescriptionNull() {
        InputData givenInputData = createValidInputData();
        givenInputData.name = null;
        givenInputData.description = null;
        return givenInputData;
    }

    public static InputData updateValidInputData(Integer existingId) {
        InputData givenInputData = new InputData();
        givenInputData.id = existingId;
        givenInputData.name = "Updated Bug Name";
        givenInputData.description = "Updated Bug Description";
        givenInputData.projectId = 6;
        givenInputData.projectName = "New Project Name";
        return givenInputData;
    }

    public static InputData updateInputDataWithAllNullValues(Integer existingId) {
        InputData givenInputData = new InputData();
        givenInputData.id = existingId;
        givenInputData.name = null;
        givenInputData.description = null;
        givenInputData.projectId = null;
        givenInputData.projectName = null;
        return givenInputData;
    }

    public static InputData updateInputDataWithAllEmptyValues(Integer existingId) {
        InputData givenInputData = new InputData();
        givenInputData.id = existingId;
        givenInputData.name = "";
        givenInputData.description = "";
        givenInputData.projectId = null;
        givenInputData.projectName = "";
        return givenInputData;
    }

    public static InputData updateInputDataWithEmptyName(Integer existingId) {
        InputData givenInputData = updateValidInputData(existingId);
        givenInputData.name = "";
        return givenInputData;
    }

    public static InputData updateInputDataWithEmptyDescription(Integer existingId) {
        InputData givenInputData = updateValidInputData(existingId);
        givenInputData.description = "";
        return givenInputData;
    }

    public static InputData updateInputDataWithBothEmptyNameAndDescription(Integer existingId) {
        InputData givenInputData = updateValidInputData(existingId);
        givenInputData.name = "";
        givenInputData.description = "";
        return givenInputData;
    }

    public static InputData findValidInputData(Integer existingId) {
        InputData givenInputData = new InputData();
        givenInputData.id = existingId;
        return givenInputData;
    }

    public static InputData findInvalidInputData(Integer existingId) {
        InputData givenInputData = new InputData();
        givenInputData.id = existingId;
        return givenInputData;
    }

    public static InputData createInputDataWithNullProjectId() {
        InputData givenInputData = createValidInputData();
        givenInputData.projectId = null;
        return givenInputData;
    }
}
