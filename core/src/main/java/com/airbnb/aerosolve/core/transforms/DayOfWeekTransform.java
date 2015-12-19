package com.airbnb.aerosolve.core.transforms;

import com.airbnb.aerosolve.core.FeatureVector;
import com.typesafe.config.Config;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;
import java.util.HashMap;
import java.util.Map;

/**
 * output = day_of_week(field1)
 */
public class DayOfWeekTransform extends Transform {
  private String fieldName1;
  private String outputName;
  private final static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
  @Override
  public void configure(Config config, String key) {
    fieldName1 = config.getString(key + ".field1");
    outputName = config.getString(key + ".output");
  }

  @Override
  public void doTransform(FeatureVector featureVector) {
    Map<String, Set<String>> stringFeatures = featureVector.getStringFeatures();
    Map<String, Map<String, Double>> floatFeatures = featureVector.getFloatFeatures();
    if (stringFeatures == null || floatFeatures == null) {
      return ;
    }

    Set<String> feature1 = stringFeatures.get(fieldName1);
    if (feature1 == null) {
      return ;
    }

    Map<String, Double> output = new HashMap<>();

    try {
      for (String dateStr: feature1) {
        Date date = format.parse(dateStr);
      }
    } catch (ParseException e) {
      e.printStackTrace();
    }
  }
}
