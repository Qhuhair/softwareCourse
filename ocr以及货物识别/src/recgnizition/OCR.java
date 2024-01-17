package recgnizition;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public interface OCR extends Client{

    JSONObject basicOCRPath(String Path) throws JSONException, IOException;

    JSONObject basicOCRURL(String Path) throws JSONException, IOException;

    JSONObject basicAccurateOCRURL(String url) throws JSONException, IOException;

    JSONObject basicAccurateOCRPath(String url) throws JSONException, IOException;

    JSONObject basicMymoduleOCRPath(String Path) throws JSONException, IOException;

    JSONObject basicMymoduleOCRURL(String url) throws JSONException, IOException;
}
