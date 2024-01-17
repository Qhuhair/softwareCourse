package recgnizition.impl;

import com.baidu.aip.imageclassify.AipImageClassify;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import recgnizition.Client;
import recgnizition.Image;

import java.util.*;

public class Imageimpl implements Image {
    AipImageClassify client = Client.getImageClient();
    @Override
    public JSONObject AnimalImagePath(String path) throws JSONException {
        // 传入可选参数调用接口
        HashMap<String, String> options = new HashMap<String, String>();
        options.put("top_num", "3");
        options.put("baike_num", "5");


        // 参数为本地路径
        String image = path;
        JSONObject res = client.animalDetect(image, options);
        System.out.println(res.toString(2));
        return res;
    }
    @Override
    public JSONObject VegetableImagePath(String path) throws JSONException {
        // 传入可选参数调用接口
        HashMap<String, String> options = new HashMap<String, String>();
        options.put("top_num", "3");
        options.put("baike_num", "5");

        // 参数为本地路径
        String image = path;
        JSONObject res = client.ingredient(image, options);
        System.out.println(res.toString(2));
        return res;
    }
    @Override
    public JSONObject PlantImagePath(String path) throws JSONException {
        // 传入可选参数调用接口
        HashMap<String, String> options = new HashMap<String, String>();
        options.put("top_num", "3");
        options.put("baike_num", "5");


        // 参数为本地路径
        String image = path;
        JSONObject res = client.plantDetect(image, options);
        System.out.println(res.toString(2));
        return res;
    }
    @Override
    public JSONObject ImagePath(String path) throws JSONException {
        HashMap<String, String> options = new HashMap<>();
        options.put("top_num", "1");
        options.put("baike_num", "3");

// 调用三种识别方法并获取结果
        JSONObject ingredientRes = client.ingredient(path, options);
        JSONObject animalRes = client.animalDetect(path, options);
        JSONObject plantRes = client.plantDetect(path, options);
//// 创建一个新的JSONObject来存储所有结果
//        JSONObject res = new JSONObject();
//
//// 将三种识别结果放入新的JSONObject中
//        res.put("ingredient", ingredientRes);
//        res.put("animal", animalRes);
//        res.put("plant", plantRes);
//
//        System.out.println(res.toString(2));

        TreeMap<Double, String> resultMap = new TreeMap<>(Comparator.reverseOrder());
        // 动物识别
        JSONArray animalArray = animalRes.getJSONArray("result");

//        // 植物识别
        JSONArray plantArray = plantRes.getJSONArray("result");
//
//        // 果蔬识别
        JSONArray ingredientArray = ingredientRes.getJSONArray("result");

        // 收集并过滤所有识别结果
        List<Map.Entry<String, Double>> sortedEntries = new ArrayList<>();
        addAndFilterEntries(resultMap, animalArray, "非动物");
        addAndFilterEntries(resultMap, plantArray, "非植物");//
        addAndFilterEntries(resultMap, ingredientArray, "非果蔬食材");//
        // 若没有有效识别结果，则添加一项“识别失败”
        if (resultMap.isEmpty()) {
            resultMap.put(0.0, "识别失败");
        }


        JSONObject res = new JSONObject(resultMap);
        System.out.println(res.toString(2));
        return res;
    }

private static void addAndFilterEntries(TreeMap<Double, String> resultMap, JSONArray jsonArray, String excludeName) throws JSONException {
    for (int i = 0; i < jsonArray.length(); i++) {
        JSONObject item = jsonArray.getJSONObject(i);
        String name = item.getString("name");
        double score = item.getDouble("score");

        // 过滤无效项
        if (!name.equals(excludeName)) {
            resultMap.put(score, name);
        }
    }
}
}



//        List<Map.Entry<String, Double>> sortedEntries = new ArrayList<>();
//
//        JSONArray ingredientArray = ingredientRes.getJSONArray("result");
//        for (int i = 0; i < ingredientArray.length(); i++) {
//            JSONObject item = ingredientArray.getJSONObject(i);
//            if (!item.getString("name").equals("非果蔬食材")) {
//                sortedEntries.add(new AbstractMap.SimpleEntry<>(item.getString("name"), item.getDouble("score")));
//            }
//        }
//
//        JSONArray animalArray = animalRes.getJSONArray("result");
//        for (int i = 0; i < animalArray.length(); i++) {
//            JSONObject item = animalArray.getJSONObject(i);
//            if (!item.getString("name").equals("非动物")) {
//                sortedEntries.add(new AbstractMap.SimpleEntry<>(item.getString("name"), item.getDouble("score")));
//            }
//        }
//
//        JSONArray plantArray = plantRes.getJSONArray("result");
//        for (int i = 0; i < plantArray.length(); i++) {
//            JSONObject item = plantArray.getJSONObject(i);
//            if (!item.getString("name").equals("非植物")) {
//                sortedEntries.add(new AbstractMap.SimpleEntry<>(item.getString("name"), item.getDouble("score")));
//            }
//        }
//
//        // 按照 score 降序排序
//        sortedEntries.sort((e1, e2) -> e2.getValue().compareTo(e1.getValue()));
//
//        // 将结果整合到 HashMap 中
//        HashMap<String, String> resultMap = new LinkedHashMap<>();
//        boolean hasRecognizedResult = false;
//        for (Map.Entry<String, Double> entry : sortedEntries) {
//            resultMap.put(entry.getKey(), String.valueOf(entry.getValue()));
//            hasRecognizedResult = true;
//        }
//
//        // 若都没有有效识别结果，则添加一项“识别失败”
//        if (!hasRecognizedResult) {
//            resultMap.put("识别失败", "1.0000");
//        }
// 按照 score 降序排序
//        sortedEntries.sort(Comparator.comparing(Map.Entry<String, Double>::getValue).reversed());

// 将结果整合到 HashMap 中
//        HashMap<String, String> resultMap = new LinkedHashMap<>();
//        boolean hasRecognizedResult = false;
//        for (Map.Entry<String, Double> entry : sortedEntries) {
//            resultMap.put(entry.getKey(), String.format("%.4f", entry.getValue()));
//            hasRecognizedResult = true;
//        }

// 若都没有有效识别结果，则添加一项“识别失败”
//        if (!hasRecognizedResult) {
//            resultMap.put(0.0,"识别失败" );
//        }
//    private static void addAndFilterEntries(List<Map.Entry<String, Double>> entries, JSONArray jsonArray, String excludeName) throws JSONException {
//        for (int i = 0; i < jsonArray.length(); i++) {
//            JSONObject item = jsonArray.getJSONObject(i);
//            String name = item.getString("name");
//            double score = item.getDouble("score"); // 修改这一行，将 "getString" 改为 "getDouble"
//
//            // 过滤无效项
//            if (!name.equals(excludeName)) {
//                entries.add(new AbstractMap.SimpleEntry<>(name, score));
//            }
//        }
//    }
