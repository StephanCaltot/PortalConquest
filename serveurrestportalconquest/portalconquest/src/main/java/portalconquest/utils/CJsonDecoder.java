package portalconquest.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Screetts on 12/05/2016.
 */

/**
 * Generic Json decoder in order to deserialize ( rebuild ) objects from Rest's server
 * @param <T>
 */
public class CJsonDecoder<T>
    {
        /**
         * Decodes just one generic object
         * @param pJson
         * @param pType
         * @return Object T
         * @throws IOException
         */
        public T Decoder(String pJson, Class pType) throws IOException
            {
                return (T) new ObjectMapper().readValue(pJson, pType);
            }

        /**
         * Decodes a whole generic list object
         * @param pJson
         * @param pType
         * @return Object's list List<T>
         * @throws IOException
         * @throws JSONException
         */
        public List<T> DecoderList (String pJson, Class pType) throws IOException
            {
                return new ObjectMapper ().readValue(pJson, new ObjectMapper().getTypeFactory().constructCollectionType(ArrayList.class, pType));
            }
    }