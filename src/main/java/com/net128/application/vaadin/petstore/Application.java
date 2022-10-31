package com.net128.application.vaadin.petstore;

import com.logviewer.springboot.LogViewerSpringBootConfig;
import com.net128.oss.web.lib.filemanager.FileManagerServlet;
import com.net128.oss.web.lib.jpa.csv.JpaCsv;
import com.net128.oss.web.webshell.WebShell;
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
        String tmpDir = System.getProperty("java.io.tmpdir");
        if(tmpDir!=null) {
            if(!tmpDir.endsWith("/") && !tmpDir.endsWith("\\")) {
                System.setProperty("java.io.tmpdir", tmpDir+"/");
            }
        }

        SpringApplication.run(Application.class);
    }
}
