package GUI;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.util.stream.Stream;

public class MyDocumentFilter extends DocumentFilter {

    public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
        if (Stream.of("1","2","3","4","5","6","7","8","9","0").anyMatch(text::contains))
            super.replace(fb, offset, length, text, attrs);
    }
}