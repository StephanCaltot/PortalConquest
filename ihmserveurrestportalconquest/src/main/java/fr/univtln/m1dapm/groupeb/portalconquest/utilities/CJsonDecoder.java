package fr.univtln.m1dapm.groupeb.portalconquest.utilities;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Screetts on 12/05/2016.
 */

public class CJsonDecoder<T>
    {
        public T Decoder(String pJson, Class pType) throws IOException
            {
                return (T) new ObjectMapper().readValue(pJson, pType);
            }


        public List<T> DecoderList (String pJson, Class pType) throws IOException
            {
                return new ObjectMapper ().readValue(pJson, new ObjectMapper().getTypeFactory().constructCollectionType(ArrayList.class, pType));
            }
    }