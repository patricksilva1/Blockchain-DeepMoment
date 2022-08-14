import java.lang.System.Logger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Deep {

    private static final Logger LOGGER = LoggerFactory.getLogger(Deep.class);

    private static List<Block> blockChain = new ArrayList<Block>();

    private static Block unpackBlock = new Block();

    public static Map<String, TransactionOutput> UTXOs = new HashMap<String, TransactionOutput>();

    public static final int difficulty = 3;

    public static final float minimumTransaction = 0.1f;

    public static Wallet walletA;

    public static Wallet walletB;

    public static Transaction genesisTransaction;

    private static final String version = "1";

}
