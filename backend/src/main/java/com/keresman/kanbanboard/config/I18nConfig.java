package com.keresman.kanbanboard.config;

import java.util.List;
import java.util.Locale;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

/** i18n configuration: message source, default locale, and locale switching. */
@Configuration
public class I18nConfig implements WebMvcConfigurer {

  /** Message source for i18n bundles (messages_*.properties). */
  @Bean
  public MessageSource messageSource() {
    ReloadableResourceBundleMessageSource resourceBundleMessageSource =
        new ReloadableResourceBundleMessageSource();
    resourceBundleMessageSource.setBasenames("classpath:messages", "classpath:ValidationMessages");
    resourceBundleMessageSource.setDefaultEncoding("UTF-8");
    resourceBundleMessageSource.setFallbackToSystemLocale(false);
    return resourceBundleMessageSource;
  }

  /** Locale resolver that uses the Accept-Language header but restricts to supported locales. */
  @Bean
  public LocaleResolver localeResolver() {
    AcceptHeaderLocaleResolver acceptHeaderLocaleResolver = new AcceptHeaderLocaleResolver();
    acceptHeaderLocaleResolver.setDefaultLocale(Locale.ENGLISH);
    acceptHeaderLocaleResolver.setSupportedLocales(
        List.of(Locale.ENGLISH, new Locale("hr"), Locale.GERMAN));
    return acceptHeaderLocaleResolver;
  }

  /**
   * Allows switching locale via request parameter, e.g., ?lang=hr (Accept-Language still works;
   * this just adds explicit override).
   */
  @Bean
  public LocaleChangeInterceptor localeChangeInterceptor() {
    LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
    localeChangeInterceptor.setParamName("lang");
    return localeChangeInterceptor;
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(localeChangeInterceptor());
  }
}
