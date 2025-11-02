# Assignment 4. Smart City / Smart Campus Scheduling

[![License: MIT](https://img.shields.io/badge/License-MIT-green.svg)](https://opensource.org/license/mit/)

## Summary

* SCC detection: `Kosaraju.scc(Graph)` -> `List<List<Integer>>`.
* Condensation graph: `CondensationGraph` -> maps every original vertex to its SCC, and builds a DAG.
* Topological order: `DFSTopo.sort(Graph)` and `DFSTopo.sort(CondensationGraph, decompress)` - DFS-based topological sort.
* DAG shortest/longest: `DAGPath.shortestPath(Graph, source)` and `DAGPath.longestPath(Graph, source)`.
* Instrumentation: `Metrics` interface, `SimpleMetrics`, `TimedMetrics` implementations.
* Dataset generator: `GraphDatasetGenerator` produces graphs with varied densities and cyclic/acyclic structure; output saved to `/data/*.json`.

## Design choices

* Weight model: edge weights.
* SCC algorithm: Kosaraju.
* Topological sort: DFS.
* Unreachable distances: `-1`.

## Algorithms & key implementation notes

### SCC (Kosaraju)

* Two-pass DFS:
	1. DFS on original graph, push nodes by finish time.
	2. DFS on transposed graph, pop stack to find SCCs.
* Complexity: `O(V + E)`.
* Instrumentation: counts `dfs`, `push`, `pop`, timing.

### Topological sort (DFS-based)

* Uses DFS, pushes nodes on a stack after exploring outgoing edges.
* Pop stack to obtain topo order.
* Complexity: `O(V + E)`.
* Instrumentation: `dfs`, `push`, `pop`, timing.

### DAG shortest/longest

* Process nodes in topological order.
* For each edge apply relaxation:
	* Shortest: `if dist[v] == -1 || dist[v] > dist[u] + w ...`
	* Longest: `if dist[v] == -1 || dist[v] < dist[u] + w ...`
* Complexity: `O(V + E)`.
* Instrumentation: `relaxation`, timing.

## Datasets generation

* `GraphDatasetGenerator` produces datasets:
	* `small_1.json`, `small_2.json`, `small_3.json` (`n` in 6–10)
	* `medium_1.json`, `medium_2.json`, `medium_3.json` (`n` in 10–20)
	* `large_1.json`, `large_2.json`, `large_3.json` (`n` in 20–50)

## Analysis and observations

### Result table

| Dataset       | n  | Edges | DAG source | SCCs | SCC time (ns) | Topo time (ns) | SP time (ns) | LP time (ns) | Total time (ns) |
| ------------- | -- | ----- | ---------- | ---- | ------------- | -------------- | ------------ | ------------ | --------------- |
| `small_1.json`  | 6  | 12    | 4          | 2    | 26900         | 3200           | 3900         | 2500         | 36500           |
| `small_2.json`  | 7  | 10    | 6          | 7    | 38000         | 6400           | 38200        | 7100         | 89700           |
| `small_3.json`  | 6  | 11    | 4          | 6    | 72500         | 12500          | 8000         | 7100         | 100100          |
| `medium_1.json` | 12 | 26    | 1          | 3    | 48000         | 4800           | 6300         | 3700         | 62800           |
| `medium_2.json` | 13 | 62    | 6          | 2    | 37700         | 3800           | 4600         | 2800         | 48900           |
| `medium_3.json` | 19 | 263   | 3          | 1    | 116200        | 2700           | 3300         | 2100         | 124300          |
| `large_1.json`  | 39 | 754   | 3          | 1    | 1635500       | 26900          | 133000       | 22100        | 1817500         |
| `large_2.json`  | 31 | 494   | 4          | 1    | 219600        | 4800           | 5300         | 3000         | 232700          |
| `large_3.json`  | 44 | 447   | 11         | 44   | 239100        | 89000          | 325500       | 128100       | 781700          |

### Time complexity graph

![time_vs_ve](/assignment4/graph/time_vs_ve.png)

### Observations

* SCC vs Graph size: SCC detection dominates runtime on large dense graphs (`O(V+E)`). Time grows almost linearly with the number of edges.
* Topological sort: Very fast compared to SCC and SP, confirming expected `O(V+E)` cost with small constants.
* Shortest/Longest paths: Efficient due to DAG property; overhead mainly from relaxation steps.
* Density impact: Sparse graphs finish quickly; dense graphs increase SCC time sharply.

## Conclusions

* Kosaraju’s SCC is suitable for small to medium graphs - simple and reliable.
* Topological sort is best for acyclic scheduling; use it after SCC condensation.
* DAG shortest path is efficient for dependency resolution when weights represent edge costs.
* DAG longest path reveals critical paths - useful for identifying bottlenecks or delays.
* In practice:
	* Use SCC + condensation to simplify cyclic dependencies.
	* Then apply topological + shortest/longest path for optimal scheduling.
