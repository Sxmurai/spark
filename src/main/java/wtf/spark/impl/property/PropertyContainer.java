package wtf.spark.impl.property;

import wtf.spark.util.core.ClientImpl;

import java.util.*;

public class PropertyContainer implements ClientImpl {
    private final List<Property> propertyList = new ArrayList<>();
    private final Map<String, Property> propertyNameMap = new HashMap<>();

    public void register(Property... properties) {
        for (Property property : properties) {
            register(property);
        }
    }

    public void register(Property property) {
        propertyList.add(property);
        Arrays.stream(property.getAliases()).forEach((prop) -> propertyNameMap.put(prop, property));
    }

    public <T extends Property> T getProperty(String name) {
        return (T) propertyNameMap.getOrDefault(name, null);
    }

    public List<Property> getPropertyList() {
        return propertyList;
    }

    public Map<String, Property> getPropertyNameMap() {
        return propertyNameMap;
    }
}
