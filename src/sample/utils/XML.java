package sample.utils;

import java.beans.ExceptionListener;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import org.apache.log4j.Logger;
import sample.models.Bus;

public class XML {
    private static Logger logger = Logger.getLogger(XML.class);

    public static void serializeToXML(Object obj, String file) {
        try {
            ObjectMapper mapper = new XmlMapper();
            mapper.writeValue(new File(file + ".xml"), obj);
        } catch (Exception ex) {
            logger.error("Exception in XML serialization ->", ex);
        }
    }

    public static Bus deserializeFromXML(String file) {
        try {
            XmlMapper mapper = new XmlMapper();
            Bus bus = mapper.readValue(inputStreamToString(new FileInputStream(file)), Bus.class);
            return bus;
        } catch (Exception ex) {
            logger.error("Exception in XML deserialization ->", ex);
            return null;
        }
    }

    private static String inputStreamToString(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        String line;
        BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        br.close();
        return sb.toString();
    }
}