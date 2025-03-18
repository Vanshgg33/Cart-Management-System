package com.spring.QRCODE;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import javax.imageio.ImageIO;

import com.spring.repo.ItemReposiotry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.spring.model.Item;


@Controller
@RequestMapping("/checkout")
public class QRController {

    @Autowired
    private ItemReposiotry itemRepository;  // Directly using repository

@GetMapping
    public ModelAndView generateQR() {
    System.out.println("Entering generateQR()");
        List<Item> cartlist = itemRepository.findAll();  // Fetch data directly
        String qrText = generateQRText(cartlist);
        String filePath = generateQRCodeFile(qrText);  // Save QR code to file
        String qrBase64 = generateQRCodeBase64(qrText);  // Convert to Base64

        ModelAndView mv = new ModelAndView("checkoutPage");
        mv.addObject("cartlist", cartlist);
        mv.addObject("qrCodePath", filePath);
        mv.addObject("qrCode", qrBase64);
        return mv;
    }

    private String generateQRText(List<Item> cartlist) {
        StringBuilder sb = new StringBuilder();
        for (Item item : cartlist) {
            sb.append(item.getId()).append(" - ")
                    .append(item.getProductName()).append(" - ₹")
                    .append(item.getPrice()).append("\n");
        }

        return sb.toString();
    }

    private String generateQRCodeFile(String qrText) {
        try {
            HashMap<EncodeHintType, Object> hints = new HashMap<>();
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");

            MultiFormatWriter writer = new MultiFormatWriter();
            BitMatrix bitMatrix = writer.encode(qrText, BarcodeFormat.QR_CODE, 200, 200, hints);

            String filePath = "C:/Users/jaisw/OneDrive/Desktop/HTML/qrcode.png";
            Path path = Paths.get(filePath);
            MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
            return filePath;
        } catch (WriterException | java.io.IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String generateQRCodeBase64(String qrText) {
        System.out.println("here too");
        try {
            HashMap<EncodeHintType, Object> hints = new HashMap<>();
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");

            MultiFormatWriter writer = new MultiFormatWriter();
            BitMatrix bitMatrix = writer.encode(qrText, BarcodeFormat.QR_CODE, 200, 200, hints);
            BufferedImage qrImage = MatrixToImageWriter.toBufferedImage(bitMatrix);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(qrImage, "png", baos);
            return Base64.getEncoder().encodeToString(baos.toByteArray());
        } catch (WriterException | java.io.IOException e) {
            e.printStackTrace();
            return null;
        }

    }
}
