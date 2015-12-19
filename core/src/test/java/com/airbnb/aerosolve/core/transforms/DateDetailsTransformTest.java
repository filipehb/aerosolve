package com.airbnb.aerosolve.core.transforms;

import com.airbnb.aerosolve.core.FeatureVector;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.junit.Test;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by seckcoder on 12/18/15.
 */
public class DateDetailsTransformTest {
  public String makeConfig() {
    return "test_dayofdate {\n" +
            " transform: day_of_date\n" +
            " field1: dates\n" +
            " output: bar\n" +
            "}";
  }
  public FeatureVector makeFeatureVector() {
    Map<String, Set<String>> stringFeatures = new HashMap<>();
    Map<String, Map<String, Double>> floatFeatures = new HashMap<>();

    Set dates = new HashSet<String>();
    dates.add("2009-03-01");
    dates.add("2009-02-27");
    stringFeatures.put("dates", dates);

    FeatureVector featureVector = new FeatureVector();
    featureVector.setStringFeatures(stringFeatures);
    featureVector.setFloatFeatures(floatFeatures);
    return featureVector;
  }

  @Test
  public void testTransform() {
    System.out.println("hello world");
    Config config = ConfigFactory.parseString(makeConfig());
    Transform transform = TransformFactory.createTransform(config, "test_dayofdate");
    FeatureVector featureVector = makeFeatureVector();
    transform.doTransform(featureVector);

    Map<String, Map<String, Double>> floatFeatures = featureVector.getFloatFeatures();
    assertTrue(floatFeatures.size() == 1);

    Map<String, Double> out = floatFeatures.get("bar");

    assertEquals(out.get("2009-03-01-year"), 2009, 0.1);
    assertEquals(out.get("2009-03-01-month"), 3, 0.1);
    assertEquals(out.get("2009-03-01-dayofmonth"), 1, 0.1);
    assertEquals(out.get("2009-03-01-dayofweek"), 1, 0.1);


    assertEquals(out.get("2009-02-27-year"), 2009, 0.1);
    assertEquals(out.get("2009-02-27-month"), 2, 0.1);
    assertEquals(out.get("2009-02-27-dayofmonth"), 27, 0.1);
    assertEquals(out.get("2009-02-27-dayofweek"), 6, 0.1);
  }
}
