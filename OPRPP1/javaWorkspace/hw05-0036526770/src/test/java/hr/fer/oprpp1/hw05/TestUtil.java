package hr.fer.oprpp1.hw05;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class TestUtil {
    @Test
    public void testHexToByte(){
        var a = Util.hextobyte("01aE22");
        byte[] b = {1,-82,34};
        for(int i = 0; i<3;i++){
            assertEquals(b[i], a[i],"Wrong byte output");
        }
    }

    @Test
    public void testByteToHex(){
        assertEquals("01ae22", Util.bytetohex(new byte[] {1,-82,34}),"Wrong string output");
    }
}
