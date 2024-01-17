package recgnizition.impl;

import com.baidu.aip.ocr.AipOcr;
import org.json.JSONException;
import org.json.JSONObject;
import recgnizition.OCR;
import recgnizition.Client;
import java.io.IOException;
import java.util.HashMap;

public class OCRimpl implements OCR {
    //自定义模板templateSign：222f354650ac51d7713c464f273f65cd
    AipOcr client= Client.getClient();
    @Override
    public JSONObject basicOCRPath(String Path) throws JSONException, IOException {
        // 传入可选参数调用接口
        HashMap<String, String> options = new HashMap<String, String>();
        options.put("language_type", "CHN_ENG");
        options.put("detect_direction", "true");
        options.put("detect_language", "true");
        options.put("probability", "true");
        // 参数为本地图片路径"C://Users//52897//Desktop//工作//学籍验证报告.jpg"
        String image = Path;
        JSONObject res = client.basicGeneral(image, options);
        System.out.println(res.toString(2));
        return res;
    }
    @Override
    public JSONObject basicOCRURL(String url) throws JSONException, IOException {
        // 传入可选参数调用接口
        HashMap<String, String> options = new HashMap<String, String>();
        options.put("language_type", "CHN_ENG");
        options.put("detect_direction", "true");
        options.put("detect_language", "true");
        options.put("probability", "true");
        // 通用文字识别, 图片参数为远程url图片
        JSONObject res = client.basicGeneralUrl(url, options);
        System.out.println(res.toString(2));
        return res;
    }
    @Override
    public JSONObject basicAccurateOCRURL(String url) throws JSONException, IOException {
        // 传入可选参数调用接口
        HashMap<String, String> options = new HashMap<String, String>();
        options.put("detect_direction", "true");
        options.put("probability", "true");
        // 通用精确文字识别, 图片参数为远程url图片
        JSONObject res = client.accurateGeneralUrl(url, options);
        System.out.println(res.toString(2));
        return res;
    }
    @Override
    public JSONObject basicAccurateOCRPath(String Path) throws JSONException, IOException {
        // 传入可选参数调用接口
        HashMap<String, String> options = new HashMap<String, String>();
        options.put("detect_direction", "true");
        options.put("probability", "true");

        // 通用精确文字识别, 图片参数为远程path图片
        String image = Path;
        JSONObject res = client.basicAccurateGeneral(image, options);
        System.out.println(res.toString(2));
        return res;
    }
    @Override
    public JSONObject basicMymoduleOCRPath(String Path) throws JSONException, IOException {
        AipOcr client= Client.getClient();
        // 传入可选参数调用接口
        HashMap<String, String> options = new HashMap<String, String>();

        options.put("templateSign", "cdcd50965f8d08c5b63abbf0b15b3b8a");

        // 参数为本地图片路径
        String image = Path;
        JSONObject res = client.custom(image, options);
        System.out.println(res.toString(2));
        return res;
    }
    @Override
    public JSONObject basicMymoduleOCRURL(String url) throws JSONException, IOException {
        AipOcr client= Client.getClient();
        // 传入可选参数调用接口
        HashMap<String, String> options = new HashMap<String, String>();

        options.put("templateSign", "cdcd50965f8d08c5b63abbf0b15b3b8a");

        JSONObject res = client.basicAccurateGeneralUrl(url, options);
        System.out.println(res.toString(2));
        return res;
    }
}
