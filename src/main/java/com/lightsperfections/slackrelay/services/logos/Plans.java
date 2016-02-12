package com.lightsperfections.slackrelay.services.logos;

import com.lightsperfections.slackrelay.ReadingPlanConfig;
import com.lightsperfections.slackrelay.beans.logos.ReadingPlan;
import com.lightsperfections.slackrelay.services.DependentServiceException;
import com.lightsperfections.slackrelay.services.InternalImplementationException;
import com.lightsperfections.slackrelay.services.SlackRelayService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Map;

/**
 * Use this service to find out what plans are available
 *      i.e. /logos plans
 *
 * Created by jon on 1/15/16.
 */
public class Plans implements SlackRelayService {

    private final String name;

    public Plans(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String performAction(String userName, String userText)
            throws DependentServiceException, InternalImplementationException {

        String rv = "";

        // This context for Application managed reading plan
        AnnotationConfigApplicationContext readingPlanContext =
                new AnnotationConfigApplicationContext(ReadingPlanConfig.class);

        Map<String, ReadingPlan> plans = readingPlanContext.getBeansOfType(ReadingPlan.class);

        for (ReadingPlan plan : plans.values()) {
            rv += plan.toString() + "\n\n";
        }

        return rv;
    }
}
