package com.salesmanager.core.business.configuration.cron;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
@ConditionalOnProperty(name = "scheduling.enabled", matchIfMissing = true)
public class ProfileCompletionEmailCron {

  // Todo : need to move this code from this class
  // @Scheduled(cron = "* * * * *")
  //        void someJob(){
  //            System.out.printf("thread job");
  //        }
}
