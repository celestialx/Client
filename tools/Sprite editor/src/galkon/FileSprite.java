package galkon;

/**
 * "The real danger is not that computers will begin to think like men, but that men will begin to think like computers." – Sydney Harris
 * Created on 11/4/2015
 *
 * @author Galkon
 */
public final class FileSprite {

    public byte[] getData() {
        return data;
    }

    public int getId() {
        return id;
    }

    private final int id;
    private final byte[] data;

    public FileSprite(int id, byte[] data) {
        this.id = id;
        this.data = data;
    }

}