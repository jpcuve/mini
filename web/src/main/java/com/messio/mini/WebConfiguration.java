package com.messio.mini;

import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ServletComponentScan("com.messio.mini.web")
public class WebConfiguration {
}
