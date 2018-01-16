package com.senpure.base.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.autoconfigure.AutoConfigurationPackages;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Set;

/**
 * Created by 罗中正 on 2018/1/5 0005.
 */
//@Configuration
//@Import({SenpureAutoConfiguration.SenpureImportBeanDefinitionRegistrar.class})
//@ImportResource("classpath:application-senpure.xml")
//@ComponentScan("com.senpure")
public class SenpureAutoConfiguration {
   static Logger logger = LoggerFactory.getLogger(SenpureImportBeanDefinitionRegistrar.class);



    static  class  SenpureImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar
 {

        @Override
        public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry registry) {

            logger.info(annotationMetadata.getAnnotationTypes().toString());

           // Scanner  scanner=new Scanner (registry);
            AutoConfigurationPackages.register(registry,"com.senpure");
           // scanner.doScan(registry,"com.senpure");

        }


 }
    static class Scanner  extends ClassPathBeanDefinitionScanner{
        public Scanner(BeanDefinitionRegistry registry) {
            super(registry);
        }

        public Set<BeanDefinitionHolder> doScan(BeanDefinitionRegistry registry,String... basePackages)
        {
          //  Set<BeanDefinitionHolder> beanDefinitionHolders = super.doScan(basePackages);
          //  Iterator<BeanDefinitionHolder> iterator = beanDefinitionHolders.iterator();

           // while (iterator.hasNext()) {
              //  BeanDefinitionHolder bean=iterator.next();
               // registry.registerBeanDefinition(bean.getBeanName(),bean.getBeanDefinition());

         //   }
          //  return  beanDefinitionHolders;
            return null;
        }
    }
}
