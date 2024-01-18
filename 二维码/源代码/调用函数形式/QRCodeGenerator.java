import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class QRCodeGenerator {
    public static void main(String[] args) {
        // 定义要生成的二维码内容
        String url = "软件工程";

        try {
            // 调用生成二维码的函数
            generateQRCode(url, "D:/qrcode.png");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 生成二维码的函数
    private static void generateQRCode(String content, String outputPath) throws WriterException, IOException {
        // 需要创建一个 Map 集合，用这个 Map 集合存储二维码相关的属性（参数）
        Map<EncodeHintType, Object> map = new HashMap<>();
        // 设置二维码的误差校正级别
        map.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        // 设置二维码的字符集
        map.put(EncodeHintType.CHARACTER_SET, "utf-8");
        // 设置二维码四周的留白
        map.put(EncodeHintType.MARGIN, 1);
        // 创建 zxing 的核心对象：MultiFormatWriter（多格式写入器）
        // 通过 MultiFormatWriter 对象来生成二维码的
        MultiFormatWriter writer = new MultiFormatWriter();
        // writer.encode(内容, 什么格式的二维码, 二维码宽度, 二维码高度, 二维码参数)
        // 位矩阵对象（位矩阵对象内部实际上是一个二维数组，二维数组中每一个元素是 boolean 类型：true 代表黑色，false 代表白色）
        BitMatrix bitMatrix = writer.encode(content, BarcodeFormat.QR_CODE, 300, 300, map);
        // 获取矩阵的宽度
        int width = bitMatrix.getWidth();
        // 获取矩阵的高度
        int height = bitMatrix.getHeight();

        // 生成二维码图片
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        // 编写一个嵌套的循环：遍历二维数组的一个循环。遍历位矩阵对象
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
            }
        }

        // 保存生成的二维码图片为 PNG 格式
        File outputFile = new File(outputPath);
        ImageIO.write(image, "png", outputFile);
    }
}
