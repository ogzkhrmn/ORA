/*
 * @author : oguzkahraman
 * @since : 22.05.2021
 *
 * Copyright - Tamir Guru Java API
 **/
package com.example.orion.core.i18n;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Locale;

public class CustomLocaleResolver extends AcceptHeaderLocaleResolver {
    List<Locale> locales = List.of(new Locale("tr"), new Locale("en"));

    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        if (StringUtils.isBlank(request.getHeader("Accept-Language"))) {
            return Locale.getDefault();
        }
        String[] split = request.getHeader("Accept-Language").split("_");
        List<Locale.LanguageRange> list = Locale.LanguageRange.parse(split[0]);
        return Locale.lookup(list, locales) != null ? Locale.lookup(list, locales) : Locale.getDefault();
    }
}
