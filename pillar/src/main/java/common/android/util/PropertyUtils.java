package common.android.util;

import android.util.Property;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class PropertyUtils {

    public static <T, R> Map<String, Property<T, R>> getPropertiesMap(Class<T> objectClass, Class<R> propertyClass) {

        // Reflection
        final Field[] fields = objectClass.getDeclaredFields();
        final Map<String, Property<T, R>> out = new HashMap<>(fields.length);

        for (final Field f : fields) {
            if (String.class.isAssignableFrom(f.getType())) {
                out.put(f.getName(), Property.of(objectClass, propertyClass, f.getName()));
            }
        }
        return out;
    }
}
