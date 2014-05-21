package zx.soft.sent.es.example;

import static org.elasticsearch.node.NodeBuilder.nodeBuilder;

import org.elasticsearch.client.Client;
import org.elasticsearch.node.Node;

public class NodeClient {

	public static void main(String[] args) {

		// on startup
		Node node = nodeBuilder().node();
		Client client = node.client();

		// on shutdown
		node.close();
		client.close();

	}

}
