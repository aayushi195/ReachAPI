package edu.asu.heal.reachv3.api.models;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import edu.asu.heal.core.api.models.ActivityInstance;

public class DateDeserializer extends StdDeserializer<Date> {
	private static final String DATE_FORMAT_AI="MMM dd, yyyy HH:mm:ss";
	public DateDeserializer() {
		this(null);
	}

	public DateDeserializer(Class<?> cls) {
		super(cls);
	}

	@Override
	public Date deserialize(JsonParser arg0, DeserializationContext arg1) throws IOException, JsonProcessingException {
		// TODO Auto-generated method stub
		JSONObject obj;
		try {
			obj = new JSONObject(arg0.getText());
			System.out.println("JSON obj : "+ obj.toString());
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		Long l = arg0.getValueAsLong();
		System.out.println("String : -------------------  " + l);
		try{
			SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT_AI);
			return new Date(l);
		}catch(Exception e) {
			System.out.println("Issue in Date Deserializer");
			e.printStackTrace();
			return null;
		}

	}


}
