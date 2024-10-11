package com.github.devricks.bugzapperj.service.converter;

import com.github.devricks.bugzapperj.data.OutputData;
import com.github.devricks.bugzapperj.entity.Bug;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FakeObjectConversionServiceTest {

    @Test
    void convert_GivenBugIsObjectAndClazzIsOutputData_ThenReturnsOutputDataWithMappedValues() {
        FakeObjectConversionService whenFakeObjectConversionService = new FakeObjectConversionService();
        Object givenBug = new Bug.Builder().withName("Bug Name").build();
        Class<?> givenClazz = OutputData.class;
        Object thenDataStructure = whenFakeObjectConversionService.convert(givenBug, givenClazz);
        assertNotNull(thenDataStructure);
        assertInstanceOf(OutputData.class, thenDataStructure);
        assertEquals("Bug Name", ((OutputData) thenDataStructure).name);
    }

    @Test
    void convert_GivenBugIsObjectAndClazzIsNotOutputData_ThenReturnsOutputDataWithDefaultValues() {
        FakeObjectConversionService whenFakeObjectConversionService = new FakeObjectConversionService();
        Object givenBug = new Bug.Builder().withName("Bug Name").build();
        Class<?> givenClazz = Bug.class;
        Object thenDataStructure = whenFakeObjectConversionService.convert(givenBug, givenClazz);
        assertNotNull(thenDataStructure);
        assertInstanceOf(OutputData.class, thenDataStructure);
        assertNull(((OutputData) thenDataStructure).name);
    }

    @Test
    void convert_GivenBugIsObjectAndExceptionIsPassedAndClazzIsOutputData_ThenReturnsOutputDataWithMappedValues() {
        FakeObjectConversionService whenFakeObjectConversionService = new FakeObjectConversionService();
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
}