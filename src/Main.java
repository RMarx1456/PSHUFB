
public class Main {
    /*
    Author: Raymond Marx
    Date: 03/23/2024
    Purpose: Teaching myself how to write the logic behind Assembly instructions by hand as well as create new ones.
    PSHUFB: https://www.felixcloutier.com/x86/pshufb
     */
    public static void main(String[] args) {
        int DEST = 0x00000000;
        int SRC1 = 0xDEADBEEF;
        int SRC2 = 0x02040103;
        DEST = VPSHUFB32(DEST, SRC2, SRC1);
        System.out.println("0x"+Integer.toHexString(DEST).toUpperCase());
        DEST = 0xFFFFFFFF;
        int SRC = 0x80008000;
        DEST = PSHUFB32(DEST, SRC);
        System.out.println("0x00"+Integer.toHexString(DEST).toUpperCase());
    }
    static int PSHUFB32(int DEST, int SRC) {
        /*
        Author: Raymond Marx
        Date: 03/23/2024
        Purpose: Sets bytes in DEST to 0 depending on if the MSB of the corresponding byte in SRC is one or not.
         */
        int TEMP = DEST;
        for(byte i = 0; i < 4; i++) {
            if((SRC & (1 << (i << 3)+7)) == (1 << (i << 3)+7)) {
                DEST = DEST & ~(0xFF << (i<<3));
            }
            else {
                byte index = (byte) (SRC & ~(0b00000111 << (i << 3)));
                DEST = (~(0xFF << (i << 3)) & DEST) | (TEMP & (0xFF << (i << 3)));
            }
        }
        return DEST;
    }
    static int VPSHUFB32(int DEST, int SRC2, int SRC1) {
        /*
        Author: Raymond Marx
        Date: 03/23/2024
        Purpose: Shuffles four bytes inside a 32-bit integer according to an index mask like the x86 instruction but with a 32-bit operand instead.
        Parameters: int DEST: destination variable, int SRC2: source of indices, int SRC1: source of elements.
        Feel free to inline this; it's short.
         */
        for(byte i = 0; i < 4; i++) {
            if((SRC2 & (1 << ((i<<3)+7))) == (1 << (i<<3)+7)) {
                DEST = DEST & ~(0xFF << (i<<3));
            }
            else {
                byte index = (byte) ((SRC2 >> (i<<3)) & 0x0F);
                DEST = DEST | ((SRC1 >> (i<<3) & 0xFF)<<(index<<3));
            }
        }
        return DEST;
    }
}

