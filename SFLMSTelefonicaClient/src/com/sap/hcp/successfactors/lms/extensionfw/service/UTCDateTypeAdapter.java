package com.sap.hcp.successfactors.lms.extensionfw.service;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class UTCDateTypeAdapter extends TypeAdapter<Date> {
    private final DateFormat dateFormat;

    public UTCDateTypeAdapter() {
        dateFormat = new SimpleDateFormat("dd/MM/yyyy'T'HH:mm:ssX");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
    }

    @Override
    public void write(JsonWriter out, Date value) throws IOException {
        if (value != null) {
            String dateFormatAsString = dateFormat.format(value);
            out.value(dateFormatAsString);
        } else {
            out.nullValue();
        }

    }

    @Override
    public Date read(JsonReader in) throws IOException {
        if (in.peek() == JsonToken.NULL) {
            in.nextNull();
            return null;
        }
        try {
            return dateFormat.parse(in.nextString());
        } catch (ParseException e) {
            return null;
        }

    }

}
