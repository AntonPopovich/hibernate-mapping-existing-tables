package com.javarush.domain;

import static java.util.Objects.isNull;

public enum Feature  {

    TRAILERS("Trailers"),
    COMMENTARIES("COMMENTARIES"),
    DELETED_SCENES("DELETED SCENES"),
    BEHIND_THE_SCENES("BEHIND THE SCENES");

    private final String value;

    Feature(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static Feature getFeatureByValue(String value) {
        if (isNull(value) || value.isEmpty()) {
            return null;
        }

        Feature[] features = Feature.values();
        for (Feature feature : features) {
            if (feature.value.equals(value)) {
                return feature;
            }
        }

        return null;
    }
}
