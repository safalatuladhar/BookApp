package utils;

import java.util.Map;
import java.util.Set;

public class Constants {
    public static final Map<String,String> env = System.getenv();
    public static final Set<String> ALLOWED_PATHS = Set.of("","/auth", "/genre/.*$", "/book/.*$");
    public static final Set<String> ALLOWED_ORIGIN = Set.of( "http://localhost:4200", "http://localhost:8080");
}
