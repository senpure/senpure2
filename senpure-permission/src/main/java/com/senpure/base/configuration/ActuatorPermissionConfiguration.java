package com.senpure.base.configuration;

import com.senpure.base.annotation.ExtPermission;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by 罗中正 on 2018/1/25 0025.
 */
@Configuration
public class ActuatorPermissionConfiguration {

    @Component
    @ExtPermission
    class ActuatorPermission {

        @ExtPermission(value = {"/auditevents", "/auditevents.json"}, name = "/actuator_read")
        public void readAuditevents() {
        }

        @ExtPermission(value = {"/info", "/info.json"}, name = "/actuator_read")
        public void readInfo() {
        }

        @ExtPermission(value = {"/health", "/health.json"}, name = "/actuator_read")
        public void readHealth() {
        }

        @ExtPermission(value = {"/heapdump", "/heapdump.json"}, name = "/actuator_read")
        public void readHeapdump() {
        }

        @ExtPermission(value = {"/trace", "/trace.json"}, name = "/actuator_read")
        public void readTrace() {
        }




        @ExtPermission(value = {"/mappings", "/mappings.json"}, name = "/actuator_read")
        public void readMappings() {
        }

        @ExtPermission(value = {"/autoconfig", "/autoconfig.json"}, name = "/actuator_read")
        public void readAutoconfig() {
        }

        @ExtPermission(value = {"/beans", "/beans.json"}, name = "/actuator_read")
        public void readBeans() {
        }

        @ExtPermission(value = {"/dump", "/dump.json"}, name = "/actuator_read")
        public void readDump() {
        }

        @ExtPermission(value = {"/env", "/env.json", "/env/{name:.*}"}, name = "/actuator_read")
        public void readEnv() {
        }

        @ExtPermission(value = {"/metrics", "/metrics.json", "/metrics/{name:.*}"}, name = "/actuator_read")
        public void readMetrics() {
        }

        @ExtPermission(value = {"/loggers", "/loggers.json", "/loggers/{name:.*}"}, name = "/actuator_read")
        public void readLoggers() {
        }

        @ExtPermission(value = {"/loggers/{name:.*}"}, name = "/actuator_update",method = RequestMethod.POST)
        public void updateLoggers() {
        }


        @ExtPermission(value = {"/configprops", "/configprops.json"}, name = "/actuator_read")
        public void readConfigprops() {
        }

    }
}
