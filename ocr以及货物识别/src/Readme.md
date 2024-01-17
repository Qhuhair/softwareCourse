# OCR及图像识别使用说明
## 1. 使用前提
### 加入我上传的接口调用类
- 结构如下
- ![img.png](img.png)
### 本地安装了baiduapi相应库

- 支持 JAVA版本：1.7+
- Java SDK可在github链接处查看：https://github.com/Baidu-AIP/java-sdk
- 方法一、直接导入我传在仓库ocr文件夹里的jar包
文件夹中包含的jar包如下
```
java-sdk-4.16.17.jar    //这个是解压前的jar包，不用导入
com.google.gson-2.9.1.jar
okhttp-4.10.0.jar
org.json-chargebee-1.0.jar
slf4j-api-1.7.25.jar
```
- 方法二、使用maven依赖：
添加以下依赖即可。其中版本号可在maven官网查询
```
<dependency>
    <groupId>com.baidu.aip</groupId>
    <artifactId>java-sdk</artifactId>
    <version>${version}</version>
</dependency>
```
- 方法三、自己下了使用JAR包步骤如下： 
1. 在官方网站下载Java SDK压缩工具包。 
2. 将下载的aip-java-sdk-version.zip解压后，复制到工程文件夹中。
3. 在Eclipse右键“工程 -> Properties -> Java Build Path -> Add JARs”。
4. 添加SDK工具包aip-java-sdk-version.jar和第三方依赖工具包json-20160810.jar slf4j-api-1.7.25.jar slf4j-simple-1.7.25.jar（可选）。
其中，version为版本号，添加完成后，用户就可以在工程中使用ImageClassify Java SDK。
# 2.OCR使用说明
## 2.1 通用文字识别ocrimpl接口介绍
- 接口ocrimpl拥有的函数调用如下，均有参数分别为本地路径和url的两个版本
```
    JSONObject basicOCRPath(String Path) throws JSONException, IOException;

    JSONObject basicOCRURL(String Path) throws JSONException, IOException;
//文字识别通用基础版
    JSONObject basicAccurateOCRURL(String url) throws JSONException, IOException;

    JSONObject basicAccurateOCRPath(String url) throws JSONException, IOException;
//文字识别通用高精度版
    JSONObject basicMymoduleOCRPath(String Path) throws JSONException, IOException;

    JSONObject basicMymoduleOCRURL(String url) throws JSONException, IOException;
//文字识别通用自定义版，需要付费使用，目前只能看看实验效果 ，imagefile中放了使用自定义模型尝试识别的结果
```
## 2.2 通用文字识别ocrimpl接口使用示例
- 以下是一个使用示例，其中的参数为本地路径，也可以使用url
``` java
    OCRimpl ocrimpl=new OCRimpl();
    ocrimpl.basicOCRPath("D://MY//学习资料//大三上//软工//coursefile//swdfile//订单表尝试2.jpg");
    ocrimpl.basicAccurateOCRPath("D://MY//学习资料//大三上//软工//coursefile//swdfile//订单表尝试2.jpg");
//    ocrimpl.basicMymoduleOCRPath("D://MY//学习资料//大三上//软工//coursefile//swdfile//订单表尝试2.jpg");
//在获得自定义模板使用权限之前无法调用第三个方法
```
## 2.3 通用文字识别ocrimpl接口返回值说明
三个方法演示结果如下
演示使用的图片为(订单表尝试2.jpg)
- basicOCRPath,只复制了部分结果
```
{
  "words_result": [
    {
      "probability": {
        "average": 0.9997393489,
        "min": 0.9985184073,
        "variance": 1.55206223E-7
      },
      "words": "网易仓库管理系统货物订单"
    },
    {
      "probability": {
        "average": 0.9998493195,
        "min": 0.9994986057,
        "variance": 4.124689568E-8
      },
      "words": "订单编号"
    },
    {
      "probability": {
        "average": 0.9999765158,
        "min": 0.999940753,
        "variance": 2.907505348E-10
      },
      "words": "13571231"
    },

```
- basicAccurateOCRPath
```

{
  "words_result": [
    {
      "probability": {
        "average": 0.9998223186,
        "min": 0.9992547631,
        "variance": 4.547678145E-8
      },
      "words": "网易仓库管理系统货物订单"
    },
    {
      "probability": {
        "average": 0.9998179674,
        "min": 0.9996600151,
        "variance": 8.396451534E-9
      },
      "words": "订单编号"
    },
    {
      "probability": {
        "average": 0.9991067648,
        "min": 0.9978328347,
        "variance": 3.217680273E-7
      },
      "words": "13571231"
    },
    {
      "probability": {
        "average": 0.9998310208,
        "min": 0.9996943474,
        "variance": 1.498383995E-8
      },
      "words": "订单日期"
    },
    {
      "probability": {
        "average": 0.9993159175,
        "min": 0.9982952476,
        "variance": 1.914789749E-7
      },
      "words": "2023年1月11"
    },
    {
      "probability": {
        "average": 0.9158375859,
        "min": 0.9158375859,
        "variance": 0
      },
      "words": "←"
    },
    {
      "probability": {
        "average": 0.999735713,
        "min": 0.9989697933,
        "variance": 1.955946374E-7
      },
      "words": "来源仓库"
    },
    {
      "probability": {
        "average": 0.7498972416,
        "min": 0.5011194944,
        "variance": 0.06189038232
      },
      "words": "1e"
    },
    {
      "probability": {
        "average": 0.9999023676,
        "min": 0.9998024106,
        "variance": 8.66064731E-9
      },
      "words": "目的仓库"
    },
    {
      "probability": {
        "average": 0.9997816682,
        "min": 0.9997816682,
        "variance": 0
      },
      "words": "2"
    },
    {
      "probability": {
        "average": 0.9978355765,
        "min": 0.9978355765,
        "variance": 0
      },
      "words": "←"
    },
    {
      "probability": {
        "average": 0.9994730949,
        "min": 0.9982811213,
        "variance": 4.768716053E-7
      },
      "words": "订单类型"
    },
    {
      "probability": {
        "average": 0.9998465776,
        "min": 0.9996885061,
        "variance": 1.205960487E-8
      },
      "words": "订单入库"
    },
    {
      "probability": {
        "average": 0.9985200763,
        "min": 0.9985200763,
        "variance": 0
      },
      "words": "←"
    },
    {
      "probability": {
        "average": 0.9997281432,
        "min": 0.9990522265,
        "variance": 1.538754333E-7
      },
      "words": "货物清单"
    },
    {
      "probability": {
        "average": 0.998559773,
        "min": 0.998559773,
        "variance": 0
      },
      "words": "←"
    },
    {
      "probability": {
        "average": 0.8452581167,
        "min": 0.5408766866,
        "variance": 0.03513183072
      },
      "words": "货物ID"
    },
    {
      "probability": {
        "average": 0.999971807,
        "min": 0.9999154806,
        "variance": 1.099781599E-9
      },
      "words": "货物名称"
    },
    {
      "probability": {
        "average": 0.9999634027,
        "min": 0.9999359846,
        "variance": 5.002398495E-10
      },
      "words": "货物数量"
    },
    {
      "probability": {
        "average": 0.9961361885,
        "min": 0.9865736961,
        "variance": 2.616127676E-5
      },
      "words": "货物单价(/元)"
    },
    {
      "probability": {
        "average": 0.9999979734,
        "min": 0.999997735,
        "variance": 5.684341886E-14
      },
      "words": "备注"
    },
    {
      "probability": {
        "average": 0.9968093038,
        "min": 0.9968093038,
        "variance": 0
      },
      "words": "←"
    },
    {
      "probability": {
        "average": 0.7731340528,
        "min": 0.5474398732,
        "variance": 0.0509378612
      },
      "words": "1e"
    },
    {
      "probability": {
        "average": 0.9999810457,
        "min": 0.999969244,
        "variance": 1.392805871E-10
      },
      "words": "西瓜"
    },
    {
      "probability": {
        "average": 0.9998758435,
        "min": 0.9998357296,
        "variance": 1.609127054E-9
      },
      "words": "20"
    },
    {
      "probability": {
        "average": 0.9998832941,
        "min": 0.9998832941,
        "variance": 0
      },
      "words": "2"
    },
    {
      "probability": {
        "average": 0.9999459982,
        "min": 0.9998571873,
        "variance": 3.182513808E-9
      },
      "words": "华强甄选"
    },
    {
      "probability": {
        "average": 0.9982100725,
        "min": 0.9982100725,
        "variance": 0
      },
      "words": "←"
    },
    {
      "probability": {
        "average": 0.9998505116,
        "min": 0.9998505116,
        "variance": 0
      },
      "words": "2"
    },
    {
      "probability": {
        "average": 0.9999915361,
        "min": 0.9999904633,
        "variance": 1.151079232E-12
      },
      "words": "拉面"
    },
    {
      "probability": {
        "average": 0.999858737,
        "min": 0.999858737,
        "variance": 0
      },
      "words": "2"
    },
    {
      "probability": {
        "average": 0.9999530911,
        "min": 0.9999212027,
        "variance": 1.016875473E-9
      },
      "words": "10"
    },
    {
      "probability": {
        "average": 0.9999918342,
        "min": 0.9999861717,
        "variance": 2.065192461E-11
      },
      "words": "六子推荐"
    },
    {
      "probability": {
        "average": 0.9977528453,
        "min": 0.9977528453,
        "variance": 0
      },
      "words": "←"
    },
    {
      "probability": {
        "average": 0.9996989965,
        "min": 0.9996989965,
        "variance": 0
      },
      "words": "3"
    },
    {
      "probability": {
        "average": 0.999804914,
        "min": 0.9996966124,
        "variance": 1.172924513E-8
      },
      "words": "篮球"
    },
    {
      "probability": {
        "average": 0.9998582602,
        "min": 0.9998582602,
        "variance": 0
      },
      "words": "2"
    },
    {
      "probability": {
        "average": 0.9999575019,
        "min": 0.9999405146,
        "variance": 2.885691686E-10
      },
      "words": "25"
    },
    {
      "probability": {
        "average": 0.9822814465,
        "min": 0.9381255507,
        "variance": 6.619942142E-4
      },
      "words": "坤坤最爱"
    },
    {
      "probability": {
        "average": 0.9990252256,
        "min": 0.9990252256,
        "variance": 0
      },
      "words": "←"
    },
    {
      "probability": {
        "average": 0.7359270453,
        "min": 0.7359270453,
        "variance": 0
      },
      "words": "K"
    },
    {
      "probability": {
        "average": 0.9975220561,
        "min": 0.9975220561,
        "variance": 0
      },
      "words": "←"
    },
    {
      "probability": {
        "average": 0.9983271956,
        "min": 0.9983271956,
        "variance": 0
      },
      "words": "←"
    },
    {
      "probability": {
        "average": 0.9980503321,
        "min": 0.9980503321,
        "variance": 0
      },
      "words": "←"
    },
    {
      "probability": {
        "average": 0.9985074401,
        "min": 0.9985074401,
        "variance": 0
      },
      "words": "←"
    },
    {
      "probability": {
        "average": 0.9981789589,
        "min": 0.9981789589,
        "variance": 0
      },
      "words": "←"
    },
    {
      "probability": {
        "average": 0.9201251268,
        "min": 0.9201251268,
        "variance": 0
      },
      "words": "←"
    },
    {
      "probability": {
        "average": 0.9999594688,
        "min": 0.9998978376,
        "variance": 1.932008331E-9
      },
      "words": "供应方"
    },
    {
      "probability": {
        "average": 0.9996929169,
        "min": 0.9987587929,
        "variance": 1.8910481E-7
      },
      "words": "星际和平公司"
    },
    {
      "probability": {
        "average": 0.9999456406,
        "min": 0.9997307658,
        "variance": 7.885918585E-9
      },
      "words": "供应方联系方式"
    },
    {
      "probability": {
        "average": 0.9981146455,
        "min": 0.9961043596,
        "variance": 1.753130505E-6
      },
      "words": "1919810"
    },
    {
      "probability": {
        "average": 0.9999640584,
        "min": 0.9999281168,
        "variance": 7.175001149E-10
      },
      "words": "接收方"
    },
    {
      "probability": {
        "average": 0.9979569316,
        "min": 0.9899966717,
        "variance": 1.076946774E-5
      },
      "words": "下北泽株式会社"
    },
    {
      "probability": {
        "average": 0.9999648929,
        "min": 0.9998731613,
        "variance": 1.559612328E-9
      },
      "words": "接收方联系方式"
    },
    {
      "probability": {
        "average": 0.9997456074,
        "min": 0.999550283,
        "variance": 9.83969084E-9
      },
      "words": "114514"
    },
    {
      "probability": {
        "average": 0.9977867603,
        "min": 0.9977867603,
        "variance": 0
      },
      "words": "←"
    },
    {
      "probability": {
        "average": 0.9983166456,
        "min": 0.9983166456,
        "variance": 0
      },
      "words": "←"
    },
    {
      "probability": {
        "average": 0.9912348986,
        "min": 0.9651014805,
        "variance": 2.276530286E-4
      },
      "words": "经办人："
    },
    {
      "probability": {
        "average": 0.9999055862,
        "min": 0.9997366071,
        "variance": 1.43079637E-8
      },
      "words": "刘培强"
    }
  ],
  "log_id": 1747622886376772180,
  "words_result_num": 59,
  "direction": 0
}

```
- basicMymoduleOCRPath
```
{
  "error_msg": "Open api qps request limit reached",
  "error_code": 18
}
```
# 3.Image使用说明
## 3.1 图像识别Imageimpl接口介绍
```
    JSONObject AnimalImagePath(String path) throws JSONException;
//动物识别
    JSONObject VegetableImagePath(String path) throws JSONException;
//果蔬识别
    JSONObject PlantImagePath(String path) throws JSONException;
//植物识别
    JSONObject ImagePath(String path) throws JSONException;
//我结合上述三个识别写的通用物体识别，不过需要注意返回的json对象结构不一样
```
图像识别部分还有自行建库自定义识别部分，不过需要进行申请，大概率要money，目前还没有申请，所以没有写
## 3.2 图像识别Imageimpl接口使用示例
- 以下是一个使用示例，其中的参数为本地路径，
``` java
    Imageimpl imageimpl=new Imageimpl();
    imageimpl.AnimalImagePath("D://MY//学习资料//大三上//软工//coursefile//swdfile//鳕鱼.jpg");
    imageimpl.VegetableImagePath("D://MY//学习资料//大三上//软工//coursefile//swdfile//土豆.jpg");
    imageimpl.PlantImagePath("D://MY//学习资料//大三上//软工//coursefile//swdfile//土豆.jpg");
    imageimpl.ImagePath("D://MY//学习资料//大三上//软工//coursefile//swdfile//鳕鱼.jpg");
```
## 3.3 图像识别Imageimpl接口返回值说明
- 演示使用的图片就不放了，在文件夹里一起传了
- 四个方法演示结果如下,前三种是返回可能性最大的前三种，第四种是返回可能性最大的一种，识别不了就返回识别失败（当然可以通过修改函数中        options.put("top_num", "3");
  options.put("baike_num", "5");来修改输出几种 ）
