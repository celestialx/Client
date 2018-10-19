package galkon;

import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.IOException;

/**
 * "The real danger is not that computers will begin to think like men, but that men will begin to think like computers." – Sydney Harris
 * Created on 11/4/2015
 *
 * @author Galkon
 */
public final class SpriteBean {

    public SpriteBean() {
        name = "name";
        id = -1;
        drawOffsetX = 0;
        drawOffsetY = 0;
        data = null;
        spriteImage = null;
    }

    /**
     * Read the values from the sprite buffer.
     * @param index
     * @param data
     * @throws IOException
     */
    void readValues(DataInputStream index, DataInputStream data) throws IOException {
        do {
            int opCode = data.readByte();
            if (opCode == 0) {
                break;
            }
            if (opCode == 1) {
                id = data.readShort();
            } else if (opCode == 2) {
                name = data.readUTF();
            } else if (opCode == 3) {
                drawOffsetX = data.readShort();
            } else if (opCode == 4) {
                drawOffsetY = data.readShort();
            } else if (opCode == 5) {
                int indexLength = index.readInt();
                byte[] dataread = new byte[indexLength];
                data.readFully(dataread);
                this.data = dataread;
            }
        } while (true);
    }

    public String name;
    public int id;
    public int drawOffsetX;
    public int drawOffsetY;
    public byte[] data;
    public BufferedImage spriteImage;

}