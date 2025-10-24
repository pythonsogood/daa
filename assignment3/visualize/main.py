import os
from typing import Literal

import matplotlib.pyplot
import networkx
import orjson

mst_algorithm: Literal["prim", "kruskal"] = "prim"
base_dataset_path = os.path.join("..", "dataset")

dataset = "small"
graph_id = 2

nodes = ()
edges = ()
mst_edges = ()

with open(os.path.join(base_dataset_path, f"input-{dataset}.json"), "rb") as f:
	for graph in orjson.loads(f.read()).get("graphs", ()):
		if graph["id"] == graph_id:
			nodes = graph.get("nodes", ())
			edges = tuple((edge["from"], edge["to"], edge.get("weight", 0)) for edge in graph.get("edges", ()))

with open(os.path.join(base_dataset_path, f"output-{dataset}.json"), "rb") as f:
	for graph in orjson.loads(f.read()).get("results", ()):
		if graph["graph_id"] == graph_id:
			mst_edges = tuple((edge["from"], edge["to"]) for edge in graph[mst_algorithm]["mst_edges"])

G = networkx.Graph()
G.add_nodes_from(nodes)
G.add_weighted_edges_from(edges)

mst = networkx.Graph()
mst.add_edges_from(mst_edges)

pos = networkx.spring_layout(G)

networkx.draw_networkx_edges(G, pos, edge_color="gray", alpha=0.5)
networkx.draw_networkx_edges(mst, pos, edge_color="green", width=3)

networkx.draw_networkx_nodes(G, pos, node_color="skyblue", node_size=1000, edgecolors="black")
networkx.draw_networkx_labels(G, pos, font_size=12, font_weight="bold")

edge_labels = networkx.get_edge_attributes(G, "weight")
networkx.draw_networkx_edge_labels(G, pos, edge_labels=edge_labels, font_color="red")

matplotlib.pyplot.title("Minimum Spanning Tree (green edges)")
matplotlib.pyplot.axis("off")
matplotlib.pyplot.show()
