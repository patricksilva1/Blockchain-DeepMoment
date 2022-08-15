import java.lang.System.Logger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DeepMoment {

    private static final Logger LOGGER = LoggerFactory.getLogger(DeepMoment.class);

    private static List<Block> blockChain = new ArrayList<Block>();

    private static Block unpackBlock = new Block();

    public static Map<String, TransactionOutput> UTXOs = new HashMap<String, TransactionOutput>();

    public static final int difficulty = 3;

    public static final float minimumTransaction = 0.1f;

    public static Wallet walletA;

    public static Wallet walletB;

    public static Transaction genesisTransaction;

    private static final String version = "1";

    public static void main(String[] args) throws IOException, InterruptedException {
        final Gson gson = new GsonBuilder().create();
        final Gson prettyGson = new GsonBuilder().setPrettyPrinting().create();
        int port = 8015;

        LOGGER.info("Starting Peer Network...");
        PeerNetwork PeerNetwork = new PeerNetwork(port);
        PeerNetwork.start();
        LOGGER.info("[ Node is Started in port: " + port + " ]");

        // to do: Start rpc here;
        LOGGER.info("Starting RPC daemon...");
        RpcServer rpcAgent = new RpcServer(port + 1);
        rpcAgent.start();
        LOGGER.info("[ RPC agent is Started in port: " + (port + 1) + " ]");

        // Peer list;
        ArrayList<String> peers = new ArrayList<>();
        File peerFile = new File("peers.list");

    }

}
