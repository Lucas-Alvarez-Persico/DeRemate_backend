package com.deremate.demo.service;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.deremate.demo.DTO.QRDeliveryDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Files;

@Service
public class QrService {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public byte[] generarQrDesdeDto(QRDeliveryDTO dto) throws WriterException, IOException {
        String json = objectMapper.writeValueAsString(dto);

        QRCodeWriter writer = new QRCodeWriter();
        BitMatrix bitMatrix = writer.encode(json, BarcodeFormat.QR_CODE, 300, 300);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream);
        return outputStream.toByteArray();
    }

    public void guardarQrEnDisco(QRDeliveryDTO dto, Path destino) throws Exception {
        byte[] imagen = generarQrDesdeDto(dto);
        Files.write(destino, imagen);
    }
}
