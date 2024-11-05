package com.github.devricks.bugzapperj.service.handler.data;

import com.github.devricks.bugzapperj.data.InputData;
import com.github.devricks.bugzapperj.data.OutputData;
import com.github.devricks.bugzapperj.entity.Bug;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FakeDataHandlerServiceTest {

    @Test
    void convert_GivenBugIsObjectAndClazzIsNotOutputData_ThenReturnsBug() {
        FakeDataHandlerService whenFakeObjectConversionService = new FakeDataHandlerService();
        Object givenBug = new Bug.Builder().withName("Bug Name").build();
        Class<?> givenClazz = Bug.class;
        Object thenDataStructure = whenFakeObjectConversionService.convert(givenBug, givenClazz);
        assertNotNull(thenDataStructure);
        assertInstanceOf(Bug.class, thenDataStructure);
        assertEquals("Bug Name", ((Bug) thenDataStructure).getName());
    }

    @Test
    void convert_GivenBugIsObjectAndClazzIsOutputData_ThenReturnsOutputDataWithMappedValues() {
        FakeDataHandlerService whenFakeObjectConversionService = new FakeDataHandlerService();
        Object givenBug = new Bug.Builder().withName("Bug Name").build();
        Class<?> givenClazz = OutputData.class;
        Object thenDataStructure = whenFakeObjectConversionService.convert(givenBug, givenClazz);
        assertNotNull(thenDataStructure);
        assertInstanceOf(OutputData.class, thenDataStructure);
        assertEquals("Bug Name", ((OutputData) thenDataStructure).name);
    }

    @Test
    void convert_GivenBugIsObjectAndClazzIsNotConvertableTo_ThenReturnsOriginalObject() {
        FakeDataHandlerService whenFakeObjectConversionService = new FakeDataHandlerService();
        Object givenBug = new Bug.Builder().withName("Bug Name").build();
        Class<?> givenClazz = Bug.class;
        Object thenDataStructure = whenFakeObjectConversionService.convert(givenBug, givenClazz);
        assertNotNull(thenDataStructure);
        assertInstanceOf(Bug.class, thenDataStructure);
        assertEquals("Bug Name", ((Bug) thenDataStructure).getName());
    }

    @Test
    void convert_GivenBugIsObjectAndExceptionIsPassedAndClazzIsOutputData_ThenReturnsOutputDataWithMappedValues() {
        FakeDataHandlerService whenFakeObjectConversionService = new FakeDataHandlerService();
        Object givenBug = new Bug.Builder().withName("Bug Name").build();
        Exception givenException = new Exception("Exception Message");
        Class<?> givenClazz = OutputData.class;
        Object thenDataStructure = whenFakeObjectConversionService.convert(givenBug, givenException, givenClazz);
        assertNotNull(thenDataStructure);
        assertInstanceOf(OutputData.class, thenDataStructure);
        assertEquals("FAILED", ((OutputData) thenDataStructure).status);
        assertEquals("Bug Name", ((OutputData) thenDataStructure).name);
        assertEquals("Exception Message", ((OutputData) thenDataStructure).message);
    }

    @Test
    void convert_GivenBugIsObjectAndExceptionIsPassedAndClazzIsNotOutputData_ThenReturnsClazzWithDefaultValues() {
        FakeDataHandlerService whenFakeObjectConversionService = new FakeDataHandlerService();
        Object givenBug = new Bug.Builder().withName("Bug Name").build();
        Exception givenException = new Exception("Exception Message");
        Class<?> givenClazz = Bug.class;
        Object thenDataStructure = whenFakeObjectConversionService.convert(givenBug, givenException, givenClazz);
        assertNotNull(thenDataStructure);
        assertInstanceOf(Bug.class, thenDataStructure);
        assertEquals("Bug Name", ((Bug) thenDataStructure).getName());
    }

    @Test
    void convert_GivenBugIsNotObjectAndClazzIsOutputData_ThenReturnsOutputDataWithDefaultValues() {
        FakeDataHandlerService whenFakeObjectConversionService = new FakeDataHandlerService();
        Object givenBug = "Bug";
        Class<?> givenClazz = OutputData.class;
        Object thenDataStructure = whenFakeObjectConversionService.convert(givenBug, givenClazz);
        assertNotNull(thenDataStructure);
        assertInstanceOf(OutputData.class, thenDataStructure);
        assertNull(((OutputData) thenDataStructure).name);
    }

    @Test
    void convert_GivenBugIsNotObjectAndExceptionIsPassedAndClazzIsOutputData_ThenReturnsOutputDataWithDefaultValues() {
        FakeDataHandlerService whenFakeObjectConversionService = new FakeDataHandlerService();
        Object givenBug = "Bug";
        Exception givenException = new Exception("Exception Message");
        Class<?> givenClazz = OutputData.class;
        Object thenDataStructure = whenFakeObjectConversionService.convert(givenBug, givenException, givenClazz);
        assertNotNull(thenDataStructure);
        assertInstanceOf(OutputData.class, thenDataStructure);
        assertNull(((OutputData) thenDataStructure).name);
    }

    @Test
    void convert_GivenBugIsNotConvertableObjectAndClazzIsNotOutputData_ThenReturnsOriginalObject() {
        FakeDataHandlerService whenFakeObjectConversionService = new FakeDataHandlerService();
        Object givenBug = "Bug";
        Class<?> givenClazz = Bug.class;
        Object thenDataStructure = whenFakeObjectConversionService.convert(givenBug, givenClazz);
        assertNotNull(thenDataStructure);
        assertInstanceOf(String.class, thenDataStructure);
    }

    @Test
    void merge_GivenObjectDataStructureIsInputDataAndObjectIsBug_ThenReturnsBugWithMergedValues() {
        FakeDataHandlerService whenFakeObjectConversionService = new FakeDataHandlerService();
        InputData givenData = new InputData();
        givenData.name = "Bug Name";
        givenData.description = "Bug Description";
        givenData.projectId = 1;
        givenData.projectName = "Project Name";
        Bug givenBug = new Bug.Builder().withName("Old Bug Name").withDescription("Old Bug Description").withProjectId(2).withProjectName("Old Project Name").build();
        Bug thenBug = (Bug) whenFakeObjectConversionService.merge(givenData, givenBug, Bug.class);
        assertNotNull(thenBug);
        assertEquals("Bug Name", thenBug.getName());
        assertEquals("Bug Description", thenBug.getDescription());
        assertEquals(1, thenBug.getProjectId());
        assertEquals("Project Name", thenBug.getProjectName());
    }

    @Test
    void merge_GivenObjectDataStructureIsNotInputDataAndObjectIsBug_ThenReturnsNull() {
        FakeDataHandlerService whenFakeObjectConversionService = new FakeDataHandlerService();
        Object givenData = "Data";
        Bug givenBug = new Bug.Builder().withName("Old Bug Name").withDescription("Old Bug Description").withProjectId(2).withProjectName("Old Project Name").build();
        Object thenBug = whenFakeObjectConversionService.merge(givenData, givenBug, Bug.class);
        assertNull(thenBug);
    }

    @Test
    void merge_GivenObjectDataStructureIsInputDataAndObjectIsNotBug_ThenReturnsNull() {
        FakeDataHandlerService whenFakeObjectConversionService = new FakeDataHandlerService();
        InputData givenData = new InputData();
        givenData.name = "Bug Name";
        givenData.description = "Bug Description";
        givenData.projectId = 1;
        givenData.projectName = "Project Name";
        Object givenBug = "Bug";
        Object thenBug = whenFakeObjectConversionService.merge(givenData, givenBug, Bug.class);
        assertNull(thenBug);
    }

    @Test
    void merge_GivenObjectDataStructureIsInputDataAndObjectIsBug_ThenReturnsBugWithOldValues() {
        FakeDataHandlerService whenFakeObjectConversionService = new FakeDataHandlerService();
        InputData givenData = new InputData();
        givenData.name = null;
        givenData.description = null;
        givenData.projectId = null;
        givenData.projectName = null;
        Bug givenBug = new Bug.Builder().withName("Old Bug Name").withDescription("Old Bug Description").withProjectId(2).withProjectName("Old Project Name").build();
        Bug thenBug = (Bug) whenFakeObjectConversionService.merge(givenData, givenBug, Bug.class);
        assertNotNull(thenBug);
        assertEquals("Old Bug Name", thenBug.getName());
        assertEquals("Old Bug Description", thenBug.getDescription());
        assertEquals(2, thenBug.getProjectId());
        assertEquals("Old Project Name", thenBug.getProjectName());
    }

    @Test
    void merge_GivenObjectDataStructureIsInputDataAndObjectIsBug_ThenReturnsBugWithNewValues() {
        FakeDataHandlerService whenFakeObjectConversionService = new FakeDataHandlerService();
        InputData givenData = new InputData();
        givenData.name = "New Bug Name";
        givenData.description = "New Bug Description";
        givenData.projectId = 1;
        givenData.projectName = "New Project Name";
        Bug givenBug = new Bug.Builder().withName("Old Bug Name").withDescription("Old Bug Description").withProjectId(2).withProjectName("Old Project Name").build();
        Bug thenBug = (Bug) whenFakeObjectConversionService.merge(givenData, givenBug, Bug.class);
        assertNotNull(thenBug);
        assertEquals("New Bug Name", thenBug.getName());
        assertEquals("New Bug Description", thenBug.getDescription());
        assertEquals(1, thenBug.getProjectId());
        assertEquals("New Project Name", thenBug.getProjectName());
    }


}