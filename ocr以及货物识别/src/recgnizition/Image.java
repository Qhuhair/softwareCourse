package recgnizition;

import com.baidu.aip.imageclassify.AipImageClassify;
import org.json.JSONException;
import org.json.JSONObject;

public interface Image {
    JSONObject AnimalImagePath(String path) throws JSONException;

    JSONObject VegetableImagePath(String path) throws JSONException;

    JSONObject PlantImagePath(String path) throws JSONException;

    JSONObject ImagePath(String path) throws JSONException;
}
