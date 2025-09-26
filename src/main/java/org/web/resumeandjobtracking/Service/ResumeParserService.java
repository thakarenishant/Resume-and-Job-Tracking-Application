package org.web.resumeandjobtracking.Service;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ResumeParserService {
    public String parsePDF(String filePath) throws IOException {
        try (PDDocument document = PDDocument.load(new FileInputStream(filePath))) {
            PDFTextStripper pdfStripper = new PDFTextStripper();
            return pdfStripper.getText(document);
        }
    }

    // Parse DOCX file and return extracted text
    public String parseDOCX(String filePath) throws IOException {
        try (XWPFDocument doc = new XWPFDocument(new FileInputStream(filePath))) {
            List<XWPFParagraph> paragraphs = doc.getParagraphs();
            return paragraphs.stream()
                    .map(XWPFParagraph::getText)
                    .collect(Collectors.joining("\n"));
        }
    }

    // Detect file type and parse accordingly
    public String parseResume(String filePath, String fileType) throws IOException {
        if (fileType == null) throw new IllegalArgumentException("File type is null");

        fileType = fileType.toLowerCase();
        if (fileType.contains("pdf")) {
            return parsePDF(filePath);
        } else if (fileType.contains("docx")) {
            return parseDOCX(filePath);
        } else {
            throw new IllegalArgumentException("Unsupported file type: " + fileType +" . Supported file types are PDF and DOCX");
        }
    }
}
