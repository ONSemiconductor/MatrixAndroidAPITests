package com.onsemiconductor.cameratests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.runners.Suite;
import org.junit.runners.model.InitializationError;

public class TestRunnersProvider {
	public static List<RunnerGroup> getTestRunners(Class<?>... classes) throws InitializationError {
		List<RunnerGroup> runners = new ArrayList<RunnerGroup>();
		
		for(Class<?> klass : classes) {
			Suite.SuiteClasses annotation = klass.getAnnotation(Suite.SuiteClasses.class);
			
			if (annotation != null) {
				Class<?>[] classesFromSuite = annotation.value();
				List<ExtendedHttpJUnitRunner> runnersFromSuite = new ArrayList<ExtendedHttpJUnitRunner>();
				
				for (Class<?> classFromSuite : classesFromSuite) {
					runnersFromSuite.add(new ExtendedHttpJUnitRunner(getName(classFromSuite.getSimpleName()), classFromSuite));
				}
				
				runners.add(new RunnerGroup(getName(klass.getSimpleName()), runnersFromSuite));
				
				continue;
			}
			
			runners.add(new RunnerGroup(getName(klass.getSimpleName()),
                    Arrays.asList(new ExtendedHttpJUnitRunner[]{new ExtendedHttpJUnitRunner(getName(klass.getSimpleName()), klass)})));
		}
		
		return runners;
	}

    private static String getName(String name) {
        String ending = "Test";

        if(name.endsWith(ending)) {
            int startPosition = name.lastIndexOf(ending);
            return name.substring(0, startPosition);
        }

        return name;
    }
}
