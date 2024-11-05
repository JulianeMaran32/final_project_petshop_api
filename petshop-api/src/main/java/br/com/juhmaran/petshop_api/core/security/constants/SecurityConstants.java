package br.com.juhmaran.petshop_api.core.security.constants;

import lombok.experimental.UtilityClass;

@UtilityClass
public class SecurityConstants {

    // Cabeçalhos HTTP
    public static final String ACCEPT_LANGUAGE = "Accept-Language";
    public static final String CONTENT_TYPE = "Content-Type";
    public static final String X_CSRF_TOKEN = "X-CSRF-TOKEN";

    // Constantes de segurança (Token e Autenticação)
    public static final String BEARER = "Bearer ";
    public static final String AUTHORIZATION = "Authorization";
    public static final String XSRF_TOKEN = "XSRF-TOKEN";

    //Cookies
    public static final String JSESSIONID = "JSESSIONID";

    // Constantes de URL
    public static final String LOCALHOST_4200 = "http://localhost:4200";
    public static final String LOCALHOST_9090 = "http://localhost:9090";
    public static final String VIA_CEP = "https://viacep.com.br/ws/";
    public static final String AZURE_APP_FRONTEND = "https://petshop-web-app-c3f3c7awgedthke5.eastus2-01.azurewebsites.net/";
    public static final String AZURE_API_BACKEND = "https://petshop-web-api-a5aeanc2g5gzgzer.eastus2-01.azurewebsites.net/";

    public static final String[] CLASSPATH = {"classpath:/META-INF/resources/", "classpath:/resources/",
            "classpath:/static/", "classpath:/public/"};

    public static final String[] URL_ACTUATOR = {"/actuator", "/actuator/**", "/api/actuator", "/api/actuator/**"};

    public static final String[] URL_API = {"/", "/**", "/api", "/api/**"};

    public static final String[] URL_WEBJAR = {"/webjar/**", "/api/webjar/**"};

    public static final String[] URL_CACHE = {"/cache/contents", "/api/cache/contents"};

    public static final String[] URL_ERROR = {"/error", "/errors", "/error/**", "/errors/**", "/api/error", "/api/errors"};

    public static final String[] URL_SWAGGER = {
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/swagger-resources/**",
            "/swagger-resources",
            "/swagger-ui.html",
    };

    public static final String[] URL_AUTH = {"/auth", "/auth/**", "/auth/login",
            "/auth/forgot-password", "/auth/reset-password"};

    public static final String[] URL_USER_REGISTER = {"/users/register", "/api/users/register"};

    public static final String[] URL_CONTACT = {"/contacts", "/api/contacts"};


}
