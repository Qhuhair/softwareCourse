import org.json.JSONException;
import recgnizition.impl.Imageimpl;
import recgnizition.impl.OCRimpl;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws JSONException, IOException {
//        OCRimpl ocrimpl=new OCRimpl();
//        ocrimpl.basicAccurateOCRPath("D://MY//学习资料//大三上//软工//coursefile//swdfile//订单表尝试2.jpg");
        Imageimpl imageimpl=new Imageimpl();
//        imageimpl.AnimalImagePath("D://MY//学习资料//大三上//软工//coursefile//swdfile//鳕鱼.jpg");
//        imageimpl.VegetableImagePath("D://MY//学习资料//大三上//软工//coursefile//swdfile//土豆.jpg");
        imageimpl.PlantImagePath("D://MY//学习资料//大三上//软工//coursefile//swdfile//土豆.jpg");
    }
}