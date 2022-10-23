package com.net128.application.vaadin.petstore;

import com.logviewer.springboot.LogViewerSpringBootConfig;
import com.net128.oss.web.lib.filemanager.FileManagerServlet;
import com.net128.oss.web.lib.jpa.csv.JpaCsv;
import com.net128.oss.web.webshell.WebShell;
//import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({ JpaCsv.class,
    LogViewerSpringBootConfig.class,
    WebShell.class,
    FileManagerServlet.class
})
public class Application extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }
}
