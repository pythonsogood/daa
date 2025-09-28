# Architecture notes, recurrence analyses

| Algorithm | Depth | Allocation | Recurrence analysis | Runtime |
| --- | --- | --- | --- | --- |
| MergeSort | `O(log n)` | `L`, `R` temporary arrays | `T(n) = 2T(n/2) + Θ(n)`. Master Theorem (case 2): `T(n) = Θ(n log n)` | `Θ(n log n)` |
| QuickSort | `O(n)` | in-place | Worst: `T(n) = Θ(n^2)`. Average: `T(n) = Θ(n log n)` | `Θ(n log n)` |
| Deterministic Select | `O(log n)` | in-place | `T(n) <= T(n/5) + T(7n/10) + Θ(n)`. Akra-Bazzi: `T(n) = Θ(n)` | `Θ(n)` |
| Closest Pair (2D) | `Θ(log n)` | `points2` array once | `T(n) = 2T(n/2) + Θ(n)`. Master Theorem (case 2): `Θ(n log n)` | `Θ(n log n)` |

# Plots

![time vs n](/assignment1/plot/time_vs_n.png)

![depth vs n](/assignment1/plot/depth_vs_n.png)
