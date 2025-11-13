# This `README.md` file is the task's report.

# Bonus Task. String Algorithm. Knuth–Morris–Pratt (KMP)

[![License: MIT](https://img.shields.io/badge/License-MIT-green.svg)](https://opensource.org/license/mit/)

## Algorithm summary

* The LPS array stores the length of the longest prefix suffix of the substring `pattern[0..i]`.
* KMP searches for all occurrences of `pattern` in `text` using the precomputed LPS array.

## Complexity Analysis

| Operation | Time complexity | Space complexity |
| --- | --- | --- |
| LPS | `O(m)` | `O(m)` |
| KMP | `O(n)` | `O(1)` |
| Total | `O(n + m)` | `O(m)` |

* `n` - length of text
* `m` - length of pattern

## Benchmark

### Setup

* The `Main` class reads input datasets (`small`, `medium`, `long`) from JSON files.
* JIT warm-up is performed by running random searches 1000 times before benchmarking.

## Time complexity result

|Dataset|Text length|Pattern length|Time (ns)|
|-------|---|---|------|
|small  |10 |5  |5900  |
|medium |16 |4  |899   |
|long   |55 |8  |1000  |

![time_vs_nm](/stringalgo/graph/time_vs_nm.png)

**Observations:**

* Times are in nanoseconds: small input sizes are dominated by timing noise and JVM overhead.
* Execution time scales roughly linearly with the combined length of text and pattern, confirming the theoretical `O(n + m)` complexity.

## Conclusion

* The implementation of KMP correctly finds all pattern occurrences in text, including overlapping patterns.
* Benchmarking confirms linear runtime with respect to text and pattern length.
* The algorithm is suitable for large-scale text search due to its predictable `O(n + m)` behavior.