- AnimalImagePath
```
{
  "result": [
    {
      "score": "0.995692",
      "name": "鳕鱼",
      "baike_info": {}
    },
    {
      "score": "0.00183174",
      "name": "大西洋鳕鱼",
      "baike_info": {}
    },
    {
      "score": "0.000445281",
      "name": "真鳕",
      "baike_info": {}
    }
  ],
  "log_id": 1747625592170208060
}
```
- VegetableImagePath
```
{
  "result": [
    {
      "score": 0.8934374,
      "name": "土豆"
    },
    {
      "score": 0.06336202,
      "name": "口蘑"
    },
    {
      "score": 0.040553667,
      "name": "马铃薯"
    }
  ],
  "log_id": 1747626033867053989,
  "result_num": 3
}

进程已结束,退出代码0

```
- PlantImagePath
```
{
  "result": [
    {
      "score": 0.8756019,
      "name": "南酸枣",
      "baike_info": {}
    },
    {
      "score": 0.030341974,
      "name": "黄皮",
      "baike_info": {}
    },
    {
      "score": 0.02529082,
      "name": "枇杷",
      "baike_info": {}
    }
  ],
  "log_id": 1747626721778554210
}
```
- ImagePath
```
//正常可识别的内容
{"0.995692": "鳕鱼"}
//无关内容
{"0.0": "识别失败"}
```
