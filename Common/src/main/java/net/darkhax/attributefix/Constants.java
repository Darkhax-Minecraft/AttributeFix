package net.darkhax.attributefix;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.text.DecimalFormat;

public class Constants {

	public static final String MOD_ID = "attributefix";
	public static final String MOD_NAME = "AttributeFix";
	public static final Logger LOG = LogManager.getLogger(MOD_NAME);
	public static final Gson GSON = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().registerTypeAdapter(Double.class, new DoubleJsonSerializer()).create();
	public static final DecimalFormat FORMAT = new DecimalFormat("#.##");

	private static final class DoubleJsonSerializer implements JsonSerializer<Double> {

		@Override
		public JsonElement serialize(final Double src, final Type typeOfSrc, final JsonSerializationContext context) {

			BigDecimal value = BigDecimal.valueOf(src);

			try {
				value = new BigDecimal(value.toBigIntegerExact());
			}

			catch (ArithmeticException e) {
				// NO-OP
			}

			return new JsonPrimitive(value);
		}
	}
}