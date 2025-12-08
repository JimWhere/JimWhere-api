package com.jimwhere.api.global.model;


import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

@Component
public class QRCodeGenerator {

    public byte[] generateQRCode(String text) {
        try {
            int width = 250;
            int height = 250;

            BitMatrix bitMatrix = new MultiFormatWriter().encode(
                    text,
                    BarcodeFormat.QR_CODE,
                    width,
                    height
            );

            BufferedImage qrImage = MatrixToImageWriter.toBufferedImage(bitMatrix);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(qrImage, "PNG", baos);

            return baos.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException("QR 생성 실패", e);
        }
    }
}

