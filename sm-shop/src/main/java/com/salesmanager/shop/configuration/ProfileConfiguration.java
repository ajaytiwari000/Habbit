package com.salesmanager.shop.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@ConfigurationProperties("spring.profile")
public class ProfileConfiguration {

  private String driverClassName;
  private String url;
  private String username;
  private String password;

  public String getDriverClassName() {
    return driverClassName;
  }

  public void setDriverClassName(String driverClassName) {
    this.driverClassName = driverClassName;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  @Profile("local")
  @Bean
  public String localDatabaseConnection() {
    System.out.println("DB connection for DEV - H2");
    System.out.println(driverClassName);
    System.out.println(url);
    return "DB connection for LOCAL";
  }

  @Profile("dev0")
  @Bean
  public String devDatabaseConnection() {
    System.out.println("DB connection for DEV - H2");
    System.out.println(driverClassName);
    System.out.println(url);
    return "DB connection for DEV - H2";
  }

  @Profile("qa0")
  @Bean
  public String testDatabaseConnection() {
    System.out.println("DB Connection to RDS_TEST - Low Cost Instance");
    System.out.println(driverClassName);
    System.out.println(url);
    return "DB Connection to RDS_TEST - Low Cost Instance";
  }

  @Profile("stag")
  @Bean
  public String prodDatabaseConnection() {
    System.out.println("DB Connection to RDS_PROD - High Performance Instance");
    System.out.println(driverClassName);
    System.out.println(url);
    return "DB Connection to RDS_PROD - High Performance Instance";
  }
}
